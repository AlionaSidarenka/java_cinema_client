package cinema;

import cinema.Model.Movie;
import cinema.Model.Room;
import cinema.Model.Session;
import cinema.View.SessionsOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MainApp extends Application {
    private Stage primaryStage;
    private AnchorPane rootLayout;
    private ObservableList<Session> sessionsData = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Приложение Cinema");
        initRootLayout();
        showCinemaOverview();
    }

    public MainApp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 2, 0);

        sessionsData.add(new Session(new Room(new int[] {1, 2}), new Movie("movieTitle", 12, "director", calendar, 12341234.5678901, "Armenia"), new Date()));
        sessionsData.add(new Session(new Room(new int[] {1, 2}), new Movie("movieTitle2", 11, "director2", calendar, 12341234.5678901, "Armenia"), new Date()));
    }
    /**

     * Возвращает данные в виде наблюдаемого списка адресатов.
     * @return
     */
    public ObservableList<Session> getSessionsData(){
        return sessionsData;
    }
    /**
     * Инициализирует корневой макет.
     */
    private void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/cinema/view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Отображаем сцену, содержащую корневой макет
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Показывает в корневом макете сведения о фильмах
     */
    public void showCinemaOverview() {
        try {

            // Загружаем сведения об адресатах
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/cinema/view/SessionsOverview.fxml"));
            AnchorPane cinemaOverview = loader.load();

            // Помещаем сведения об адресатах в центр корневого макета
            rootLayout.getChildren().add(cinemaOverview);

            // Даём контроллеру доступ к главному приложению
            SessionsOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Возвращает главную сцену.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}