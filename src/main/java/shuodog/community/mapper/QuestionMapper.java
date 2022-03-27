package shuodog.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import shuodog.community.model.Question;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,creator,tag,gmt_create,gmt_modified) " +
            "values (#{title},#{description},#{creator},#{tag},#{gmtCreate},#{gmtModified})")
    void create(Question question);
}
