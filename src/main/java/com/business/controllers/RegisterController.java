package com.business.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.business.entities.User;
import com.business.repositories.UserRepository;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    // Show registration page
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";   // register.html
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String uname,
            @RequestParam String uemail,
            @RequestParam String upassword,
            @RequestParam Long unumber,
            Model model) {


        User existingUser = userRepository.findUserByUemail(uemail);

        // Check if email already exists
        if (existingUser != null) {
            model.addAttribute("error", "Email already registered!");
            return "register";
        }

        User user = new User(); 
        user.setUname(uname);
        user.setUemail(uemail);
        user.setUpassword(upassword); // encode later
        user.setUnumber(unumber);

        userRepository.save(user);

        return "redirect:/login?success";
    }
}
