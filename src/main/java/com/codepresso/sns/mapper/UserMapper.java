package com.codepresso.sns.mapper;

import com.codepresso.sns.controller.dto.SignUpRequestDTO;
import com.codepresso.sns.vo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    void signUp(@Param("info") User info);
    void updateUserInfo(@Param("info") User info);

    void updatePassword(@Param("info") User info);

    User getUserById(@Param("userId") Integer userId);
    User getUserByEmail(@Param("email") String email);

    int existsByEmail(String email);

    void save(@Param("info") User info);

}
