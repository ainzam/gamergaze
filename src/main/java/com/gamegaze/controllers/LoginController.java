package com.gamegaze.controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}
	
	@GetMapping(value = "/")
	public String root(HttpServletRequest request) throws ServletException, IOException {
		return "redirect:/home";
	}

	@GetMapping(value = "/loginfailed")
	public String loginFailed(Model model) {
		model.addAttribute("error", "true");
		return "login";
	}

	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request) throws ServletException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		request.logout();
		return "redirect:/";
	}
}