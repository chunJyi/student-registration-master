package com.spring.registration.controller;

import com.spring.registration.model.entity.Category;
import com.spring.registration.model.entity.Course;
import com.spring.registration.model.repo.CategoryRepo;
import com.spring.registration.model.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping
public class CategoryController {


    @Value("${nginx.location}")
    private String nginxLocation;

    @Value("${image.baseDir}")
    private String imageBaseDir;

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private CourseRepo courseRepo;

    @GetMapping("category")
    public String home(){ return "admin/category";
    }
    @PostMapping("category")
    public String save (@RequestParam MultipartFile files ,@ModelAttribute(name = "category") Category category, BindingResult result)
            throws IllegalStateException, IOException {
        if (result.hasErrors()){
            return "admin/category";
        }

        String orginalName = files.getOriginalFilename();
        String newName = LocalDateTime.now().toString().concat(orginalName);
        String replaceName = newName.replace(":", "-");
        String imagePath = nginxLocation.concat(replaceName);

        Category cat = new Category();
        cat.setId(category.getId());
        cat.setImageName(replaceName);
        cat.setLocation(imagePath);
        cat.setName(category.getName().toUpperCase());
        try {
            files.transferTo(Paths.get(imageBaseDir).resolve(replaceName));
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        categoryRepo.save(cat);
        return "redirect:/find";
    }

    @GetMapping("/find")
    public String find(ModelMap map){
       map.addAttribute("categories",categoryRepo.findAll());
        return "admin/category";
    }

    @GetMapping("edit/{id}")
    public String  edit(@PathVariable Long id, ModelMap map){
        map.put("category",categoryRepo.getOne(id));
        String name= categoryRepo.getOne(id).getImageName();
        File file = new File("D:/nginx-1.16.2/html/images/",name);
        file.delete();
        return "admin/category";
    }


    @GetMapping("delete/{id}")
    public String CategoryDelete(@PathVariable Long id){
        String name= categoryRepo.getOne(id).getImageName();
        File file = new File("D:/nginx-1.16.2/html/images/",name);
        file.delete();
        Category category=categoryRepo.getOne(id);
        List<Course> cate = courseRepo.findByCategoryId(category.getId());
        courseRepo.deleteAll(cate);
        categoryRepo.deleteById(id);

        return "redirect:/find";
    }
}
