package com.codepresso.sns.service;

import com.codepresso.sns.controller.dto.FollowRequestDTO;
import com.codepresso.sns.mapper.FollowMapper;
import com.codepresso.sns.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowMapper followMapper;
    private final UserService userService;

    public boolean existsFollow(FollowRequestDTO followRequestDTO){
        int followerId = followRequestDTO.followerId();
        int followingId = followRequestDTO.followingId();
        return followMapper.existsFollow(followerId, followingId)==1;
    }

    public void follow(FollowRequestDTO followRequestDTO){
        int followerId = followRequestDTO.followerId();
        int followingId = followRequestDTO.followingId();
        followMapper.save(followerId, followingId);
    }

    public void unfollow(FollowRequestDTO followRequestDTO){
        int followerId = followRequestDTO.followerId();
        int followingId = followRequestDTO.followingId();
        followMapper.unsave(followerId, followingId);
    }

    public List<User> getFollowingUsers(Integer userId){
        return followMapper.findFollowingUsers(userId);
    }

    public List<User> getFollowerUsers(Integer userId){
        return followMapper.findFollowerUsers(userId);
    }




}
