package cinema.View;

import cinema.MainApp;
import cinema.Model.Seat;
import cinema.Model.Session;
import cinema.services.SessionsService;
import cinema.util.DateUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class SessionsOverviewController {
    @FXML
    private TableView<Session> sessionsTable;
    @FXML
    public AnchorPane seatsPane;
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
            sessionMovieTitleLabel.setText(session.getMovie().getTitle());
            drawSeats(session.getRoom().getSeats());
        } else {
            sessionStartDateTimeLabel.setText("");
            sessionMovieTitleLabel.setText("");
        }
    }

    public void drawSeats(ArrayList<ArrayList<Seat>> seats) {
        int rows = seats.size();
        int col = 0;
        Button seat;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(rows);

        gridPane.setPrefWidth(seatsPane.getWidth());
        String seatName;
        Iterator<ArrayList<Seat>> iter = seats.iterator();

        while (iter.hasNext()) {
            ArrayList<Seat> next = iter.next();
            col = Math.max(col, next.size());

            Iterator<Seat> innerIter = next.iterator();
            while (innerIter.hasNext()) {
                Seat roomSeat = innerIter.next();
                seat = new Button();
                seat.setAlignment(Pos.CENTER);
                seat.setPrefSize(40, 40);

                seatName = Integer.toString(roomSeat.getPlace() + 1) + " " + Integer.toString(roomSeat.getRow() + 1);
                seat.setText(seatName);
                if (roomSeat.getSold()) {
                    seat.getStyleClass().add("sold");
                } else {
                    seat.getStyleClass().add("available");
                }

                Button finalSeat = seat;
                seat.setOnAction(event -> {
                    if (roomSeat.getSold()) {
                        return;
                    } else {
                        roomSeat.setReserved(!roomSeat.getReserved());
                        finalSeat.getStyleClass().clear();
                        finalSeat.getStyleClass().add(roomSeat.getReserved() ? "reserved" : "available");
                    }
                });
                gridPane.add(seat, roomSeat.getPlace(), roomSeat.getRow()); //  (child, columnIndex, rowIndex)
            }
        }

        gridPane.setVgap(col);
        for (int i = 0; i < col; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / col);
            gridPane.getColumnConstraints().add(cc);
        }

        // gridPane.setGridLinesVisible(true);
        seatsPane.getChildren().add(gridPane);
        this.mainApp.getPrimaryStage().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0,
                                Number arg1, Number arg2) {
                gridPane.setPrefWidth(seatsPane.getWidth());
            }
        });
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

    @FXML
    private void handleNewSession() {
        Session session = new Session();

        boolean okClicked = mainApp.showSessionEditDialog(session);

        if (okClicked) {
            mainApp.getSessionsData().add(session);
        }
    }
    /**
     * Вызывается, когда пользователь кликает по кнопка
     "Редактировать"
     * Открывает диалоговое окно для изменения выбранного адресата.
     */
    @FXML
    private void handleEditSession() {
        Session selectedSession = sessionsTable.getSelectionModel().getSelectedItem();
        if (selectedSession != null) {
            boolean okClicked = mainApp.showSessionEditDialog(selectedSession);
            if (okClicked) {
                Integer id = selectedSession.getId();
                SessionsService.updateSession(selectedSession.getId(), selectedSession);
                showSessionDetails(selectedSession);
                sessionsTable.refresh();
            }
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Нет выбранной записи");
            alert.setHeaderText("Не выбрана запись");
            alert.setContentText("Выберите запись в таблице для редактирования");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleBuyTickets() {
        Session selectedSession = sessionsTable.getSelectionModel().getSelectedItem();
        if (selectedSession != null) {
            SessionsService.updateSession(selectedSession.getId(), selectedSession);
            sessionsTable.refresh();
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Нет выбранной записи");
            alert.setHeaderText("Не выбрана запись");
            alert.setContentText("Выберите запись в таблице для редактирования");
            alert.showAndWait();
        }
    }
}
