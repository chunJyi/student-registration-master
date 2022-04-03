package com.spring.registration.controller;

import com.spring.registration.model.entity.Register;
import com.spring.registration.model.repo.AccountRepo;
import com.spring.registration.model.repo.RegisterRepo;
import com.spring.registration.model.repo.StudentRepo;
import com.spring.registration.util.MailHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping()
public class AdminProfileController {

    private final AccountRepo accountRepo;
    private final  MailHelper mailHelper;
    private final RegisterRepo registerRepo;
    private final StudentRepo studentRepo;

    public AdminProfileController(AccountRepo accountRepo, MailHelper mailHelper, RegisterRepo registerRepo, StudentRepo studentRepo) {
        this.accountRepo = accountRepo;
        this.mailHelper = mailHelper;
        this.registerRepo = registerRepo;
        this.studentRepo = studentRepo;
    }


    @GetMapping("/requestProfile")
    public String requestProfile(ModelMap map){
        map.addAttribute("register_student",registerRepo.findByEnable(false));
        List<Register> look = registerRepo.findByLook(false);
        look.forEach(item->item.setLook(true));
        registerRepo.saveAll(look);
        return "admin/profile";
    }
    @GetMapping("/acceptProfile")
    public String profile(ModelMap map){
        map.addAttribute("accept_student", registerRepo.findByEnable(true));
       return "admin/acceptProfile";
    }
    @GetMapping("/student/{id}")
    public String findStudentByCourseName(ModelMap map,@PathVariable Long id){
        map.addAttribute("accept_student", registerRepo.findByCourseId(id));
       return "admin/acceptProfile";
    }
    @GetMapping("accept/{id}")
    public String accept(@PathVariable Long id, RedirectAttributes attributes){
        Register accept = registerRepo.getOne(id);
        accept.setEnable(true);
        registerRepo.save(accept);
        String url="Accept Your Registration";
        String a = accept.getStudent().getAccount().getEmail();
        mailHelper.sendEmail(a,"Student", url);
        attributes.addFlashAttribute("accept",true);
        return "redirect:/requestProfile";
    }
    @GetMapping("remove/{id}")
    public String remove(@PathVariable Long id,RedirectAttributes attributes){
        registerRepo.deleteById(id);
        attributes.addFlashAttribute("remove",true);
        return "redirect:/acceptProfile";
    }
    @GetMapping("deleteStu/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        registerRepo.deleteById(id);
        attributes.addFlashAttribute("remove",true);
        return "redirect:/requestProfile";
    }
    @GetMapping("info/{id}")
    public String stuInfo(@PathVariable Long id,ModelMap map){
        List<Register> stuInfo = registerRepo.findByStudentId(id);
        map.addAttribute("student",studentRepo.getOne(id));
        map.addAttribute("myProfile",stuInfo);
        return "member/profile";
    }

}
