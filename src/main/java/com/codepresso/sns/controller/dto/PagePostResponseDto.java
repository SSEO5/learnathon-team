package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"likeCount", "commentCount"})
public class PagePostResponseDto extends PostResponseDto {
//    @JsonIgnore
//    public Integer likeCount;
    @JsonIgnore
    public Integer commentCount;


    public PagePostResponseDto(Post post) {
        super(post);
    }
    // Constructors, getters, and setters...
}
