package wpl.server.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolveDto {

    private QuizDto quiz;
    private String status;
}
