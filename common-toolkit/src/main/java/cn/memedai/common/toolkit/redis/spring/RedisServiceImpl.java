package cn.memedai.common.toolkit.redis.spring;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.transaction.annotation.Transactional;

public class RedisServiceImpl implements RedisService {
	private RedisTemplate<byte[], byte[]> template;
	private RedisTemplate<String, Object> template1;
	private ListOperations<String, String> listOps;
	private StringRedisTemplate stringTemplate;

	public void addData(final byte[] key, final byte[] value) {

		try {
			String res = template.execute(new RedisCallback<String>() {
				public String doInRedis(RedisConnection connection)
						throws DataAccessException {
					connection.set(key, value);
					return null;
				}

			});
			// System.out.println("res="+res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//RedisTemplate<byte[], byte[]> Ĭ�ϲ���Serializer
	public void addData(String key, String value) {
		//template1.setKeySerializer(new StringRedisSerializer());
		// template.o
		template.opsForValue().set(key.getBytes(), value.getBytes());
		//template1.opsForValue().set(key, value);
	}

	public void addDataByConnection(byte[] key, byte[] vals) {
		RedisConnection conn = template.getConnectionFactory().getConnection();
		conn.set(key, vals);
		conn.close();// ��Ҫ���йر�connection
	}

	public void flushAll(){
		RedisConnection conn = template.getConnectionFactory().getConnection();
		conn.flushAll();
		conn.close();// 	
	}
	public void addDataByStringTemplate(String key, String vals) {
		stringTemplate.opsForValue().set(key, vals);
		stringTemplate.opsForValue().increment(key, 2.1d);
	}

	//@Transactional 
	public List<Object> setAndUpdateIntransaction(String key, String vals) {
		System.out.println("1111");
		//stringTemplate.setEnableTransactionSupport(true);
		stringTemplate.multi();
		stringTemplate.opsForValue().set(key, vals);
		stringTemplate.opsForValue().increment(key, 2.01);
		List<Object> reses = stringTemplate.exec();
		return reses;
	}

	/*public void addUser(String key, User u) {
		template1.setValueSerializer(new Jackson2JsonRedisSerializer<User>(
				User.class));
		template1.opsForValue().set(key, u);
	}

	public User getUser(String k) {
		template1.setValueSerializer(new JacksonJsonRedisSerializer<User>(
				User.class));
		User user = (User) template1.opsForValue().get(k);
		return user;
	}*/

	public void getData(String key) {
		// listOps = template.opsForList();
		String s = listOps.leftPop(key);
		System.out.println(s);
		template.boundValueOps(key.getBytes());
		template.opsForValue();

	}

	public void putList(byte[] key, byte[] vals, int lorr) {
		if (lorr == 0)
			template.opsForList().leftPush(key, vals);
		else
			template.opsForList().rightPush(key, vals);
	}

	public RedisTemplate<byte[], byte[]> getTemplate() {
		return template;
	}

	public void setTemplate(RedisTemplate<byte[], byte[]> template) {
		this.template = template;
	}

	public ListOperations<String, String> getListOps() {
		return listOps;
	}

	public void setListOps(ListOperations<String, String> listOps) {
		this.listOps = listOps;
	}

	public StringRedisTemplate getStringTemplate() {
		return stringTemplate;
	}

	public void setStringTemplate(StringRedisTemplate stringTemplate) {
		this.stringTemplate = stringTemplate;
	}

	public RedisTemplate<String, Object> getTemplate1() {
		return template1;
	}

	public void setTemplate1(RedisTemplate<String, Object> template1) {
		this.template1 = template1;
	}

}
