package cn.memedai.common.toolkit.redis;

import org.junit.Test;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

/**
 * redis性能测试
 * @author chengtx
 *
 */
public class RedisPermormanceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}
	
	@Test
	public void testThreadJunit() throws Throwable{
		
		//Runner数组，相当于并发多少个。 
		TestRunnable[] test = new TestRunnable[10];
		for(int i=0;i<10;i++){
			test[i] = new MyTestRunable();
		}
		
		//用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入 
		MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(test);
		//开始并发执行数组里定义的内容 
		mttr.runTestRunnables();
	}

}


class MyTestRunable extends TestRunnable{

	@Override
	public void runTest() throws Throwable {
		//测试内容
		System.out.println("===" + Thread.currentThread().getId() + "begin to execute");  
        for (int i = 0; i <100000; i++) {  
        	SimpleRedisDemo.set("key_"+i, "value_"+i);  
        }  
        System.out.println("===" + Thread.currentThread().getId() + "end to execute");  
	}
	
	
	
}