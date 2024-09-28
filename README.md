# Bank Database Management System

## Overview
The Bank Database Management System is a robust and scalable solution for managing customer data, account details, transactions, and loan information in a banking context. This project is designed using MySQL and adheres to best practices in software design and development, ensuring high maintainability and flexibility.

## Features
- **Customer Management**: Store and manage customer information including first name, last name, email, phone number, address, city, state, and ZIP code.
- **Account Management**: Support multiple account types (Savings, Checking, Fixed Deposit) linked to each customer. Maintain account balances and opened dates.
- **Transaction Tracking**: Record deposits, withdrawals, and transfers for each account. Maintain a history of all transactions made by the customers.
- **Loan Management**: Manage loan applications for different types of loans (Personal, Home, Car). Track loan amounts, interest rates, and repayment dates.


## Database Schema
The database consists of the following tables:
1. **Customers**
   - `customer_id`: INT (Primary Key)
   - `first_name`: VARCHAR(50)
   - `last_name`: VARCHAR(50)
   - `email`: VARCHAR(100) (Unique)
   - `phone`: VARCHAR(15)
   - `address`: VARCHAR(255)
   - `city`: VARCHAR(50)
   - `state`: VARCHAR(50)
   - `zip_code`: VARCHAR(10)
   - `created_at`: TIMESTAMP (Default: CURRENT_TIMESTAMP)
   
2. **Accounts**
   - `account_number`: INT (Primary Key)
   - `customer_id`: INT (Foreign Key)
   - `account_type`: ENUM ('SAVINGS', 'CHECKING', 'FIXED_DEPOSIT')
   - `balance`: DECIMAL(15, 2) (Default: 0.00)
   - `opened_date`: TIMESTAMP (Default: CURRENT_TIMESTAMP)

3. **Transactions**
   - `transaction_id`: INT (Primary Key)
   - `account_number`: INT (Foreign Key)
   - `transaction_type`: ENUM ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')
   - `amount`: DECIMAL(15, 2)
   - `transaction_date`: TIMESTAMP (Default: CURRENT_TIMESTAMP)

4. **Loans**
   - `loan_id`: INT (Primary Key)
   - `account_number`: INT (Foreign Key)
   - `loan_type`: ENUM ('PERSONAL', 'HOME', 'CAR')
   - `amount`: DECIMAL(15, 2)
   - `interest_rate`: DECIMAL(5, 2)
   - `loan_start_date`: TIMESTAMP (Default: CURRENT_TIMESTAMP)
   - `loan_end_date`: DATE

## Best Practices
- **Code Organization**: The project follows a modular structure, making it easier to maintain and scale.
- **Error Handling**: Comprehensive error handling is implemented to manage exceptions and ensure the stability of the application.
- **Documentation**: Code is well-documented with comments and descriptions to enhance readability and facilitate collaboration.

## Getting Started

### Prerequisites
- MySQL server installed
- Java Development Kit (JDK)
- IDE (e.g., IntelliJ IDEA, Eclipse) for Java development
- JDBC driver for MySQL

### Installation
1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/bank-database-management-system.git
   cd bank-database-management-system

   ## Set Up MySQL Database

2. **Create a new database named BANK.**
- **Execute the provided SQL scripts in the `sql` directory to set up the database schema.**
    ```sql
    CREATE DATABASE bank;
    USE bank;
    -- Add all table creation scripts here
    ```

3. **Connect Java Application to Database**

- **Update your database connection details in the JDBC configuration of your Java application.**
- **Ensure you have the necessary JDBC driver added to your project.**

## Usage

- **Use the provided Java classes to interact with the database for customer management, account creation, transaction processing, and loan management.**
- **Handle exceptions for unique constraints and foreign key relationships in your implementation.**

## Contributing

- **Contributions are welcome! Feel free to fork the repository and submit pull requests.**

## License

- **This project is not licensed**

## Contact

- **For any inquiries or issues, please reach out to sambhavsurthi14@gmail.com .**

