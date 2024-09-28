import java.sql.*;

public class CheckBalance extends ConnectDB {

    public void checkBalance(int acno) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            String balanceQuery = "SELECT Accounts.account_number, Customers.first_name, Customers.last_name, Customers.phone, Accounts.balance " +
                    "FROM Accounts JOIN Customers ON Accounts.customer_id = Customers.customer_id " +
                    "WHERE Accounts.account_number = ?";
            pst = conn.prepareStatement(balanceQuery);
            pst.setInt(1, acno);
            rs = pst.executeQuery();

            // Check if there is any result
            if (rs.next()) {
                int actno = rs.getInt("account_number");
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                String phno = rs.getString("phone"); // corrected the column name
                double avabalance = rs.getDouble("balance");

                System.out.println(actno + " " + firstname + " " + lastname + " " + phno + " " + avabalance);
            } else {
                System.out.println("Account not found.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Close resources in the reverse order of their creation
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
