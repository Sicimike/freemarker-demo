package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制层
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@RequestMapping(value = "/index")
	public Model index(Model model) {
		model.addAttribute("username", "Hello, freemarker");
		return model;
	}
}
