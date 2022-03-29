package shuodog.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import shuodog.community.model.Question;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,creator,tag,gmt_create,gmt_modified) " +
            "values (#{title},#{description},#{creator},#{tag},#{gmtCreate},#{gmtModified})")
    void create(Question question);

    @Select("select * from question limit #{limit},#{size}")
    List<Question> list(@Param(value = "limit") Integer limit, @Param(value = "size")Integer size);

    @Select("select count(1) from question")
    Integer count();
}
