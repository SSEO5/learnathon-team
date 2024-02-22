package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.Post;
import com.codepresso.sns.vo.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class PostPageWithTagResponseDto extends PagePostResponseDto{
    public ArrayList<Tag> tags;

    @JsonIgnore
    public Date updatedAt;
    public PostPageWithTagResponseDto(Post post) {
        super(post);
    }

}
