package gui.mvp.quiz.overview;

import gui.mvp.quiz.main.MainPresenter;
import gui.mvp.quiz.model.Model;
import gui.mvp.quiz.model.Question;

public class OverviewPresenter {
    private OverviewView view;

    private MainPresenter mainPresenter;

    private Model model;

    public OverviewPresenter() {

    }

    public void setView(OverviewView view) {
        this.view = view;
    }

    public OverviewView getView() {
        updateListView();
        return view;
    }

    public void setMainPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public int getIndexOfCorrectAnswer() {
        return mainPresenter.getIndexOfCorrectAnswer();
    }

    public void questionSelected() {
        mainPresenter.getQuestion();
    }

    public void updateListView() {

        view.clearListView();

        for (int i = 0; i < model.getKey(); i++) {

            Question q = model.getQuestions().get(i);
            view.getOverviewList().getItems().add(q);

        }

    }

    public void deleteHistory() {

        for (int i = 0; i < model.getKey(); i++) {

            Question q = model.getQuestions().get(i);
            q.clearAllAnswersGiven();

        }
        updateListView();

    }
}
