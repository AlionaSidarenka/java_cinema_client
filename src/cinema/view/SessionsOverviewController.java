package cinema.view;

import cinema.Client;
import cinema.connection.Response;
import cinema.model.Seat;
import cinema.model.Session;
import cinema.util.DateUtil;
import cinema.util.RoomFactory;
import cinema.util.RoomType;
import cinema.services.SessionsService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import org.apache.commons.lang3.SerializationUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class SessionsOverviewController {
    @FXML
    public Label sessionMoviePriceLabel;
    @FXML
    public Label sessionMovieAgeRestrictionLabel;
    @FXML
    public Label sessionMovieCountriesLabel;
    @FXML
    public Label sessionMovieLengthLabel;
    @FXML
    public Label sessionMovieDirectorLabel;
    @FXML
    private TableView<Session> sessionsTable;
    @FXML
    public AnchorPane seatsPane;
    @FXML
    private TableColumn<Session, LocalDateTime> sessionStartDateTimeColumn;
    @FXML
    private TableColumn<Session, String> sessionMovieTitleColumn;

    @FXML
    private Label sessionMovieTitleLabel;
    @FXML
    private DatePicker selectedDate;

    // reference to Main App
    private Client mainApp;

    /**
     * Constructor.
     * Called before initialize() method.
     */
    public SessionsOverviewController() {
    }

    // Controller initialization. Is being called automatically after fxml-file loaded.
    @FXML
    private void initialize() {
        sessionStartDateTimeColumn.setCellValueFactory(cellData ->
                cellData.getValue().startDateTimeProperty());

        sessionStartDateTimeColumn.setCellFactory(column -> new TableCell<Session, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {

                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(DateUtil.timeFormat(item));
            }
        });



        sessionMovieTitleColumn.setCellValueFactory(cellData ->
                cellData.getValue().getMovie().titleProprety());

        showSessionDetails(null);

        // Listen to table selection change
        sessionsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showSessionDetails(newValue));
        selectedDate.setValue(LocalDate.now());
    }

    public void setMainApp(Client mainApp) {
        this.mainApp = mainApp;
        sessionsTable.setItems(mainApp.getSessionsData());
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Session is not selected!");
        alert.setHeaderText("Warning!");
        alert.setContentText("Please choose line in the table");
        alert.showAndWait();
    }

    public void showErrorAlert(String msg, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle(msg);
        alert.setHeaderText("Error!");
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showSuccessAlert(String msg, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle(msg);
        alert.setHeaderText("Success!");
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showSessionDetails(Session session) {
        if (session != null) {

            // movie in quotes
            sessionMovieTitleLabel.setText("\"" + session.getMovie().getTitle() + "\"");
            sessionMovieAgeRestrictionLabel.setText(session.getMovie().getAgeRestriction() + "+"); //int

            // BigDecimal formatted to show 2 digits after dot
            String price = String.format("%.2f руб", session.getMovie().getPrice().doubleValue()); // BigDecimal
            sessionMoviePriceLabel.setText(price);

            // formatted output for String array that shows all countries split by coma except last
            StringBuilder sb = new StringBuilder();
            Arrays.stream(session.getMovie().getCountries()).forEach(c -> sb.append(c).append(", "));
            sessionMovieCountriesLabel.setText(sb.substring(0, sb.length() - 2));
            sessionMovieLengthLabel.setText(session.getMovie().getLength());
            sessionMovieDirectorLabel.setText(session.getMovie().getDirector());


            drawSeats(session.getRoom().getSeats());
        } else {
            sessionMoviePriceLabel.setText("");
            sessionMovieTitleLabel.setText("");
            sessionMovieAgeRestrictionLabel.setText("");
            sessionMovieCountriesLabel.setText("");
            sessionMovieLengthLabel.setText("");
            sessionMovieDirectorLabel.setText("");
        }
    }

    public void drawSeats(Seat[][] seats) {
        String seatName;
        Button seat;
        GridPane gridPane = new GridPane();
        int rows = seats.length;
        int col = 0;
        gridPane.setHgap(rows);
        gridPane.setPrefWidth(seatsPane.getWidth());

        for (int i = 0; i < rows; i++) { // [[1, 2], [1, 2, 3]]
            col = Math.max(col, seats[i].length);

            for (int j = 0; j < seats[i].length; j++) {
                Seat roomSeat = seats[i][j];
                seat = new Button();
                seat.setAlignment(Pos.CENTER);
                seat.setPrefSize(40, 40);

                seatName = Integer.toString(roomSeat.getRow() + 1) + " " + Integer.toString(roomSeat.getPlace() + 1);
                seat.setText(seatName);
                if (roomSeat.isSold()) {
                    seat.getStyleClass().add("sold");
                } else {
                    seat.getStyleClass().add(roomSeat.isReserved() ? "reserved" : "available");
                }

                Button finalSeat = seat;
                seat.setOnAction(event -> {
                    if (!roomSeat.isSold()) {
                        roomSeat.setReserved(!roomSeat.isReserved());
                        finalSeat.getStyleClass().clear();
                        finalSeat.getStyleClass().add(roomSeat.isReserved() ? "reserved" : "available");
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
        seatsPane.getChildren().clear();
        seatsPane.getChildren().add(gridPane);

        this.mainApp.getPrimaryStage().widthProperty().addListener((arg0, arg1, arg2) ->
                gridPane.setPrefWidth(seatsPane.getWidth()));
    }

    @FXML
    private void handleDeleteSession() {
        int selectedIndex = sessionsTable.getSelectionModel().getSelectedIndex();
        Session selectedSession = sessionsTable.getSelectionModel().getSelectedItem();

        if (selectedIndex >= 0) {
            Response response = SessionsService.deleteSession(selectedSession);

            if (response.getStatus().equals("Ok")) {
                sessionsTable.getItems().remove(selectedIndex);
                this.showSuccessAlert("Session was deleted", "Congratulations!");
                seatsPane.getChildren().clear();
            } else {
                this.showErrorAlert("Session was not deleted!", response.getMessage());
            }
        } else {
            this.showAlert();
        }
    }

    @FXML
    private void handleNewSession() {

        Session session = new Session();
        session.setRoom(RoomFactory.getInstance().getRoom(RoomType.B));

        boolean okClicked = mainApp.showSessionEditDialog(session);

        if (okClicked) {
            SessionsService.createSession(session);
            loadSessions();
        }
    }

    @FXML
    private void handleEditSession() {
        // TODO for Ivan: if we change date or time it should fail as timestamp is our id
        // you can save date here from selectedSession and change updateSession method to accept {params: { prevDate: <oldValue> }}
        Session selectedSession = sessionsTable.getSelectionModel().getSelectedItem();

       Session toDelete = new Session();
       toDelete.setStartDateTime(selectedSession.getStartDateTime());

        if (selectedSession != null) {
            boolean okClicked = mainApp.showSessionEditDialog(selectedSession);

            if (okClicked) {
                SessionsService.updateSession(selectedSession, toDelete);
                loadSessions();
            }
        } else {
            this.showAlert();
        }
    }

    @FXML
    private void handleBuyTickets() {
        Session selectedSession = sessionsTable.getSelectionModel().getSelectedItem();
        Session sessionToSave = SerializationUtils.clone(selectedSession);

        if (selectedSession != null) {
            Seat[][] seats = selectedSession.getRoom().getSeats();
            for (int i = 0; i < seats.length; i++) {
                for (int j = 0; j < seats[i].length; j++) {
                    if (seats[i][j].isReserved()) {
                        sessionToSave.getRoom().getSeats()[i][j].setSold(true);
                        sessionToSave.getRoom().getSeats()[i][j].setReserved(false);
                    }
                }
            }
            SessionsService.updateSession(sessionToSave);
            loadSessions();
        } else {
            this.showAlert();
        }
    }

    @FXML
    private void loadSessions() {
        List<Session> sessions = SessionsService.getAllSessions(selectedDate.getValue());
        mainApp.setSessions(sessions);
        seatsPane.getChildren().clear();
        // sessionsTable.setItems(FXCollections.observableArrayList(sessions));
    }
}
