package com.codepresso.sns.mapper;
import com.codepresso.sns.vo.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    //POST 작성
    //user 있는지 확인
    Integer checkMemberExists(@Param("userId") Integer userId);
//    Integer save(@Param("post") Post newPost);
@Options(useGeneratedKeys = true, keyProperty = "postId")
    void savePost(@Param("newPost") Post newPost);
    void newUserPost(@Param("userId") Integer userId);
    List<Post> findAll();

    int countPost();

    List<Post> findByPage(@Param("offset") Integer offset);
    List<Post> findByUserId(@Param("userId") Integer userId);

    Post findOne(@Param("id") Integer id);
    Integer update(@Param("post") Post post);
    Integer delete(@Param("post") Post post);
}
