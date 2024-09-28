import java.sql.*;

public class DeleteAccount extends ConnectDB
{
    public void deleteAccount(int accountno){
        Connection connect =null;
        try
        {
            connect= DriverManager.getConnection(url,user,password);
            String fnameQuery="DELETE Customers, Accounts FROM Accounts JOIN Customers ON Accounts.customer_id = Customers.customer_id  WHERE Accounts.account_number = ? AND Accounts.balance = 0.00";
            PreparedStatement pst=connect.prepareStatement(fnameQuery);
            pst.setInt(1,accountno);
            int updateresult=pst.executeUpdate();
            if(updateresult>0)
            {
                System.out.println("Account Deleted Successful");
            }
            else
            {
                System.out.println("Account Not Deleted---Please Check Your balace It Should Be 0.00");
                System.out.println("Account Not Deleted---Please Enter Your Account Number Correctly And Retry Again");
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

}
