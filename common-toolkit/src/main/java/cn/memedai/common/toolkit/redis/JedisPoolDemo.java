package cn.memedai.common.toolkit.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * redis连接池
 * @author chengtx
 *
 */
public class JedisPoolDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//构建连接池配置
		JedisPoolConfig config = new JedisPoolConfig();
		//设置最大连接数
		config.setMaxTotal(50);
		//最大空闲数
		config.setMaxIdle(5);
		config.setMaxWaitMillis(1000 * 100);
		//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
		config.setTestOnBorrow(true);
		
		//构建连接池
		JedisPool jedisPool = new JedisPool(config,"192.168.1.1",6379);
		
		//从连接池中获取连接
		Jedis jedis = jedisPool.getResource();
		
		//读取数据
		jedis.set("pool", "xiongxiong");
		System.out.println(jedis.get("pool"));
		
		//关闭连接,将连接还回到连接池中
		//jedisPool.returnResource(jedis);
		jedis.close();
		
		//释放连接池
		jedisPool.close();
	}

}
