package com.spring.registration.common;

import com.spring.registration.model.entity.*;
import com.spring.registration.model.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.util.List;


@ControllerAdvice
public class CommonAdvice {


    @Autowired
    private CategoryRepo repo;
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private AdminInfoRepo adminInfoRepo;
    @Autowired
    private RegisterRepo registerRepo;
    @Autowired
    private AccountRepo accountRepo;



    @ModelAttribute(name = "account")
    public Account account(){
        return new Account();
    }

    @ModelAttribute("catego")
    public List<Category> categories() {
        return repo.findAll();
    }
    @ModelAttribute("courses")
    public List<Course> addCourse(){
      return courseRepo.findAll();
    }
    @ModelAttribute("openCourses")
    public List<Course> openCourse(){
      return courseRepo.findByStartBefore(LocalDate.now());
    }
    @ModelAttribute(name = "category")
    public Category category(){ return new Category();
    }
    @ModelAttribute("admininfo")
    public List<AdminInfo> find(){
        return adminInfoRepo.findAll();
    }
    @ModelAttribute("look")
    public int look(){
        List<Register> register = registerRepo.findByLook(false);
        return register.size();
    }

    public String member(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account a= accountRepo.findByEmail(userDetails.getUsername());
        String  user = a.getUsername();
        return user;


    }













}
