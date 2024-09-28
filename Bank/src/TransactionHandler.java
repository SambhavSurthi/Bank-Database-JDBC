import java.sql.*;

public class TransactionHandler extends ConnectDB {

    // Method to transfer money between accounts
    public void transferMoney(int senderAccount, int receiverAccount, double amount) {
        Connection conn = null;
        PreparedStatement updateSender = null;
        PreparedStatement updateReceiver = null;
        PreparedStatement logTransaction = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false); // Start transaction

            // Check if sender has enough balance
            String checkBalanceQuery = "SELECT balance FROM Accounts WHERE account_number = ?";
            PreparedStatement checkBalance = conn.prepareStatement(checkBalanceQuery);
            checkBalance.setInt(1, senderAccount);
            ResultSet rs = checkBalance.executeQuery();

            if (rs.next()) {
                double senderBalance = rs.getDouble("balance");

                if (senderBalance >= amount) {
                    // Deduct money from sender's account
                    String deductSender = "UPDATE Accounts SET balance = balance - ? WHERE account_number = ?";
                    updateSender = conn.prepareStatement(deductSender);
                    updateSender.setDouble(1, amount);
                    updateSender.setInt(2, senderAccount);
                    updateSender.executeUpdate();

                    // Add money to receiver's account
                    String addReceiver = "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?";
                    updateReceiver = conn.prepareStatement(addReceiver);
                    updateReceiver.setDouble(1, amount);
                    updateReceiver.setInt(2, receiverAccount);
                    updateReceiver.executeUpdate();

                    // Log the transaction in the Transactions table
                    String logTransactionQuery = "INSERT INTO Transactions (sender_account_number, receiver_account_number, transaction_type, amount) VALUES (?, ?, 'TRANSFER', ?)";
                    logTransaction = conn.prepareStatement(logTransactionQuery);
                    logTransaction.setInt(1, senderAccount);
                    logTransaction.setInt(2, receiverAccount);
                    logTransaction.setDouble(3, amount);
                    logTransaction.executeUpdate();

                    // Commit the transaction
                    conn.commit();
                    System.out.println("Transaction successful: " + amount + " transferred from Account " + senderAccount + " to Account " + receiverAccount);
                } else {
                    System.out.println("Insufficient balance in sender's account.");
                }
            }

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback(); // Rollback transaction in case of failure
                System.out.println("Transaction failed. Rolled back.");
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (updateSender != null) updateSender.close();
                if (updateReceiver != null) updateReceiver.close();
                if (logTransaction != null) logTransaction.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to deposit money into an account
    public void depositMoney(int accountNumber, double amount) {
        Connection conn = null;
        PreparedStatement updateAccount = null;
        PreparedStatement logTransaction = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false); // Start transaction

            // Add money to the account
            String addMoneyQuery = "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?";
            updateAccount = conn.prepareStatement(addMoneyQuery);
            updateAccount.setDouble(1, amount);
            updateAccount.setInt(2, accountNumber);
            updateAccount.executeUpdate();

            // Log the transaction in the Transactions table
            String logTransactionQuery = "INSERT INTO Transactions (sender_account_number, receiver_account_number, transaction_type, amount) VALUES (NULL, ?, 'DEPOSIT', ?)";
            logTransaction = conn.prepareStatement(logTransactionQuery);
            logTransaction.setInt(1, accountNumber);
            logTransaction.setDouble(2, amount);
            logTransaction.executeUpdate();

            // Commit the transaction
            conn.commit();
            System.out.println("Deposit successful: " + amount + " added to Account " + accountNumber);

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback(); // Rollback transaction in case of failure
                System.out.println("Deposit failed. Rolled back.");
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (updateAccount != null) updateAccount.close();
                if (logTransaction != null) logTransaction.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to withdraw money from an account
    public void withdrawMoney(int accountNumber, double amount) {
        Connection conn = null;
        PreparedStatement updateAccount = null;
        PreparedStatement logTransaction = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false); // Start transaction

            // Check if account has enough balance
            String checkBalanceQuery = "SELECT balance FROM Accounts WHERE account_number = ?";
            PreparedStatement checkBalance = conn.prepareStatement(checkBalanceQuery);
            checkBalance.setInt(1, accountNumber);
            ResultSet rs = checkBalance.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");

                if (balance >= amount) {
                    // Deduct money from the account
                    String deductMoneyQuery = "UPDATE Accounts SET balance = balance - ? WHERE account_number = ?";
                    updateAccount = conn.prepareStatement(deductMoneyQuery);
                    updateAccount.setDouble(1, amount);
                    updateAccount.setInt(2, accountNumber);
                    updateAccount.executeUpdate();

                    // Log the transaction in the Transactions table
                    String logTransactionQuery = "INSERT INTO Transactions (sender_account_number, receiver_account_number, transaction_type, amount) VALUES (?, NULL, 'WITHDRAWAL', ?)";
                    logTransaction = conn.prepareStatement(logTransactionQuery);
                    logTransaction.setInt(1, accountNumber);
                    logTransaction.setDouble(2, amount);
                    logTransaction.executeUpdate();

                    // Commit the transaction
                    conn.commit();
                    System.out.println("Withdrawal successful: " + amount + " withdrawn from Account " + accountNumber);
                } else {
                    System.out.println("Insufficient balance.");
                }
            }

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback(); // Rollback transaction in case of failure
                System.out.println("Withdrawal failed. Rolled back.");
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (updateAccount != null) updateAccount.close();
                if (logTransaction != null) logTransaction.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
