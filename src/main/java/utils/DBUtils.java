package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtils {

    private static Connection conn = null;

    public static Connection connect() throws Exception {
        String dbhost = "127.0.0.1";
        String dbname = "sbs-impl";
        String dbuser = "root";
        String dbpwd = "1234qwer";
        if (conn == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://" + dbhost + "/" + dbname + "?" + "user=" + dbuser + "&password=" + dbpwd  + "&useUnicode=true&characterEncoding=utf-8");
        }
        return conn;
    }
}