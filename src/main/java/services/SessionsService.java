package services;

import org.apache.ibatis.session.SqlSession;

import entitys.User;
import mappers.UserMapper;
import utils.MyBatisUtils;

public class SessionsService {

    public static Integer auth(String account, String password) throws Exception {
        SqlSession session = MyBatisUtils.getSqlSession();
        // User user = (User)
        // session.selectOne("mybatis.mappers.UsersMapper.selectUserByAccount",
        // account);
        // BlogMapper mapper = session.getMapper(BlogMapper.class);
        // Blog blog = mapper.selectBlog(101);
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