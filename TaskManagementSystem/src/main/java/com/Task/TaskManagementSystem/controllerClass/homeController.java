package com.Task.TaskManagementSystem.controllerClass;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Task.TaskManagementSystem.databaseClass.database;
import com.Task.TaskManagementSystem.serverClass.server;
@Controller
public class homeController {

    private final server service;

    public homeController(server service) {
        this.service = service;
    }

    @GetMapping("/login")
    String login(){
        return "login";
    }



    @GetMapping(value="/")
    public String home(Model model) {
        
        List<database> arr = service.findAll();
        for (database d : arr) {
            System.out.println(d.getUser());
        }

        model.addAttribute("users", arr);
        return "home"; // Make sure this matches the actual name of your Thymeleaf template
    }
}
