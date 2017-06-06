package gui.mvp.quiz.overview;

import gui.mvp.quiz.model.Question;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class OverviewView extends VBox {
    private OverviewPresenter presenter;

    private Label overviewLabel;

    private TableView<Question> overviewTable;

    private TableColumn<Question, String> questionCol;

    private TableColumn<Question, Integer> answersGivenCol;

    private TableColumn<Question, Integer> answersCorrectGivenCol;

    public TableView<Question> getOverviewList() {
        return overviewTable;
    }

    public void setOverviewTable(TableView<Question> overviewTable) {
        this.overviewTable = overviewTable;
    }

    private Button deleteHistory;

    public OverviewView() {
        initOverviewView();
    }

    private void initOverviewView() {
        overviewLabel = new Label("Überblick:");
        getChildren().add(overviewLabel);

        overviewTable = new TableView<>();
        overviewTable.setId("overviewTable");
        getChildren().add(overviewTable);

        questionCol = new TableColumn<Question, String>("Fragen");
        questionCol.setId("questionCol");
        questionCol.setCellValueFactory(item -> item.getValue().getQuestionProperty());
        overviewTable.getColumns().add(questionCol);
        answersGivenCol = new TableColumn<Question, Integer>("Abgegebene Antworten");
        answersGivenCol.setId("totalAnswerCol");
        answersGivenCol.setCellValueFactory(item -> item.getValue().getSumOfAllAnswersGiven().asObject());
        overviewTable.getColumns().add(answersGivenCol);
        answersCorrectGivenCol = new TableColumn<Question, Integer>("Richtige Antworten");
        answersCorrectGivenCol.setId("correctAnswerCol");
        answersCorrectGivenCol.setCellValueFactory(item -> item.getValue().getCorrectAnswersGiven().asObject());
        overviewTable.getColumns().add(answersCorrectGivenCol);

        deleteHistory = new Button("Ergebnisse löschen");
        deleteHistory.setId("deleteHistory");
        deleteHistory.setOnAction(e -> presenter.deleteHistory());
        getChildren().add(deleteHistory);
    }

    public void setPresenter(OverviewPresenter presenter) {
        this.presenter = presenter;
    }

    public void clearListView() {
        overviewTable.getItems().clear();
    }
}
