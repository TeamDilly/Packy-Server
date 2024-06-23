package com.dilly.member.application;

import static com.dilly.global.Constants.MINIMUM_REQUIRED_VERSION;

import com.dilly.exception.BadRequestException;
import com.dilly.exception.ErrorCode;
import com.dilly.exception.internalserver.InternalServerException;
import com.dilly.global.util.SecurityUtil;
import com.dilly.member.adaptor.MemberReader;
import com.dilly.member.domain.Member;
import com.dilly.member.domain.Status;
import com.dilly.member.dto.response.AppStatusResponse;
import com.dilly.member.dto.response.Reason;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberReader memberReader;

    public AppStatusResponse getStatus(String appVersion) {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        // 유저 계정 상태 확인
        if (!member.getStatus().equals(Status.REGISTERED)) {
            return AppStatusResponse.from(memberId, false, Reason.INVALID_STATUS);
        }

        // 유저 버전 확인
        Integer minimumRequiredMajorVersion = extractMajorVersion(MINIMUM_REQUIRED_VERSION);
        Integer minimumRequiredMinorVersion = extractMinorVersion(MINIMUM_REQUIRED_VERSION);

        Integer memberMajorVersion = extractMajorVersion(appVersion);
        Integer memberMinorVersion = extractMinorVersion(appVersion);
        
        if (minimumRequiredMajorVersion == null || minimumRequiredMinorVersion == null) {
            throw new InternalServerException(ErrorCode.INVALID_LATEST_VERSION);
        }

        if (memberMajorVersion == null || memberMinorVersion == null) {
            throw new BadRequestException(ErrorCode.FAILED_TO_EXTRACT_VERSION);
        }

        if (memberMajorVersion < minimumRequiredMajorVersion) {
            return AppStatusResponse.from(memberId, false, Reason.NEED_UPDATE);
        }

        if (memberMinorVersion < minimumRequiredMinorVersion) {
            return AppStatusResponse.from(memberId, false, Reason.NEED_UPDATE);
        }

        return AppStatusResponse.from(memberId, true);
    }

    private Integer extractMajorVersion(String version) {
        String[] parts = version.split("\\.");
        if (parts.length > 0) {
            return Integer.parseInt(parts[0]);
        }

        return null;
    }

    private Integer extractMinorVersion(String version) {
        String[] parts = version.split("\\.");
        if (parts.length > 1) {
            return Integer.parseInt(parts[1]);
        } else {
            return null;
        }
    }
}
