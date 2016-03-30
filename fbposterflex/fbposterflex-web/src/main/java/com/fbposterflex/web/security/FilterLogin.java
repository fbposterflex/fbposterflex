package com.fbposterflex.web.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fbposterflex.api.usuarios.UsuarioDTO;

public class FilterLogin implements Filter {

	@Override
	public void init(FilterConfig init) throws ServletException {
		System.out.println("Init Filtro uno iniciado");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String[] recursos = { "/bootstrap", "/dist", "/plugins", "/login" };
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		for (String array : recursos) {
			if (path.startsWith(array)) {
				chain.doFilter(request, response);
				return;
			}
		}
		HttpSession httpSession = httpRequest.getSession();
		UsuarioDTO usuario = (UsuarioDTO) httpSession.getAttribute(ConfiguracionConst.API_NAME);
		boolean apiEnSesion = usuario != null;

		if (!apiEnSesion) {

			String cookieId = ConfiguracionConst.getCookieValue(httpRequest, ConfiguracionConst.COOKIE_NAME);
			boolean existeCookie = cookieId != null;

			if (existeCookie) {
				// Agregar el api en sesion
				UsuarioDTO usuarioFlex = new UsuarioDTO();
				httpSession.setAttribute(ConfiguracionConst.API_NAME, usuarioFlex);
				setCookie(httpResponse, ConfiguracionConst.COOKIE_NAME, cookieId, 30);
			} else {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
			}
		} else {
			// TODO: Verificar el token remoto
		}
		chain.doFilter(request, response);
	}

	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	@Override
	public void destroy() {
	}

}