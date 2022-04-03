package com.spring.registration.controller;

import com.spring.registration.model.entity.Account;
import com.spring.registration.model.entity.Student;
import com.spring.registration.model.repo.AccountRepo;
import com.spring.registration.model.repo.RegisterRepo;
import com.spring.registration.model.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class MemberProfileController {
    @Autowired
    private RegisterRepo registerRepo;
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private AccountRepo accountRepo;
    @GetMapping("/memberProfile")
    public String home(ModelMap map){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account a= accountRepo.findByEmail(userDetails.getUsername());
        Student student = studentRepo.findByAccountId(a.getId());
        if(student !=null ){
        map.addAttribute("student",studentRepo.findByAccountId(a.getId()));
        map.addAttribute("myProfile",registerRepo.findByStudentId(student.getId()));
        return "member/profile";
        }
        return "redirect:/";
    }
}
