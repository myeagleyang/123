package irsy.testcase;


import org.junit.Test;  
  
//import com.tsxs.syncmethods.NoSync;  
//import com.tsxs.syncmethods.SyncMethod;  
  
public class TreadSyncTest {  
  
//  @Test  
//  public void test() {  
/*Junit不适合多线程并发测试。 
    因为线程还在激活状态的时候，Junit已经执行完成。 
    在Junit的TestRunner中,它没有被设计成搜寻Runnable实例， 
    并且等待这些线程发出报告，它只是执行它们并且忽略了它们的存在。 
    综上,不可能在Junit中编写和维护多线程的单元测试。 
}*/   
    public static void main(String[] args) {  
        //实现Runnable：所有Thread多线程线程都共享Runnable(接口对象)  
//      NoSync target =new NoSync();  
        SyncMethod target = new SyncMethod();  
        //创建李琦和他老婆两个线程实现取款(同时)  
        Thread lq = new Thread(target);  
        lq.setName("罗密欧");  
        Thread lqwf = new Thread(target);  
        lqwf.setName("朱丽叶");  
        //调用Thread对象的start()方法,启动线程,执行run()方法(OS)  
        lq.start();  
        lqwf.start();  
    }  
}  