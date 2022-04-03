package com.spring.registration.controller;

import com.spring.registration.model.entity.Account;
import com.spring.registration.model.entity.Token;
import com.spring.registration.model.repo.AccountRepo;
import com.spring.registration.model.repo.TokenRepo;
import com.spring.registration.util.MailHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping
public class AccountController {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private TokenRepo tokenRepo;
    @Autowired
    private MailHelper mailHelper;
    @GetMapping("sign-up")
    public String home(){
        return "sign-up";
    }
    @PostMapping("sign-up")
    public String save(@ModelAttribute(name = "account") @Valid Account account, BindingResult result
                                                , Model model){
        if(result.hasErrors()){
            return "sign-up";
        }
        Account accou = accountRepo.findByEmail(account.getEmail());
        if(accou != null){
            model.addAttribute("fail",true);
            return "sign-up";
        }

        account.setEnable(true);
        account.setPassword(encoder.encode(account.getPassword()));
        account.setRole(Account.Role.ROLE_ADMIN);
        accountRepo.save(account);
        model.addAttribute("check",true);  // use own
//        Token token = new Token(account);

//        tokenRepo.save(token);
//        String url=token.getToken();
//        mailHelper.sendEmail(account.getEmail(),"Account Confirmation", url);
        return "login";
    }
    @PostMapping("/confirm")
    public String confirm(@RequestParam String token,RedirectAttributes attributes){
        Token confirmToken = tokenRepo.findByToken(token);

        if(Objects.isNull(confirmToken)) {
//            throw new EntityNotFoundException("Token not found!");
           attributes.addFlashAttribute("token",true);
            return "redirect:/login";
        }
        Account account = confirmToken.getAccount();
        Long tokenId = confirmToken.getId();
        if(confirmToken.isExpire() && account.getEnable().equals(Boolean.FALSE)){


            tokenRepo.deleteById(tokenId);
//            questionRepo.deleteByAccount(account);
            accountRepo.deleteById(account.getId());
//            throw new RuntimeException("Try again with new account!");
            attributes.addFlashAttribute("delete",true);
            return "redirect:/login";
        }

        account.setEnable(Boolean.TRUE);
        accountRepo.save(account);
        tokenRepo.deleteById(tokenId);
        attributes.addFlashAttribute("create",true);
        return "redirect:/login";
    }
//    @GetMapping("/confirm")
//    public String getConfirm(){
//        return "confirm";
//    }
    @PostMapping("/forget")
     public String forget(@RequestParam String email,Model model,RedirectAttributes attributes){

        Account account = accountRepo.findByEmail(email);

        if(Objects.isNull(account)) {
//            throw new EntityNotFoundException("account not found!");
            attributes.addFlashAttribute("notfound",true);
            return "redirect:/login";
        }
//        model.addAttribute("forgetAccount",account);
        model.addAttribute("email",email);
        return "forgetpassword";

    }
    @PostMapping("/forgetPassword")
    public String forgetPassword(@ModelAttribute Account account,@RequestParam String email,Model model,RedirectAttributes attributes){
        Account forgetaccount = accountRepo.findByEmailAndUsernameAndBirthday(email,account.getUsername(),account.getBirthday());
        if (Objects.isNull(forgetaccount)){
//            throw new EntityNotFoundException("account not found!");
            attributes.addFlashAttribute("notfound",true);
            return "redirect:/login";
        }
        model.addAttribute("restartaccount",forgetaccount);
        return "restartPassword";
    }
    @PostMapping("/restartPassword")
    public String restartPassword(@ModelAttribute(name = "restartaccount")  Account restartaccount,@RequestParam String password,RedirectAttributes attributes){
        Account account = accountRepo.getOne(restartaccount.getId());
        account.setPassword(encoder.encode(password));
        accountRepo.save(account);
        attributes.addFlashAttribute("change",true);
        return "redirect:/login";
    }


    @ModelAttribute(name = "account")
    public Account account(){
        return new Account();
    }

}
