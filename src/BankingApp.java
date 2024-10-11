import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
//import static java.lang.Class.forName;


public class BankingApp {
    private static final String url ="jdbc:mysql://127.0.0.1:3306/banking_system";
    private static final String username = "root";
    private static final String password = "Dhami@123";
     public static void main(String[] args) throws ClassNotFoundException, SQLException {
         // Main method to handle the banking system application

        // try{
        //     Class.forName("com.mysql.cj.jdbc.Driver");
        // }catch (ClassNotFoundException e){
        //     System.out.println(e.getMessage());
        // }
        try{
            // Establish a connection to the MySQL database
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner scanner =  new Scanner(System.in);
            User user = new User(connection, scanner);
            Accounts accounts = new Accounts(connection, scanner);
            AccountManager accountManager = new AccountManager(connection, scanner);

            String email; // To store the logged-in user's email
            long account_number; // // To store account number for various operations

            while(true){ // Infinite loop for showing the main menu
                System.out.println("========================================");
                System.out.println("      Welcome to Secure Bank System      ");
                System.out.println("========================================");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.println("========================================");
                System.out.println("Please select an option (1-3): ");

                System.out.println("Enter your choice: ");
                int choice1 = scanner.nextInt();
                 // Handle user choices
                switch (choice1){
                    case 1:
                     // Register a new user
                        user.register();
                        break;
                    case 2:
                    // Login the user
                        email = user.login();
                        if(email!=null){// If login is successful
                            System.out.println();
                            System.out.println("User Logged In!");
                            // Check if the user has an existing bank account
                            if(!accounts.account_exist(email)){
                                System.out.println();
                                System.out.println("1. Open a new Bank Account");
                                System.out.println("2. Exit");
                                // If the user chooses to open a new account
                                if(scanner.nextInt() == 1) {
                                    account_number = accounts.open_account(email);
                                    System.out.println("Account Created Successfully");
                                    System.out.println("Your Account Number is: " + account_number);
                                }else{
                                    break;
                                }

                            }
                            // If the user already has an account, retrieve the account number
                            account_number = accounts.getAccount_number(email);

                            // Submenu for bank account operations
                            int choice2 = 0;
                            while (choice2 != 5) {
                                System.out.println();
                                System.out.println("1. Debit Money");
                                System.out.println("2. Credit Money");
                                System.out.println("3. Transfer Money");
                                System.out.println("4. Check Balance");
                                System.out.println("5. Log Out");
                                System.out.println("Enter your choice: ");
                                choice2 = scanner.nextInt();

                                 // Handle user actions within the account

                                switch (choice2) {
                                    case 1:
                                        accountManager.debit_money(account_number);
                                        break;
                                    case 2:
                                        accountManager.credit_money(account_number);
                                        break;
                                    case 3:
                                        accountManager.transfer_money(account_number);
                                        break;
                                    case 4:
                                        accountManager.getBalance(account_number);
                                        break;
                                    case 5:
                                        break;
                                    default:
                                        System.out.println("Enter Valid Choice!");
                                        break;
                                }
                            }

                        }
                        else{
                            System.out.println("Incorrect Email or Password!");
                        }
                    case 3:
                        System.out.println("THANK YOU FOR USING BANKING SYSTEM!!!");
                        System.out.println("Exiting System!");
                        return;
                    default:
                        System.out.println("Enter Valid Choice");
                        break;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
