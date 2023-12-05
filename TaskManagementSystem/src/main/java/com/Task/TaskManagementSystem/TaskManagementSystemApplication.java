package com.Task.TaskManagementSystem;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import com.Task.TaskManagementSystem.serverClass.server;
@Controller
@SpringBootApplication
@ComponentScan("com.Task.TaskManagementSystem")
public class TaskManagementSystemApplication {

server service;
	
	public static void main(String[] args) {
		SpringApplication.run(TaskManagementSystemApplication.class, args);
	}


}
