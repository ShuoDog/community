package shuodog.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import shuodog.community.model.Comment;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment(commentator, parent_id, comment, type, like_count, gmt_create, gmt_modified) " +
            "values (#{commentator}, #{parentId}, #{comment}, #{type}, #{likeCount}, #{gmtCreate}, #{gmtModified})")
    void insert(Comment comment);
}
