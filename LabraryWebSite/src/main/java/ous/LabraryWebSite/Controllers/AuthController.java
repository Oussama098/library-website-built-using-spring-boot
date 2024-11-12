package ous.LabraryWebSite.Controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ous.LabraryWebSite.Services.UserService;
import ous.LabraryWebSite.models.UserEntity;

import java.security.Principal;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserEntity());
        return "login";
    }

    @GetMapping(path = "/register")
    public String register(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping(path = "/register/save")
    public String save(@Valid @ModelAttribute("user") UserEntity user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register?error=true"; // Redirect back to the registration page if there are validation errors
        }

        // Check if the user already exists
        UserEntity existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null && (existingUser.getUsername().equals(user.getUsername())
                || existingUser.getEmail().equals(user.getEmail()))) {
            return "redirect:/register?error=true"; // Redirect with error if user already exists
        }

        userService.addUser(user);
        return "redirect:/login?register=true";
    }


}
