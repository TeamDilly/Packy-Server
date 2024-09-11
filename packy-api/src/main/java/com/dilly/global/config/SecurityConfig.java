package com.dilly.global.config;

import com.dilly.jwt.JwtAccessDeniedHandler;
import com.dilly.jwt.JwtAuthenticationEntryPoint;
import com.dilly.jwt.JwtFilter;
import com.dilly.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// CSRF 설정 Disable
		http
			.csrf(AbstractHttpConfigurer::disable)

			// exception handling 할 때 우리가 만든 클래스를 추가
			.exceptionHandling(exceptHandling ->
				exceptHandling
					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
					.accessDeniedHandler(jwtAccessDeniedHandler)
			)

			.headers(headers ->
				headers
					.frameOptions((FrameOptionsConfig::disable))
			)

			// 시큐리티는 기본적으로 세션을 사용
			// 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)

			// 토큰이 없는 상태에서 요청이 가능한 API는 permitAll 설정
			.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
					.requestMatchers("/", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
					.permitAll()
					.requestMatchers(
						"/api/v1/admin/health",
						"/api/v1/auth/token/kakao/**",
						"/api/v1/admin/design/profiles",
						"/api/v1/admin/branch",
						"/api/v1/admin/notices/**",
						"/api/v1/auth/sign-up",
						"/api/v1/auth/sign-in/**",
						"/api/v1/auth/reissue",
						"/api/v1/auth/withdraw/kakao",
						"/api/v1/giftboxes/web/**"
					).permitAll()
					.anyRequest().authenticated()
			)
			.formLogin(Customizer.withDefaults())

			// JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
			.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
