package services;

import java.util.Date;
import org.apache.ibatis.session.SqlSession;

import entitys.User;
import mappers.UserMapper;
import utils.MyBatisUtils;

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
        User inUser = new User();
        inUser.account = account;
        inUser.password = password;
        inUser.createdAt = now;
        inUser.modifiedAt = now;
        mapper.insertUser(inUser);
        session.commit();
        return inUser.userId;
    }
}