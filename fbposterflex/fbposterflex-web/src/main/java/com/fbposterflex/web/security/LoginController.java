package com.fbposterflex.web.security;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fbposterflex.api.autenticacion.AuthenticationEJB;
import com.fbposterflex.api.exception.CredencialesInvalidasException;
import com.fbposterflex.api.usuarios.UsuarioDTO;

@Controller
public class LoginController {

	private boolean tokenValido;


	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();
		if (session != null) {
			if (session.getAttribute(ConfiguracionConst.API_NAME) != null) {
				return "index";
			}
		}
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String home(@ModelAttribute(value = "login") LoginBean user, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) throws NamingException {

		try{
			
			Context context = new InitialContext();
					
			AuthenticationEJB  authEJB =  (AuthenticationEJB)context.lookup("java:global/fbposterflex-web/AuthenticationEJB!com.fbposterflex.api.autenticacion.AuthenticationEJB");
			
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setUserName(user.getUsername());
			usuario.setPassword(user.getPassword());
	
			String token = authEJB.login(usuario);
			String checkbox = request.getParameter("checkbox");
	
			tokenValido = token != "";
	
			if (tokenValido) {
	
				HttpSession session = request.getSession();
				session.setAttribute(ConfiguracionConst.API_NAME, usuario);
				session.setMaxInactiveInterval(30 * 60);
	
				if (checkbox != null) {
					Cookie cookie = new Cookie(ConfiguracionConst.COOKIE_CHECKBOX, token);
					cookie.setPath("/fbposterflex/");
					cookie.setMaxAge(30 * 60);
					response.addCookie(cookie);
				} else {
					Cookie cookie = new Cookie(ConfiguracionConst.COOKIE_NAME, token);
					cookie.setPath("/fbposterflex/");
					cookie.setMaxAge(-1);
					response.addCookie(cookie);
				}
			}
			return "index";
		}catch(CredencialesInvalidasException ex){
			return "login";
		}
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) throws NamingException, IOException {
					
		Context context = new InitialContext();
		
		AuthenticationEJB  authEJB =  (AuthenticationEJB)context.lookup("java:global/fbposterflex-web/AuthenticationEJB!com.fbposterflex.api.autenticacion.AuthenticationEJB");

		
		String name = ConfiguracionConst.getCookieName(request, ConfiguracionConst.COOKIE_CHECKBOX);
		String tokenUser = "";
		
		if(name != null){
			tokenUser = ConfiguracionConst.getCookieValue(request, ConfiguracionConst.COOKIE_CHECKBOX);
			authEJB.cerrarSesion(tokenUser);
			Cookie opentoken = new Cookie(ConfiguracionConst.COOKIE_CHECKBOX, null);
			opentoken.setMaxAge(0);
			opentoken.setPath("/fbposterflex/");
			response.addCookie(opentoken);
		
		}else{
			tokenUser = ConfiguracionConst.getCookieValue(request, ConfiguracionConst.COOKIE_NAME);
			authEJB.cerrarSesion(tokenUser);
			Cookie opentoken = new Cookie(ConfiguracionConst.COOKIE_NAME, null);
			opentoken.setMaxAge(0);
			opentoken.setPath("/fbposterflex/");
			response.addCookie(opentoken);
		}
		
		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
			response.sendRedirect(request.getContextPath() + "/login");
		}
		
	}

	/************* Getters and Setters *********************/

	public void setTokenValido(boolean tokenValido) {
		this.tokenValido = tokenValido;
	}

	public boolean isTokenValido() {
		return tokenValido;
	}

}