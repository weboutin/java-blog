package services;

import org.apache.ibatis.session.SqlSession;

import entitys.User;
import utils.MyBatisUtils;

public class SessionsService {

    public static Integer auth(String account, String password) throws Exception {
        SqlSession session = MyBatisUtils.getSqlSession();
        User user = (User) session.selectOne("mybatis.mappers.UsersMapper.selectUserByAccount", account);

        if (user == null) {
            throw new Exception("user not exist");
        }

        if (!password.equals(user.password)) {
            throw new Exception("password error");
        }

        return user.userId;
    }
}