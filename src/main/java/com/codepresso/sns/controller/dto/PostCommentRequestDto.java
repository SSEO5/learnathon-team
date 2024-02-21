package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.Post;
import com.codepresso.sns.vo.PostComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentRequestDto {
    Integer postId;
    Integer userId;
    String comment;
    String username;
    //작성 요청
    public PostComment getPostComment(){
        return new PostComment(this.postId, this.userId, this.comment);
    }
    public PostCommentRequestDto(PostComment postComment){
        this.postId = postComment.getPostId();
        this.userId = postComment.getUserId();
        this.comment = postComment.getComment();
        this.username = "MEED WORK HERE TOO";
    }
}
