/**
 User
 Class for handling user-related operations like registration and login.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class User {
    private Connection connection;
    private Scanner sc;
    public User(Connection connection, Scanner sc) {
        this.connection = connection;
        this.sc = sc;
    }

    /**
     * Method to register a new user by taking user details.
     */

    public void register(){
        sc.nextLine();// Clear the scanner buffer
        System.out.print("Full Name: ");
        String full_name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
         // Check if user already exists
        if(user_exist(email)){
            System.out.println("User alraedy exists for this email address!!");
        }

        // Insert user details into the database
        String register_query = "INSERT INTO User(full_name, email, password) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
            preparedStatement.setString(1, full_name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Registration Successfully!");
            } else{
                System.out.println("Resistration failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to log in the user by verifying email and password.
     */
    public String login(){
        sc.nextLine();
        System.out.println("Email: ");
        String email = sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();

        String lgin_query = "SELECT * FROM User WHERE email = ? AND password = ?";
        try {
          PreparedStatement preparedStatement = connection.prepareStatement(lgin_query);
          preparedStatement.setString(1, email);
          preparedStatement.setString(2, password); 

          ResultSet resultSet = preparedStatement.executeQuery();

          if(resultSet.next()){
            return email;// Successful login
          } else{
            return null; // Login failed
          }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

      /**
     * Method to check if the user already exists in the system.
     */
    public boolean user_exist(String email){
        String query = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}