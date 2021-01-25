package mappers;

import org.apache.ibatis.annotations.Select;
import entitys.User;

public interface UserMapper {
    @Select("select id userId,account,password from `sbs-users` where account = #{account}")
    User selectUserByAccount(String account);
}