package mappers;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import entitys.User;

public interface UserMapper {
    @Select("select id userId,account,password from `sbs-users` where account = #{account}")
    User selectUserByAccount(String account);

    @Insert("insert into `sbs-users` (account,password,created_at,modified_at) values (#{account},#{password},#{created_at},#{modified_at})")
    int insertUser(@Param("account") String account, @Param("password") String password, @Param("created_at") Long created_at, @Param("modified_at") Long modified_at);
}