package cn.memedai.common.toolkit.redis.spring;

import java.util.List;

public interface RedisService {
	
	void addData(String key,String value);
	
	void addData(byte[] key,byte[] value);
	
	void addDataByConnection(byte[] key,byte[] vals);
	
	void getData(String key);
	
	//void addUser(String key, User u);
	//User getUser(String k);
	
	void addDataByStringTemplate(String key,String vals);
	
	 List<Object> setAndUpdateIntransaction(String key,String vals);
	 
	void putList(byte[] key,byte[] vals,int lorr);
	
	void flushAll();
}
