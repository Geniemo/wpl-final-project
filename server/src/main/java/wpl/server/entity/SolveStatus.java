package wpl.server.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum SolveStatus {
    SUCCESS("SUCCESS"),
    FAIL("FAIL");

    private final String code;

    public static SolveStatus of(String code) {
        return Arrays.stream(SolveStatus.values())
                .filter(s -> s.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new SolveStatusNotMatchException("solve status doesn't match"));
    }
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class SolveStatusNotMatchException extends RuntimeException {

    public SolveStatusNotMatchException(String message) {
        super(message);
    }

    public SolveStatusNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
