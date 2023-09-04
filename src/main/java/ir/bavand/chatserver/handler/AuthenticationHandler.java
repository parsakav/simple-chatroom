package ir.bavand.chatserver.handler;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonObject;
import ir.bavand.chatserver.dto.UserDto;
import ir.bavand.chatserver.repository.MessageRepository;
import ir.bavand.chatserver.security.Authentication;
import ir.bavand.chatserver.verticles.UserVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationHandler implements Handler<ServerWebSocket> {
    private static final HashMap<UserDto, ServerWebSocket> clients = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationHandler.class);
    private final Vertx vertx;

    public AuthenticationHandler(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void handle(ServerWebSocket serverWebSocket) {

        serverWebSocket.handler(buf -> {
            logger.info("A new client are connecting");
            JsonObject jsonObject = null;
            UserDto user;
            try {
                jsonObject = new JsonObject(buf);
                if ((user = Authentication.authenticate(jsonObject)) != null) {
                    success(serverWebSocket,user);
                    if(jsonObject.getBoolean("get")!=null && jsonObject.getBoolean("get")==true){
                        sendMessage(serverWebSocket, user.getTeamName());
                    }
                } else {
                    serverWebSocket.writeFinalTextFrame("Authentication failed");
                    serverWebSocket.close();

                }
            } catch (Throwable e) {
                serverWebSocket.writeFinalTextFrame("The message format was not recognized"
                );
                serverWebSocket.close();
            }




        });

    }

    private void sendMessage(ServerWebSocket serverWebSocket,String teamName) {

       serverWebSocket.writeFinalTextFrame( MessageRepository.getInstance().getAll(teamName).toString());
    }

    private void success(ServerWebSocket serverWebSocket, UserDto user){
        // remove authentication handler
        serverWebSocket.handler(null);
        logger.info("Client was accepted");
        clients.put(user, serverWebSocket);

        user.setId(UserDto.numberOfUsers++);
        serverWebSocket.closeHandler(v -> closeHandler(v, serverWebSocket));

        vertx.deployVerticle(new UserVerticle(user, serverWebSocket), ar -> {
            user.setDeploymanetVerticleId(ar.result());

        });
        serverWebSocket.textMessageHandler(new MessageHandler(user, vertx));

    }

    private void closeHandler(Void unused, ServerWebSocket serverWebSocket) {


        for (Map.Entry<UserDto, ServerWebSocket> entry : clients.entrySet()) {
            if (entry.getValue().equals(serverWebSocket)) {
                vertx.undeploy(entry.getKey().getDeploymanetVerticleId());

                break;
            }
        }

        clients.values().remove(serverWebSocket);
        logger.info("Client was closed");

    }


}
