package com.codepresso.sns.mapper;
import com.codepresso.sns.vo.Post;
import com.codepresso.sns.vo.PostComment;
import com.codepresso.sns.vo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
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
    List<Post> findAllSorted();

    int countPost();

    List<Post> findByPage(@Param("offset") Integer offset);
    List<Post> findByUserId(@Param("userId") Integer userId);

    Post findOne(@Param("id") Integer id);
    Integer update(@Param("post") Post post);
    Integer delete(@Param("post") Post post);

    //////////////////////////////////// 여기부터 댓글 관련/////////////////////////////////////
    Integer findOneExists(@Param("id") Integer id);
    void savePostComment(@Param("newPostComment") PostComment newPostComment);
    PostComment findOneComment(@Param("id") Integer id);

    List<PostComment> findCommentByPostId(@Param("postId") Integer postId);
    PostComment findCommentByCommentId(@Param("commentId") Integer commentId);

    //////////////////////////////////// 여기부터 좋아요 관련/////////////////////////////////////
    Integer likePost(@Param("postId") Integer postId, @Param("userId") Integer userId);
    Integer unlikePost(@Param("postId") Integer postId, @Param("userId") Integer userId);
    int existsLike(@Param("userId") Integer userId, @Param("postId") Integer postId);

    //////////////////////////////////// 여기부터 태그 관련/////////////////////////////////////

    //태그 삽입
    Integer findTag(@Param("tag") String tag);
    void tagInsertion(@Param("tag") String tag);
    void tagPostInsertion(@Param("tagId") Integer tagId, @Param("postId") Integer postId);
    void tagPostDeletion(@Param("tagId") Integer tagId, @Param("postId") Integer postId);

    //태그 탑색
    ArrayList<Tag> findPostTag(@Param("postId") Integer postId);
}
