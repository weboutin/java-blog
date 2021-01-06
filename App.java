import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class App {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/blog?" + "user=root&password=root");
            stmt = conn.createStatement();
            if (stmt.execute("SELECT userid FROM user")) {
                rs = stmt.getResultSet();
                while (rs.next()) {
                    int userId = rs.getInt("userid");
                    System.out.print("userId: " + userId);
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                    System.out.println("SQLException: " + sqlEx.getMessage());
                }
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                    System.out.println("SQLException: " + sqlEx.getMessage());
                }
                stmt = null;
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlEx) {
                    System.out.println("SQLException: " + sqlEx.getMessage());
                }
                conn = null;
            }
        }
    }
}