package com.Task.TaskManagementSystem.controllerClass;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.Task.TaskManagementSystem.databaseClass.database;
import com.Task.TaskManagementSystem.databaseClass.taskTODO;
import com.Task.TaskManagementSystem.databaseClass.userSession;

import com.Task.TaskManagementSystem.serverClass.server;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class homeController {

    private final server service;
    private database user;
public static String upload = System.getProperty("user.dir") + "\\TaskManagementSystem\\src\\main\\resources\\static";

    @Autowired
     userSession userSession;
    
    @Autowired
     loginController login;


    public homeController(server service,loginController login) {
        this.service = service;
       this.login=login;
       try{
        new File(upload).mkdir();}
    catch(Exception e){}
      
    }



    @GetMapping("/profile")
    public String returnProfile(Model model) {
       
        if(userSession.getUser()==null)
        return "login";

         userSession.setUser(service.findUser(userSession.getUser().getEmail()));
        //   model.addAttribute("user", data);
        //     model.addAttribute("profiles", data.getProfile());
        model.addAttribute("profiles", (userSession.getUser().getProfile()));
        System.out.println(userSession.getUser().getProfile()+"--------------------------------------------------------------");
        model.addAttribute("userObj", userSession.getUser());
         model.addAttribute("user", userSession.getUser());
         model.addAttribute("totalTasksCompleted", service.countByStatusAndId("completed",userSession.getUser().getId()));
         System.out.println( userSession.getUser().hashCode());
        System.out.println("in profile");
        return "profile";

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
    model.addAttribute("users", user);
        model.addAttribute("users", user);
  model.addAttribute("userTask",arr);
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
    model.addAttribute("users", user);
    model.addAttribute("userTask", arr);
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
        
       user= userSession.getUser();
       if(user==null){


        
            System.out.println("------------------------------------------user null -----------------------------------------------");
       return "home";
    }
    model.addAttribute("users", user);
        
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
        model.addAttribute("users", user);
        model.addAttribute("show", true);
         model.addAttribute("userIn",true);
  model.addAttribute("userTask",arr);
     return "home";
 }


 @GetMapping("/deleteTask")
 public String postMethodName(@RequestParam int id,Model model) {
    service.delete(id);
       user = userSession.getUser();
     //TODO: process POST request
     System.out.println(id);
        List<taskTODO> arr = service.taskFindAll(user.getId());
        model.addAttribute("users", user);
        model.addAttribute("show", true);
         model.addAttribute("userIn",true);
        
  model.addAttribute("userTask",arr);
     return "home";
 }
 


 @GetMapping("/logout")
 public String logout() {
     userSession.setUser(null);
     return "home";
 }


 @PostMapping("/uploadImage")
public String uploadImage(@RequestParam("userImage") MultipartFile file, Model model) {
    // database user = new database();
    database data=userSession.getUser();
    if (data == null) {
        return "login";
    }
        try {
            data=userSession.getUser();
           // it used to get the image and store in data base
            //StringBuilder filenames = new StringBuilder();
            
            String filename = data.getId() + file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4);
            System.out.println("filename:"+filename);
            Path fileNameAndpath = Paths.get(upload, filename);
            System.out.println("fileNameAndpath :"+fileNameAndpath);
            Files.write(fileNameAndpath, file.getBytes());
            System.out.println(upload+"---------------------------------------");
            // data.setStudname(name);
            data.setProfile(filename);
            
            service.saveData(data);
            
           // System.out.println(data.hashCode() +" ---------------------------"+(data=service.findUser(data.getEmail())).hashCode());

             
            //setAttributes(model);
            model.addAttribute("user",data);
            model.addAttribute("profiles", data.getProfile());
            return "redirect:/profile";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }


    @PostMapping("/changeDetials")
    public String changeDetails(@ModelAttribute("userObj") database obj) {
        //TODO: process POST request
        if(userSession.getUser()==null)
        return "login";
       
        System.out.println(obj);
        obj.setId(userSession.getUser().getId());
        obj.setPassword(userSession.getUser().getPassword());
        obj.setProfile(userSession.getUser().getProfile());
        service.saveData(obj);
        return "redirect:/profile";
    }
    


}