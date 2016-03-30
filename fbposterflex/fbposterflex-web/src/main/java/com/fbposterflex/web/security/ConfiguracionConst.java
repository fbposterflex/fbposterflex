package com.fbposterflex.web.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class ConfiguracionConst {

	public static final String API_NAME = "fbposterflex";
	public static final String COOKIE_NAME = "fbposterflex.cookieId";
	public static final String COOKIE_CHECKBOX = "fbposterflex.cookiecheckbox";

	/**
	 * Obtenemos el token si se encuentra en la cookie persistente del browser
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie != null && name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public static String getCookieName(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie != null && name.equals(cookie.getName())) {
					return cookie.getName();
				}
			}
		}
		return null;
	}

}