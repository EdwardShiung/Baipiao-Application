package com.baipiao.api.users.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baipiao.api.users.User;
import lombok.Data;

@Data
public class UserCreateDTO implements Serializable {
  private Long id;

  private String email;

  private String userName;

  private String password;

  private String userType;

  private String displayName;

  private String phoneNumber;

  private LocalDateTime createdAt;

    
    public UserCreateDTO() {
    }

    public UserCreateDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.userType = user.getUserType();
        this.displayName = user.getDisplayName();
        this.phoneNumber = user.getPhoneNumber();
        this.createdAt = user.getCreatedAt();

    }
}