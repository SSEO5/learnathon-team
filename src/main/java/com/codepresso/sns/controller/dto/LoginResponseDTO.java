package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.User;
import lombok.Getter;

import java.util.UUID;

@Getter
public class LoginResponseDTO {
    Integer userId;
    String userName;
    String email;
    String token;

    public LoginResponseDTO(User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.token = generateToken();
    }
    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}


