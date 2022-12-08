package wpl.server.payload;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@Setter
public class Message {
    private final String message;
    private final Object data;
}
