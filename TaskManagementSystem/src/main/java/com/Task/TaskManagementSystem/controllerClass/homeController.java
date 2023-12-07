package com.Task.TaskManagementSystem.controllerClass;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Task.TaskManagementSystem.databaseClass.database;
import com.Task.TaskManagementSystem.databaseClass.taskTODO;
import com.Task.TaskManagementSystem.databaseClass.userSession;
import com.Task.TaskManagementSystem.repoClass.repoTask;
import com.Task.TaskManagementSystem.serverClass.server;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class homeController {

    private final server service;
    private database user;


    @Autowired
    private userSession userSession;
    
    @Autowired
     loginController login;


    public homeController(server service,loginController login) {
        this.service = service;
       this.login=login;
      
    }
           


    @GetMapping("/login")
    String login(){
        // List<database> arr = service.findAll();
        
        
        return "login";
    }

@GetMapping("/getTime")
@ResponseBody
public String getTime() {
    ZonedDateTime indianTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
    return indianTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
}


@GetMapping("/show")
public String getMethodName(Model model) {
    user = userSession.getUser();
    if (user == null) {
        return "login";
    }
    List<taskTODO> arr = service.taskFindAll(user.getId());
        
  model.addAttribute("users",arr);
  model.addAttribute("show", true);
   model.addAttribute("userIn",true);
    return "home";
}

@PostMapping("/addTask")
public String addTask(@RequestParam("taskToAdd") String taskToAdd,
                      @RequestParam("dueDate") String dueDate,
                      Model model) {
    user = userSession.getUser();
    // System.out.println("user-----------------------------------------"+user);
    if (user == null) {
        return "login";
    }

    // Handle the task data, e.g., save it to a database
    System.out.println("Received task: " + taskToAdd);
    System.out.println("Received due date: " + dueDate);

    taskTODO newTask = new taskTODO();
    newTask.setUserId(user.getId());
    newTask.setTask(taskToAdd);
    newTask.setDate(dueDate);
    service.saveTask(newTask);

    List<taskTODO> arr = service.taskFindAll(user.getId());
    model.addAttribute("users", arr);
     model.addAttribute("userIn",true);
 model.addAttribute("show", true);
    return "home";
}

  



    @GetMapping(value="/")
    public String home(Model model) {
        ZonedDateTime indianTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));

        // Format the time in 12-hour format with seconds
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ss a");
        
        System.out.println("Indian Time: " + indianTime.format(formatter));
        

        
        return "home"; 
    }


    @GetMapping("/completeTask")
 public String statusUploader(@RequestParam int id,Model model) {
    taskTODO task=service.findbyid(id);
    task.setStatus("completed");
    service.saveTask(task);
       user = userSession.getUser();
     //TODO: process POST request
     System.out.println(id);
        List<taskTODO> arr = service.taskFindAll(user.getId());
        model.addAttribute("show", true);
         model.addAttribute("userIn",true);
  model.addAttribute("users",arr);
     return "home";
 }


 @GetMapping("/deleteTask")
 public String postMethodName(@RequestParam int id,Model model) {
    service.delete(id);
       user = userSession.getUser();
     //TODO: process POST request
     System.out.println(id);
        List<taskTODO> arr = service.taskFindAll(user.getId());
        model.addAttribute("show", true);
         model.addAttribute("userIn",true);
        
  model.addAttribute("users",arr);
     return "home";
 }
 


 @GetMapping("/logout")
 public String logout() {
     userSession.setUser(null);
     return "home";
 }


 @GetMapping("/profileShow")
 String showProfile(Model model){
     user = userSession.getUser();
    if(user==null)
    return "login";
    model.addAttribute("user", user);

  return "home";
 }
 
}



