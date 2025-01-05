package com.kosa.jungdoin.common;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieManager {
	public static final String ACCESS_TOKEN = "accessToken";
	public static final String REFRESH_TOKEN = "refreshToken";

	public void setSecureHttpOnlyCookie(HttpServletResponse res, String value, String name, long expiration) {
		ResponseCookie cookie = ResponseCookie.from(name, value)
			.maxAge(expiration)
			.path("/")
			.sameSite("Lax")
			//TODO: 배포할 때 주석 해제
//			.secure(true)
			.httpOnly(true)
			.build();
		res.addHeader("Set-Cookie", cookie.toString());
	}

	public void setCookie(HttpServletResponse res, String value, String name, long expiration) {
		ResponseCookie cookie = ResponseCookie.from(name, value)
				.maxAge(expiration)
				.path("/")
				.sameSite("Lax")
				.build();
		res.addHeader("Set-Cookie", cookie.toString());
	}

	public String getCookie(HttpServletRequest req, String name) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public void deleteCookie(HttpServletResponse res, String name) {
		ResponseCookie cookie = ResponseCookie.from(name, "")
			.maxAge(0)
			.path("/")
			.secure(true)
			.httpOnly(true)
			.build();
		res.addHeader("Set-Cookie", cookie.toString());
	}
}
