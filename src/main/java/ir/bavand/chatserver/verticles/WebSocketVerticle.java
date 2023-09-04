package ir.bavand.chatserver.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import ir.bavand.chatserver.handler.AuthenticationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketVerticle extends AbstractVerticle {


    private static final Logger logger = LoggerFactory.getLogger(WebSocketVerticle.class);


    @Override
    public void start(Promise<Void> startPromise) throws Exception {

        vertx.createHttpServer().webSocketHandler(
                new AuthenticationHandler(vertx)).listen(
                8080, event -> {
                    if (event.succeeded()) {
                        logger.info("Server was successfully started");
                     startPromise.complete();
                    } else {
                        logger.info("Server wasn't successfully started");

                        startPromise.fail("Port is already in use");
                    }
                }
        );
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        super.stop(stopPromise);
    }


}
