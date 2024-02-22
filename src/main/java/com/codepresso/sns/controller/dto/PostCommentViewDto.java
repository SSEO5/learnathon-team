package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.Post;
import com.codepresso.sns.vo.PostComment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostCommentViewDto {
    private Integer commentId;
    private Integer postId;
    private Integer userId;
    private String comment;
    private Date createdAt; // Assuming createdAt is of type LocalDateTime

    public PostCommentViewDto(PostComment postComment) {
        this.commentId = postComment.getCommentId();
        this.postId = postComment.getPostId();
        this.userId = postComment.getUserId();
        this.comment = postComment.getComment();
        this.createdAt = postComment.getCreatedAt();
    }
}

