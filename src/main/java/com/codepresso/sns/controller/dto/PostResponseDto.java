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
public class PostResponseDto {
    Integer postId;
    Integer userId;
    String userName;
    String content;
    Integer likeCount;
    Integer commentCount;
    Date createdAt;
    Date updatedAt;

    //작성 응답
    public PostResponseDto(Post post){
        this.postId = post.getPostId();
        this.userId = post.getUserId();
        this.userName = "USERNAME";
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentCount();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}

