package cinema.View;

import cinema.MainApp;
import cinema.model.Seat;
import cinema.model.Session;
import cinema.services.SessionsService;
import cinema.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    @FXML
    private DatePicker selectedDate;

    // reference to Main App
    private MainApp mainApp;

    /**
     * Constructor.
     * Called before initialize() method.
     */
    public SessionsOverviewController() { }

    // Controller initialization. Is being called automatically after fxml-file loaded.
    @FXML
    private void initialize() {
        sessionStartDateTimeColumn.setCellValueFactory(cellData ->
                cellData.getValue().startDateTimeProperty());
        sessionMovieTitleColumn.setCellValueFactory(cellData ->
                cellData.getValue().getMovie().titleProprety());

        showSessionDetails(null);

        // Listen to table selection change
        sessionsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showSessionDetails(newValue));
        selectedDate.setValue(LocalDate.now());
    }

    public void setMainApp(MainApp mainApp) {
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

    private void showSessionDetails(Session session) {
        if (session != null) {
            sessionStartDateTimeLabel.setText(DateUtil.format(session.getStartDateTime()));
            sessionMovieTitleLabel.setText(session.getMovie().getTitle());
            // drawSeats(session.getRoom().getSeats());
        } else {
            sessionStartDateTimeLabel.setText("");
            sessionMovieTitleLabel.setText("");
        }
    }

    public void drawSeats(ArrayList<ArrayList<Seat>> seats) {
        String seatName;
        Iterator<ArrayList<Seat>> iter = seats.iterator();
        Button seat;
        GridPane gridPane = new GridPane();
        int rows = seats.size();
        int col = 0;

        gridPane.setHgap(rows);
        gridPane.setPrefWidth(seatsPane.getWidth());

        while (iter.hasNext()) {
            ArrayList<Seat> next = iter.next();
            col = Math.max(col, next.size());

            Iterator<Seat> innerIter = next.iterator();

            while (innerIter.hasNext()) {
                Seat roomSeat = innerIter.next();
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

        if (selectedIndex >= 0) {
            sessionsTable.getItems().remove(selectedIndex);
        } else {
            this.showAlert();
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

    @FXML
    private void handleEditSession() {
        Session selectedSession = sessionsTable.getSelectionModel().getSelectedItem();

        if (selectedSession != null) {
            boolean okClicked = mainApp.showSessionEditDialog(selectedSession);

            if (okClicked) {
                // SessionsService.updateSession(selectedSession.getId(), selectedSession);
                showSessionDetails(selectedSession);
                // sessionsTable.refresh();
            }

            int selectedIndex = sessionsTable.getSelectionModel().getSelectedIndex();

            if (selectedIndex >= 0) {
                sessionsTable.getItems().set(selectedIndex, selectedSession);
            } else {
                this.showAlert();
            }
        } else {
            this.showAlert();
        }
    }

    @FXML
    private void handleBuyTickets() {
        Session selectedSession = sessionsTable.getSelectionModel().getSelectedItem();
        if (selectedSession != null) {
            // SessionsService.updateSession(selectedSession.getId(), selectedSession);
            sessionsTable.refresh();
        } else {
            this.showAlert();
        }
    }

    @FXML
    private void loadSessions() {
        List<Session> sessions = SessionsService.getAllSessions(selectedDate.getValue());
        Session selectedSession = sessionsTable.getSelectionModel().getSelectedItem();
        sessionsTable.setItems(FXCollections.observableArrayList(sessions));
    }
}
