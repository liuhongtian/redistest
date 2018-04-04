package net.lht.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class Tester {

	@Autowired
	StringRedisTemplate redis;
	
	public long test() {
		return redis.opsForValue().increment("count", 1);
		// return Integer.parseInt(redis.opsForValue().get("count"));
	}
	
	public void reset() {
		redis.opsForValue().set("count", "0");
	}
	
	public int getResult() {
		return Integer.parseInt(redis.opsForValue().get("count"));
	}

}
