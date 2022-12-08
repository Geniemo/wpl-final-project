package wpl.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wpl.server.payload.dto.SolveDto;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Solve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solve_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Enumerated(EnumType.STRING)
    private SolveStatus status;

    public static Solve createSolve(User user, Quiz quiz) {
        Solve solve = new Solve();
        solve.setUser(user);
        solve.setQuiz(quiz);
        return solve;
    }

    public static SolveDto convertToDto(Solve solve) {
        return new SolveDto(Quiz.convertToDto(solve.getQuiz()), solve.getStatus().getCode());
    }
}
