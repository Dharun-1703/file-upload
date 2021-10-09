package com.project.fileUpload.controller;
 

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@RequestMapping("/")
//@EnableWebMvc 
//@EnableAutoConfiguration
public class WelcomeController {

	@RequestMapping("/w")
	public String welcome(ModelMap map) {
		map.put("currentDate", new Date());
		return "welcome";
	}
}