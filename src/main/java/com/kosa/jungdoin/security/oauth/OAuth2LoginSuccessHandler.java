package com.kosa.jungdoin.security.oauth;

import com.kosa.jungdoin.common.CookieManager;
import com.kosa.jungdoin.common.Role;
import com.kosa.jungdoin.common.jwt.JwtProvider;
import com.kosa.jungdoin.security.CustomUserDetails;
import com.kosa.jungdoin.security.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 *   OAuth2가 성공했을 때, 실행되는 핸들러
 *   여기서 OAuth 정보를 기반으로 객체를 생성한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService userDetailsService;
    private final CookieManager cookieManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("OAuth2 Login 성공!");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        log.info("객체 생성 시작");
        String provider = oAuth2User.getAttribute("provider");
        String providerId = oAuth2User.getAttribute("id");

        String memberOauthId = provider + "_" + providerId;

        if (!userDetailsService.userExists(memberOauthId)) {
            userDetailsService.createUser(new CustomUserDetails(
                    oAuth2User.getAttribute("nickname"), Role.REGULAR, provider, memberOauthId)
            );
        }
        log.info("객체 생성 완료 - {}", userDetailsService.loadUserByUsername(memberOauthId).toString());

        log.info("토큰 생성 시작");

        Map<String, Object> claims = new HashMap<>();
        claims.put("provider", provider);
        claims.put("id", providerId);
        String accessToken = jwtProvider.createAccessToken(claims);
        String refreshToken = jwtProvider.createRefreshToken();

        cookieManager.setSecureHttpOnlyCookie(response, accessToken, CookieManager.ACCESS_TOKEN, jwtProvider.getRefreshTokenExpirationPeriod());
        cookieManager.setSecureHttpOnlyCookie(response, refreshToken, CookieManager.REFRESH_TOKEN, jwtProvider.getRefreshTokenExpirationPeriod());
        cookieManager.setCookie(response, provider, "provider", jwtProvider.getRefreshTokenExpirationPeriod());
        cookieManager.setCookie(response, providerId, "id", jwtProvider.getRefreshTokenExpirationPeriod());
        response.sendRedirect("http://localhost:5173/");

        log.info("토큰 생성 완료 -> 클라이언트 브라우저 쿠키에 주입 완료");

        clearAuthenticationAttributes(request);
    }
}