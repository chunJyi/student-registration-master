package com.spring.registration.controller;

import com.spring.registration.model.entity.Course;
import com.spring.registration.model.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseRepo courseRepo;

    @GetMapping()
    public String addCourse(ModelMap map){
        map.addAttribute("courses",courseRepo.findAll());
        return "admin/addCourse";
    }
    @GetMapping("/course")
    public String home(){
        return "admin/course";
    }

    @PostMapping("add")
    public String add(@ModelAttribute(name="course") Course course){
        courseRepo.save(course);
        return "redirect:/";
    }
    @GetMapping("/find")
    public String find(ModelMap map){
        map.addAttribute("courses",courseRepo.findAll());
        return "admin/course";
    }
    @GetMapping("edit/{id}")
    public String edit(@PathVariable Long id ,ModelMap map){
        map.addAttribute("course",courseRepo.getOne(id));
        return "admin/course";
    }
    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id ){
        courseRepo.deleteById(id);
        return "redirect:/";
    }

    @ModelAttribute(name = "course")
    public Course course(){ return new Course();
    }
    @PostMapping("/findCategoryName")
    public String find(@RequestParam String name, RedirectAttributes attributes){
        String  category = name.toUpperCase();
        attributes.addFlashAttribute("courses",courseRepo.findByCategoryName(category));
        return "redirect:/";
    }


}
