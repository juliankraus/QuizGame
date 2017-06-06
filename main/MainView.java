package gui.mvp.quiz.main;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MainView extends BorderPane {
    private MainPresenter presenter;

    private Button quizStart, quizContinue, quizOverview, quizEditor;

    public MainView() {
        initView();
    }

    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    private void initView() {
        HBox topArea = new HBox(5);

        quizStart = new Button("Quiz starten!");
        topArea.getChildren().add(quizStart);
        quizStart.setOnAction(e -> {
            disableQuizContinue();
            presenter.startQuizView();
            presenter.showQuizView(presenter.getQuestionType());
        });

        quizContinue = new Button("Quiz fortsetzen!");
        topArea.getChildren().add(quizContinue);
        quizContinue.setOnAction(e -> {
            presenter.showQuizView(presenter.getQuestionType());
        });

        quizOverview = new Button("Überblick!");
        quizOverview.setId("overview");
        topArea.getChildren().add(quizOverview);
        quizOverview.setOnAction(e -> {
            disableQuizContinue();
            presenter.showOverviewView();
        });

        quizEditor = new Button("Quiz editieren!");
        topArea.getChildren().add(quizEditor);
        quizEditor.setOnAction(e -> {
            quizContinue.setDisable(true);
            presenter.showEditorView();
        });

        setTop(topArea);

    }

    public void setContent(Pane content) {
        setCenter(content);
        setMargin(content, new Insets(20, 20, 20, 20));
    }

    public void disableQuizContinue() {
        quizContinue.setDisable(false);
    }
}
