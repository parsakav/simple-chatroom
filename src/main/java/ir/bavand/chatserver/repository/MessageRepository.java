package ir.bavand.chatserver.repository;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import ir.bavand.chatserver.db.RedisUtils;
import lombok.SneakyThrows;
import redis.clients.jedis.Jedis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MessageRepository {
    private final static MessageRepository instance = new MessageRepository();
    private MessageRepository(){

    }

   public void save(JsonObject tMessage) {
        String team = tMessage.getString("team");
        if (team != null && !team.isEmpty())
            try (Jedis j = RedisUtils.getJedisPool().getResource()) {
                tMessage.remove("team");
                tMessage.remove("id");
                j.sadd("message." + team, tMessage.toString());

                j.bgrewriteaof();
            }


    }
    public JsonArray getAll(String teamName) {
JsonArray a = new JsonArray();

        if (teamName != null && !teamName.isEmpty())
            try (Jedis j = RedisUtils.getJedisPool().getResource()) {
               Set<String> s= j.smembers("message." + teamName);
               for(String msg:s){

                   a.add(new JsonObject(msg));
               }
            }
    sort(a);
        return a;
    }
    public void sort(JsonArray array){
        SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");

        List<JsonObject> objects=array.getList();
        Collections.sort(objects, (o1, o2) -> {
            try {
                System.out.println(o1.getString("date"));
                return parser.parse(o1.getString("date")).compareTo(parser.parse(o2.getString("date")));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static MessageRepository getInstance() {
        return instance;
    }
}
