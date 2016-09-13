package cn.memedai.common.toolkit.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Map;
import java.util.Set;

/**
 * 
 * @描述: Redis缓存工具类.
 * @author chengtx.
 * @版本: V1.0.
 * <p>
 * key规则:项目名_模块名_业务名,所有的key值放在一个类中
 */
public class RedisUtils{

	private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

	/** 默认缓存时间 */
	private static final int DEFAULT_CACHE_SECONDS = 60 * 60 * 1;// 单位秒 设置成一个钟

	private static JedisPool jedisPool;
	/** 连接池 (哨兵)**/
	//private static JedisSentinelPool jedisSentinelPool;
	/**连接池(分片)*/
	private ShardedJedisPool shardedJedisPool;
	
	private <T> T execute(Function<T,Jedis> fun ){
		Jedis jedis = null;
		try{
			//从连接池中获取jedis对象
			jedis = jedisPool.getResource();
			return fun.callback(jedis);
		}catch(Exception e){
			logger.error("redis操作异常",e);
			throw new RuntimeException("redis操作异常",e);
		}finally{
			releaseResource(jedis);
		}
	}
	

	/**
	 * 释放redis资源
	 * 
	 * @param jedis
	 */
	private void releaseResource(Jedis jedis) {
		//关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 删除Redis中的所有key
	 * 
	 * @throws Exception
	 */
	public String flushAll() {
		return execute(new Function<String, Jedis>() {
			@Override
			public String callback(Jedis jedis) {
				return jedis.flushAll();
			}
		});
	}

	/**
	 * 保存一个对象到Redis中(缓存过期时间:使用此工具类中的默认时间) . <br/>
	 * 
	 * @param key
	 *            键 . <br/>
	 * @param object
	 *            缓存对象 . <br/>
	 * @return true or false . <br/>
	 * @throws Exception
	 */
	public Boolean save(Object key, Object object) {
		return save(key, object, DEFAULT_CACHE_SECONDS);
	}

	/**
	 * 保存一个对象到redis中并指定过期时间
	 * 
	 * @param key
	 *            键 . <br/>
	 * @param object
	 *            缓存对象 . <br/>
	 * @param seconds
	 *            过期时间（单位为秒）.<br/>
	 * @return true or false .
	 */
	public Boolean save(final Object key, final Object object, final int seconds) {
		return execute(new Function<Boolean, Jedis>() {
			@Override
			public Boolean callback(Jedis jedis) {
				byte[] keyBytes = SerializeUtils.serialize(key);
				jedis.set(keyBytes, SerializeUtils.serialize(object));
				jedis.expire(keyBytes, seconds);
				return true;
			}
		});
		
	}

	/**
	 * 根据缓存键获取Redis缓存中的值.<br/>
	 * 
	 * @param key
	 *            键.<br/>
	 * @return Object .<br/>
	 * @throws Exception
	 */
	public Object get(final Object key) {
		
		return execute(new Function<Object, Jedis>() {
			@Override
			public Object callback(Jedis jedis) {
				byte[] obj = jedis.get(SerializeUtils.serialize(key));
				return obj == null ? null : SerializeUtils.unSerialize(obj);
			}
		});
		
	}

	/**
	 * 根据缓存键清除Redis缓存中的值.<br/>
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Boolean del(final Object key) {
		
		return execute(new Function<Boolean, Jedis>() {
			@Override
			public Boolean callback(Jedis jedis) {
				jedis.del(SerializeUtils.serialize(key));
				return true;
			}
		});
		
	}

	/**
	 * 根据缓存键清除Redis缓存中的值.<br/>
	 * 
	 * @param keys
	 * @return
	 * @throws Exception
	 */
	public Boolean del(final Object... keys) {
		
		return execute(new Function<Boolean, Jedis>() {
			@Override
			public Boolean callback(Jedis jedis) {
				jedis.del(SerializeUtils.serialize(keys));
				return true;
			}
		});
		
	}

	/**
	 * 
	 * @param key
	 * @param seconds
	 *            超时时间（单位为秒）
	 * @return
	 */
	public Boolean expire(final Object key, final int seconds) {

		return execute(new Function<Boolean, Jedis>() {
			@Override
			public Boolean callback(Jedis jedis) {
				jedis.expire(SerializeUtils.serialize(key), seconds);
				return true;
			}
		});
		
	}

	/**
	 * 添加一个内容到指定key的hash中
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Boolean addHash(final String key, final Object field, final Object value) {
		
		return execute(new Function<Boolean, Jedis>() {
			@Override
			public Boolean callback(Jedis jedis) {
				jedis.hset(SerializeUtils.serialize(key), SerializeUtils.serialize(field), SerializeUtils.serialize(value));
				return true;
			}
		});
	}

	/**
	 * 从指定hash中拿一个对象
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public Object getHash(final Object key, final Object field) {
		
		return execute(new Function<Object, Jedis>() {
			@Override
			public Object callback(Jedis jedis) {
				byte[] obj = jedis.hget(SerializeUtils.serialize(key), SerializeUtils.serialize(field));
				return SerializeUtils.unSerialize(obj);
			}
		});
		
	}

	/**
	 * 从hash中删除指定filed的值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public Boolean delHash(final Object key, final Object field) {
		
		return execute(new Function<Boolean, Jedis>() {
			@Override
			public Boolean callback(Jedis jedis) {
				long result = jedis.hdel(SerializeUtils.serialize(key), SerializeUtils.serialize(field));
				return result == 1 ? true : false;
			}
		});
	}

	/**
	 * 拿到缓存中所有符合pattern的key
	 * 
	 * @param pattern
	 * @return
	 */
	public Set<byte[]> keys(final String pattern) {
		
		return execute(new Function<Set<byte[]>, Jedis>() {
			@Override
			public Set<byte[]> callback(Jedis jedis) {
				Set<byte[]> allKey = jedis.keys(("*" + pattern + "*").getBytes());
				return allKey;
			}
		});
		
	}

	/**
	 * 获得hash中的所有key value
	 * 
	 * @param key
	 * @return
	 */
	public Map<byte[], byte[]> getAllHash(final Object key) {
		
		return execute(new Function<Map<byte[], byte[]>, Jedis>() {
			@Override
			public Map<byte[], byte[]> callback(Jedis jedis) {
				Map<byte[], byte[]> map = jedis.hgetAll(SerializeUtils.serialize(key));
				return map;
			}
		});
	}

	/**
	 * 判断一个key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public Boolean exists(final Object key) {
		
		return execute(new Function<Boolean, Jedis>() {
			@Override
			public Boolean callback(Jedis jedis) {
				Boolean result = Boolean.FALSE;
				result = jedis.exists(SerializeUtils.serialize(key));
				return result;
			}
		});
		
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public static JedisPool getJedisPool() {
		return jedisPool;
	}
	
}
