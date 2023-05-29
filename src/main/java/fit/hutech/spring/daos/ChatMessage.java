package fit.hutech.spring.daos;

import fit.hutech.spring.constants.MessageType;
import lombok.Data;

@Data
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
}
