package com.codepresso.sns.service;

import com.codepresso.sns.mapper.PostMapper;
import com.codepresso.sns.vo.Post;
import com.codepresso.sns.vo.PostComment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@Transactional

public class PostService {
    //POST 작성

    //회원 여부 확인
    public boolean checkIfMember(int userId){
        return postMapper.checkMemberExists(userId)==1;
    }

    public int totalPost(){
        return postMapper.countPost();
    }

    public Post savePost(Post post){
        Post newPost = new Post(post.getUserId(), post.getContent());
        postMapper.savePost(newPost);
        // Retrieve the updated Post object after saving
        Post savedPost = postMapper.findOne(newPost.getPostId());
        System.out.println(savedPost.getPostId()); // Ensure postId is not null
        postMapper.newUserPost(post.getUserId());
        return savedPost;
    }


    private PostMapper postMapper;
    public PostService(PostMapper postMapper){
        this.postMapper = postMapper;
    }

    public List<Post> getPosts() {
        return postMapper.findAll();
    }

    public List<Post> getPostByPage(Integer page, Integer size) {
        return postMapper.findByPage((page-1)*size);
    }

    public List<Post> getPostByUserId(Integer userId) {
        return postMapper.findByUserId(userId);
    }


    public Post getPostById(Integer id) {
        return postMapper.findOne(id);
    }


    public Post updatePost(Post post){
        Post editedPost;
        postMapper.update(post);
        editedPost = postMapper.findOne(post.getUserId());
        return editedPost;
    }

    public boolean deletePost(Post post){
        Integer result = postMapper.delete(post);
        return result ==1;
    }

//////////////////////////////////// 여기부터 댓글 관련/////////////////////////////////////
    public boolean checkIfWritten(int postId){
    return postMapper.findOneExists(postId)==1;
}

    public PostComment savePostComment(PostComment postComment){
        PostComment newPostComment = new PostComment(postComment.getPostId(), postComment.getUserId(), postComment.getComment());
        postMapper.savePostComment(newPostComment);
        PostComment savedComment = postMapper.findCommentByCommentId(newPostComment.getCommentId());
        return newPostComment;
    }

//    public Post savePost(Post post){
//        Post newPost = new Post(post.getUserId(), post.getContent());
//        postMapper.savePost(newPost);
//        // Retrieve the updated Post object after saving
//        Post savedPost = postMapper.findOne(newPost.getPostId());
//        System.out.println(savedPost.getPostId()); // Ensure postId is not null
//        postMapper.newUserPost(post.getUserId());
//        return savedPost;
//    }

    public List<PostComment> getPostCommentByPostId(Integer postId) {
        return postMapper.findCommentByPostId(postId);
    }
    public PostComment getPostCommentByCommentId(Integer commentId) {
        return postMapper.findCommentByCommentId(commentId);
    }

    //////////////////////////////////// 여기부터 좋아요 관련/////////////////////////////////////
    public Integer checkLike(Integer postId, Integer userId) {return postMapper.likePost(postId, userId);}

    public Integer checkUnlike(Integer postId, Integer userId) {
        if (postMapper.existsLike(userId, postId) != 1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Like not found");
        }
        return postMapper.unlikePost(postId, userId);
    }

}
