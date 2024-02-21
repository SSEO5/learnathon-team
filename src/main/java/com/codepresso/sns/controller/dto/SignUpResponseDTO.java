package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.User;
import lombok.Getter;

import java.util.Date;

@Getter
public class SignUpResponseDTO {
    Integer userId;
    String userName;
    String email;
    String introduction;
    String occupation;
    Date birthday;
    String city;

    public SignUpResponseDTO(User user){
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.introduction = user.getIntroduction();
        this.occupation = user.getOccupation();
        this.birthday = user.getBirthday();
        this.city = user.getCity();
    }
}
