package cinema;

import cinema.Model.Session;
import cinema.View.SessionEditDialogController;
import cinema.View.SessionsOverviewController;
import cinema.connection.APIService;
import cinema.connection.TCPConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class MainApp extends Application {
    List<Session> values;
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

    // Request{ command, data, params }
    public MainApp() throws IOException, ClassNotFoundException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 2, 0);
        TCPConnection tcpConnection = new TCPConnection();
        tcpConnection.connect();
        values = (List<Session>) APIService.<Session>makeRequest("get", Session.class);
        sessionsData.addAll(values);
    }
    /**

     * Возвращает данные в виде наблюдаемого списка.
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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/cinema/view/SessionsOverview.fxml"));
            AnchorPane cinemaOverview = loader.load();

            ((BorderPane) rootLayout.getChildren().get(0)).setCenter(cinemaOverview);

            // Даём контроллеру доступ к главному приложению
            SessionsOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSeatsOverview(GridPane seatsOverview, String seatsPane) {
        ((AnchorPane) rootLayout.lookup("#" + seatsPane)).getChildren().add(seatsOverview);
    }
    /**
     * Возвращает главную сцену.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        launch(args);
    }

    public boolean showSessionEditDialog(Session session) {
        // Загружаем fxml-файл и создаём новую сцену
        // для всплывающего диалогового окна.
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/cinema/view/SessionEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            // Передаём адресата в контроллер.
            SessionEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSession(session);
            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}