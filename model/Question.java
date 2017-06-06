package gui.mvp.quiz.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Question {

    private SimpleStringProperty question = new SimpleStringProperty();

    private int key;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    private String[] possibleAnswers;

    private int indexOfCorrectAnswer;

    private SimpleIntegerProperty correctAnswersGiven = new SimpleIntegerProperty();

    private SimpleIntegerProperty sumOfAllAnswersGiven = new SimpleIntegerProperty();

    // weitere Attribute nach Bedarf
    public Question(String question, String[] possibleAnswers, int indexOfCorrectAnswer) {
        if (indexOfCorrectAnswer < 0 || indexOfCorrectAnswer > 3) {
            throw new IllegalArgumentException();
        } else {
            this.question.set(question);
            this.possibleAnswers = possibleAnswers;
            this.indexOfCorrectAnswer = indexOfCorrectAnswer;
        }
    }

    public String getQuestion() {
        return question.get();
    }

    public SimpleStringProperty getQuestionProperty() {
        return question;
    }

    public String[] getPossibleAnswers() {
        return possibleAnswers;
    }

    public int getIndexOfCorrectAnswer() {
        return indexOfCorrectAnswer;
    }

    public String getCorrectAnswer() {
        return possibleAnswers[indexOfCorrectAnswer];
    }

    public void answerGiven(boolean correctness) {
        if (correctness) {
            setCorrectAnswersGiven(getCorrectAnswersGiven().get() + 1);

        }

        setSumOfAllAnswersGiven(getSumOfAllAnswersGiven().get() + 1);
    }

    public void clearAllAnswersGiven() {
        setCorrectAnswersGiven(setSumOfAllAnswersGiven(0));
    }
    // weitere Methoden nach Bedarf

    public SimpleIntegerProperty getCorrectAnswersGiven() {
        return correctAnswersGiven;
    }

    public void setCorrectAnswersGiven(int correctAnswersGiven) {
        this.correctAnswersGiven.set(correctAnswersGiven);
    }

    public SimpleIntegerProperty getSumOfAllAnswersGiven() {
        return sumOfAllAnswersGiven;
    }

    public int setSumOfAllAnswersGiven(int s) {
        this.sumOfAllAnswersGiven.set(s);
        return sumOfAllAnswersGiven.get();
    }

    @Override
    public String toString() {
        return getQuestionProperty().get();
    }

}
