package com.Task.TaskManagementSystem.repoClass;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Task.TaskManagementSystem.databaseClass.taskTODO;

public interface repoTask  extends JpaRepository<taskTODO,Integer> {

    List<taskTODO> findByuserId(int userId);
    
    taskTODO findById(int id);
    long countByStatusAndUserId(String status, int userId);
}
