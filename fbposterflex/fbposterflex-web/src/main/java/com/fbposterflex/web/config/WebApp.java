package com.fbposterflex.web.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebApp {

	@RequestMapping(value = "/*")
	public ModelAndView mainPage() {
		return new ModelAndView("index");
	}
}