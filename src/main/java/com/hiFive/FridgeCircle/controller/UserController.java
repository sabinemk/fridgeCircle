package com.hiFive.FridgeCircle.controller;

import com.hiFive.FridgeCircle.entity.LoginRequest;
import com.hiFive.FridgeCircle.entity.User;
import com.hiFive.FridgeCircle.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model){
        User user=new User();
        model.addAttribute("user",user);
        System.out.println("Controller getmapping register");
        return "register";
    }

    @PostMapping("/register")
    public String handleUserRegistration(User user){
        try {
            System.out.println("Controller post mapping register "+user);
            this.userService.createUser(user);
            return "redirect:login?status=REGISTER_SUCCESS";
        } catch (Exception exception){
            exception.printStackTrace();
            return "redirect:register?status=REGISTER_FAILED&message="+exception.getMessage();
        }
    }

    @GetMapping("/login")
    public String displayLoginPage(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "message", required = false) String message,
            Model model
    ){
        model.addAttribute("status", status);
        model.addAttribute("message", message);
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(LoginRequest loginRequest, HttpServletResponse response){
        try {
            User loggedInUser = this.userService.verifyUser(loginRequest.username, loginRequest.password);
            if (loggedInUser == null) throw new RuntimeException("User not found");


            Cookie cookie = new Cookie("loggedInUserId", loggedInUser.getUserId().toString());

            cookie.setMaxAge(86400); //a day
            response.addCookie(cookie);

            return "redirect:index";
        } catch (Exception exception){
            return "redirect:login?status=LOGIN_FAILED&message=" + exception.getMessage();
        }
    }

    @GetMapping("/logout")
    public String logout(
            @CookieValue(value = "loggedInUserId", defaultValue = "") String userId,
            HttpServletResponse response
    ){
        try {

            Cookie cookie = new Cookie("loggedInUserId", null);
            cookie.setMaxAge(0); // expiry the cookie = deleting
            response.addCookie(cookie);

            return "redirect:login?status=LOGOUT_SUCCESS&message=You logged out successfully";
        } catch (Exception exception){
            return "redirect:login?status=LOGOUT_FAILED&message=" + exception.getMessage();
        }
    }




}
