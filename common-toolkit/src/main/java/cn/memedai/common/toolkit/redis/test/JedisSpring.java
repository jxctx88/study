package cn.memedai.common.toolkit.redis.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.memedai.common.toolkit.redis.spring.RedisService;

public class JedisSpring extends Assert {
	ApplicationContext appContext = null;
	RedisService rs = null;

	@Before
	public void setUp() throws Exception {
		appContext = new ClassPathXmlApplicationContext("applicationContext-test.xml");
		rs = (RedisService) appContext.getBean("redisService");
		rs.flushAll();
	}

	@Test
	public void add() {
		rs.addData("a1", "a1value");
		rs.addData("a2".getBytes(), "a2value".getBytes());
		rs.addDataByConnection("a3".getBytes(), "a3value".getBytes());
		//rs.getData("list1");
	}
	
	@Test 
	public void trans(){
		List<Object> reses  = rs.setAndUpdateIntransaction("trans1", "3");
		System.out.println("size="+reses.size());
		assertEquals(reses.size(),1);
		Double val = (Double)reses.get(0);
		assertTrue(val.equals(5.01d));
		//assertEquals(val,5.01d,null);
	}
	/*@Test
	public void optUser(){
		User u = new User();
		u.setId(1);
		u.setName("meimei");
		rs.addUser("u/1", u);
		User u1 = rs.getUser("u/1");
		Assert.assertEquals(u1.getName(),"meimei");
	}*/
	@Test
	public void testStringTemplateSet(){
		//rs.addDataByStringTemplate("st01", "st01data"); 
		rs.addDataByStringTemplate("floatkey2", "1.0"); 
	}
	
	@Test
	public void listPush() {
		rs.putList("list2".getBytes(), "data1".getBytes(), 0);
	}
}
