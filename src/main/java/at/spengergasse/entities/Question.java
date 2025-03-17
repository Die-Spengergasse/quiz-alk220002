package at.spengergasse.entities;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "q_questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "q_id")

    private int id;
    @Column(name = "q_text")
    private String text;
    @OneToMany (fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_q_id")
    private List<Answer> answerList = new ArrayList<>();
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", answerList=" + answerList +
                '}';
    }
}
