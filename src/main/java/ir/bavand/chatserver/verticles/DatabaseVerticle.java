package ir.bavand.chatserver.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import ir.bavand.chatserver.handler.AuthenticationHandler;
import ir.bavand.chatserver.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseVerticle  extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationHandler.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        startPromise.complete();
        vertx.eventBus().consumer("messages",this::save);

    }

    private  void save(Message<JsonObject> tMessage) {
        MessageRepository.getInstance().save(tMessage.body());
    }
}
