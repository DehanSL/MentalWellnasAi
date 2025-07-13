package com.Mentalwellness.MentelWellnessApplication;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;




@Controller
public class AuthController {

      @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "Login";
    }

     @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        // User login logic
        User user = userService.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        }

        model.addAttribute("error", "Invalid email or password");
        return "Login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "Register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String fullName,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "Register";
        }
        
        // Check if user already exists
        if (userService.findByEmail(email) != null) {
            model.addAttribute("error", "Email already registered");
            return "Register";
        }
        
        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setPassword(password);
        
        userService.registerUser(newUser);
        return "redirect:/login?registered";
    }
}