package com.codepresso.sns.vo;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Setter
public class User {
    Integer userId;
    String userName;
    String email;
    String password;
    int postCount;
    int followingCount;
    int follwerCount;
    String introduction;
    String occupation;
    Date birthday;
    String city;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public User(Integer id, String userName, String email, String password, int postCount, int followingCount, int follwerCount, String introduction, String occupation, Date birthday, String city, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.userId = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.postCount = postCount;
        this.followingCount = followingCount;
        this.follwerCount = follwerCount;
        this.introduction = introduction;
        this.occupation = occupation;
        this.birthday = birthday;
        this.city = city;
        this.createdAt = createdAt;
        this.updatedAt = updateAt;
    }

    public User(String userName, String email, String password, String introduction, String occupation, Date birthday, String city) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.introduction = introduction;
        this.occupation = occupation;
        this.birthday = birthday;
        this.city = city;
    }

    public User(String userName, String introduction, String occupation, String city) {
        this.userName = userName;
        this.introduction = introduction;
        this.occupation = occupation;
        this.city = city;
    }

    public User() {
    }
}
