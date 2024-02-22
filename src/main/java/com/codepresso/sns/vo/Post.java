package com.codepresso.sns.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
public class Post {
    //Getter and Setter
    private Integer postId;
    private Integer userId;
    private String content;
    private Integer likeCount;
    private Integer commentCount;
    private Date createdAt;
    private Date updatedAt;

    //작성 요청
    public Post(Integer userId, String content) {
        this.userId = userId;
        this.content = content;
    }



    //작성 응답
}
