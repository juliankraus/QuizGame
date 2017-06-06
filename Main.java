package gui.mvp.quiz;

import java.io.IOException;

import gui.mvp.quiz.editor.EditorPresenter;
import gui.mvp.quiz.editor.EditorView;
import gui.mvp.quiz.game.QuizPresenter;
import gui.mvp.quiz.game.QuizView;
import gui.mvp.quiz.main.MainPresenter;
import gui.mvp.quiz.main.MainView;
import gui.mvp.quiz.model.Model;
import gui.mvp.quiz.overview.OverviewPresenter;
import gui.mvp.quiz.overview.OverviewView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        MainPresenter mainPresenter = initApplication();
        Scene scene = new Scene(mainPresenter.getView(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Quiz");
        primaryStage.show();
    }

    private MainPresenter initApplication() throws IOException {
        Model model = ModelInitializer.initModel(getParameters().getUnnamed().get(0));
        MainPresenter mainPresenter = new MainPresenter();
        MainView mainView = new MainView();
        QuizPresenter quizPresenter = new QuizPresenter();
        QuizView quizView = new QuizView();
        OverviewPresenter overviewPresenter = new OverviewPresenter();
        OverviewView overviewView = new OverviewView();
        EditorPresenter editorPresenter = new EditorPresenter();
        EditorView editorView = new EditorView();

        mainPresenter.setView(mainView);
        mainPresenter.setQuizPresenter(quizPresenter);
        mainPresenter.setOverviewPresenter(overviewPresenter);
        mainView.setPresenter(mainPresenter);
        mainPresenter.setModel(model);
        mainPresenter.setEditorPresenter(editorPresenter);

        quizPresenter.setView(quizView);
        quizPresenter.setMainPresenter(mainPresenter);

        quizPresenter.setModel(model);
        quizView.setPresenter(quizPresenter);
        quizView.setLabelText(model.getQuestion());
        quizView.setRadioButtonText(model.getPossibleAnswers());

        overviewPresenter.setView(overviewView);
        overviewPresenter.setMainPresenter(mainPresenter);
        overviewPresenter.setModel(model);
        overviewView.setPresenter(overviewPresenter);

        editorPresenter.setView(editorView);
        editorPresenter.setMainPresenter(mainPresenter);
        editorPresenter.setModel(model);
        editorView.setPresenter(editorPresenter);

        mainPresenter.showQuizView(model.getQuestionType());

        return mainPresenter;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
