package com.softisland.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String indexNull(HttpServletRequest request){
		return "index";
	}
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		return "index";
	}
}
