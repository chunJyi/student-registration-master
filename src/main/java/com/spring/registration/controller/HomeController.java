package com.spring.registration.controller;

import com.spring.registration.model.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {
    @Autowired
    private CourseRepo courseRepo;
    @GetMapping({"/","/home"})
    private String home(){
        return "home";
    }

    @GetMapping("home{id:\\d+}")
    private String find(@PathVariable(name = "id") Long id, ModelMap map){
        map.addAttribute("courses",courseRepo.findByCategoryId(id));
        return "home";
    }
}
