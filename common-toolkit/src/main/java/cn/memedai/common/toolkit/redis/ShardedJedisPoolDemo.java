package cn.memedai.common.toolkit.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 分片式集群,根据一致性hash算法
 * 会存在无法动态添加减少服务节点的问题
 * @author chengtx
 *
 */
public class ShardedJedisPoolDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//构建连接池配置信息
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		//设置最大连接数
		poolConfig.setMaxTotal(50);
		
		//定义集群信息
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("192.168.1.1",6379));
		shards.add(new JedisShardInfo("192.168.1.1",6379));
		
		//定义集群连接池
		ShardedJedisPool shardedJedisPool = new
				ShardedJedisPool(poolConfig, shards);
		
		ShardedJedis shardedJedis = null;
		try{
			//从连接池中获取jedis分片对象
			shardedJedis = shardedJedisPool.getResource();
			
			for(int i=0;i<10;i++){
				shardedJedis.set("KEY_"+i, "value_"+i);
			}
			
			//从redis中获取数据
			System.out.println(shardedJedis.get("KEY_4"));;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			// 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
			if(null != shardedJedis){
				shardedJedis.close();
			}
		}
		//关闭连接池
		shardedJedisPool.close();
	}

}
