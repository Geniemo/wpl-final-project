package wpl.server.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SolveRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long quizId;

    @NotNull
    private Integer answer;
}
