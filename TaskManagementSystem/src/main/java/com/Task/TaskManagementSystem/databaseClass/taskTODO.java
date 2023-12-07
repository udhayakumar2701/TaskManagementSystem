package com.Task.TaskManagementSystem.databaseClass;

import javax.persistence.*;

import lombok.Data;


@Entity
@Data
@Table(name="taskList")
public class taskTODO {

    public taskTODO(){
status="On Goging";
    }
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name="id")
    private int id;

    @Column(name="userId")
    int userId;

    @Column(name="Task")
    String task;
    
    @Column(name="status")
    String status;
  
    // public String getStatus() {
    //     return this.status;
    // }

    // public void setStatus(String status) {
    //     this.status = status;
    // }
  

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    String date; 

    public int getId() {
        return this.id;
    }


    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    
}
