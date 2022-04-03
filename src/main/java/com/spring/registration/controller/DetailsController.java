package com.spring.registration.controller;

import com.spring.registration.model.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DetailsController {
    @Autowired
    private CourseRepo courseRepo;
    @GetMapping("detail/{id}")
    public String detail(@PathVariable Long id, ModelMap map){
        map.addAttribute("detail",courseRepo.getOne(id));
        return "details";
    }
}
