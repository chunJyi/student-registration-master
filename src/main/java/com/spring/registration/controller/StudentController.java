package com.spring.registration.controller;

import com.spring.registration.model.entity.Account;
import com.spring.registration.model.entity.Course;
import com.spring.registration.model.entity.Register;
import com.spring.registration.model.entity.Student;
import com.spring.registration.model.repo.AccountRepo;
import com.spring.registration.model.repo.CourseRepo;
import com.spring.registration.model.repo.RegisterRepo;
import com.spring.registration.model.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping
public class StudentController {

    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private RegisterRepo registerRepo;

    @GetMapping("register/{id}")
    public String  edit(@PathVariable Long id, ModelMap map, RedirectAttributes attributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account a = accountRepo.findByEmail(userDetails.getUsername());
        Student stu = studentRepo.findByAccountId(a.getId());
        Course course = courseRepo.getOne(id);
        int studentCount  = course.getCount();
        LocalDate endDate = course.getStart().plusDays(7);
        LocalDate date = LocalDate.now();
        if( date.isAfter(endDate)){
            attributes.addFlashAttribute("reject",true);
            return "redirect:/";
        }
        List<Register> registers= registerRepo.findByCourseIdAndEnable(id,true);

        int confirmedStudent =registers.size();
        if(confirmedStudent >= studentCount){
            attributes.addFlashAttribute("full",true);
            return "redirect:/";
        }
        if (stu == null) {
            map.addAttribute("student_id", a.getId());
            map.addAttribute("course_id", id);
            return "member/register";
        }
        Register regi = registerRepo.findByStudentIdAndCourseId(stu.getId(), id);
        if (regi != null) {
//            map.addAttribute("message","Can not more Register Course");
            attributes.addFlashAttribute("un",true);
            return "redirect:/";
        }
            Register register = new Register();
            register.setStudent(studentRepo.getOne(stu.getId()));
            register.setCourse(courseRepo.getOne(id));
            registerRepo.save(register);
            attributes.addFlashAttribute("success",true);
//            map.addAttribute("message","Success Register");
            return "redirect:/";

    }
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        Account a= accountRepo.findByEmail(userDetails.getUsername());
//        Student stu=  studentRepo.findByAccountId(a.getId());
//        if (stu == null) {
//
//            map.addAttribute("account_id", a.getId());
//            map.addAttribute("course_id", id);
//            return "member/register";
//        }
//        Register register = new Register();
//        register.setStudent(studentRepo.);
//        register.setCourse(courseRepo.getOne(id));
//        registerRepo.save(register);


    @ModelAttribute(name = "student")
    public Student student(){
        return new Student();
    }

    @PostMapping("regi")
    public String register(@ModelAttribute("student") Student student,@RequestParam Account account,@RequestParam Course course ,RedirectAttributes attributes){
        studentRepo.save(student);
        Register register = new Register();
        register.setStudent(student);
        register.setCourse(course);
        registerRepo.save(register);
        attributes.addFlashAttribute("success",true);
        return "redirect:/";
    }
    @GetMapping("deleteRegi/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        Register register = registerRepo.getOne(id);
        if(register.getEnable().equals(false)){
        registerRepo.deleteById(id);
        return "redirect:/memberProfile";
        }
        redirectAttributes.addFlashAttribute("n",true);
        return "redirect:/memberProfile";
    }


}
