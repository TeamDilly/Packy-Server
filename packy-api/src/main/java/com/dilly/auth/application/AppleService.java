package com.dilly.auth.application;

import com.dilly.auth.domain.AppleAccount;
import com.dilly.auth.model.AppleAccountInfo;
import com.dilly.auth.model.ApplePublicKey;
import com.dilly.auth.model.ApplePublicKey.Key;
import com.dilly.auth.model.AppleToken;
import com.dilly.exception.ErrorCode;
import com.dilly.exception.internalserver.AppleServerException;
import com.dilly.exception.internalserver.InternalServerException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppleService {

	@Value("${security.oauth2.provider.apple.audience-uri}")
	private String appleAudienceUri;
	@Value("${security.oauth2.provider.apple.token-uri}")
	private String appleTokenUri;
	@Value("${security.oauth2.provider.apple.key-uri}")
	private String appleKeyUri;
	@Value("${security.oauth2.provider.apple.revoke-uri}")
	private String appleRevokeUri;
	@Value("${security.oauth2.provider.apple.team-id}")
	private String appleTeamId;
	@Value("${security.oauth2.provider.apple.client-id}")
	private String appleClientId;
	@Value("${security.oauth2.provider.apple.key-id}")
	private String appleKeyId;
	@Value("${security.oauth2.provider.apple.private-key}")
	private String applePrivateKey;

	public AppleToken getAppleToken(String providerAccessToken) {
		try {
			WebClient webClient = WebClient.builder()
				.baseUrl(appleTokenUri)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.build();

			MultiValueMap<String, String> bodyData = new LinkedMultiValueMap<>();
			bodyData.add("client_id", appleClientId);
			bodyData.add("client_secret", getAppleClientSecret());
			bodyData.add("code", providerAccessToken);
			bodyData.add("grant_type", "authorization_code");

			return webClient.post()
				.body(BodyInserters.fromFormData(bodyData))
				.retrieve()
				.bodyToMono(AppleToken.class)
				.block();
		} catch (Exception e) {
			throw new AppleServerException(ErrorCode.APPLE_FAILED_TO_GET_TOKEN);
		}
	}

	public AppleAccountInfo getAppleAccountInfo(String idToken) {
		ApplePublicKey applePublicKey;
		try {
			WebClient webClient = WebClient.builder()
				.baseUrl(appleKeyUri)
				.build();

			applePublicKey = webClient.get()
				.retrieve()
				.bodyToMono(ApplePublicKey.class)
				.block();
		} catch (WebClientException e) {
			throw new InternalServerException(ErrorCode.APPLE_FAILED_TO_GET_PUBLIC_KEY);
		}

		try {
			String headerOfIdToken = idToken.substring(0, idToken.indexOf("."));

			Map<String, String> header = new ObjectMapper().readValue(
				new String(Base64.getUrlDecoder().decode(headerOfIdToken), StandardCharsets.UTF_8),
				Map.class
			);
			Key key = applePublicKey.getMatchedKeyBy(header.get("kid"), header.get("alg"))
				.orElseThrow(NullPointerException::new);

			byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
			byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

			BigInteger n = new BigInteger(1, nBytes);
			BigInteger e = new BigInteger(1, eBytes);

			RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
			KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

			Claims memberInfo = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(idToken).getBody();

			Map<String, Object> expectedMap = new HashMap<>(memberInfo);

			return new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.convertValue(expectedMap, AppleAccountInfo.class);
		} catch (Exception e) {
			throw new InternalServerException(ErrorCode.APPLE_FAILED_TO_GET_INFO);
		}
	}

	private String getAppleClientSecret() {
		Date expirationDate = Date.from(
			LocalDateTime.now().plusDays(180).atZone(ZoneId.systemDefault()).toInstant()
		);

		try {
			return Jwts.builder()
				.setHeaderParam("kid", appleKeyId)
				.setHeaderParam("alg", "ES256")
				.setIssuer(appleTeamId)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(expirationDate)
				.setAudience(appleAudienceUri)
				.setSubject(appleClientId)
				.signWith(getApplePrivateKey(), SignatureAlgorithm.ES256)
				.compact();
		} catch (Exception e) {
			throw new InternalServerException(ErrorCode.APPLE_FAILED_TO_GET_CLIENT_SECRET);
		}
	}

	private PrivateKey getApplePrivateKey() {
		try {
			ClassPathResource resource = new ClassPathResource(applePrivateKey);
			String privateKey = new String(resource.getInputStream().readAllBytes());
			Reader pemReader = new StringReader(privateKey);
			PEMParser pemParser = new PEMParser(pemReader);
			JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
			PrivateKeyInfo object = (PrivateKeyInfo)pemParser.readObject();

			return converter.getPrivateKey(object);
		} catch (Exception e) {
			throw new InternalServerException(ErrorCode.APPLE_FAILED_TO_GET_PUBLIC_KEY);
		}
	}

	public void revokeAppleAccount(AppleAccount appleAccount) {
		try {
			String appleRefreshToken = appleAccount.getRefreshToken();

			WebClient webClient = WebClient.builder()
				.baseUrl(appleRevokeUri)
				.build();

			MultiValueMap<String, String> bodyData = new LinkedMultiValueMap<>();
			bodyData.add("client_id", appleClientId);
			bodyData.add("client_secret", getAppleClientSecret());
			bodyData.add("token", appleRefreshToken);
			bodyData.add("token_type_hint", "refresh_token");

			webClient.post()
				.body(BodyInserters.fromFormData(bodyData))
				.retrieve()
				.bodyToMono(Void.class)
				.block();

		} catch (Exception e) {
			throw new InternalServerException(ErrorCode.APPLE_FAILED_TO_REVOKE_ACCOUNT);
		}
	}
}
