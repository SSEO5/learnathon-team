package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SortedLikedPagePostResponseDto {
    public Integer postId;
    public Integer userId;
    public String userName;
    public String content;
    public Date createdAt;
    public Integer likeCount;
    public boolean likedByUser;

    //작성 응답
    public SortedLikedPagePostResponseDto(Post post){
        this.postId = post.getPostId();
        this.userId = post.getUserId();
        this.userName = "USERNAME";
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.createdAt = post.getCreatedAt();
//        this.likedByUser = post.getL();
    }
}

