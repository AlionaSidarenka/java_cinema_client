package cinema.View;

import cinema.MainApp;
import cinema.Model.Session;
import cinema.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;

public class SessionsOverviewController {
    @FXML
    private TableView<Session> sessionsTable;
    @FXML
    public String seatsPane = "seatsPane";
    @FXML
    private TableColumn<Session, LocalDateTime> sessionStartDateTimeColumn;
    @FXML
    private TableColumn<Session, String> sessionMovieTitleColumn;
    @FXML
    private Label sessionStartDateTimeLabel;
    @FXML
    private Label sessionMovieTitleLabel;

    // Ссылка на главное приложение
    private MainApp mainApp;
    /**
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public SessionsOverviewController() {
    }

    /*Инициализация класса-контроллера. Этот метод вызывается
    автоматически после того, как fxml-файл будет загружен*/
    @FXML
    private void initialize() {
        sessionStartDateTimeColumn.setCellValueFactory(cellData ->
                cellData.getValue().startDateTimeProperty());
        sessionMovieTitleColumn.setCellValueFactory(cellData ->
                cellData.getValue().getMovie().titleProprety());

        showSessionDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем дополнительную информацию о фильме
        sessionsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showSessionDetails(newValue));
    }
    /**
     * Вызывается главным приложением, которое даёт на себя ссылку
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        sessionsTable.setItems(mainApp.getSessionsData());
    }

    private void showSessionDetails(Session session) {
        if (session != null) {
            sessionStartDateTimeLabel.setText(DateUtil.format(session.getStartDateTime()));
            sessionMovieTitleLabel.setText(session.getMovie().title());
            GridPane seats = SeatsOverviewController.drawSeats(2,3);
            mainApp.showSeatsOverview(seats, this.seatsPane);
        } else {
            sessionStartDateTimeLabel.setText("");
            sessionMovieTitleLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteSession() {
        int selectedIndex = sessionsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            sessionsTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Не выбрана строка для удаления!");
            alert.setHeaderText("Предупреждение!");
            alert.setContentText("Выберите, пожалуйста, строку в таблице");
            alert.showAndWait();
        }
    }
}
