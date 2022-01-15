package cinema.view;

import cinema.model.Movie;
import cinema.model.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;

public class SessionEditDialogController {
    @FXML
    private DatePicker sessionDate;
    @FXML
    private ComboBox<Movie> sessionMovie;
    @FXML
    private ComboBox<Integer> sessionMinutes;
    @FXML
    private ComboBox<Integer> sessionHours;
    @FXML
    private Label sessionRoomLabel;
    private Stage dialogStage;
    private Session session;
    private boolean okClicked = false;

    @FXML
    private void initialize() { }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setSession(Session session, List<Movie> movies) {
        this.session = session;

        ObservableList<Movie> movies$ = FXCollections.observableArrayList();
        movies$.addAll(movies);
        Callback<ListView<Movie>, ListCell<Movie>> factory = lv -> new ListCell<Movie>() {
            @Override
            protected void updateItem(Movie item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getTitle());
            }
        };

        sessionMovie.setCellFactory(factory);
        sessionMovie.setButtonCell(factory.call(null));
        sessionMovie.setItems(movies$);

        ObservableList<Integer> hours = FXCollections.observableArrayList();
        for (int i = 1; i <= 24; i++) {
            hours.add(i);
        }
        sessionHours.setItems(hours);

        ObservableList<Integer> minutes = FXCollections.observableArrayList();
        for (int i = 1; i <= 60; i++) {
            minutes.add(i);
        }
        sessionMinutes.setItems(minutes);

        sessionMovie.getSelectionModel().select(session.getMovie());
        sessionDate.setValue(session.getStartDateTime().toLocalDate());
        sessionHours.setValue(session.getStartDateTime().getHour());
        sessionMinutes.setValue(session.getStartDateTime().getMinute());
        sessionRoomLabel.setText(session.getRoom().getName());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            session.setMovie(sessionMovie.getValue());
            session.setStartDateTime(
                    sessionDate.getValue(),
                    sessionHours.getValue(),
                    sessionMinutes.getValue());
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        // TODO validate
        return true;
    }
}
