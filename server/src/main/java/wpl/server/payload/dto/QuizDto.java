package wpl.server.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class QuizDto {

    private Long quizId;
    private String title;
    private String description;
    private List<String> images = new ArrayList<>();
    private List<SolveDto> solves = new ArrayList<>();
}
