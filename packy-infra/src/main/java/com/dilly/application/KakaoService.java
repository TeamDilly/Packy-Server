package com.dilly.application;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.internalserver.InternalServerException;
import com.dilly.model.KakaoResource;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class KakaoService {

	@Value("${security.oauth2.provider.kakao.token-uri}")
	private String kakaoTokenUri;
	@Value("${security.oauth2.provider.kakao.user-info-uri}")
	private String kakaoUserInfoUri;
	@Value("${security.oauth2.provider.kakao.unlink-uri}")
	private String kakaoUnlinkUri;
	@Value("${security.oauth2.provider.kakao.client-id}")
	private String kakaoClientId;
	@Value("${security.oauth2.provider.kakao.admin-key}")
	private String kakaoAdminKey;

	public KakaoResource getKaKaoAccount(String kakaoAccessToken) {
		String bearerPrefix = "Bearer ";
		WebClient webClient = WebClient.builder()
			.baseUrl(kakaoUserInfoUri)
			.defaultHeader(HttpHeaders.AUTHORIZATION, bearerPrefix + kakaoAccessToken)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
			.build();

		try {
			return webClient.get()
				.retrieve()
				.bodyToMono(KakaoResource.class)
				.block();
		} catch (WebClientException e) {
			throw new InternalServerException(ErrorCode.KAKAO_SERVER_ERROR);
		}
	}

	public String getKakaoAccessToken(String code) {
		String access_Token = "";

		try {
			URL url = new URL(KakaoService.this.kakaoTokenUri);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code")
				.append("&client_id=")
				.append(kakaoClientId)
				.append("&redirect_uri=http://127.0.0.1:9000/kakaocallback")
				.append("&code=" + code);
			bw.write(sb.toString());
			bw.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

			access_Token = ((JsonObject)JsonParser.parseString(result)).get("access_token").getAsString();
			br.close();
			bw.close();
		} catch (IOException e) {
			log.error("IOException", e);
		}

		return access_Token;
	}

    public void unlinkKakaoAccount(String kakaoAccountId) {
		WebClient webClient = WebClient.builder()
			.baseUrl(kakaoUnlinkUri)
			.defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoAdminKey)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
			.build();

		MultiValueMap<String, String> bodyData = new LinkedMultiValueMap<>();
		bodyData.add("target_id_type", "user_id");
        bodyData.add("target_id", kakaoAccountId);

		try {
			webClient.post()
				.body(BodyInserters.fromFormData(bodyData))
				.retrieve()
				.bodyToMono(String.class)
				.block();
		} catch (WebClientException e) {
			throw new InternalServerException(ErrorCode.KAKAO_SERVER_ERROR);
		}
	}
}
