package com.codepresso.sns.mapper;

import com.codepresso.sns.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FollowMapper {

    int existsFollow(int followerId, int followingId);

    void save(int followerId, int followingId);
    void unsave(int followerId, int followingId);

    List<User> findFollowingUsers(@Param("userId") Integer userId);

    List<User> findFollowerUsers(@Param("userId") Integer userId);
}
