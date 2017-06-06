package gui.mvp.quiz.editor;

import gui.mvp.quiz.main.MainPresenter;
import gui.mvp.quiz.model.Model;
import gui.mvp.quiz.model.Question;

public class EditorPresenter {
    private EditorView view;

    private MainPresenter mainPresenter;

    private Model model;

    public EditorPresenter() {

    }

    public EditorView getView() {
        updateListView();
        return view;
    }

    public void setView(EditorView view) {
        this.view = view;
    }

    public void setMainPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void updateListView() {

        view.clearEditorList();

        for (int i = 0; i < model.getKey(); i++) {

            Question q = model.getQuestions().get(i);
            if (q != null) {
                view.getEditorList().getItems().add(q);
            } else {
                continue;
            }

        }

    }

    public void deleteQuestion() {
        model.getQuestions().remove(view.getEditorList().getSelectionModel().getSelectedItem().getKey());
        updateListView();
    }

    public MainPresenter getMainPresenter() {
        return mainPresenter;
    }

    public int getSumPossibleAnswers() {
        return model.getPossibleAnswers().length;
    }

    public String getAnswerAt(int i) {
        String[] tmp = model.getQuestionAtKey(view.getEditorList().getSelectionModel().getSelectedIndex()).getPossibleAnswers();
        return tmp[i];
    }

    public Question getQuestionAtKey(int key) {
        return model.getQuestionAtKey(key);
    }

    public void overwriteQuestion(Question q, int key) {
        model.overwriteQuestion(q, key);
    }

    public void addNewQuestion(Question q) {
        model.addQuestion(q);
    }

    public int getIndexOfCorrectAnswer() {
        return model.getIndexOfCorrectAnswer();
    }
}
