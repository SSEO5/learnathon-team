package com.codepresso.sns.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Tag {
    public Integer tagId;
    public String tagName;

    public Tag(Integer tagId, String tagName){
        this.tagId = tagId;
        this.tagName = tagName;
    }

}
