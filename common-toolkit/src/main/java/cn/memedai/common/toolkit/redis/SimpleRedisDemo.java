package cn.memedai.common.toolkit.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import cn.memedai.common.toolkit.serialize.SerializeUtil;

/**
 * 简单的redisclient使用
 */
public class SimpleRedisDemo {

    private static Jedis jedis = null;
    static {
        jedis = new Jedis("192.168.1.1",6379);
    }

    public static void main(String[] args) throws Exception {
    	for(int i =0;i<100;i++){
    		set("name"+i,"xiong"+i);
    	}
    	//set("name","xiong");
    	//set("name","xiong",2);
    	//del("name");
    	//get("name");
    	//List<String> list = new ArrayList<String>();
    	//addSet("name_set",new HashSet<String>(Arrays.asList(new String[]{"xiong1","xiong2","xiong3"})));
    	//addList("name_list",Arrays.asList("xiong1","xiong2","xiong3"));
    	/*HashMap<String, String> map = new HashMap<String, String>(){{
    		put("name", "xiong");
    		put("set","男");
    		put("nickname", "雄");
    	}};
    	addMap("name_map",map);*/
    	
    	/*List<ScoreBean> list = new ArrayList<ScoreBean>();
    	list.add(new ScoreBean("xiong1", 1));
    	list.add(new ScoreBean("xiong2", 2));
    	list.add(new ScoreBean("xiong3", 3));
    	addZset("name_zset", list);*/
    	
    	/*set("name_seri", "name_seri_val", 3);
    	System.out.println(get((Object) "name_seri"));;*/
    	
    }

    /**
     * 设置key值
     * @param key
     * @param value
     */
    public static void set(String key,String value){
    	/*long begin = System.currentTimeMillis();
    	System.out.println("===" + Thread.currentThread().getId() + "begin to execute "+begin);  
        for (int i = 0; i <100000; i++) {  
        	jedis.set(key+i, value+i);  
        }  
        System.out.println("===" + Thread.currentThread().getId() + "end to execute " + (System.currentTimeMillis()-begin));  */
        jedis.set(key,value);
        //管道命令
        //jedis.pipelined();
        //String val = jedis.get(key);
        //System.out.println("name="+val);
        //jedis.close();
    }
    
    /**
     * 设置过期时间
     * @param key
     * @param value
     * @param expire
     * @throws Exception
     */
    public static void set(String key,String value,Integer expire) throws Exception{
    	jedis.setex(key, expire, value);
    	//jedis.set(key, value);
        //jedis.expire(key, expire);
        System.out.println(key+"="+jedis.get(key));
        Thread.sleep(expire*1000+1000);
        System.out.println(key+"="+jedis.get(key));
        jedis.close();
    }
    
    /**
     * 获取key值
     * @param key
     */
    public static void get(String key){
    	System.out.println(jedis.get(key));
    	jedis.close();
    }
    
    /**
     * 删除key
     * @param key
     */
    public static void del(String key){
    	System.out.println(jedis.del(key));
    	jedis.close();
    }
    
    /**
     * 添加set集合,无序
     * @param key
     * @param list
     */
    public static void addSet(String key,Set<String> set){
    	Long result = jedis.sadd(key,set.toArray(new String[]{}));
		System.out.println("保存"+key+"的返回值:"+result);
		Set<String> memSet = jedis.smembers(key);
		for(String str : memSet){
			System.out.println(str);
		}
		jedis.close();
    }
    
    /**
     * 添加List集合,有序
     */
    public static void addList(String key,List<String> list){
    	Long result = jedis.rpush(key, (String[]) list.toArray());
		System.out.println(result);
		List<String> nameList = jedis.lrange(key, 0, -1);
		for(String str : nameList){
			System.out.println(str);
		}
		jedis.close();
    }
    
    /**
     * 添加Map
     * @param key
     * @param map
     */
    public static void addMap(String key,Map<String,String> map){
    	for(Map.Entry<String, String> entry : map.entrySet()){
    		jedis.hset(key, entry.getKey(), entry.getValue());
    	}
		Map<String,String> entryMap = jedis.hgetAll("name_map");
		for(Map.Entry<String, String> entry : entryMap.entrySet()){
			System.out.println(entry.getKey()+ ":" + entry.getValue());
		}
		jedis.close();
    }
    
    /**
     * 添加zset
     * @param key
     * @param list
     */
    public static void addZset(String key,List<ScoreBean> list){
    	for(ScoreBean scoreBean : list){
    		jedis.zadd(key, scoreBean.getScore(),scoreBean.getValue());
    	}
		Set<Tuple> set = jedis.zrangeWithScores("name_zset", 0, -1);
		for(Tuple str :set){
			System.out.println(str.getScore()+":" +str.getElement());
		}
		jedis.close();
    }
    
    /**
     * 添加key
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public static boolean set(Object key,Object value,int expire){
    	jedis.setex(SerializeUtil.serialize(key), expire, SerializeUtil.serialize(value));
    	jedis.close();
    	return Boolean.TRUE;
    }
    
    /**
     * 获取key
     * @param key
     * @return
     */
    public static String get(Object key){
    	byte[] bytes = jedis.get(SerializeUtil.serialize(key));
    	jedis.close();
    	return (String) (bytes == null 
    			? null 
    			: SerializeUtil.unSerialize(bytes));
    	
    }
}


class ScoreBean{
	private String value;
	private Integer score;
	public ScoreBean(String value,Integer score){
		this.value = value;
		this.score = score;
	}
	public String getValue() {
		return value;
	}
	public Integer getScore() {
		return score;
	}
	
}


