package com.dilly.member.aspect;

import com.dilly.application.FCMNotificationService;
import com.dilly.dto.request.FCMNotificationRequest;
import com.dilly.member.adaptor.DeviceReader;
import com.dilly.member.application.MemberService;
import com.dilly.member.domain.Device;
import com.dilly.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class FCMNotificationAspect {

    private final MemberService memberService;
    private final FCMNotificationService fcmNotificationService;

    private final DeviceReader deviceReader;

    @Pointcut("execution(* com.dilly.member..MyPageController.updateProfile(..))")
    public void updateProfile() {}

    @AfterReturning("updateProfile()")
    public void sendNotification() {
        Member member = memberService.getMember();

        List<String> tokens = deviceReader.findByMember(member).stream()
            .map(Device::getFcmToken)
            .toList();

        FCMNotificationRequest fcmNotificationRequest = FCMNotificationRequest.builder()
            .title("테스트 제목")
            .body("테스트 본문")
            .build();

        fcmNotificationService.sendNotificationByToken(tokens, fcmNotificationRequest);
    }
}
