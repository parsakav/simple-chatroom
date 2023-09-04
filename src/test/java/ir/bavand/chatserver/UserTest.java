package ir.bavand.chatserver;

import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class UserTest {

    @Order(-1)
@RepeatedTest(3)
    @DisplayName("UserTe ")
  //  @Disabled("Disabled until UserService is up!")
    public void test(){
        new Jedis();
        System.out.println("a");
    } @Order(0)


    @DisplayName("UserTe ")
  //  @Disabled("Disabled until UserService is up!")
    @Test
    public void test1(){
        System.out.println("b");
    }

}
