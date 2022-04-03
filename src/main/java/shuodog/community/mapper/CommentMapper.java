package shuodog.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import shuodog.community.model.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment(commentator, parent_id, content, type, gmt_create, gmt_modified) " +
            "values (#{commentator}, #{parentId}, #{content}, #{type}, #{gmtCreate}, #{gmtModified})")
    void insert(Comment comment);

    @Select("select * from comment where id=#{id}")
    Comment getById(@Param(value = "id")Integer id);

    @Select("select count(1) from comment where parent_id =#{id}")
    Integer countByParentId(@Param(value = "id")Integer id);

    @Select("select * from comment where parent_id=#{id} and type=#{type} limit #{offset},#{limit}")
    List<Comment> listByIdAndType(@Param(value = "id")Integer id, @Param(value = "type")Integer type,  @Param(value = "offset") Integer offset,
                                  @Param(value = "limit") Integer limit);
}
