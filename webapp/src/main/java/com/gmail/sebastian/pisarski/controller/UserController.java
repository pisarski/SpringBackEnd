package com.gmail.sebastian.pisarski.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gmail.sebastian.pisarski.aspect.validation.Validate;
import com.gmail.sebastian.pisarski.entity.User;
import com.gmail.sebastian.pisarski.service.UserService;

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

	@RequestMapping(value = "new", method = RequestMethod.GET)
	public String addUser(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		initUserFormModel(model, user);
		return "user.new_edit";
	}

	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public String editUser(Model model, @PathVariable("id") long id) {
		User user = userService.getById(id);
		model.addAttribute("user", user);
		initUserFormModel(model, user);
		return "user.new_edit";
	}

	@RequestMapping(value = { "new", "edit/{id}" }, method = RequestMethod.POST)
	@Validate(value = "user.new_edit", initMethod = "initUserFormModel")
	public String saveUser(@ModelAttribute("user") User user, Model model,
			Errors errors) {

		userService.save(user);
		return "redirect:/user/list";
	}

	private void initUserFormModel(Model model, User user) {
		model.addAttribute("userLimitExceeded",
				UserService.MAX_USERS_COUNT <= userService.getNumberOfUsers());
		if (user.getId() == null) {
			model.addAttribute("submitBtnCode", "button.create");
			model.addAttribute("saveActionUrl", "/user/new");
		} else {
			model.addAttribute("submitBtnCode", "button.edit");
			model.addAttribute("saveActionUrl", "/user/edit/" + user.getId());
		}
	}

}
