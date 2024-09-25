package com.dilly.admin.api;

import com.dilly.admin.application.AdminV2Service;
import com.dilly.admin.dto.response.SettingV2Response;
import com.dilly.global.response.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관리자 API")
@RestController
@RequestMapping("/api/v2/admin")
@RequiredArgsConstructor
public class AdminV2Controller {

    private final AdminV2Service adminV2Service;

    @Operation(summary = "설정 링크 조회")
    @GetMapping("/settings")
    public DataResponseDto<List<SettingV2Response>> getSettingUrls() {
        return DataResponseDto.from(adminV2Service.getSettingUrls());
    }
}
