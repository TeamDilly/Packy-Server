package com.dilly.global;

import com.dilly.member.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

	@Value("${jwt.secret}")
	String secretKey;

	@Override
	public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
		return createSecurityContext(annotation.id());
	}

	public SecurityContext createSecurityContext(String memberId) {
		final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

		Claims claims;
		UserDetails principal;
		Collection<? extends GrantedAuthority> authorities;

		long now = (new Date()).getTime();
		long acessTokenExpireTime = 1000 * 60 * 30;
		Date accessTokenExpiresIn = new Date(now + acessTokenExpireTime);

		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		Key key = Keys.hmacShaKeyFor(keyBytes);

		String accessToken = Jwts.builder()
			.setSubject(memberId)
			.claim("provider", "TEST")
			.claim("nickname", "테스트")
			.claim("auth", Role.ROLE_USER)
			.setExpiration(accessTokenExpiresIn) // 토큰 만료 시간 설정
			.signWith(key, SignatureAlgorithm.HS512)
			.compact(); // 토큰 생성

		claims = Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(accessToken)
			.getBody();
		authorities = Arrays.stream(claims.get("auth").toString().split(","))
			.map(SimpleGrantedAuthority::new)
			.toList();
		principal = new User(claims.getSubject(), "", authorities);

		final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			principal, "", authorities
		);
		securityContext.setAuthentication(authenticationToken);

		return securityContext;
	}
}
