package com.codepresso.sns.controller;

import com.codepresso.sns.controller.dto.FollowRequestDTO;
import com.codepresso.sns.controller.dto.FollowerResponseDTO;
import com.codepresso.sns.controller.dto.FollowingResponseDTO;
import com.codepresso.sns.service.FollowService;
import com.codepresso.sns.service.UserService;
import com.codepresso.sns.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@Valid @RequestBody FollowRequestDTO request){
        if(followService.existsFollow(request)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Follow already exists");
        }
        User follower = userService.getUserById(request.followerId());
        if(follower == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Follower user not found");
        }
        User following = userService.getUserById(request.followingId());
        if(following == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Following user not found");
        }
        followService.follow(request);

        String message = "User " + request.followerId() + " successfully followed User " + request.followingId() + ".";
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"" + message + "\"}");
    }

    @DeleteMapping("/follow")
    public ResponseEntity<String> unfollowUser(@Valid @RequestBody FollowRequestDTO request){
        User follower = userService.getUserById(request.followerId());
        if(follower == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Follower user not found");
        }
        User following = userService.getUserById(request.followingId());
        if(following == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Following user not found");
        }
        if(!followService.existsFollow(request)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Follow does not exist");
        }

        followService.unfollow(request);

        String message = "User " + request.followerId() + " successfully unfollowed User " + request.followingId() + ".";
        return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
    }

    @GetMapping("/user/{userId}/following")
    public ResponseEntity<Map<String, List<FollowingResponseDTO>>> getFollowingList (@PathVariable("userId") Integer userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        List<User> followingUsers = followService.getFollowingUsers(userId);
        List<FollowingResponseDTO> followingList = followingUsers.stream()
                .map(FollowingResponseDTO::fromUser)
                .collect(Collectors.toList());

        Map<String, List<FollowingResponseDTO>> response = new HashMap<>();
        response.put("following", followingList);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/user/{userId}/followers")
    public ResponseEntity<Map<String, List<FollowerResponseDTO>>> getFollowerList (@PathVariable("userId") Integer userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        List<User> followerUsers = followService.getFollowerUsers(userId);
        List<FollowerResponseDTO> followerList = followerUsers.stream()
                .map(FollowerResponseDTO::fromUser)
                .collect(Collectors.toList());

        Map<String, List<FollowerResponseDTO>> response = new HashMap<>();
        response.put("followers", followerList);

        return ResponseEntity.ok().body(response);
    }
}
