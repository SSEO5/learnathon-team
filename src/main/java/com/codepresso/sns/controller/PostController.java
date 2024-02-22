package com.codepresso.sns.controller;


import com.codepresso.sns.controller.dto.*;
import com.codepresso.sns.service.PostService;
import com.codepresso.sns.service.UserService;
import com.codepresso.sns.vo.Post;
import com.codepresso.sns.vo.PostComment;
import com.codepresso.sns.vo.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Controller
@RestController
public class PostController {
    private PostService postService;
    private UserService userService;
    public PostController(UserService userService, PostService postService){
        this.userService = userService;
        this.postService = postService;
    }

    //Post 작성
    @PostMapping("/post")
    public ResponseEntity createPost(@RequestBody PostRequestDto postDto){
        Post post = postDto.getPost();

        if (post.getUserId() == null || post.getContent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId and content are required");
        }
        //username 검증 과정
        if (postService.checkIfMember(post.getUserId())) {
            //tag => postId 가져온 후 나와야함
            // Tag 단어들 추출 후 본래 본문에서는 제거 처리
            String s = post.getContent();
            String[] tags = s.split("\\#", 0);
            post.setContent(tags[0].substring(0, tags[0].length() - 1));

            Post savedPost = postService.savePost(post);

            PostWithTagResponseDto response = new PostWithTagResponseDto(post);

            //Tag 있는지 확인 -> 있고 없고에 따라 리턴하는 형태 다름
            if (tags.length > 1 ) { //Tag가 있다면
                tags = Arrays.copyOfRange(tags, 1, tags.length); //Tag만 있는 Array로 카피해서 온전한 문자열로 처리
                ArrayList<Tag> tagArray = new ArrayList<>();
                for (int i = 0; i< tags.length; i++) { //각 태그에 대해
                    String sub_string = tags[i];
                    if (sub_string.endsWith(", ")) { //구분자인 , 재거
                        tags[i] = sub_string.substring(0, sub_string.length() - 2);
                    }

                    //해당 Tag 있는지 확인 후 삽입
                    if (postService.findTag(tags[i]) == null){
                        postService.tagInsertion(tags[i]);
                    }

                    // (tagId, postId) 주입
                    postService.tagPostInsertion(postService.findTag(tags[i]), savedPost.getPostId());

                    //dto에 들어갈 tag 배열에 추가
                    tagArray.add(new Tag(postService.findTag(tags[i]), tags[i]));

                }
                //dto에 tag 배열 저장
                response.setTags(tagArray);
                response.setPostId(savedPost.getPostId());
                return ResponseEntity.status(HttpStatus.CREATED).body(response); //새로운 dto 만들어서 수정하면 됨.
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
            }

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

    }

    //전체 Post 조회 API
    @GetMapping("/posts")
    public Object getPostList(@RequestParam(required = false, defaultValue = "0") Integer page){
        if (page == 0){ //페이지 없이

//            태그 적용 전
//            Map<String, Object> res = new HashMap<>();
//            List<Post> postList = postService.getPosts();
//            List<PagePostResponseDto> postResponseDtoList = new ArrayList<>();
//            for(Post post: postList){
//                PagePostResponseDto formattedPost = new PagePostResponseDto(post);
//                System.out.println(userService.getUserById(post.getUserId()).getUserName());
//                formattedPost.setUserName(userService.getUserById(post.getUserId()).getUserName());
//                postResponseDtoList.add(formattedPost);
//            }
//            res.put("posts", postResponseDtoList);
//            return res;

//            태그 적용 후
            Map<String, Object> res = new HashMap<>();
            List<Post> postList = postService.getPosts();
            List<PostPageWithTagResponseDto> postResponseDtoList = new ArrayList<>();
            for(Post post: postList){
                PostPageWithTagResponseDto formattedPost = new PostPageWithTagResponseDto(post);
                formattedPost.setUserName(userService.getUserById(post.getUserId()).getUserName());
                //여기서 태그 관련 기능 구현
                ArrayList<Tag> tagArray;
                tagArray = postService.findPostTag(post.getPostId());
                for(Tag tag: tagArray){
                    System.out.println(tag.getTagName());
                }
                formattedPost.setTags(tagArray);
                postResponseDtoList.add(formattedPost);
            }
            res.put("posts", postResponseDtoList);
            return res;

        } else { //페이지 적용
            //페이지 컨텐트
            List<Post> postList = postService.getPostByPage(page, 3);
            List<PagePostResponseDto> postResponseDtoList = new ArrayList<>();
            for(Post post: postList){
                PagePostResponseDto formattedPost = new PagePostResponseDto(post);
                formattedPost.setUserName(userService.getUserById(post.getUserId()).getUserName());
                postResponseDtoList.add(formattedPost);
            }

            @Getter
            @Setter
            class CustomPostResponse {
                private List<PagePostResponseDto> posts;
                private int page;
                private int totalPages;
            }

            CustomPostResponse pageResponse = new CustomPostResponse();

            pageResponse.setPosts(postResponseDtoList);
            pageResponse.setPage(page);
            pageResponse.setTotalPages((int) Math.ceil(postService.totalPost()/3.0));

            return pageResponse;
        }
    }


    //유저별 포스트 조회
    @GetMapping("/user/{userId}/posts")
    public Object getUserPost(@PathVariable Integer userId){
        Map<String, Object> userPostResponse = new HashMap<>();

        //페이지 컨텐트
        List<Post> postList = postService.getPostByUserId(userId);
        System.out.println(postList);
        if (postList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no such user exists");
        }

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
        pageResponse.setUserName(userService.getUserById(pageResponse.getUserId()).getUserName());

        return pageResponse;  //숨길건 숨기고 아닌건 나오게 수정
    }

    //포스트 수정
    @PatchMapping("/post/{postId}")
    public ResponseEntity updatePost(@PathVariable Integer postId, @RequestBody PostRequestDto postDto){
//        태그 적용 전
//        Post post = postDto.getPost();
//        post.setPostId(postId);
//        System.out.println(postId);
//        System.out.println(post.getContent());
//
//        if (post.getUserId() == null || post.getContent() == null){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Factor Missing");
//        }
//
//    // valid postId check
//        if (postService.getPostById(postId) == null){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
//        }
//    // userid check
//        if (postService.getPostById(postId).getUserId() != postDto.getUserId()){
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Forbidden");
//        }
//        Post savedPost =  postService.updatePost(post);
//        PagePostResponseDto res = new PagePostResponseDto(savedPost);
//        res.setUserName(userService.getUserById(postDto.getUserId()).getUserName());
//        // Convert the Post object to PostResponseDto and return
//        return res;

//        태그 적용 후
        Post post = postDto.getPost();
        post.setPostId(postId);

        if (post.getUserId() == null || post.getContent() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Factor Missing");
        }

        if (postService.getPostById(postId) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        if (postService.getPostById(postId).getUserId() != postDto.getUserId()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Forbidden");
        }

        //여기서 태그 관련 기능 구현

        //기존 태그 배열 받기
        ArrayList<Tag> oldTagArray;
        oldTagArray = postService.findPostTag(post.getPostId());

        //추후 비교 위한 구) 태그의 id 배열 선언
        ArrayList oldTagIdArray = new ArrayList<Integer>();
        for (Tag tag:oldTagArray ){
            oldTagIdArray.add(tag.getTagId());
        }

        //수정된 태그 배열 선언
        ArrayList<Tag> newTagArray = new ArrayList<>();

        //태그 추출 및 수정된 본문
        String s = post.getContent();
        String[] tags = s.split("\\#", 0);
        post.setContent(tags[0].substring(0, tags[0].length() - 1));

        PostWithTagResponseDto response = new PostWithTagResponseDto(post);

        //Tag 있는지 확인 -> 있고 없고에 따라 리턴하는 형태 다름
        if (tags.length > 1 ) { //Tag가 있다면
            tags = Arrays.copyOfRange(tags, 1, tags.length); //Tag만 있는 Array로 카피해서 온전한 문자열로 처리

            for (int i = 0; i< tags.length; i++) { //각 태그에 대해
                String sub_string = tags[i];
                if (sub_string.endsWith(", ")) { //구분자인 , 재거
                    tags[i] = sub_string.substring(0, sub_string.length() - 2);
                }

//                System.out.println(tags[i] + postService.findTag(tags[i]) );

                //새 베열에 있는 것들
                //새 배열에 있는 것들은 일단 태그 테이블에 다 삽입
                if (postService.findTag(tags[i]) == null){ //중복 방지
                    postService.tagInsertion(tags[i]);
                }

                //임시 Tag Element 생성
                Tag tempTag = new Tag(postService.findTag(tags[i]), tags[i]);
//                System.out.println("TEMP: " + postService.findTag(tags[i])+ " " + tags[i]);
                newTagArray.add(tempTag);

                //해당 Tag가 이미 있었고, 있다면 스킵
                //해당 Tag가 없었는데 있다면? 관계 테이블에 삽입
                if (!oldTagIdArray.contains(tempTag.getTagId())) {
                    postService.tagPostInsertion(tempTag.getTagId(), post.getPostId());
                }
            }

            //추후 비교 위한 id 배열 선언
            ArrayList newTagIdArray = new ArrayList<Integer>();
            for (Tag tag:newTagArray ){
                newTagIdArray.add(tag.getTagId());
            }

            //원래 있지만 새 테이블에 없는 경우 -> 관계 테이블에서 삭제
            for(Tag oldTempTag: oldTagArray){
                if(!newTagIdArray.contains(oldTempTag.getTagId())){
                    postService.tagPostDeletion(oldTempTag.getTagId(), post.getPostId());
                }
            }

        }
        //dto에 tag 배열 저장
        response.setTags(newTagArray);
        response.setPostId(post.getPostId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response); //새로운 dto 만들어서 수정하면 됨.


        //있었는데 없으면 삭제

        //없었는데 있으면 추가

        //게시글 작성과 같은 방식으로 응답하기
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
    public ResponseEntity<PostCommentViewDto> createPostComment(@PathVariable Integer postId, @RequestBody PostCommentRequestDto postCommentDto){
        PostComment postComment = postCommentDto.getPostComment();
        postComment.setPostId(postId);
//      bad request 검증
        if (postComment.getUserId() == null || postComment.getComment() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId and comment are required");
        }
//        post 검증 과정
        if (postService.checkIfWritten(postId)) { //Post가 존재하면
            PostComment savedPostComment = postService.savePostComment(postComment);
            PostCommentViewDto postCommentViewDto = new PostCommentViewDto(savedPostComment);
            return ResponseEntity.status(HttpStatus.CREATED).body(postCommentViewDto);
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
                formattedPostComment.setUserName(userService.getUserById(formattedPostComment.getUserId()).getUserName());
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
            } catch (DuplicateKeyException e) { //이미 Like 한 경우
//                System.out.println(e);
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Already Liked");
            } catch (DataIntegrityViolationException e) { //요청이 잘못된 경우
//                System.out.println(e);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field Missing");
            }
            Map<String, String> response = new HashMap<>();
            response.put("message", "Like successfully added to the post.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
            postService.checkUnlike(postId, userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Like successfully removed from the post.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }

    //////////////////////////////////// 여기부터 좋아요 implement 후 /////////////////////////////////////

    @GetMapping(value = "/posts", params = "userId")
    public Object getPostListLikedByUser(@RequestParam("userId") Integer userId) {
        //페이지 없이
            Map<String, Object> res = new HashMap<>();
            List<Post> postList = postService.getPosts();
            List<LikedPagePostResponseDto> postResponseDtoList = new ArrayList<>();
            for(Post post: postList){
                LikedPagePostResponseDto formattedPost = new LikedPagePostResponseDto(post);
                formattedPost.setUserName(userService.getUserById(post.getUserId()).getUserName());
                formattedPost.setUpdatedAt(null);
//                formattedPost.setLikeCount(30);
                formattedPost.setLikedByUser(postService.existsLike(post.getPostId(), userId) == 1);
                postResponseDtoList.add(formattedPost);
            }
            res.put("posts", postResponseDtoList);
            return res;

    }

    @GetMapping(value = "/posts/sortedByLikes", params = "userId")
    public Object getPostListSortedLikedByUser(@RequestParam("userId") Integer userId) {
        Map<String, Object> res = new HashMap<>();
        List<Post> postList = postService.getPostsSorted();
        List<SortedLikedPagePostResponseDto> postResponseDtoList = new ArrayList<>();
        for(Post post: postList){
            SortedLikedPagePostResponseDto formattedPost = new SortedLikedPagePostResponseDto(post);
            formattedPost.setUserName(userService.getUserById(post.getUserId()).getUserName());
            formattedPost.setLikeCount(post.getLikeCount());
            System.out.println(post.getPostId() + "likes:" + post.getLikeCount());
            formattedPost.setLikedByUser(postService.existsLike(post.getPostId(), userId) == 1);
            postResponseDtoList.add(formattedPost);
        }
        res.put("posts", postResponseDtoList);
        return res;

    }

    //////////////////////////////////// 여기부터 태그 관련/////////////////////////////////////
    @GetMapping(value = "/tags")
    public Object getTags() {
        Map<String, Object> resArray = new HashMap<>();
        resArray.put("tags", postService.findAllTag());
        return resArray;
    }
}

