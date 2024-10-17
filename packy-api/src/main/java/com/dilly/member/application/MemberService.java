package com.dilly.member.application;

import static com.dilly.global.constant.Constants.MINIMUM_REQUIRED_VERSION;

import com.dilly.exception.AuthorizationFailedException;
import com.dilly.exception.BadRequestException;
import com.dilly.exception.ErrorCode;
import com.dilly.exception.internalserver.InternalServerException;
import com.dilly.global.util.SecurityUtil;
import com.dilly.member.adaptor.DeviceReader;
import com.dilly.member.adaptor.DeviceWriter;
import com.dilly.member.adaptor.MemberReader;
import com.dilly.member.domain.Device;
import com.dilly.member.domain.Member;
import com.dilly.member.domain.Platform;
import com.dilly.member.domain.Status;
import com.dilly.member.dto.request.FCMTokenRequest;
import com.dilly.member.dto.response.AppStatusResponse;
import com.dilly.member.dto.response.Reason;
import java.util.Optional;
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
    private final DeviceReader deviceReader;
    private final DeviceWriter deviceWriter;

    public Member getMember() {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        Boolean isValidMember = member.getStatus().equals(Status.REGISTERED);
        if (Boolean.FALSE.equals(isValidMember)) {
            throw new AuthorizationFailedException(ErrorCode.INVALID_MEMBER);
        }

        return member;
    }

    public AppStatusResponse getStatus(String appVersion) {
        Member member = getMember();
        Long memberId = member.getId();

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

    public String issueFcmToken(FCMTokenRequest fcmTokenRequest) {
        Member member = getMember();
        String deviceId = fcmTokenRequest.deviceId();
        Platform platform = Platform.valueOf(fcmTokenRequest.platform());
        String fcmToken = fcmTokenRequest.fcmToken();

        Optional<Device> device = deviceReader.findByDeviceId(deviceId);
        if (device.isPresent()) {
            boolean newMemberUseDevice = !device.get().getMember().equals(member);
            boolean fcmTokenChanged = !device.get().getFcmToken().equals(fcmToken);

            if (newMemberUseDevice) {
                device.get().updateMember(member);
            }
            if (fcmTokenChanged) {
                device.get().updateFcmToken(fcmToken);
            }
        } else {
            deviceWriter.save(deviceId, fcmToken, platform, member);
        }

        return "FCM 토큰 저장이 완료되었습니다";
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
