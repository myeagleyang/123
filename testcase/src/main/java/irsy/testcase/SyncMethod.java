package irsy.testcase;


/** 
 * 此线程类实现Runnable接口<br /> 
 * 线程同步 ：使用同步方法，实现线程同步<br /> 
 * 同步synchronized方法的的对象监视锁为this,当前对象<br /> 
 * 多个线程使用同一把锁，如果线程安全必需确保:多个线程使用的是同一个this对象<br /> 
 * 所有访问此对象方法的线程都在方法外等待,都会判断同步锁,降低效率,但确保线程安全问题<br /> 
 * */  
public class SyncMethod implements Runnable{  
    //所有Thread多线程线程都共享Runnable(接口对象)和account对象  
    private BankAccount account = new BankAccount();  
    @Override  
    public void run() {  
        for(int i = 0; i< 5; i++){           //总共取款5次  
            makeWithdraw(100);          //每次取款100  
            if(account.getBalance() < 0){  
                System.out.println("☆"+Thread.currentThread().getName()+"   透支了!");  
            }  
        }  
    }  
  
    /** 
     * makeWithdraw 账户取款 
     * @param amount 取款金额<br /> 
     * 打印log记录取款过程 
     * */  
    private synchronized void makeWithdraw(int amount){   
        if(account.getBalance() >= amount){          //如果余额足够则取款  
            System.out.println("☆"+Thread.currentThread().getName()+"   准备取款!");  
            try {  
                Thread.sleep(500);  
            } catch (InterruptedException e) {  
                System.out.println(Thread.currentThread().getName()+"   准备取款,等待0.5s线程中断!"+e.getMessage());  
            }  
            account.withdraw(amount);  
            System.out.println("☆"+Thread.currentThread().getName()+"   完成"+amount+"取款!余额为"+account.getBalance());     }else{          //余额不足则提示  
            System.out.println("☆"+"余额不足以支付"+Thread.currentThread().getName()+amount+"   的取款,余额为"+account.getBalance());  
        }  
    }  
}  