package at.spengergasse.entities;
import at.spengergasse.entities.Question;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Quiz {
    private List<Question> questions;
    private int correctAnswers = 0;
    private int currentIndex = 0;
    private Scanner scanner = new Scanner(System.in);
    private Iterator<Question> iterator; // Klassenfeld für den Iterator


    public Quiz(List<Question> questions) {
        EntityManager em = Persistence.createEntityManagerFactory("demo").createEntityManager();
        TypedQuery<Question> query = em.createQuery("SELECT q FROM Question q", Question.class);
        this.questions = query.getResultList();
        em.close();
        questions = query.getResultList();
        Collections.shuffle(questions);
        // shuffle all the answers for each question
        for (Question question : questions) {
            Collections.shuffle(question.getAnswerList());

        }

        
    }
    public boolean nextQuestion() {
        if (iterator.hasNext()) {
            Question question = iterator.next();
            System.out.println("\nFrage: " + question.getText());

            List<Answer> answers = question.getAnswerList();
            for (int i = 0; i < answers.size(); i++) {
                System.out.println((i + 1) + ". " + answers.get(i).getText());
            }

            System.out.print("Gib die Nummer der richtigen Antwort ein: ");
            try {
                int choice = scanner.nextInt() - 1;
                if (choice >= 0 && choice < answers.size()) {
                    checkAnswer(answers.get(choice));
                } else {
                    System.out.println("Ungültige Eingabe. Die Antwort muss zwischen 1 und " + answers.size() + " liegen.");
                }
            } catch (Exception e) {
                System.out.println("Ungültige Eingabe. Bitte gib eine Zahl ein.");
                scanner.nextLine(); // Clear scanner buffer
            }
            return true;
        }
        return false;
    }

    public void start() {
        for (Question question : questions) {
            askQuestion(question);
            currentIndex++;
        }
        showResults();
    }

    private void askQuestion(Question question) {
        System.out.println("Frage: " + question.getText());
        List<Answer> answers = question.getAnswerList();

        for (int i = 0; i < answers.size(); i++) {
            System.out.println((i + 1) + ". " + answers.get(i).getText());
        }

        System.out.print("Gib die Nummer der richtigen Antwort ein: ");
        int choice = scanner.nextInt() - 1;

        if (choice >= 0 && choice < answers.size()) {
            checkAnswer(answers.get(choice));
        } else {
            System.out.println("Ungültige Eingabe. Die Frage wird übersprungen.");
        }
    }







    private void checkAnswer(Answer selectedAnswer) {
        if (selectedAnswer.isCorrect()) {
            System.out.println("Richtig!");
            correctAnswers++;
        } else {
            System.out.println("Falsch! Die richtige Antwort wäre: " + getCorrectAnswerText(selectedAnswer));
        }
    }

    private String getCorrectAnswerText(Answer selectedAnswer) {
        return selectedAnswer.isCorrect() ? selectedAnswer.getText() : "Keine richtige Antwort gespeichert.";
    }

    private void showResults() {
        int totalQuestions = questions.size();
        double percentage = ((double) correctAnswers / totalQuestions) * 100;
        System.out.println("\nQuiz beendet! Du hast " + correctAnswers + " von " + totalQuestions + " Fragen richtig beantwortet.");
        System.out.printf("Erfolgsquote: %.2f%%\n", percentage);
    }

    }