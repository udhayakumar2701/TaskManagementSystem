package com.Task.TaskManagementSystem.repoClass;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Task.TaskManagementSystem.databaseClass.database;

public interface repo extends JpaRepository<database,Integer> {

     database findByuser(String username);

     database findByemail(String username);

     List<database> findAll();
     

}