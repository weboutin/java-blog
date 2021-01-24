package services;

import utils.DBUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class UsersService {

    public static Integer register(String account, String password) throws Exception {
        Connection conn = DBUtils.connect();
        Integer userId = null;
        try {
            String sql = "select id from `sbs-users` where account = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account);
            ResultSet rs = null;
            if (ps.execute()) {
                rs = ps.getResultSet();
                while (rs.next()) {
                    throw new Exception("user already exist");
                }
            }
            rs.close();
            ps.close();    
        } catch (Exception e) {
            throw e;
        }

        try {
            String sql = "insert into `sbs-users` (account, password,created_at,modified_at) values(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account);
            ps.setString(2, password);
            Date date = new Date(); 
            ps.setLong(3, date.getTime());
            ps.setLong(4, date.getTime());
            ResultSet rs = null;
            ps.executeUpdate(); 
            rs = ps.getGeneratedKeys();
            while (rs.next()) {
                userId = rs.getInt(1);
                return userId;
            }
            rs.close();
            ps.close();    
        } catch (Exception e) {
            throw e;
        }
        return userId;
    }
}