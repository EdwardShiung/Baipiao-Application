package com.baipiao.api.users.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baipiao.api.users.User;
import org.locationtech.jts.geom.Point;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class UserDTO implements Serializable {
    private Long id;

    private String email;

    private String userName;

    private String password;

    private String userType;

    private String displayName;

    private String phoneNumber;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public UserDTO() {
    }

    public UserDTO(User user) {
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