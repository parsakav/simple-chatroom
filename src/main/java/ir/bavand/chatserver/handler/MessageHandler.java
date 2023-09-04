package ir.bavand.chatserver.handler;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import ir.bavand.chatserver.config.Constants;
import ir.bavand.chatserver.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class MessageHandler implements Handler<String> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationHandler.class);

    private final UserDto user;
    private final Vertx vertx;


    public MessageHandler(UserDto user, Vertx vertx){
        this.vertx=vertx;
        this.user=user;
    }

    @Override
    public void handle(String event) {
        try {
            JsonObject jsonObject = new JsonObject(event);

            if(jsonObject.size()!=1){
                return;
            }
            if(jsonObject.getString("msg")==null){
                return;
            }
            jsonObject.put("from", user.getName())
                    .put("date", new Date().toString())
                    .put("id", user.getId());
            logger.info("A new message received:" + jsonObject);
            vertx.eventBus().publish(
                    String.format("%s.%s",
                            Constants.DEFAULT_MESSAGE_SUBSCRIBER_DESTINATION.getValue()
                            , user.getTeamName()), jsonObject);
            vertx.eventBus().publish("messages",jsonObject.put("team",user.getTeamName()));

        } catch (Throwable t){

        }
    }
}
