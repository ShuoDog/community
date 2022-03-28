package shuodog.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import shuodog.community.model.Question;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,creator,tag,gmt_create,gmt_modified) " +
            "values (#{title},#{description},#{creator},#{tag},#{gmtCreate},#{gmtModified})")
    void create(Question question);

    @Select("select * from question")
    List<Question> list();
}
