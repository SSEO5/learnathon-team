package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.User;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class UserInfoDetailResponseDTO {
    Integer userId;
    String userName;
    String email;
    int postcount;
    int followingCount;
    int follwerCount;
    String introduction;
    String occupation;
    String birthday;
    String city;
    LocalDateTime createdAt;
    LocalDateTime updateAt;

    public UserInfoDetailResponseDTO(User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.postcount = user.getPostCount();
        this.followingCount = user.getFollowingCount();
        this.follwerCount = user.getFollwerCount();
        this.introduction = user.getIntroduction();
        this.occupation = user.getOccupation();
        //this.birthday = user.getBirthday();
        this.birthday = formatDate(user.getBirthday());
        this.city = user.getCity();
        this.createdAt = user.getCreatedAt();
        this.updateAt = user.getUpdatedAt();
    }

    private String formatDate(Date birthday) {
        if (birthday != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(birthday);
        }
        return null;
    }
}
