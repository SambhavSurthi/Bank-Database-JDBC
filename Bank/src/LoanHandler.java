import java.sql.*;
import java.util.Scanner;

public class LoanHandler extends ConnectDB {

    // Method to add a new loan to the database
    public void addLoan(int accountNumber, String loanType, double amount, Date loanEndDate) {
        Connection conn = null;
        PreparedStatement pst = null;

        // Set the interest rate based on loan type
        double interestRate = 0.0;
        switch (loanType.toUpperCase()) {
            case "PERSONAL":
                interestRate = 15.0;
                break;
            case "CAR":
                interestRate = 12.0;
                break;
            case "HOME":
                interestRate = 10.0;
                break;
            default:
                System.out.println("Invalid loan type. Please enter PERSONAL, CAR, or HOME.");
                return;
        }

        try {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false); // Start transaction

            // Insert loan details into the Loans table
            String addLoanQuery = "INSERT INTO Loans (account_number, loan_type, amount, interest_rate, loan_end_date) VALUES (?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(addLoanQuery);
            pst.setInt(1, accountNumber);
            pst.setString(2, loanType);
            pst.setDouble(3, amount);
            pst.setDouble(4, interestRate);
            pst.setDate(5, loanEndDate);
            pst.executeUpdate();

            // Update the account balance in the Accounts table by adding the loan amount
            String updateBalanceQuery = "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?";
            pst = conn.prepareStatement(updateBalanceQuery);
            pst.setDouble(1, amount);
            pst.setInt(2, accountNumber);
            pst.executeUpdate();

            conn.commit(); // Commit the transaction
            System.out.println("Loan successfully added for Account Number: " + accountNumber);
            System.out.println("Account balance updated with loan amount.");

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback(); // Rollback transaction in case of failure
                System.out.println("Failed to add loan. Rolled back.");
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // Method to view loan details of a specific account
    public void viewLoanDetails(int accountNumber) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, user, password);

            // Query to retrieve loan details for the given account number
            String loanDetailsQuery = "SELECT * FROM Loans WHERE account_number = ?";
            pst = conn.prepareStatement(loanDetailsQuery);
            pst.setInt(1, accountNumber);
            rs = pst.executeQuery();

            // Display the loan details if available
            if (rs.next()) {
                int loanId = rs.getInt("loan_id");
                String loanType = rs.getString("loan_type");
                double amount = rs.getDouble("amount");
                double interestRate = rs.getDouble("interest_rate");
                Timestamp loanStartDate = rs.getTimestamp("loan_start_date");
                Date loanEndDate = rs.getDate("loan_end_date");

                System.out.println("Loan ID: " + loanId);
                System.out.println("Loan Type: " + loanType);
                System.out.println("Loan Amount: " + amount);
                System.out.println("Interest Rate: " + interestRate + "%");
                System.out.println("Loan Start Date: " + loanStartDate);
                System.out.println("Loan End Date: " + loanEndDate);
            } else {
                System.out.println("No loans found for Account Number: " + accountNumber);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to calculate interest for a loan based on amount and interest rate
    public void calculateInterest(int loanId) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, user, password);

            // Query to retrieve the loan details
            String interestQuery = "SELECT amount, interest_rate FROM Loans WHERE loan_id = ?";
            pst = conn.prepareStatement(interestQuery);
            pst.setInt(1, loanId);
            rs = pst.executeQuery();

            // Calculate the interest if the loan exists
            if (rs.next()) {
                double amount = rs.getDouble("amount");
                double interestRate = rs.getDouble("interest_rate");

                double interest = amount * (interestRate / 100);
                System.out.println("Interest for Loan ID " + loanId + ": " + interest);
            } else {
                System.out.println("Loan not found for Loan ID: " + loanId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
