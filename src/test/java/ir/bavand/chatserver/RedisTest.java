package ir.bavand.chatserver;

import ir.bavand.chatserver.db.RedisUtils;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

public class RedisTest {

    @Test
    public void testAdd(){
      try(Jedis j=  RedisUtils.getJedisPool().getResource()){
          j.sadd("s","a","b","c","d");

    for(var s:  j.smembers("s")){
        System.out.println(s);
    }
      }

    }
}
