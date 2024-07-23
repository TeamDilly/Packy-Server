package com.dilly.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dilly.exception.internalserver.InternalServerException;
import com.dilly.model.BranchUrl;
import global.config.ServiceTestSupport;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class BranchServiceTest extends ServiceTestSupport {

    @Autowired
    private BranchService branchService;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String branchApiUrl = mockWebServer.url("/").toString();
        String branchApiKey = "test";
        branchService = new BranchService(branchApiKey, branchApiUrl);
    }

    @AfterEach
    void terminate() throws IOException {
        mockWebServer.shutdown();
    }

    @DisplayName("선물박스 ID를 받아 branch URL을 생성한다.")
    @Test
    void createBranchUrl() throws Exception {
        // given
        Long boxId = 1L;
        BranchUrl branchUrl = BranchUrl.builder()
            .url("www.test.com")
            .build();

        mockWebServer.enqueue(
            new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody(objectMapper.writeValueAsString(branchUrl))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        );

        // when
        String result = branchService.createBranchUrl(boxId);

        // then
        assertThat(result).isEqualTo(branchUrl.url());
    }

    @DisplayName("branch API 호출에 실패하면 예외가 발생한다.")
    @Test
    void createBranchUrlThrowsException() {
        // given
        Long boxId = 1L;
        String errorResponse = "{\"error\":{\"code\":400,\"message\":\"Invalid JSON\"}}";

        mockWebServer.enqueue(
            new MockResponse()
                .setResponseCode(HttpStatus.BAD_REQUEST.value())
                .setBody(errorResponse)
        );

        // when // then
        assertThatThrownBy(() -> branchService.createBranchUrl(boxId))
            .isInstanceOf(InternalServerException.class);
    }
}
