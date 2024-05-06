 package com.gamegaze.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gamegaze.domain.User;
import com.gamegaze.service.RegistrationService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class RegistrationController {

	private final RegistrationService registrationService;
	
    @GetMapping(value = "/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView("registrationForm");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("user") @Validated User user, BindingResult result) {
        if (result.hasErrors()) {
            return "registrationForm";
        }
        registrationService.register(user);
        return "redirect:/login";
    }

}
