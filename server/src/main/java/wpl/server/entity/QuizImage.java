package wpl.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wpl.server.file_storage.File;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class QuizImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "file_id")
    private File file;

    public static QuizImage createQuizImage(Quiz quiz, File file) {
        QuizImage quizImage = new QuizImage();
        quizImage.setQuiz(quiz);
        quizImage.setFile(file);
        return quizImage;
    }
}
