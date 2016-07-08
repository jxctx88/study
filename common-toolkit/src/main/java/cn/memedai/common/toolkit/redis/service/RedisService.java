package cn.memedai.common.toolkit.redis.service;

import org.springframework.beans.factory.annotation.Autowired;


import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
/**
 * redis服务类,特别注意
 * key规则:项目名_模块名_业务名,所有的key值放在一个类中
 * @author chengtx
 *
 */
public class RedisService {
		
	@Autowired(required=false)
	private ShardedJedisPool shardedJedisPool;
	//private JedisSentinelPool jedisSentinelPool;
	
	private<T> T execute(Function<T,ShardedJedis> fun ){
		ShardedJedis shardedJedis = null;
		try{
			//从连接池中获取jedis分片对象
			shardedJedis = shardedJedisPool.getResource();
			return fun.callback(shardedJedis);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null != shardedJedis){
				// 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
				shardedJedis.close();
			}
		}
		return null;
	}
	
	/**
	 * 添加key值并设置生存时间
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(final String key,final String value,final Integer expire){
		return this.execute(new Function<String, ShardedJedis>() {
			@Override
			public String callback(ShardedJedis e) {
				return e.setex(key, expire, value);
			}
		});
	}
	
	/**
	 * 获取key值
	 * @param key
	 * @return
	 */
	public String get(final String key){
		return this.execute(new Function<String, ShardedJedis>() {
			@Override
			public String callback(ShardedJedis e) {
				return e.get(key);
			}
		});
	}

}
