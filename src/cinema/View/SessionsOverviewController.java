package cinema.View;

import cinema.MainApp;
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
            drawSeats(2,3);
        } else {
            sessionStartDateTimeLabel.setText("");
            sessionMovieTitleLabel.setText("");
        }
    }

    public void drawSeats(int rows, int col) {
        Button seat;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(rows);
        gridPane.setVgap(col);
        gridPane.setPrefWidth(seatsPane.getWidth());
        String seatName;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < col; j++) {
                seat = new Button();
                seat.setAlignment(Pos.CENTER);
                seat.setPrefSize(40, 40);

                seatName = Integer.toString(i + 1) + " " + Integer.toString(j + 1);
                seat.setText(seatName);
                seat.setStyle("-fx-background-color: MediumSeaGreen");


                Button finalSeat = seat;
                int finalI = i;
                int finalJ = j;
                seat.setOnAction(event -> { // computeIfAbsent,
                    System.out.println(finalI);
                    System.out.println(finalJ);
                    finalSeat.setStyle("-fx-background-color: Yellow");
                    System.out.println("Hello World!");

                });
                System.out.println(i);
                System.out.println(j);
                //add them to the GridPane
                gridPane.add(seat, j, i); //  (child, columnIndex, rowIndex)

            }
        }

        System.out.println(col);
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
}
