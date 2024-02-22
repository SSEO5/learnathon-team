package com.codepresso.sns.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonComponent
public class PostComment {
    //Getter and Setter
    private Integer commentId;
    private Integer postId;
    private Integer userId;
    private String comment;
    private Date createdAt;
    private Date updatedAt;

    //작성 요청
    public PostComment(Integer postId, Integer userId, String comment) {
        this.postId = postId;
        this.userId = userId;
        this.comment = comment;
    }

    public PostComment(PostComment postComment){
        this.commentId = postComment.getCommentId();
        this.postId = postComment.getPostId();
        this.userId = postComment.getUserId();
        this.comment = postComment.getComment();
        this.createdAt = postComment.getCreatedAt();
        this.updatedAt = postComment.getUpdatedAt();
    }

    public PostComment(Integer commentId, Integer postId, Integer userId, String comment, Date createdAt, Date updatedAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    //작성 응답
}
