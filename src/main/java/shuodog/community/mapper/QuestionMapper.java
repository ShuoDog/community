package shuodog.community.mapper;

import org.apache.ibatis.annotations.*;
import shuodog.community.model.Question;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,creator,tag,gmt_create,gmt_modified) " +
            "values (#{title},#{description},#{creator},#{tag},#{gmtCreate},#{gmtModified})")
    void create(Question question);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question limit #{limit},#{size}")
    List<Question> list(@Param(value = "limit") Integer limit, @Param(value = "size") Integer size);

    @Select("select count(1) from question where creator =#{userId}")
    Integer countByUserId(@Param(value = "userId") Integer userId);

    @Select("select * from question where creator = #{userId} limit #{offset},#{limit}")
    List<Question> listByUserId(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset,
                                @Param(value = "limit") Integer limit);

    @Select("select * from question where id = #{id}")
    Question getById(@Param(value = "id") Integer id);

    @Update("update question set title=#{title}, description=#{description}, " +
            "tag=#{tag}, gmt_modified=#{gmtModified} where id=#{id}")
    void update(Question question);

    @Update("update question set read_count=read_count+1 where id=#{id}")
    void updateReadCount(@Param(value = "id") Integer id);

    @Update("update question set comment_count=comment_count+1 where id=#{id}")
    void updateCommentCount(@Param(value = "id") Integer id);
}
