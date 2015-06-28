package com.lumesse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lumesse.aspect.validation.Validate;
import com.lumesse.entity.User;
import com.lumesse.service.UserService;

@Controller
@RequestMapping(value = "user")
@PreAuthorize("hasAuthority('USER_MANAGEMENT')")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String displayUsersList(Model model) {
		model.addAttribute("users", userService.findAll());
		return "user.list";
	}

	@RequestMapping(value = { "new", "save" }, method = RequestMethod.GET)
	public String addUser(Model model) {
		model.addAttribute("userLimitExceeded",
				UserService.MAX_USERS_COUNT <= userService.getNumberOfUsers());
		model.addAttribute("user", new User());
		return "user.new_edit";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@Validate("user.new_edit")
	public String saveUser(@ModelAttribute("user") User user, Errors errors) {

		userService.save(user);
		return "redirect:list";
	}

}
