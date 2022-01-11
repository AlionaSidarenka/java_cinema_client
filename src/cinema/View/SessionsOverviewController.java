package cinema.View;

import cinema.MainApp;
import cinema.Model.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Date;

public class SessionsOverviewController {
    @FXML
    private TableView<Session> sessionsTable;
    @FXML
    private TableColumn<Session, Date> sessionStartDateTimeColumn;
    @FXML
    private TableColumn<Session, String> sessionMovieTitleColumn;
    @FXML
    private Label sessionRoomLabel;
    @FXML
    private Label sessionMovieLabel;

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
//метод setCellValueFactory определяет, как необходимо заполнить данные каждой ячейки
// Инициализация столбца с данными об именах
        sessionStartDateTimeColumn.setCellValueFactory(cellData ->
                cellData.getValue().startDateTimeProperty());
// Инициализация столбца с данными об фамилиях
        sessionMovieTitleColumn.setCellValueFactory(cellData ->
                cellData.getValue().getMovie().titleProprety());
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
}
