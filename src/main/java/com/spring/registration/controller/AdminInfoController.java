package com.spring.registration.controller;

import com.spring.registration.model.entity.AdminInfo;
import com.spring.registration.model.repo.AccountRepo;
import com.spring.registration.model.repo.AdminInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminInfoController  {
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private AdminInfoRepo adminInfoRepo;

    @GetMapping("/infoForm")
    public String infoForm(ModelMap map){
          int id = 1;
        map.addAttribute("info", adminInfoRepo.getOne(id));
  //     map.addAttribute("info",new AdminInfo());
        return "admin/infoForm";
    }

    @PostMapping("/infoSave")
    public String save(@ModelAttribute AdminInfo adminInfo){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        Account a= accountRepo.findByEmail(userDetails.getUsername());
//        AdminInfo info = adminInfoRepo.findByAccountId(a.getId());
        adminInfoRepo.save(adminInfo);
        return "home";
    }



}
