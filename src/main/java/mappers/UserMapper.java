package mappers;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import entitys.User;

public interface UserMapper {
    @Select("select id userId,account,password from `sbs-users` where account = #{account}")
    User selectUserByAccount(String account);

    @Insert("insert into `sbs-users` (account,password,created_at,modified_at) values (#{user.account},#{user.password},#{user.createdAt},#{user.modifiedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "user.userId", keyColumn = "id")
    int insertUser(@Param("user") User user);
}