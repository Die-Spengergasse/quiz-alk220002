package at.spengergasse;

import at.spengergasse.entities.Question;
import at.spengergasse.entities.Quiz;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("demo").createEntityManager();
        TypedQuery<Question> query = em.createQuery("SELECT q FROM Question q", Question.class);
        List<Question> questions = query.getResultList();
        em.close();

        Quiz quiz = new Quiz(questions);
        quiz.start();
    }
}