package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
  public static Connection connect() {
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(db_file);
      System.out.println("We're connected");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }

  public static Customer getCustomer(String username) {
    String sql = "select * from customers where username = ?";
    Customer customer = null;

    /// Try with resources closesable resource after try block is completed
    try (Connection connection = connect(); PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery()) {
        customer = new Customer(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getInt("account_id"));
      }
      ;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return customer;
  }

  public static Account getAccount(int accountId) {
    String sql = "select * from accounts where id = ?";
    Account account = null;
    /// Try with resources closesable resource after try block is completed
    try (Connection connection = connect(); PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, accountId);
      try (ResultSet resultSet = statement.executeQuery()) {
        account = new Account(resultSet.getInt("id"), resultSet.getString("type"), resultSet.getDouble("balance"));
      }
      ;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return account;
  }

  public static void updateAccountBalance(int accountId, double balance) {
    String sql = "update accounts set balance ? where id = ?";

    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql);) {
      /// Set the paramter for the sql statement with prepare statment to change the
      /// value of ? to a validated value not raw.
      statement.setDouble(1, balance);
      statement.setInt(2, accountId);
      /// Execute the update
      statement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Customer customer = getCustomer("twest8o@friendfeed.com");

    System.out.println(customer.getName());

    Account account = getAccount(10385);

    System.out.println(account.getBalance());
    System.out.println(account.getType());
  }

}
