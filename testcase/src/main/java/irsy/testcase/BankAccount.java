package irsy.testcase;


public class BankAccount {  
  //余额  
  private int balance = 500;  
  //查询  
  public int getBalance(){  
      return balance;  
  }  
  //取款  
  public void withdraw(int amount){  
	  balance = balance - amount;  
  }  
  //存款  
  public void deposit(int amount){  
	  balance = balance + amount;  
  }  
}  