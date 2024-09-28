import java.sql.*;

public class OpenAccount extends ConnectDB{
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String ustate;
    private String zip_code;
    // customer account details
    private String account;

    //-------------------constructor to initialize values-------------------
    public OpenAccount(String first_name, String last_name, String email, String phone, String address, String city, String state, String zip, String account) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.ustate = state;
        this.zip_code = zip;
        this.account = account;
    }

    public void openAccount() {
        Connection connect = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish connection to the database
            connect = DriverManager.getConnection(url,user,password);

            // SQL query to insert the new customer record
            String insertionQuery = "INSERT INTO Customers (first_name, last_name, email, phone, address, city, state, zip_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connect.prepareStatement(insertionQuery);

            // Setting the parameters for the PreparedStatement
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, city);
            preparedStatement.setString(7, ustate);
            preparedStatement.setString(8, zip_code);

            // Executing the update
            int result = preparedStatement.executeUpdate();

            // Checking if the insertion was successful
            if (result > 0) {
                // SQL Query to retrieve customer id
                String customerIdQuery = "SELECT customer_id FROM Customers WHERE phone=? AND email=?";
                PreparedStatement customerIdRetrieve = connect.prepareStatement(customerIdQuery);
                customerIdRetrieve.setString(1, phone);
                customerIdRetrieve.setString(2, email);
                ResultSet resultSet = customerIdRetrieve.executeQuery();
                //----------------------Open Account------------------------
                if (resultSet.next()) {
                    int id = resultSet.getInt("customer_id");

                    // open account for customer
                    String openAccountQuery = "INSERT INTO Accounts (customer_id, account_type) VALUES (?, ?)";
                    PreparedStatement openAccountPreparedStatement = connect.prepareStatement(openAccountQuery);
                    openAccountPreparedStatement.setInt(1, id);

                    //-------------------if savings or current then open account---------------------
                    if (account.equals("SAVINGS") || account.equals("CURRENT")) {
                        openAccountPreparedStatement.setString(2, account);
                    } else {
                        //DELETE CUSTOMER ID FROM CUSTOMER TABLE
                        String customerIdDel = "DELETE FROM Customers WHERE customer_id=?";
                        PreparedStatement pscustomerIdDel = connect.prepareStatement(customerIdQuery);
                        customerIdRetrieve.setInt(1, id);
                        customerIdRetrieve.executeUpdate();
                        System.out.println("Account Type Not Available");
                        return;
                    }

                    int openAccountResult = openAccountPreparedStatement.executeUpdate();
                    if (openAccountResult > 0) {
                        //Query to retirve customer information
                        String accountIdQuery = "SELECT Accounts.account_number, Customers.first_name, Customers.last_name, Customers.phone, Accounts.account_type " +
                                "FROM Accounts JOIN Customers ON Accounts.customer_id = Customers.customer_id " +
                                "WHERE Customers.customer_id = ?";
                        PreparedStatement accountNoRetrieve = connect.prepareStatement(accountIdQuery);
                        accountNoRetrieve.setInt(1, id);
                        ResultSet accountNoResultSet = accountNoRetrieve.executeQuery();

                        if (accountNoResultSet.next()) {
                            int accountNo = accountNoResultSet.getInt("account_number");
                            String fname = accountNoResultSet.getString("first_name");
                            String lname = accountNoResultSet.getString("last_name");
                            String phoneno = accountNoResultSet.getString("phone");
                            String accountType = accountNoResultSet.getString("account_type");
                            //Print to values
                            System.out.println("Account Created Successfully");
                            System.out.println("Your Customer ID: " + id);
                            System.out.println("Your Account Number: " + accountNo);
                            System.out.println("Your Name: " + fname + " " + lname);
                            System.out.println("Your Phone Number: " + phoneno);
                            System.out.println("Your Account Type: " + accountType);
                        }
                    } else {
                        System.out.println("Failed to Open New Account");
                    }
                }
            } else {
                System.out.println("Failed to Open New Account");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle duplicate email or phone error
            System.out.println("Error: A customer with this email/phone number already exists.");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
