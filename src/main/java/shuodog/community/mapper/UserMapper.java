package shuodog.community.mapper;

import org.apache.ibatis.annotations.*;
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

    @Select("select * from user where id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name=#{name},avatar_url=#{avatarUrl},token = #{token},gmt_modified=#{gmtModified} where id = #{id}")
    void update(User userData);
}
