package com.joe.testdb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTests {
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    HashOperations<String, String, Object> hashOperations;

   @Test
    public void testOne() {
       String s = redisTemplate.opsForValue().get("234234").toString();
//       redisTemplate.opsForHash().put();
       System.out.println(s);
   }

    @Test
    public void testSet() {
        redisTemplate.opsForValue().set("234232344", "345345", 10, TimeUnit.SECONDS);
//        redisTemplate.opsForHash().put();
//        System.out.println(s);
    }
    @Test
    public void testHashSet() {

        Map<String,Object> testMap = new HashMap();
        testMap.put("name","jack");
        testMap.put("age",27);
        testMap.put("class","1");
        redisTemplate.opsForHash().putAll("hash",testMap);
    }


}
