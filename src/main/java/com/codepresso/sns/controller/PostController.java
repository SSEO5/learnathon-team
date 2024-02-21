package com.codepresso.sns.controller;


import com.codepresso.sns.controller.dto.PagePostResponseDto;
import com.codepresso.sns.controller.dto.PostRequestDto;
import com.codepresso.sns.controller.dto.PostResponseDto;
import com.codepresso.sns.controller.dto.UserPostResponseDto;
import com.codepresso.sns.service.PostService;
import com.codepresso.sns.vo.Post;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
public class PostController {
    private PostService postService;
    public PostController(PostService postService){
        this.postService = postService;
    }

    //Post 작성
    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto postDto){
        Post post = postDto.getPost();
        //username 검증 과정
        if (postService.checkIfMember(post.getUserId())) {
            // Save the post and retrieve the updated Post object
            Post savedPost = postService.savePost(post);
            // Convert the Post object to PostResponseDto and return
            return new PostResponseDto(savedPost);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    //전체 Post 조회 API
    @GetMapping("/posts")
    public Object getPostList(@RequestParam(required = false, defaultValue = "0") Integer page){
        if (page == 0){
            List<Post> postList = postService.getPosts();
            List<PostResponseDto> postResponseDtoList = new ArrayList<>();
            for(Post post: postList){
                postResponseDtoList.add(new PostResponseDto(post));
            }
            return postResponseDtoList; //숨길건 숨기고 아닌건 나오게 수정
        } else {
            //페이지 리턴될 배열
            Map<String, Object> pageResponse = new HashMap<>();

            //페이지 컨텐트
            List<Post> postList = postService.getPostByPage(page, 3);
            List<PagePostResponseDto> postResponseDtoList = new ArrayList<>();
            for(Post post: postList){
                PagePostResponseDto formattedPost = new PagePostResponseDto(post);
                formattedPost.setUserName("User와 동기화 TBD");
                postResponseDtoList.add(formattedPost);
            }

            pageResponse.put("totalPages", (int) Math.ceil(postService.totalPost()/3.0)); //total Post 개수 새야함
            pageResponse.put("page", page);
            pageResponse.put("posts", postResponseDtoList);

            return pageResponse;  //숨길건 숨기고 아닌건 나오게 수정
        }
    }

    //유저별 포스트 조회
    @GetMapping("/user/{userId}/posts")
    public Map getUserPost(@PathVariable Integer userId){
        Map<String, Object> userPostResponse = new HashMap<>();

        //페이지 컨텐트
        List<Post> postList = postService.getPostByUserId(userId);
        List<UserPostResponseDto> userPostResponseDtoList = new ArrayList<>();
        for(Post post: postList){
            UserPostResponseDto formattedPost = new UserPostResponseDto(post);
            userPostResponseDtoList.add(formattedPost);
        }
        userPostResponse.put("userId", userId);
        userPostResponse.put("userName", "User와 동기화 TBD");
        userPostResponse.put("posts", userPostResponseDtoList);

        return userPostResponse;  //숨길건 숨기고 아닌건 나오게 수정
    }

    //포스트 수정
    @PatchMapping("/post/{postId}")
    public PostResponseDto updatePost(@PathVariable Integer postId, @RequestBody PostRequestDto postDto){
// valid postId check
        if (postService.getPostById(postId) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
// userid check
        if (postService.getPostById(postId).getUserId() != postDto.getUserId()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Forbidden");
        }

        Post post = postDto.getPost();
        post.setPostId(postId);
        Post savedPost =  postService.updatePost(post);
        // Convert the Post object to PostResponseDto and return
        return new PostResponseDto(savedPost);
    }

    @DeleteMapping("/post/{postId}")
    public Map deletePost(@PathVariable Integer postId, @RequestBody PostRequestDto postDto){

        // userid check
        if (postDto.getUserId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }
        // valid postId check
        if (postService.getPostById(postId) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        // userid check
        if (postService.getPostById(postId).getUserId() != postDto.getUserId()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Forbidden");
        }
        Post post = postDto.getPost();
        post.setPostId(postId);
        postService.deletePost(post);
        Map<String, String> resMessage = new HashMap<>();
        resMessage.put("message", "Post successfully deleted.");
        return resMessage; //json
    }

}

