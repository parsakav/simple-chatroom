package ir.bavand.chatserver.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto implements Serializable {

    public transient static int numberOfUsers=0;
    private String name;

    private int id;
    private String teamName;


    private String deploymanetVerticleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto user = (UserDto) o;
        return name.equals(user.name) && teamName.equals(user.teamName) && id==user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, teamName,id);
    }
}
