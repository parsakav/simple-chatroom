package ir.bavand.chatserver;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import ir.bavand.chatserver.verticles.DatabaseVerticle;
import ir.bavand.chatserver.verticles.WebSocketVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        DeploymentOptions deploymentOptions=new DeploymentOptions();
        deploymentOptions.setWorker(true);
        new Future<>()
        vertx.deployVerticle(DatabaseVerticle.class.getName(),deploymentOptions,x-> System.out.println(" Prepare db successfully"));
        vertx.deployVerticle(new WebSocketVerticle(),e->{
            if(e.succeeded()){
                logger.info("Deployed WebSocketVerticle successfully");
            } else {
                logger.info("Deployed occur errors: ",e.cause());
            }
        } );

    }
}
