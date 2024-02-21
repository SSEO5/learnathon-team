package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.User;
import lombok.Getter;

@Getter
public class UserInfoSummaryDTO {
    Integer userId;
    String userName;
    int postcount;
    int followingCount;
    int follwerCount;
    String introduction;
    String occupation;

    public UserInfoSummaryDTO(User user){
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.postcount = user.getPostCount();
        this.followingCount = user.getFollowingCount();
        this.follwerCount = user.getFollwerCount();
        this.introduction = user.getIntroduction();
        this.occupation = user.getOccupation();
    }
}
