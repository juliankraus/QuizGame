package gui.mvp.quiz.main;

import gui.mvp.quiz.editor.EditorPresenter;
import gui.mvp.quiz.game.QuizPresenter;
import gui.mvp.quiz.model.Model;
import gui.mvp.quiz.model.Question;
import gui.mvp.quiz.overview.OverviewPresenter;

public class MainPresenter {
    private MainView view;

    private OverviewPresenter overviewPresenter;

    private QuizPresenter quizPresenter;

    private EditorPresenter editorPresenter;

    private Model model;

    public MainPresenter() {

    }

    public void setView(MainView view) {
        this.view = view;
    }

    public MainView getView() {
        return view;
    }

    public void setOverviewPresenter(OverviewPresenter overviewPresenter) {
        this.overviewPresenter = overviewPresenter;
    }

    public void setQuizPresenter(QuizPresenter quizPresenter) {
        this.quizPresenter = quizPresenter;
    }

    public void setEditorPresenter(EditorPresenter editorPresenter) {
        this.editorPresenter = editorPresenter;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void showQuizView(Question question) {
        quizPresenter.setQuestion(question);
        view.setContent(quizPresenter.getView());
    }

    public void showOverviewView() {
        view.setContent(overviewPresenter.getView());
    }

    public void showEditorView() {
        view.setContent(editorPresenter.getView());
    }

    public void startQuizView() {
        model.setCounter();
    }

    public Question getQuestionType() {
        return model.getQuestionType();
    }

    public String getQuestion() {
        return model.getQuestion();
    }

    public String[] getPossibleAnswers() {
        return model.getPossibleAnswers();
    }

    public int getIndexOfCorrectAnswer() {
        return model.getIndexOfCorrectAnswer();
    }

    public void nextQuestion() {
        model.nextQuestion();
    }

}
