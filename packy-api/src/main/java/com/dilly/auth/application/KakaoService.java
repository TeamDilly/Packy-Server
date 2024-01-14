package com.dilly.auth.application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import com.dilly.auth.KakaoAccount;
import com.dilly.auth.domain.KakaoAccountReader;
import com.dilly.auth.model.KakaoResource;
import com.dilly.global.exception.InternalServerException;
import com.dilly.global.response.ErrorCode;
import com.dilly.member.Member;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KakaoService {

	private final KakaoAccountReader kakaoAccountReader;

	@Value("${security.oauth2.provider.kakao.token-uri}")
	private String KAKAO_TOKEN_URI;
	@Value("${security.oauth2.provider.kakao.user-info-uri}")
	private String KAKAO_USER_INFO_URI;
	@Value("${security.oauth2.provider.kakao.unlink-uri}")
	private String KAKAO_UNLINK_URI;
	@Value("${security.oauth2.provider.kakao.client-id}")
	private String KAKAO_CLIENT_ID;
	@Value("${security.oauth2.provider.kakao.admin-key}")
	private String KAKAO_ADMIN_KEY;
	private String BEARER_PREFIX = "Bearer ";

	public KakaoResource getKaKaoAccount(String kakaoAccessToken) {
		WebClient webClient = WebClient.builder()
			.baseUrl(KAKAO_USER_INFO_URI)
			.defaultHeader(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + kakaoAccessToken)
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
			URL url = new URL(KakaoService.this.KAKAO_TOKEN_URI);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=" + KAKAO_CLIENT_ID);
			sb.append("&redirect_uri=http://127.0.0.1:9000/kakaocallback");
			sb.append("&code=" + code);
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

	public void unlinkKakaoAccount(Member member) {
		KakaoAccount kakaoAccount = kakaoAccountReader.findByMember(member);

		WebClient webClient = WebClient.builder()
			.baseUrl(KAKAO_UNLINK_URI)
			.defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK " + KAKAO_ADMIN_KEY)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
			.build();

		MultiValueMap<String, String> bodyData = new LinkedMultiValueMap<>();
		bodyData.add("target_id_type", "user_id");
		bodyData.add("target_id", kakaoAccount.getId());

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
