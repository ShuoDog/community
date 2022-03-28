package shuodog.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import shuodog.community.model.User;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,avatar_url,token,gmt_create,gmt_modified) " +
            "values (#{name},#{accountId},#{avatarUrl},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findByID(@Param("id") Integer id);
}
