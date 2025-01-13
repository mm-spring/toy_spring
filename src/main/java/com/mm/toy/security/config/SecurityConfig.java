package com.mm.toy.security.config;

import com.mm.toy.security.exception.JwtAccessDeniedHandler;
import com.mm.toy.security.exception.JwtAuthenticationEntryPoint;
import com.mm.toy.security.filter.JwtAuthenticationFilter;
import com.mm.toy.security.filter.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        configureCorsAndSecurity(httpSecurity);
        configureAuth(httpSecurity);
        configureExceptionHandling(httpSecurity);
        addFilter(httpSecurity);

        return httpSecurity.build();
    }

    private static void configureCorsAndSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .headers(
                        httpSecurityHeadersConfigurer ->
                                httpSecurityHeadersConfigurer.frameOptions(
                                        HeadersConfigurer.FrameOptionsConfig::disable
                                )
                )
                // stateless한 rest api 이므로 csrf 공격 옵션 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .httpBasic(HttpBasicConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                );
    }

    private void configureAuth(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequest -> {
                    authorizeRequest
                            .requestMatchers("/", "/.well-known/**", "/css/**", "/*.ico", "/error", "/images/**").permitAll()
                            .requestMatchers(permitAllRequest()).permitAll()
                            .requestMatchers(authRelatedEndpoints()).permitAll()
                            .requestMatchers(permitAllRequestV2()).permitAll()
                            .anyRequest().authenticated();
                });
    }

    private void addFilter(HttpSecurity httpSecurity) {
        httpSecurity
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
    }

    private void configureExceptionHandling(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler));        // 403
    }



    private RequestMatcher[] permitAllRequest() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher(HttpMethod.GET, "/"),
                antMatcher(HttpMethod.POST, "/api/users"),
                antMatcher(HttpMethod.GET, "/api/users"),
                antMatcher(HttpMethod.GET, "/api/users/user"),
                antMatcher(HttpMethod.GET, "/api/boards"),
                antMatcher(HttpMethod.GET, "/api/boards/user"),
                antMatcher(HttpMethod.GET, "/api/board/{boardId}"),
                antMatcher(HttpMethod.GET, "/api/comments"),
                antMatcher(HttpMethod.GET, "/api/comments/boards")
        );
        return requestMatchers.toArray(RequestMatcher[]::new);
    }private RequestMatcher[] permitAllRequestV2() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher("/api/v2/promotions"),
                antMatcher("/api/v2/promotions/{promotionId}"),
                antMatcher("/api/v2/promotions/search")
        );
        return requestMatchers.toArray(RequestMatcher[]::new);
    }
    private RequestMatcher[] authRelatedEndpoints() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher("/login/**"),
                antMatcher("/auth/**")
        );
        return requestMatchers.toArray(RequestMatcher[]::new);
    }
}
