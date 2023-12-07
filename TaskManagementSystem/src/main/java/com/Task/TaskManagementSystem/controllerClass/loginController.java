package com.Task.TaskManagementSystem.controllerClass;

import java.util.List;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.Task.TaskManagementSystem.databaseClass.database;
import com.Task.TaskManagementSystem.databaseClass.userSession;
import com.Task.TaskManagementSystem.serverClass.server;

@Controller
@SessionAttributes("user")
public class loginController {




  @Autowired
private userSession usersession;


  server service;
  database db = new database();

 
  public loginController(server service) {
    this.service = service;
  }

  public int otpGenerator() {
    Random random = new Random();
    return (random.nextInt(900000) + 100000);
  }

  int count = 0;

  public database user;

  @GetMapping("/log")
  public String Login() {
      return "login";
  }
  


  @GetMapping("/forgot")
  public String Forgot(Model model) {
    model.addAttribute("showEmailForm", true);
    return "forgotPassword";
  }

  @GetMapping("/register")
  public String Register(Model model) {

    model.addAttribute("database", new database());
    return "register";
  }


  // @PostMapping("/login")
  // public String loginCheck(Model model, @RequestParam("user") String username,
  //     @RequestParam("password") String password,@ModelAttribute("user") database user) {
     @PostMapping("/login")
    public String loginCheck(Model model, @RequestParam("user") String username,
        @RequestParam("password") String password) {

    user = service.findUser(username);
    if (user == null) {
      model.addAttribute("userNull", "User Not found");
      return "login";
    }
    if (user != null) {
      if (!password.equals(user.getPassword())) {
        count++;
        if (count >= 2) {
          model.addAttribute("wrongPassword", true);
        }
        model.addAttribute("check", "Wrong password");
        return "login";
      }
    }
 
    usersession.setUser(user);

    //  List<database> arr = service.findAll();
    //     for (database d : arr) {
    //         System.out.println(d.getUser());
    //     }
      
    //     model.addAttribute("userData", user);

         model.addAttribute("users", user);

        return "home";
   // return "home";

  }

  int otpNumber;

  @PostMapping(value = "/forgot-password")
  public String forgotPassword(@RequestParam("email") String email, Model model) {
    // TODO: process POST request
    user = service.findUser(email);
    if (user != null) {
      model.addAttribute("showOtpForm", true);
      sendOtp();
      return "forgotPassword";
    } else {
      model.addAttribute("userCheck", true);
      return "forgotPassword";
    }
  }

  private void sendOtp() {
    String greeting = "Hi " + user.getUser() + ",";
    String msg1 = "We received a request to access your Google Account " + user.getEmail() +
        " through your email address. Your Google verification code is:\n ";
    String otp = String.valueOf(otpNumber = otpGenerator());
    String msg2 = "\n\nIf you did not request this code, it is possible that someone else is trying to access the Google Account "
        +
        user.getEmail() + ". Do not forward or give this code to anyone.";
    String msg3 = "\n\nYou received this message because this email address is listed as the recovery email for the Google Account "
        +
        user.getEmail()
        + ". If that is incorrect, please click here to remove your email address from that Google Account.";

    service.sendOtpEmail(user.getEmail(), "Verification OTP CODE", greeting + "\n" + msg1 + otp + msg2 + msg3);
  }

  @PostMapping(value = "/verify-otp")
  public String VerifyOtp(@RequestParam("otp") String otp, Model model) {

    if (otpNumber == Integer.parseInt(otp)) {
      model.addAttribute("showPasswordForm", true);
      return "forgotPassword";
    } else {
      model.addAttribute("showOtpForm", true);
      model.addAttribute("otpWorng", "Entered otp is Wrong");
      // sendOtp();
      return "forgotPassword";
    }

  }

  @GetMapping(value = "/resendOtp")
  public String getMethodName(Model model) {
    model.addAttribute("showOtpForm", true);
    model.addAttribute("otpWorng", "Entered otp is Wrong");
    sendOtp();
    return "forgotPassword";

  }

  @PostMapping(value = "/change-password")
  public String changepassword(@RequestParam("newPassword") String password, Model model) {
    user.setPassword(password);
    service.saveData(user);
    model.addAttribute("userNull", "Password changed sucessfully plz Login");
    return "login";
  }


 

}
