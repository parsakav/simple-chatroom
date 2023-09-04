package ir.bavand.chatserver;

import io.vertx.core.json.JsonObject;
import ir.bavand.chatserver.repository.MessageRepository;
import net.bytebuddy.asm.MemberRemoval;
import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;

public class MessageRepositoryTest {
    MessageRepository repository= MessageRepository.getInstance();


    @Order(1)
    @Test
    public void add(){
        JsonObject jsonObject = new JsonObject();

        jsonObject.put("team","test");
        jsonObject.put("t","d1");
        repository.save(jsonObject);

        JsonObject jsonObject2 = new JsonObject();

        jsonObject2.put("team","test");
        jsonObject2.put("t","d2");
        System.out.println(jsonObject.size());
        repository.save(jsonObject2);
        Assertions.assertTrue(new Jedis().smembers("message.test").size()!=0);

    }
    @Order(2)
    @Test
    public void getAll(){

       Assertions.assertEquals(repository.getAll("test").getList().size(),2);



    }

}
