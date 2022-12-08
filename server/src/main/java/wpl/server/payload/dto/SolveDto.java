package wpl.server.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolveDto {

    private Long userId;
    private Long quizId;
    private String status;
}
