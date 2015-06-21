package com.lumesse.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lumesse.entity.User;
import com.lumesse.service.UserService;

@Controller
@RequestMapping(value = "user")
@Secured("ROLE_ADMIN")
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
	public String saveUser(@Valid @ModelAttribute("user") User user,
			Errors errors) {

		if (!usernameIsUnique(user)) {
			errors.rejectValue("username", "user.username.Unique");
		}

		if (errors.hasErrors()) {
			return "user.new_edit";
		}

		userService.save(user);
		return "redirect:list";
	}

	private boolean usernameIsUnique(User user) {
		User existingUser = userService.findByUsername(user.getUsername());
		return existingUser == null
				|| existingUser.getId().equals(user.getId());
	}
}
