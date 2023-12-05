package com.Task.TaskManagementSystem.controllerClass;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Task.TaskManagementSystem.databaseClass.database;
import com.Task.TaskManagementSystem.serverClass.server;

@Controller
public class registerController {

    server service;
    loginController login;
    int otpNumber;

    
    public registerController(server service, loginController login) {
        this.service = service;
        this.login = login;
    }

    database DB;

    @PostMapping("/registed")
    String register(@ModelAttribute("database") database db, @RequestParam("conformPassword") String password,
            Model model) {

        if (password.length() < 7) {
            model.addAttribute("db", db);
            db.setPassword("");
            model.addAttribute("Password", "Weak One");
            return "register";
        }

        if (!db.getPassword().equals(password)) {
            db.setPassword("");
            model.addAttribute("Password", "Password doesn't match");
            model.addAttribute("db", db);

            return "register";
        }

        DB = service.findUser(db.getEmail());
        if (DB != null) {
            // Set the password from the existing user to the form data
            db.setPassword("");

            // Now, you can use the existing user's data in the form
            model.addAttribute("db", db);
            System.out.println(db.getPhoneNo());
            model.addAttribute("Notification", "User already exists");
            System.out.println(db.toString());
            return "register";
        }
        DB = db;
        try {
            model.addAttribute("database", db);
            model.addAttribute("checkEmail", true);
            sendOtp(db);

            return "register";
        } catch (Exception e) {
            model.addAttribute("database", DB);
            model.addAttribute("checkEmail", true);
            model.addAttribute("Notification", "Wrong Email ");
            return "register";

        }

        // //System.out.println( db.setPhoneNo(request.getParameter("phoneNo")));
        // model.addAttribute("db",db);
        // model.addAttribute("Notification","User already exist");
        // return "register";

    }

    @PostMapping("/regis")
    public String checkOtp(@RequestParam("otp") String otp, @ModelAttribute("database") database db, Model model) {
        if (otp.equals(String.valueOf(otpNumber))) {
            service.saveData(DB);
            return "login";
        } else {
            model.addAttribute("database", db);
            model.addAttribute("checkEmail", true);
            model.addAttribute("Notification", "OTP is Wrong ");
            return "register";
        }
    }

    @GetMapping("/resendOTP")
    String resendOtp(Model model) {
        try {
            model.addAttribute("database", DB);
            model.addAttribute("checkEmail", true);
            sendOtp(DB);
            return "register";
        } catch (Exception e) {
            model.addAttribute("database", DB);
            model.addAttribute("checkEmail", true);
            model.addAttribute("Notification", "Wrong Email ");
            return "register";
        }

    }

    private void sendOtp(database user) throws Exception {

        String greeting = "Hi " + user.getUser() + ",";
        String msg1 = "We received a request to access your Google Account " + user.getEmail() +
                " through your email address. Your Google verification code is:\n ";
        String otp = String.valueOf(otpNumber = login.otpGenerator());
        String msg2 = "\n\nIf you did not request this code, it is possible that someone else is trying to access the Google Account "
                +
                user.getEmail() + ". Do not forward or give this code to anyone.";
        String msg3 = "\n\nYou received this message because this email address is listed as the recovery email for the Google Account "
                +
                user.getEmail()
                + ". If that is incorrect, please click here to remove your email address from that Google Account.";

        service.sendOtpEmail(user.getEmail(), "Verification OTP CODE", greeting + "\n" + msg1 + otp + msg2 + msg3);
    }

}