package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.Post;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostRequestDto {
    Integer userId;
    String title;
    String content;
    String username;

    //작성 요청
    public Post getPost(){
        return new Post(this.userId, this.content);
    }
}
