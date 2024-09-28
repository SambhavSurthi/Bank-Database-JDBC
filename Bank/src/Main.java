import java.sql.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver Not Found: " + e.getMessage());
            return;
        }

        // Collecting user input
        Scanner scanner = new Scanner(System.in);


        while(true) {
            System.out.println("-------------------------------------------------------------Bank -----------------------------------------------------");
            System.out.println("1. Open Account");
            System.out.println("2. Update Details Of Your Account");
            System.out.println("3. Delete Account");
            System.out.println("4. Check Account Balance");
            System.out.println("5. Deposit Amount");
            System.out.println("6. Withdraw Amount");
            System.out.println("7. Transfer Amount");
            System.out.println("8. Add Loan");
            System.out.println("9. View Loan Details");
            System.out.println("10. Calculate Interest");
            System.out.println("0. Exit");
            System.out.print("Enter Your Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Clear the newline after nextInt()

            if (choice == 0) {
                System.out.println("Exiting...");
                break;
            }

            switch (choice) {
                case 1:
                    System.out.println("Enter First Name:");
                    String first_name = scanner.nextLine();

                    System.out.println("Enter Last Name:");
                    String last_name = scanner.nextLine();

                    System.out.println("Enter Email:");
                    String email = scanner.nextLine();

                    System.out.println("Enter Phone:");
                    String phone = scanner.nextLine();

                    System.out.println("Enter Address:");
                    String address = scanner.nextLine();

                    System.out.println("Enter City:");
                    String city = scanner.nextLine();

                    System.out.println("Enter State:");
                    String state = scanner.nextLine();

                    System.out.println("Enter Zip Code:");
                    String zip_code = scanner.nextLine();

                    System.out.println("Enter Account Type (SAVINGS/CURRENT):");
                    String account = scanner.nextLine().toUpperCase();  // Convert to uppercase directly

                    // Creating an instance of OpenAccount with the user input and opening the account
                    OpenAccount openAccount = new OpenAccount(first_name, last_name, email, phone, address, city, state, zip_code, account);
                    openAccount.openAccount();
                    break;

                case 2:
                    System.out.println("1. Update Name");
                    System.out.println("2. Update Address");
                    System.out.println("3. Update Phone Number");
                    System.out.println("4. Update Email");

                    int ichoice = scanner.nextInt();
                    scanner.nextLine();  // Clear the newline after nextInt()

                    UpdateDetails updateDetails = new UpdateDetails();
                    System.out.println("Enter Account Number:");
                    int accountNo = scanner.nextInt();
                    scanner.nextLine();  // Clear the newline after nextInt()

                    switch (ichoice) {
                        case 1:
                            System.out.println("Enter New First Name:");
                            String newFname = scanner.nextLine();
                            System.out.println("Enter New Last Name:");
                            String newLname = scanner.nextLine();
                            updateDetails.updateName(newFname, newLname, accountNo);
                            break;

                        case 2:
                            System.out.println("Enter New Address: ");
                            String newAddress = scanner.nextLine();
                            System.out.println("Enter new city");
                            String newCity=scanner.nextLine();
                            System.out.println("Enter new State");
                            String newState=scanner.nextLine();
                            System.out.println("Enter new Zip code");
                            String newZipcode=scanner.nextLine();
                            updateDetails.updateAddress(newAddress,newCity,newState,newZipcode,accountNo);
                            break;

                        case 3:
                            System.out.println("Enter New Phone Number:");
                            String newPhone = scanner.nextLine();
                            updateDetails.updatePhoneNo(newPhone, accountNo);
                            break;

                        case 4:
                            System.out.println("Enter New Email:");
                            String newEmail = scanner.nextLine();
                            updateDetails.updateEmail(newEmail, accountNo);
                            break;

                        default:
                            System.out.println("Invalid Choice");
                            break;
                    }
                    break;

                case 3:
                    System.out.println("Enter Account Number:");
                    int acno=scanner.nextInt();
                    DeleteAccount daccount=new DeleteAccount();
                    daccount.deleteAccount(acno);

                    break;

                case 4:
                    System.out.println("Enter Account Number: ");
                    int accNo = scanner.nextInt();
                    CheckBalance cb=new CheckBalance();
                    cb.checkBalance(accNo);

                    break;
                case 5:
                    TransactionHandler handler = new TransactionHandler();
                    System.out.print("Enter account number: ");
                    int accountNumber = scanner.nextInt();
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    handler.depositMoney(accountNumber, depositAmount);

                    break;

                case 6:
                    TransactionHandler handler1 = new TransactionHandler();
                    System.out.print("Enter account number: ");
                    int accountNumber1 = scanner.nextInt();
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    handler1.withdrawMoney(accountNumber1, withdrawalAmount);

                    break;

                case 7:
                    TransactionHandler handler2 = new TransactionHandler();
                    System.out.print("Enter account number: ");
                    int accountNumber2 = scanner.nextInt();
                    System.out.print("Enter receiver's account number: ");
                    int receiverAccountNumber = scanner.nextInt();
                    System.out.print("Enter transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    handler2.transferMoney(accountNumber2, receiverAccountNumber, transferAmount);
                    break;

                case 8:
                    LoanHandler loanHandler = new LoanHandler();
                    System.out.print("Enter Account Number: ");
                    int accountNumber4 = scanner.nextInt();
                    System.out.print("Enter Loan Type (PERSONAL, CAR, HOME): ");
                    String loanType = scanner.next(); // Get the loan type
                    System.out.print("Enter Loan Amount: ");
                    double amount = scanner.nextDouble();
                    System.out.print("Enter Loan End Date (YYYY-MM-DD): ");
                    String endDateString = scanner.next();
                    Date loanEndDate = Date.valueOf(endDateString);

                    loanHandler.addLoan(accountNumber4, loanType, amount, loanEndDate);
                    break;

                case 9:
                    LoanHandler loanHandler1 = new LoanHandler();
                    System.out.print("Enter Account Number: ");
                    accountNumber = scanner.nextInt();
                    loanHandler1.viewLoanDetails(accountNumber);
                    break;

                case 10:
                    LoanHandler loanHandler2 = new LoanHandler();
                    System.out.print("Enter Loan ID: ");
                    int loanId = scanner.nextInt();
                    loanHandler2.calculateInterest(loanId); // Call method to calculate interest
                    break;

                default:
                    System.out.println("Invalid Choice");
            }


        }
    }
}
