package com.codepresso.sns.controller.dto;

import com.codepresso.sns.controller.dto.PagePostResponseDto;
import com.codepresso.sns.controller.dto.PostResponseDto;
import com.codepresso.sns.vo.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

import java.util.Date;

@Getter
@Setter
@JsonComponent
@JsonInclude(JsonInclude.Include.ALWAYS)
public class SortedLikedPagePostResponseDto extends PagePostResponseDto {


    public Integer likeCount;
    public boolean likedByUser;

    @JsonIgnore
    public Date updatedAt;

    public SortedLikedPagePostResponseDto(Post post) {
        super(post);
        this.updatedAt = null;
//        this.likeCount = 30;
    }

}
