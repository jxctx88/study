package cn.memedai.common.toolkit.cache.redis;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**
 * 
 * @描述: Redis缓存工具类.
 * @author chengtx.
 * @版本: V1.0.
 * <p>
 * key规则:项目名_模块名_业务名,所有的key值放在一个类中
 */
public class RedisUtils {

	private static Logger logger = Logger.getLogger(RedisUtils.class);

	/** 默认缓存时间 */
	private static final int DEFAULT_CACHE_SECONDS = 60 * 60 * 1;// 单位秒 设置成一个钟

	/** 连接池 (哨兵)**/
	private static JedisSentinelPool jedisSentinelPool;
	/**连接池(分片)*/
	//private ShardedJedisPool shardedJedisPool;
	
	private static <T> T execute(Function<T,Jedis> fun ){
		Jedis jedis = null;
		try{
			//从连接池中获取jedis对象
			jedis = jedisSentinelPool.getResource();
			return fun.callback(jedis);
		}catch(Exception e){
			logger.error("redis操作异常",e);
		}finally{
			releaseResource(jedis);
		}
		return null;
	}
	

	/**
	 * 释放redis资源
	 * 
	 * @param jedis
	 */
	private static void releaseResource(Jedis jedis) {
		//关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
		if (jedis != null) {
			//jedisSentinelPool.returnResource(jedis);Jedis 3.0使用jedis.close();
			jedis.close();
		}
	}

	/**
	 * 删除Redis中的所有key
	 * 
	 * @param jedis
	 * @throws Exception
	 */
	public static String flushAll() {
		return execute(new Function<String, Jedis>() {
			@Override
			public String callback(Jedis e) {
				return e.flushAll();
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
	public static Boolean save(Object key, Object object) {
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
	public static Boolean save(final Object key, final Object object, final int seconds) {
		return execute(new Function<Boolean, Jedis>() {
			@Override
			public Boolean callback(Jedis jedis) {
				jedis.set(SerializeUtils.serialize(key), SerializeUtils.serialize(object));
				jedis.expire(SerializeUtils.serialize(key), seconds);
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
	public static Object get(final Object key) {
		
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
	public static Boolean del(final Object key) {
		
		return execute(new Function<Boolean, Jedis>() {
			@Override
			public Boolean callback(Jedis jedis) {
				jedis = jedisSentinelPool.getResource();
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
	public static Boolean del(final Object... keys) {
		
		return execute(new Function<Boolean, Jedis>() {
			@Override
			public Boolean callback(Jedis jedis) {
				jedis = jedisSentinelPool.getResource();
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
	public static Boolean expire(final Object key, final int seconds) {

		return execute(new Function<Boolean, Jedis>() {
			@Override
			public Boolean callback(Jedis jedis) {
				jedis = jedisSentinelPool.getResource();
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
	public static Boolean addHash(final String key, final Object field, final Object value) {
		
		return execute(new Function<Boolean, Jedis>() {
			@Override
			public Boolean callback(Jedis jedis) {
				jedis = jedisSentinelPool.getResource();
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
	public static Object getHash(final Object key, final Object field) {
		
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
	public static Boolean delHash(final Object key, final Object field) {
		
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
	public static Set<byte[]> keys(final String pattern) {
		
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
	public static Map<byte[], byte[]> getAllHash(final Object key) {
		
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
	public static Boolean exists(final Object key) {
		
		return execute(new Function<Boolean, Jedis>() {
			@Override
			public Boolean callback(Jedis jedis) {
				Boolean result = Boolean.FALSE;
				result = jedis.exists(SerializeUtils.serialize(key));
				return result;
			}
		});
		
	}

	public void setJedisSentinelPool(JedisSentinelPool jedisSentinelPool) {
		RedisUtils.jedisSentinelPool = jedisSentinelPool;
	}

	public static JedisSentinelPool getJedisSentinelPool() {
		return jedisSentinelPool;
	}
	
}
