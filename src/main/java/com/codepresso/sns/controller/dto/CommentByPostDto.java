package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.PostComment;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class CommentByPostDto{
    private Integer commentId;
    private Integer postId;
    private Integer userId;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
    private String userName;

    public CommentByPostDto(PostComment postComment) {
        this.commentId = postComment.getCommentId();
        this.postId = postComment.getPostId();
        this.userId = postComment.getUserId();
        this.comment = postComment.getComment();
        this.createdAt = postComment.getCreatedAt();
        this.updatedAt = postComment.getUpdatedAt();
        this.userName = "NEED WORK";
    }
}
