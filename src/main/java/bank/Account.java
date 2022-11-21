package bank;

import bank.execptions.AmountException;

public class Account {

  public Account(int id, String type, double balance) {
    setId(id);
    setType(type);
    setBalance(balance);
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double getBalance() {
    return this.balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void deposit(double amount) throws AmountException {
    if (amount < 1) {
      throw new AmountException("The minimum deposit is 1.00.");
    } else {
      double newBalance = this.balance + amount;
      setBalance(newBalance);
      DataSource.updateAccountBalance(this.id, newBalance);
    }

  }

  public void withdraw(double amount) throws AmountException {
    if (amount < 1) {
      throw new AmountException("The withdrawal amount must be greater than 0.");
    }

    if (this.balance > amount) {
      double newBalance = this.balance - amount;
      setBalance(newBalance);
      DataSource.updateAccountBalance(this.id, newBalance);
    } else {
      throw new AmountException("You do not have sufficient funds for this withdrawal.");

    }
  }

  private int id;
  private String type;
  private double balance;

}
