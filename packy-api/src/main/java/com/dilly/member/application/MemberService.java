package com.dilly.member.application;

import static com.dilly.global.Constants.LATEST_MAJOR_VERSION;

import com.dilly.global.util.SecurityUtil;
import com.dilly.member.adaptor.MemberReader;
import com.dilly.member.domain.Member;
import com.dilly.member.domain.Status;
import com.dilly.member.dto.response.Reason;
import com.dilly.member.dto.response.StatusResponse;
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

    public StatusResponse getStatus(Integer memberMajorVersion) {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        // 유저 계정 상태 확인
        if (!member.getStatus().equals(Status.REGISTERED)) {
            return StatusResponse.from(false, Reason.INVALID_STATUS);
        }

        // 유저 버전 확인
        if (memberMajorVersion < LATEST_MAJOR_VERSION) {
            return StatusResponse.from(false, Reason.NEED_UPDATE);
        }

        return StatusResponse.from(true);
    }
}
