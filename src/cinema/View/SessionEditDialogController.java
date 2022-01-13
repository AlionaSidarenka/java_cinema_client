package cinema.View;

import cinema.Model.Movie;
import cinema.Model.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

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
    /**
     * Инициализирует класс-контроллер. Этот метод вызывается
     автоматически после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    }
    /**
     * Устанавливает сцену для этого окна.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public class StringConverterStreet extends StringConverter<Movie> {

        @Override
        public String toString(Movie movie) {
            return movie == null ? null : movie.getTitle();
        }

        @Override
        public Movie fromString(String string) {
            return null;
        }

    }

    public void setSession(Session session, List<Movie> movies) {
        this.session = session;
        ObservableList<Movie> movies$ = FXCollections.observableArrayList();
        movies$.addAll(movies);
        sessionMovie.setItems(movies$);
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        for (int i = 1; i <= 24; i++) {
            hours.add(i);
        }
        sessionHours.setItems(hours);
        Callback<ListView<Movie>, ListCell<Movie>> factory = lv -> new ListCell<Movie>() {
            @Override
            protected void updateItem(Movie item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getTitle());
            }

        };

        // sessionMovie.setConverter(new StringConverterStreet());
        sessionMovie.setCellFactory(factory);
        sessionMovie.setButtonCell(factory.call(null));
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
    /**
     * Returns true, если пользователь кликнул OK, в другом случае
     false.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    /**
     * Вызывается, когда пользователь кликнул по кнопке OK.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            session.setMovie(sessionMovie.getValue());
            session.setStartDateTime(sessionDate.getValue(), sessionHours.getValue(), sessionMinutes.getValue());
            okClicked = true;
            dialogStage.close();
        }
    }
    /**
     * Вызывается, когда пользователь кликнул по кнопке Cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    /**
     * Метод проверяет пользовательский ввод в текстовых полях.
     *
     * @return true, если пользовательский ввод корректен
     */
    private boolean isInputValid() {
        /*String errorMessage = "";
        if (firstNameField.getText() == null ||
                firstNameField.getText().length() == 0) {
            errorMessage += "Некорректно введено имя!\n";
        }
        if (lastNameField.getText() == null ||
                lastNameField.getText().length() == 0) {
            errorMessage += "Некорректно введена фамилия!\n";
        }
        if (streetField.getText() == null ||
                streetField.getText().length() == 0) {
            errorMessage += "Некорректно введена улица!\n";
        }
        if (postalCodeField.getText() == null ||
                postalCodeField.getText().length() == 0) {
            errorMessage += "Некорректно введен почтовый код!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Некорректно введен почтовый код(должен быть целочисленным)!\n";
            }
        }
        if (cityField.getText() == null ||
                cityField.getText().length() == 0) {
            errorMessage += "Некорректно введен город!\n";
        }
        if (birthdayField.getText() == null ||
                birthdayField.getText().length() == 0) {
            errorMessage += "Некорректно введен день рождения!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "Неверный формат даты. Используйте формат дд.мм.гггг!\n";
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Неверно заполнены поля");
            alert.setHeaderText("Введите корректные значения полей");
                    alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }*/
        return true;
    }
}
