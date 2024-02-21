package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagePostResponseDto extends PostResponseDto {
    @JsonIgnore
    public Integer likeCount;
    @JsonIgnore
    public Integer commentCount;
    public String userName;

    public PagePostResponseDto(Post post) {
        super(post);
        this.userName = "TEST"; //controller에서 바꿔야 할수도?
    }
    // Constructors, getters, and setters...
}