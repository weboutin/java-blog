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

        PreparedStatement psQuery = null;
        ResultSet rsQuery = null;

        PreparedStatement psUpdate = null;
        ResultSet rsUpdate = null;

        try {
            String sql = "select id from `sbs-users` where account = ?";
            psQuery = conn.prepareStatement(sql);
            psQuery.setString(1, account);
            if (psQuery.execute()) {
                rsUpdate = psQuery.getResultSet();
                while (rsQuery.next()) {
                    throw new Exception("user already exist");
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rsQuery.close();
            psQuery.close();    
        }

        try {
            String sql = "insert into `sbs-users` (account, password,created_at,modified_at) values(?,?,?,?)";
            psUpdate = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            psUpdate.setString(1, account);
            psUpdate.setString(2, password);
            Date date = new Date(); 
            psUpdate.setLong(3, date.getTime());
            psUpdate.setLong(4, date.getTime());
            psUpdate.executeUpdate(); 
            rsUpdate = psUpdate.getGeneratedKeys();
            while (rsUpdate.next()) {
                userId = rsUpdate.getInt(1);
                return userId;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rsUpdate.close();
            psUpdate.close();    
        }
        
        return userId;
    }
}