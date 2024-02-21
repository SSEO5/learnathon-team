package com.codepresso.sns.service;

import com.codepresso.sns.mapper.PostMapper;
import com.codepresso.sns.vo.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
