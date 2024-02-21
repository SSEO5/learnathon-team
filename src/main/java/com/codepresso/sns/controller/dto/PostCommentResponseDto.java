package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.Post;
import com.codepresso.sns.vo.PostComment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentResponseDto {
    Integer commentId;
    Integer postId;
    Integer userId;
    String comment;
    Date createdAt;
    @JsonIgnore
    Date updatedAt;

    public PostCommentResponseDto(PostComment postComment) {
    }

//    //작성 응답
//    public PostCommentResponseDto(PostComment post){
//        this.commentId = post.getCommentId();
//        this.postId = post.getPostId();
//        this.userId = post.getUserId();
//        this.comment = post.getComment();
//        this.createdAt = post.getCreatedAt();
//        this.updatedAt = post.getUpdatedAt();
//    }
}

