package com.codepresso.sns.vo;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Date;

public class User {
    Integer id;
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
    LocalDateTime updateAt;

    public User(Integer id, String userName, String email, String password, int postCount, int followingCount, int follwerCount, String introduction, String occupation, Date birthday, String city, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
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
        this.updateAt = updateAt;
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

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getFollwerCount() {
        return follwerCount;
    }

    public void setFollwerCount(int follwerCount) {
        this.follwerCount = follwerCount;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
