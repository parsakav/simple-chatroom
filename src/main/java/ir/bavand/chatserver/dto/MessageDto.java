package ir.bavand.chatserver.dto;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageDto implements Serializable {

    private String text;

    private UserDto sender;

    private Date data;

}
