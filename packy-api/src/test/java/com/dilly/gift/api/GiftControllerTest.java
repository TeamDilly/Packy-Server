package com.dilly.gift.api;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dilly.gift.dto.response.PhotoResponseDto.PhotoWithoutSequenceResponse;
import com.dilly.global.ControllerTestSupport;
import com.dilly.global.WithCustomMockUser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;

class GiftControllerTest extends ControllerTestSupport {

    @DisplayName("받은 선물박스에 있는 사진을 모아본다.")
    @Test
    @WithCustomMockUser
    void getPhotos() throws Exception {
        // given
        List<PhotoWithoutSequenceResponse> content = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            content.add(PhotoWithoutSequenceResponse.builder()
                .photoUrl("www.example.com")
                .description("test"+i)
                .build());
        }
        PageRequest pageable = PageRequest.ofSize(6);

        SliceImpl<PhotoWithoutSequenceResponse> slice = new SliceImpl<>(content,
            pageable, true);

        given(giftService.getPhotos(null, pageable)).willReturn(slice);

        // when // then
        mockMvc.perform(
            get(baseUrl + "/gifts/photos")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.content").isArray())
            .andExpect(jsonPath("$.data.content", hasSize(6)))
            .andExpect(jsonPath("$.data.first").value(true));
        ;
    }
}
