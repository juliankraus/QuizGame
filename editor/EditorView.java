package gui.mvp.quiz.editor;

import java.util.Optional;

import gui.mvp.quiz.model.Question;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EditorView extends BorderPane {
    private EditorPresenter presenter;

    private Label label;

    private ListView<Question> editorList;

    private Button addQuestion, editQuestion, deleteQuestion;

    private TextArea dialogQuestion;

    private Label dialogLabel;

    public EditorView() {
        initEditorView();
    }

    public void initEditorView() {
        HBox bottomArea = new HBox();
        VBox centerArea = new VBox();
        label = new Label("Editor");
        editorList = new ListView<>();
        editorList.setId("editorList");
        addQuestion = new Button("Fragen hinzuf\u00fcgen");
        addQuestion.setId("addQuestion");
        addQuestion.setOnAction(e -> questionAdd());
        editQuestion = new Button("Fragen editieren");
        editQuestion.setId("editQuestion");
        editQuestion.setOnAction(e -> questionEdit());
        deleteQuestion = new Button("Fragen l\u00f6schen");
        deleteQuestion.setId("deleteQuestion");
        deleteQuestion.setOnAction(e -> questionDelete());
        bottomArea.getChildren().addAll(addQuestion, editQuestion, deleteQuestion);
        centerArea.getChildren().addAll(label, editorList, bottomArea);
        setCenter(centerArea);

    }

    public void setPresenter(EditorPresenter presenter) {
        this.presenter = presenter;
    }

    public ListView<Question> getEditorList() {
        return editorList;
    }

    public void clearEditorList() {
        editorList.getItems().clear();
    }

    public void questionDelete() {
        if (!editorList.getSelectionModel().isEmpty()) {
            Alert alertConfirmation = new Alert(AlertType.CONFIRMATION);
            alertConfirmation.setTitle("Frage");
            alertConfirmation.setHeaderText(null);
            alertConfirmation.setContentText("Soll diese Frage wirklich gel\u00f6scht werden?");
            Optional<ButtonType> result = alertConfirmation.showAndWait();
            if (result.get() == ButtonType.OK) {
                presenter.deleteQuestion();
                presenter.updateListView();
            }
        } else {
            Alert alertInformation = new Alert(AlertType.INFORMATION);
            alertInformation.setTitle("Information");
            alertInformation.setHeaderText(null);
            alertInformation.setContentText("Es muss eine Frage ausgew\u00e4hlt werden!");
            alertInformation.showAndWait();
        }
    }

    public void questionEdit() {
        if (!editorList.getSelectionModel().isEmpty()) {
            ButtonType questionEdit = new ButtonType("\u00c4ndern", ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);
            Alert dialog = new Alert(AlertType.CONFIRMATION);
            DialogPane dialogPane = dialog.getDialogPane();

            dialog.getButtonTypes().clear();
            dialog.getButtonTypes().add(cancel);
            dialog.getButtonTypes().add(questionEdit);

            dialogQuestion = new TextArea();
            dialogQuestion.setId("dialogQuestion");
            dialogQuestion.setEditable(true);
            dialogQuestion.setText(editorList.getSelectionModel().getSelectedItem().getQuestion());

            dialogLabel = new Label("Frage:");
            ToggleGroup tg = new ToggleGroup();

            RadioButton rb0 = new RadioButton();
            RadioButton rb1 = new RadioButton();
            RadioButton rb2 = new RadioButton();
            RadioButton rb3 = new RadioButton();
            tg.getToggles().addAll(rb0, rb1, rb2, rb3);
            tg.getToggles().get(editorList.getSelectionModel().getSelectedItem().getIndexOfCorrectAnswer()).setSelected(true);

            TextField tf0 = new TextField(presenter.getAnswerAt(0));
            TextField tf1 = new TextField(presenter.getAnswerAt(1));
            TextField tf2 = new TextField(presenter.getAnswerAt(2));
            TextField tf3 = new TextField(presenter.getAnswerAt(3));

            HBox hb0 = new HBox();
            HBox hb1 = new HBox();
            HBox hb2 = new HBox();
            HBox hb3 = new HBox();
            hb0.getChildren().addAll(rb0, tf0);
            hb1.getChildren().addAll(rb1, tf1);
            hb2.getChildren().addAll(rb2, tf2);
            hb3.getChildren().addAll(rb3, tf3);

            GridPane gp = new GridPane();
            gp.add(dialogLabel, 0, 0);
            gp.add(dialogQuestion, 0, 1);
            gp.add(hb0, 0, 2);
            gp.add(hb1, 0, 3);
            gp.add(hb2, 0, 4);
            gp.add(hb3, 0, 5);

            dialog.getDialogPane().setContent(gp);
            dialog.setTitle("Dialog");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == questionEdit) {
                if (tg.getToggles().indexOf(tg.getSelectedToggle()) == -1) {
                    Alert warning = new Alert(AlertType.INFORMATION);
                    warning.setTitle("Information");
                    warning.setHeaderText(null);
                    warning.setContentText("Es muss eine Antwort ausgew\u00e4hlt werden!");
                    warning.showAndWait();
                    dialog.showAndWait();

                } else {
                    String[] possibleAnswers = { tf0.getText(), tf1.getText(), tf2.getText(), tf3.getText() };
                    Question tmp = new Question(dialogQuestion.getText(), possibleAnswers, tg.getToggles().indexOf(tg.getSelectedToggle()));

                    if (presenter.getQuestionAtKey(editorList.getSelectionModel().getSelectedIndex()) != tmp) {
                        presenter.overwriteQuestion(tmp, editorList.getSelectionModel().getSelectedIndex());
                        presenter.updateListView();
                    }

                }
            }
        } else {
            Alert alertInformation = new Alert(AlertType.INFORMATION);
            alertInformation.setTitle("Information");
            alertInformation.setHeaderText(null);
            alertInformation.setContentText("Es muss eine Frage ausgew\u00e4hlt werden!");
            alertInformation.showAndWait();
        }
    }

    public void questionAdd() {
        ButtonType buttonAdd = new ButtonType("Hinzuf\u00fcgen", ButtonData.OK_DONE);
        ButtonType buttonCancel = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);
        Alert dialog = new Alert(AlertType.CONFIRMATION);
        dialog.getButtonTypes().clear();
        dialog.getButtonTypes().add(buttonAdd);
        dialog.getButtonTypes().add(buttonCancel);
        dialog.setTitle("Dialog");

        dialogLabel = new Label("Frage:");
        dialogQuestion = new TextArea();
        dialogQuestion.setEditable(true);
        dialogQuestion.setId("dialogQuestion");
        ToggleGroup tg = new ToggleGroup();

        RadioButton rb0 = new RadioButton();
        RadioButton rb1 = new RadioButton();
        RadioButton rb2 = new RadioButton();
        RadioButton rb3 = new RadioButton();
        tg.getToggles().addAll(rb0, rb1, rb2, rb3);

        TextField tf0 = new TextField();
        TextField tf1 = new TextField();
        TextField tf2 = new TextField();
        TextField tf3 = new TextField();

        HBox hb0 = new HBox();
        HBox hb1 = new HBox();
        HBox hb2 = new HBox();
        HBox hb3 = new HBox();

        hb0.getChildren().addAll(rb0, tf0);
        hb1.getChildren().addAll(rb1, tf1);
        hb2.getChildren().addAll(rb2, tf2);
        hb3.getChildren().addAll(rb3, tf3);

        GridPane gp = new GridPane();
        gp.add(dialogLabel, 0, 0);
        gp.add(dialogQuestion, 0, 1);
        gp.add(hb0, 0, 2);
        gp.add(hb1, 0, 3);
        gp.add(hb2, 0, 4);
        gp.add(hb3, 0, 5);

        dialog.getDialogPane().setContent(gp);
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == buttonAdd) {
            if (tg.getToggles().indexOf(tg.getSelectedToggle()) == -1) {
                Alert warning = new Alert(AlertType.INFORMATION);
                warning.setTitle("Information");
                warning.setHeaderText(null);
                warning.setContentText("Es muss eine Antwort ausgew\u00e4hlt werden!");
                warning.showAndWait();
                dialog.showAndWait();
            } else {
                String[] possibleAnswers = { tf0.getText(), tf1.getText(), tf2.getText(), tf3.getText() };
                Question tmp = new Question(dialogQuestion.getText(), possibleAnswers, tg.getToggles().indexOf(tg.getSelectedToggle()));
                presenter.addNewQuestion(tmp);
                presenter.updateListView();
            }
        }

    }

    public TextArea getDialogTextArea() {
        return dialogQuestion;
    }

    public Label getDialogLabel() {
        return dialogLabel;
    }

    public ListView<Question> getListView() {
        return editorList;
    }

}
