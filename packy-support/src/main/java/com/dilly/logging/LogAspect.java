package com.dilly.logging;

import com.dilly.member.adaptor.MemberReader;
import com.dilly.member.domain.Status;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Profile("prod")
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final SlackService slackService;
    private final MemberReader memberReader;

    @Value("${slack.webhook-url.sign-up}")
    private String signUpUrl;

    @Pointcut("execution(* com.dilly.auth..AuthController.signUp(..))")
    public void signUp() {}

    @After("signUp()")
    public void countMember() {
        String title = "패키에 유저가 들어왔어요!";
        HashMap<String, String> data = new HashMap<>();

        Long memberCount = memberReader.countByStatus(Status.REGISTERED);
        data.put("현재 유저 수: ", memberCount.toString() + "명");

        slackService.sendMessage(signUpUrl, title, data);
    }
}
