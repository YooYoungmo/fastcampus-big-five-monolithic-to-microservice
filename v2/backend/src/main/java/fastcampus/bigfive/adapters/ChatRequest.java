package fastcampus.bigfive.adapters;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private boolean stream;
}
