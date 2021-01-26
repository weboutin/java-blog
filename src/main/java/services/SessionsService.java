package services;

import org.apache.ibatis.session.SqlSession;

import entitys.User;
import mappers.UserMapper;
import utils.MyBatisUtils;

public class SessionsService {

    public static Integer auth(String account, String password) throws Exception {
        SqlSession session = MyBatisUtils.getSqlSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.selectUserByAccount(account);

        if (user == null) {
            throw new Exception("user not exist");
        }

        if (!password.equals(user.password)) {
            throw new Exception("password error");
        }

        return user.userId;
    }
}