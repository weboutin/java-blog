package services;

import utils.DBUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class SessionsService {

    public static Integer auth(String account, String password) throws Exception {
        Connection conn = DBUtils.connect();
        Integer userId = null;
        String realPassword = null;

        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = "select id,password from `sbs-users` where account = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, account);
            if (ps.execute()) {
                rs = ps.getResultSet();
                while (rs.next()) {
                    userId = rs.getInt("id");
                    realPassword = rs.getString("password");
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            ps.close();    
        }

        if (userId == null) {
            throw new Exception("user not exist");
        }
        
        if (!password.equals(realPassword)) {
            throw new Exception("password error");
        }

        return userId;
    }
}