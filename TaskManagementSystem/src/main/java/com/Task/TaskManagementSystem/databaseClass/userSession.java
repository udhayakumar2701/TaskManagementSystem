package com.Task.TaskManagementSystem.databaseClass;

import org.springframework.stereotype.Component;

@Component
public class userSession {
      private database user;

    public database getUser() {
        return user;
    }

    public void setUser(database user) {
        this.user = user;
    }
}
