package ir.bavand.chatserver.security;

import io.vertx.core.json.JsonObject;
import ir.bavand.chatserver.dto.UserDto;

public class Authentication {

    // authentication logic goes here
    public static UserDto authenticate(JsonObject json) {
        UserDto user = new UserDto();
       user.setName(json.getString("name"));
       user.setTeamName(json.getString("team"));

        return user;
    }
}
