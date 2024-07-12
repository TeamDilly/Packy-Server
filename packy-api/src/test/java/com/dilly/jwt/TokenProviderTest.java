package com.dilly.jwt;

import static com.dilly.MemberEnumFixture.NORMAL_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;

import com.dilly.global.IntegrationTestSupport;
import com.dilly.jwt.dto.JwtResponse;
import com.dilly.member.domain.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

class TokenProviderTest extends IntegrationTestSupport {

    private Member member;
    private final Long memberId = 1L;

    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    @BeforeEach
    void setUp() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);

        // given
        member = NORMAL_MEMBER.createMember(memberId);
    }

    @DisplayName("Access, Refresh 토큰을 생성하여 반환한다.")
    @Test
    void generateJwt() {
        // given
        String bearerType = "Bearer";
        long accessTokenExpireTime = 1000L * 60 * 30;

        LocalDateTime now = LocalDateTime.now(clock);
        long nowMillis = now.atZone(Clock.systemDefaultZone().getZone()).toInstant().toEpochMilli();
        Date accessTokenExpiresIn = new Date(nowMillis + (accessTokenExpireTime));

        String expectedAccessToken = Jwts.builder()
            .setSubject(member.getId().toString())
            .claim("socialLoginType", member.getProvider())
            .claim("nickname", member.getNickname())
            .claim("auth", member.getRole())
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        String expectedRefreshToken = Jwts.builder()
            .setSubject(member.getId().toString())
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        // when
        JwtResponse jwtResponse = tokenProvider.generateJwt(member);

        // then
        assertThat(jwtResponse.id()).isEqualTo(memberId);
        assertThat(jwtResponse.grantType()).isEqualTo(bearerType);
        assertThat(jwtResponse.accessToken()).isEqualTo(expectedAccessToken);
        assertThat(jwtResponse.refreshToken()).isEqualTo(expectedRefreshToken);
        assertThat(jwtResponse.accessTokenExpiresIn()).isEqualTo(accessTokenExpiresIn.getTime());
    }
}
