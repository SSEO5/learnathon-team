package com.codepresso.sns.controller;


import com.codepresso.sns.controller.dto.*;
import com.codepresso.sns.service.PostService;
import com.codepresso.sns.vo.Post;
import com.codepresso.sns.vo.PostComment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        if (post.getUserId() == null || post.getContent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId and content are required");
        }
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
        if (page == 0){ //페이지 없이
            Map<String, Object> res = new HashMap<>();
            List<Post> postList = postService.getPosts();
            List<PagePostResponseDto> postResponseDtoList = new ArrayList<>();
            for(Post post: postList){
                postResponseDtoList.add(new PagePostResponseDto(post));
            }
            res.put("posts", postResponseDtoList);
            return res;
        } else { //페이지 적용
            //페이지 리턴될 배열
//            Map<String, Object> pageResponse = new HashMap<>();

            //페이지 컨텐트
            List<Post> postList = postService.getPostByPage(page, 3);
            List<PagePostResponseDto> postResponseDtoList = new ArrayList<>();
            for(Post post: postList){
                PagePostResponseDto formattedPost = new PagePostResponseDto(post);
                formattedPost.setUserName("User와 동기화 TBD");
                postResponseDtoList.add(formattedPost);
            }

            @Getter
            @Setter
            class CustomPostResponse {
                private List<PagePostResponseDto> posts;
                private int page;
                private int totalPages;

                // Constructors, getters, and setters
            }

            CustomPostResponse pageResponse = new CustomPostResponse();

            // Put values into map with adjusted order
            pageResponse.setPosts(postResponseDtoList);
            pageResponse.setPage(page);
            pageResponse.setTotalPages((int) Math.ceil(postService.totalPost()/3.0));

            return pageResponse;  //숨길건 숨기고 아닌건 나오게 수정
        }
    }

    //유저별 포스트 조회
    @GetMapping("/user/{userId}/posts")
    public Object getUserPost(@PathVariable Integer userId){
        Map<String, Object> userPostResponse = new HashMap<>();

        //페이지 컨텐트
        List<Post> postList = postService.getPostByUserId(userId);
        List<UserPostResponseDto> userPostResponseDtoList = new ArrayList<>();
        for(Post post: postList){
            UserPostResponseDto formattedPost = new UserPostResponseDto(post);
            userPostResponseDtoList.add(formattedPost);
        }

        @Getter
        @Setter
        class CustomPostResponse {
            private int userId;
            private String userName;
            private List<UserPostResponseDto> posts;
        }

        CustomPostResponse pageResponse = new CustomPostResponse();

        // Put values into map with adjusted order
        pageResponse.setPosts(userPostResponseDtoList);
        pageResponse.setUserId(userId);
        pageResponse.setUserName("User와 동기화 TBD");

        return pageResponse;  //숨길건 숨기고 아닌건 나오게 수정
    }

    //포스트 수정
    @PatchMapping("/post/{postId}")
    public PagePostResponseDto updatePost(@PathVariable Integer postId, @RequestBody PostRequestDto postDto){
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
        return new PagePostResponseDto(savedPost);
    }

    //포스트 삭제
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

    //////////////////////////////////// 여기부터 댓글 관련/////////////////////////////////////
    @PostMapping("/post/{postId}/comment")
    public PostComment createPostComment(@PathVariable Integer postId, @RequestBody PostCommentRequestDto postCommentDto){
        PostComment postComment = postCommentDto.getPostComment();
//        System.out.println("data: ");
//        System.out.println(postComment.getComment());
//        System.out.println(postComment.getPostId());

        postComment.setPostId(postId);
//        return new PostCommentResponseDto(postComment);
//        post 검증 과정
        if (postService.checkIfWritten(postId)) { //Post가 존재하면
            // Save the post comment and retrieve the updated Post object
            PostComment savedPostComment = postService.savePostComment(postComment);
            return savedPostComment;
        } else { //존재하지 않으면 그냥 뱉는다
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }

    //포스트별 코멘트 조회
    @GetMapping("/post/{postId}/comments")
    public Map getPostComment(@PathVariable Integer postId){
        if (postService.checkIfWritten(postId)) { //Post가 존재하면
            List<PostComment> postCommentList = postService.getPostCommentByPostId(postId);
            List<CommentByPostDto> userPostResponseDtoList = new ArrayList<>();
            for(PostComment postComment: postCommentList){
                CommentByPostDto formattedPostComment = new CommentByPostDto(postComment);
                userPostResponseDtoList.add(formattedPostComment);
            }
            Map<String, Object> CommentsList = new HashMap<>();
            CommentsList.put("comments", userPostResponseDtoList);
            return CommentsList;  //숨길건 숨기고 아닌건 나오게 수정
        } else { //존재하지 않으면 그냥 뱉는다
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        //페이지 컨텐트
    }

    //////////////////////////////////// 여기부터 좋아요 관련/////////////////////////////////////
    @PostMapping("/post/{postId}/like")
    public ResponseEntity likePost(@PathVariable Integer postId, @RequestBody PostRequestDto postDto){
    //valid postId check -> 404
        if (postService.getPostById(postId) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        } else {
            Integer userId = postDto.getUserId();
            try {
                postService.checkLike(postId, userId);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Already Liked");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"Like successfully added to the post.\"}");
        }

    }

    @DeleteMapping("/post/{postId}/like")
    public ResponseEntity unlikePost(@PathVariable Integer postId, @RequestBody PostRequestDto postDto){
        //valid postId check -> 404
        Integer userId = postDto.getUserId();
        if(userId == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Important factor missing");
        } else if (postService.getPostById(postId) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        } else {
//            Integer userId = postDto.getUserId();
            postService.checkUnlike(postId, userId);
            try {
//                postService.checkUnlike(postId, userId);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Already Liked");
            }
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Like successfully removed from the post.\"}");
        }

    }
}

