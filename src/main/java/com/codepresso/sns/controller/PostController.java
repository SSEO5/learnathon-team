package com.codepresso.sns.controller;


import com.codepresso.sns.controller.dto.PagePostResponseDto;
import com.codepresso.sns.controller.dto.PostRequestDto;
import com.codepresso.sns.controller.dto.PostResponseDto;
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
                postResponseDtoList.add(new PagePostResponseDto(post));
            }

            pageResponse.put("totalPages", (int) Math.ceil(postService.totalPost()/3.0)); //total Post 개수 새야함
            pageResponse.put("page", page);
            pageResponse.put("posts", postResponseDtoList);

            return pageResponse;  //숨길건 숨기고 아닌건 나오게 수정
        }
    }

    //유저별 포스트 조회
    @GetMapping("/user/{userId}/posts")
    public List<PostResponseDto> getUserPost(@PathVariable Integer userId){
        List<Post> postList = postService.getPostByUserId(userId);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for(Post post: postList){
            postResponseDtoList.add(new PostResponseDto(post));
        }
        return postResponseDtoList; //숨길건 숨기고 아닌건 나오게 수정
    }

    //포스트 수정

    @PatchMapping("/post/{postId}")
    public String updatePost(@PathVariable Integer postId, @RequestBody PostRequestDto postDto){
//        userid check
        Post post = postDto.getPost();
        post.setPostId(postId);
        postService.updatePost(post);
        return "success";
    }

    @DeleteMapping("/post/{postId}")
    public String deletePost(@PathVariable Integer postId, @RequestBody PostRequestDto postDto){
//        userid check -> sql delete 전 체크
        Post post = postDto.getPost();
        post.setPostId(postId);
        postService.deletePost(post);
        return "success"; //json
    }

}

