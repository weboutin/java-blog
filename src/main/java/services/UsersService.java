package services;

import java.util.Date;
import org.apache.ibatis.session.SqlSession;

import entitys.User;
import mappers.UserMapper;
import utils.MyBatisUtils;
import entitys.User;

public class UsersService {

    public static Integer register(String account, String password) throws Exception {
        SqlSession session = MyBatisUtils.getSqlSession();
        UserMapper mapper = session.getMapper(UserMapper.class);

        User user = mapper.selectUserByAccount(account);
        if (user != null) {
            throw new Exception("user already exist");
        }
        Date date = new Date(); 
        Long now = date.getTime();
        int userId = mapper.insertUser(account,password,now,now);
        System.out.println(userId);
        return userId;
    }
}