package at.spengergasse.entities;
import at.spengergasse.entities.Question;
import java.util.List;
import java.util.Scanner;

public class Quiz {
    private List<Question> questions;
    private int correctAnswers = 0;
    private int currentIndex = 0;
    private Scanner scanner = new Scanner(System.in);

    public Quiz(List<Question> questions) {
        this.questions = questions;
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

    private void setCorrectAnswersCount() {
        correctAnswers = questions.size();
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