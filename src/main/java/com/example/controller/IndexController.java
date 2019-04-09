package com.example.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.FreeMarkerSort;
import com.example.domain.User;

/**
 * 控制层
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

	@RequestMapping(value = "/index")
	public Model index(Model model) {
		model.addAttribute("username", "Hello, freemarker");
		model.addAttribute("userList", this.loadUserList());
		model.addAttribute("languageMap", this.loadLanguageMap());
		model.addAttribute("htmlString", "<a href=\"https://www.baidu.com\">贪玩蓝月，点一下，玩一年。</a>");
		return model;
	}

	private List<User> loadUserList() {
		List<User> list = new ArrayList<User>();
		for (int i = 0; i < 5; i++) {
			list.add(new User("用户" + i, new Date()));
		}
		return list;
	}

	private Map<String, String> loadLanguageMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Python", "人生苦短，我用Python");
		map.put("PHP", "PHP是世界上最好的语言");
		map.put("Java", "谁，谁在说话");
		return map;
	}

	/**
	 * freemarker基本用法
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/basic")
	public Model basic(Model model) {
		model.addAttribute("intVal", 100);
		model.addAttribute("longVal", 10000l);
		model.addAttribute("stringVal", "freemarker");
		model.addAttribute("dateVal", new Date());
		model.addAttribute("nullVal", null);
		return model;
	}

	/**
	 * freemarker高级用法
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/advanced")
	public Model advanced(Model model) {
		model.addAttribute("mySort", new FreeMarkerSort());
		return model;
	}
	
	/**
	 * freemarker常用内建函数
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/tool")
	public Model tool(Model model) {
		return model;
	}
}
