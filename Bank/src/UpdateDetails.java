import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

class UpdateDetails extends ConnectDB
{
    //update First Name
    public void updateName(String fname,String lname,int accountno)
    {
        Connection connect=null;
        try
        {
            connect= DriverManager.getConnection(url,user,password);
            String fnameQuery="UPDATE Customers c JOIN Accounts a ON c.customer_id = a.customer_id SET c.first_name = ?,c.last_name=? WHERE a.account_number = ?";
            PreparedStatement pst=connect.prepareStatement(fnameQuery);
            pst.setString(1,fname);
            pst.setString(2,lname);
            pst.setInt(3,accountno);
            int updateresult=pst.executeUpdate();
            if(updateresult>0)
            {
                System.out.println("Update Successful");
            }
            else
            {
                System.out.println("Account Details Not Updated---Please Enter Your Account Number Correctly And Retry Again");
            }

        }
        catch(Exception e)
        {
            System.out.println("Enter Correct Account Number");
        }
    }
    //update address
    public void updateAddress(String uaddress,String ucity,String ustate,String uzipcode,int accountno)
    {
        Connection connect=null;
        try
        {
            connect= DriverManager.getConnection(url,user,password);
            String addressQuery="UPDATE Customers c JOIN Accounts a ON c.customer_id = a.customer_id SET c.address = ?,c.city=?,c.state=?,c.zip_code=? WHERE a.account_number = ?";
            PreparedStatement pst=connect.prepareStatement(addressQuery);
            pst.setString(1,uaddress);
            pst.setString(2,ucity);
            pst.setString(3,ustate);
            pst.setString(4,uzipcode);
            pst.setInt(5,accountno);
            int updateresult=pst.executeUpdate();
            if(updateresult>0)
            {
                System.out.println("Update Successful");
            }
            else
            {
                System.out.println("Account Details Not Updated---Please Enter Your Account Number Correctly And Retry Again");
            }
        }
        catch(Exception e)
        {
            System.out.println("Enter Correct Account Number");
        }
    }

    //update phone no
    public void updatePhoneNo(String uphoneno, int accountno)
    {
        Connection connect=null;
        try
        {
            connect= DriverManager.getConnection(url,user,password);
            String phoneNoQuery="UPDATE Customers c JOIN Accounts a ON c.customer_id = a.customer_id SET c.phone=? WHERE a.account_number =?";
            PreparedStatement pst=connect.prepareStatement(phoneNoQuery);
            pst.setString(1,uphoneno);
            pst.setInt(2,accountno);
            int updateresult=pst.executeUpdate();
            if(updateresult>0)
            {
                System.out.println("Update Successful");
            }
            else
            {
                System.out.println("Account Details Not Updated---Please Enter Your Account Number Correctly And Retry Again");
            }
        }
        catch(Exception e)
        {
            System.out.println("Enter Correct Account Number");
        }
    }

    //update email
    public void updateEmail(String uemail, int accountno)
    {
        Connection connect=null;
        try
        {
            connect= DriverManager.getConnection(url,user,password);
            String emailQuery="UPDATE Customers c JOIN Accounts a ON c.customer_id = a.customer_id SET c.email=? WHERE a.account_number =?";
            PreparedStatement pst=connect.prepareStatement(emailQuery);
            pst.setString(1,uemail);
            pst.setInt(2,accountno);
            int updateresult=pst.executeUpdate();
            if(updateresult>0)
            {
                System.out.println("Update Successful");
            }
            else
            {
                System.out.println("Account Details Not Updated---Please Enter Your Account Number Correctly And Retry Again");
            }
        }
        catch(Exception e)
        {
            System.out.println("Enter Correct Account Number");
        }
    }

}
