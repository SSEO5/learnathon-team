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
public class UserPostResponseDto extends PostResponseDto {
    @JsonIgnore
    public Integer likeCount;
    @JsonIgnore
    public Integer commentCount;
    @JsonIgnore
    public String userName;

    public UserPostResponseDto(Post post) {
        super(post);
    }
    // Constructors, getters, and setters...
}
