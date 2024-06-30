package com.dilly.auth.application.strategy;

import com.dilly.member.domain.Provider;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AuthActionProvider {

    private final Map<Provider, AuthStrategy> authActions;

    public AuthActionProvider(
        final KakaoStrategy kakaoStrategy,
        final AppleStrategy appleStrategy,
        final TestStrategy testStrategy
    ) {
        this.authActions = new EnumMap<>(Provider.class);
        this.authActions.put(Provider.KAKAO, kakaoStrategy);
        this.authActions.put(Provider.APPLE, appleStrategy);
        this.authActions.put(Provider.TEST, testStrategy);
    }

    public AuthStrategy getStrategy(Provider provider) {
        return authActions.get(provider);
    }
}
