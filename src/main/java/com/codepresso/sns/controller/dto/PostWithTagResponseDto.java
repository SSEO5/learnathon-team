package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.Post;
import com.codepresso.sns.vo.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
public class PostWithTagResponseDto {
    //Getter and Setter
    private Integer postId;
    private Integer userId;
    private String content;
    private Object tags;

    public PostWithTagResponseDto(Post post) {
        this.postId = post.getPostId();
        this.userId = post.getUserId();
        this.content = post.getContent();
    }

}
