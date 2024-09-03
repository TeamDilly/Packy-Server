package com.dilly.admin.dto.response;

import com.dilly.admin.domain.notice.Notice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record NoticeResponse(
    @Schema(example = "www.example.com")
    String imgUrl,
    @Schema(example = "www.example.com")
    String noticeUrl
) {

    public static NoticeResponse from(Notice notice) {
        return NoticeResponse.builder()
            .imgUrl(notice.getImgUrl())
            .noticeUrl(notice.getNoticeUrl())
            .build();
    }
}
