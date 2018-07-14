package cn.tianfahui.webApp02.dmz.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configuration
public class RedisUtil {
	 @Autowired
	 private JedisPool jedisPool;
	 
	 
	 public void saveObj(String key,Object obj) {
		 try {
			 Jedis jedis = jedisPool.getResource();
//			 jedis.strin
		} catch (Exception e) {
			// TODO: handle exception
		}
		 
	 }
}
