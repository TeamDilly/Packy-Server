package com.dilly.application;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.internalserver.InternalServerException;
import com.dilly.model.BranchUrl;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
public class BranchService {

    @Value("${branch.api.key}")
    private String branchApiKey;

    @Value("${branch.api.url}")
    private String branchApiUrl;

    public String createBranchUrl(Long boxId) {
        WebClient webClient = WebClient.builder()
            .baseUrl(branchApiUrl)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

        Map<String, Object> bodyData = new HashMap<>();

        bodyData.put("branch_key", branchApiKey);

        Map<String, String> data = new HashMap<>();
        data.put("boxId", boxId.toString());
        bodyData.put("data", data);

        try {
            BranchUrl branchUrl = webClient.post()
                .bodyValue(bodyData)
                .retrieve()
                .bodyToMono(BranchUrl.class)
                .block();

            if (branchUrl == null) {
                throw new InternalServerException(ErrorCode.BRANCH_SERVER_ERROR);
            }

            return branchUrl.url();
        } catch (WebClientException e) {
            throw new InternalServerException(ErrorCode.BRANCH_SERVER_ERROR);
        }
    }
}
