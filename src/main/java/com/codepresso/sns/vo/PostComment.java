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

    //작성 응답
}
