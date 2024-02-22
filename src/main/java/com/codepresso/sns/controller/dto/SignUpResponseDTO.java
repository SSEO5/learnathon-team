package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.User;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class SignUpResponseDTO {
    Integer userId;
    String userName;
    String email;
    String introduction;
    String occupation;
    String birthday;
    String city;

    public SignUpResponseDTO(User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.introduction = user.getIntroduction();
        this.occupation = user.getOccupation();
        //this.birthday = user.getBirthday();
        this.birthday = formatDate(user.getBirthday());
        this.city = user.getCity();
    }

    private String formatDate(Date birthday) {
        if (birthday != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(birthday);
        }
        return null;
    }

}
