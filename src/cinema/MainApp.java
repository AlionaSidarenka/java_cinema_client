package cinema;

import cinema.connection.TCPConnection;
import cinema.model.Movie;
import cinema.model.Session;
import cinema.services.MoviesService;
import cinema.services.SessionsService;
import cinema.view.SessionEditDialogController;
import cinema.view.SessionsOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

public class MainApp extends Application {
    List<Session> sessions;
    List<Movie> movies;

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private ObservableList<Session> sessionsData = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Cinema App");
        initRootLayout();
        showCinemaOverview();

        TCPConnection tcpConnection = new TCPConnection();
        try {
            tcpConnection.connect();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        sessions = SessionsService.getAllSessions(LocalDate.now());
        movies = MoviesService.getAllMovies(sessions);
        sessionsData.addAll(sessions);
    }

    public MainApp() { }

    public ObservableList<Session> getSessionsData(){
        return sessionsData;
    }

    public void setSessions(List<Session> sessions){
        sessionsData.clear();
        sessionsData.addAll(sessions);
    }

    private void initRootLayout() {
        try {
            // Load rootLayout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/cinema/view/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCinemaOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/cinema/view/SessionsOverview.fxml"));
            AnchorPane cinemaOverview = loader.load();

            ((BorderPane) rootLayout.getChildren().get(0)).setCenter(cinemaOverview);

            // provide SessionsOverviewController access to MainApp
            SessionsOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        try (OutputStream output = new FileOutputStream("config.properties")) {
            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("socket.host", "127.0.0.1");
            prop.setProperty("socket.port", "2525");

            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
        launch(args);
    }

    public boolean showSessionEditDialog(Session session) {

        // Load fxml-file and create new stage for "Edit Session" dialog
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/cinema/view/SessionEditDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Session");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            SessionEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSession(session, movies);

            // wait until user closes dialog
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}