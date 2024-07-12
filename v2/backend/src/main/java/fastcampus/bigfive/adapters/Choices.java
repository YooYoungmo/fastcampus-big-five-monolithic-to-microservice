package fastcampus.bigfive.adapters;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Choices {
    private int index;
    private Message message;
    private Object logprobs;
    @JsonProperty("finish_reason")
    private String finishReason;
}
