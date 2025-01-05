package com.kosa.jungdoin.member.service;

import com.kosa.jungdoin.common.CookieManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final CookieManager cookieManager;

    public void deleteAuthCookies(HttpServletResponse response) {
        cookieManager.deleteCookie(response, CookieManager.ACCESS_TOKEN);
        cookieManager.deleteCookie(response, CookieManager.REFRESH_TOKEN);
        cookieManager.deleteCookie(response, "id");
        cookieManager.deleteCookie(response, "provider");
    }
}
