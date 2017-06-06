package gui.mvp.quiz.game;

import gui.mvp.quiz.model.Question;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class QuizView extends GridPane {
    private QuizPresenter presenter;

    private Label label;

    private RadioButton answer0, answer1, answer2, answer3;

    private Button nextQuestion;

    private ToggleGroup toggleGroup;

    public QuizView() {
        initView();
    }

    private void initView() {
        setHgap(10);
        setVgap(10);

        label = new Label();
        label.setId("question");
        add(label, 0, 0);

        answer0 = new RadioButton();
        add(answer0, 0, 1);
        answer1 = new RadioButton();
        add(answer1, 0, 2);
        answer2 = new RadioButton();
        add(answer2, 0, 3);
        answer3 = new RadioButton();
        add(answer3, 0, 4);

        toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(answer0, answer1, answer2, answer3);

        nextQuestion = new Button("=>");
        nextQuestion.setOnAction(e -> {

            RadioButton answerSelected = null;

            if (toggleGroup.getSelectedToggle() != null) {
                answerSelected = (RadioButton) toggleGroup.getSelectedToggle();
            }

            presenter.validateAnswer(answerSelected);

            presenter.nextQuestion();
            setSelected();
            showQuestion(presenter.getQuestionType());
        });
        add(nextQuestion, 0, 6);
    }

    public void setPresenter(QuizPresenter presenter) {
        this.presenter = presenter;
    }

    public void setLabelText(String question) {
        label.setText(question);
    }

    public void setRadioButtonText(String[] possibleAnswers) {
        answer0.setText(possibleAnswers[0]);
        answer1.setText(possibleAnswers[1]);
        answer2.setText(possibleAnswers[2]);
        answer3.setText(possibleAnswers[3]);
    }

    public void disableRadioButton() {
        answer0.setVisible(false);
        answer1.setVisible(false);
        answer2.setVisible(false);
        answer3.setVisible(false);
    }

    public void enableRadioButton() {
        answer0.setVisible(true);
        answer1.setVisible(true);
        answer2.setVisible(true);
        answer3.setVisible(true);
    }

    public void showQuestion(Question question) {
        if (question != null) {
            setLabelText(presenter.getQuestion());
            setRadioButtonText(presenter.getPossibleAnswers());
            enableRadioButton();
            nextQuestion.setDisable(false);
        } else {
            setLabelText("Ende des Quiz");
            disableRadioButton();
            nextQuestion.setDisable(true);
        }
    }

    public void setSelected() {
        toggleGroup.selectToggle(null);
    }

}
