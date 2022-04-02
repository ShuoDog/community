package shuodog.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import shuodog.community.model.Comment;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment(commentator, parent_id, content, type, gmt_create, gmt_modified) " +
            "values (#{commentator}, #{parentId}, #{content}, #{type}, #{gmtCreate}, #{gmtModified})")
    void insert(Comment comment);

    @Select("select * from comment where id=#{id}")
    Comment getById(@Param(value = "id")Integer id);
}
