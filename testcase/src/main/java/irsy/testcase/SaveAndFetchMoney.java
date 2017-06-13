package irsy.testcase;

public class SaveAndFetchMoney {
	 /**
	  *   */
	 public static void main(String[] args) {
	  int num_of_save = 20; // 存款线程数
	  int num_of_fetch = 100; // 取款线程数
	  Thread[] thread = new Thread[num_of_save];
	  Thread[] thread1 = new Thread[num_of_fetch];
	  Account t = new Account("1928106", 10000);

	  for (int i = 0; i < num_of_save; i++) {// 创建存款线程并启动
	   thread[i] = new Thread(new SaveMoney(t,
	     (float) Math.random() * 1000));
	   thread[i].start();
	  }
	  for (int i = 0; i < num_of_fetch; i++) {// 创建取款线程并启动
	   thread1[i] = new Thread(new FetchMoney(t,
	     (float) Math.random() * 1000));
	   thread1[i].start();
	  }
	  for (int i = 0; i < num_of_save; i++) {// 等待所有存款线程结束
	   try {
	    thread[i].join();
	   } catch (InterruptedException e) {
	    e.printStackTrace();
	   }
	  }
	  for (int i = 0; i < num_of_fetch; i++) {// 等待所有取款线程结束
	   try {
	    thread1[i].join();
	   } catch (InterruptedException e) {
	    e.printStackTrace();
	   }
	  }
	  System.out.println("账号：" + t.accountName + " 最终余额为：" + t.balance);

	 }

	}

	class Account {
	 String accountName;
	 float balance;

	 Account(String accountName, int value) {
	  this.accountName = accountName;
	  this.balance = value;
	 }

	 public synchronized void add(float amount) {// 模拟存款操作
	  System.out.print("账号：" + this.accountName + " 的原来余额为：" + this.balance);
	  balance += amount;
	  System.out.println(" 存入" + amount + "元后，现在余额为：" + this.balance);
	 }

	 public synchronized void sub(float amount) {// 模拟取款操作
	  if (balance >= amount) {
	   System.out.print("账号：" + this.accountName + " 的原来余额为："
	     + this.balance);
	   balance -= amount;
	   System.out.println(" 取出" + amount + "元后，现在余额为：" + this.balance);
	  } else {
	   System.out.println("账号：" + this.accountName + " 的原来余额为："
	     + this.balance + "要取出的金额为：" + amount + " 余额不足！无法完成取款操作！");
	  }

	 }
	}

	class SaveMoney implements Runnable {
	 Account a;
	 float amount;

	 SaveMoney(Account a, float amount) {
	  this.a = a;
	  this.amount = amount;
	 }

	 public void run() {
	  try {
	   Thread.sleep((int) Math.random() * 2000); // 设定随机休眠时间
	  } catch (InterruptedException e) {
	   e.printStackTrace(); // 追踪异常事件发生时执行堆栈的内容
	   System.out.println(e.toString()); // 打印输出异常的信息的简短描述
	  }
	  a.add(amount);
	 }

	}

	class FetchMoney implements Runnable {
	 Account a;
	 float amount;

	 FetchMoney(Account a, float amount) {
	  this.a = a;
	  this.amount = amount;
	 }

	 public void run() {
	  try {
	   Thread.sleep((int) Math.random() * 2000); // 设定随机休眠时间
	  } catch (InterruptedException e) {
	   e.printStackTrace(); // 追踪异常事件发生时执行堆栈的内容
	   System.out.println(e.toString()); // 打印输出异常的信息的简短描述
	  }
	  if (a.balance >= amount) {
	   a.sub(amount);
	  }

	 }

	}
	 