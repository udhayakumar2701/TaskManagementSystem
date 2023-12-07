package com.Task.TaskManagementSystem.databaseClass;

import lombok.Data;



import javax.persistence.*;

import org.springframework.stereotype.Component;

@Entity
@Data
@Table(name="task")
@Component
public class database {
    @Id
   @Column(name="id")
   @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name="firstName")
    String firstName;

    @Column(name="lastName")
    String lastName;
    @Column(name="email")
    String email;
    @Column(name="Date")
    String date;

    @Column(name="phoneNo")
    String phoneNo;

    @Column(name="Gender")
    String gender;

    @Column(name="user")
    String user;

    @Column(name = "password")
    String password;

    public database() {
        
        this.firstName ="";
        this.lastName = "";
        this.email = "";
        this.date = "";
        this.phoneNo = "91";
        this.gender = "";
        this.user = "";
        this.password = "";
    }

    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   

   

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
    
        this.gender = gender;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   


    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

 

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
     
    
}
