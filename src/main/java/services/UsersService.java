package services;

import utils.DBUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersService {

    public static Integer register(String account, String password) throws Exception {
        Connection conn = DBUtils.connect();
        Statement stmt = conn.createStatement();
        String sql = "select * from `sbs-users`";
        ResultSet rs = null;
        if (stmt.execute(sql)) {
            rs = stmt.getResultSet();
            while (rs.next()) {
                int userId = rs.getInt("id");
                System.out.println("userId: " + userId);
            }
        }
        return 0;
        // throw new Exception("user already exist");
        // return 10086;
    }
}