package wpl.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wpl.server.payload.dto.QuizDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;

    private String title;
    private String description;
    private Integer answer;
    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizImage> images = new ArrayList<>();

    public static Quiz createQuiz(String title, String description, Integer answer) {
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setDescription(description);
        quiz.setAnswer(answer);
        return quiz;
    }

    public static QuizDto convertToDto(Quiz quiz) {
        return new QuizDto(quiz.getId(), quiz.getTitle(), quiz.getDescription(),
                quiz.getImages()
                        .stream()
                        .map(img -> img.getFile().getUri())
                        .collect(Collectors.toList()));
    }
}
