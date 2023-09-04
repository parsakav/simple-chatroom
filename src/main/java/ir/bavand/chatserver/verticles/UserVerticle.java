package ir.bavand.chatserver.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonObject;
import ir.bavand.chatserver.config.Constants;
import ir.bavand.chatserver.dto.UserDto;
import lombok.Getter;

public class UserVerticle extends AbstractVerticle {

    @Getter
    private final UserDto user;
    @Getter
    private final ServerWebSocket serverWebSocket;


    public UserVerticle(UserDto user, ServerWebSocket serverWebSocket){
        this.user = user;
        this.serverWebSocket = serverWebSocket;
    }
    @Override
    public void start(Promise<Void> promise) throws Exception {
vertx.eventBus().consumer(String.format("%s.%s",
        Constants.DEFAULT_MESSAGE_SUBSCRIBER_DESTINATION.getValue()
        ,user.getTeamName()),this::consumeMessage);
    }

    private  void consumeMessage(Message<JsonObject> tMessage) {
       JsonObject j = tMessage.body();

        if(j.getInteger("id")!=user.getId()) {
            serverWebSocket.writeFinalTextFrame(j.toString());
        }
    }
}
