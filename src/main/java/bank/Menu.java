package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.execptions.AmountException;

public class Menu {
  private Scanner scanner;

  public static void main(String[] args) {
    System.out.println("Welcome to sample Bank project. ");

    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();

    if (customer != null) {
      Account account = DataSource.getAccount(customer.getAccountId());

      menu.showMenu(customer, account);

    }

    menu.scanner.close();
  }

  private void showMenu(Customer customer, Account account) {
    int selection = 0;

    while (selection != 4 && customer.isAuthenticated()) {
      System.out.println("============================================");
      System.out.println("Please select one of the following options: ");
      System.out.println("1. Deposit");
      System.out.println("2. Withdraw");
      System.out.println("3. Check Balance");
      System.out.println("4. Logout");

      selection = scanner.nextInt();
      double amount = 0;

      switch (selection) {
        case 1:
          System.out.println("How much deposit? ");
          amount = scanner.nextDouble();
          try {
            account.deposit(amount);
          } catch (AmountException e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again.");
          }
          break;
        case 2:
          System.out.println("How much withdraw? ");
          amount = scanner.nextDouble();
          try {
            account.withdraw(amount);
          } catch (AmountException e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again.");
          }
          break;
        case 3:
          System.out.println("Your balance is: " + account.getBalance());
          break;
        case 4:
          Authenticator.logout(customer);
          System.out.println("Thanks for banking at sample project.");
          break;
        default:
          System.out.println("Invalid input.");
          break;
      }
    }
  }

  private Customer authenticateUser() {
    System.out.println("Please enter username: ");
    String username = scanner.next();

    System.out.println("Please enter password: ");
    String password = scanner.next();

    Customer customer = null;
    try {

      customer = Authenticator.login(username, password);
    } catch (LoginException e) {
      System.out.println("There was an error: " + e.getMessage());
    }
    return customer;

  }

}
