package com.Task.TaskManagementSystem.serverClass;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

import com.Task.TaskManagementSystem.databaseClass.database;
import com.Task.TaskManagementSystem.databaseClass.taskTODO;
import com.Task.TaskManagementSystem.repoClass.repo;
import com.Task.TaskManagementSystem.repoClass.repoTask;

@Service
public class server {
    @Autowired
    repo reposistory;

    @Autowired
    repoTask repoTask;
    
    public database  findUser(String username){
        return reposistory.findByemail(username);
    }
    public void saveData(database db) {
        reposistory.save(db);
    }
    public void deleteUser(database user) {
        reposistory.delete(user);
    }

     @Autowired
    private JavaMailSender javaMailSender;
  
    public void sendOtpEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@SpringBoot.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setReplyTo("no-reply@example.com");
        javaMailSender.send(message);
    }

    public List<database> findAll() {
        
       return  reposistory.findAll();
    }
    public void saveTask(taskTODO db) {
        repoTask.save(db);
    }
    
    public List<taskTODO> taskFindAll(int userId) {
        return  repoTask.findByuserId(userId);
    }
    public void delete(int id) {
        repoTask.deleteById(id);
    }
    public taskTODO findbyid(int id) {
      return  repoTask.findById(id);
    }

   
}
