package cinema.View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

// TODO refactor, this part was googled and copy-pasted in crazy manner
public class SeatsOverviewController {
    public static GridPane drawSeats(int rows, int col) {
        Button seat;
        GridPane table = new GridPane();
        table.setHgap(rows);
        table.setVgap(col);
        table.setAlignment(Pos.CENTER);
        String seatName;

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < col; j++) {
                seat = new Button();
                seat.setAlignment(Pos.CENTER);
                seat.setPrefSize(80, 31);

                seatName = Integer.toString((i + 1) + (j + 1));
                seat.setText(seatName);
                seat.setStyle("-fx-background-color: MediumSeaGreen");


                Button finalSeat = seat;
                seat.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        finalSeat.setStyle("-fx-background-color: Yellow");
                        System.out.println("Hello World!");

                    }
                });

                //add them to the GridPane
                table.add(seat, j, i); //  (child, columnIndex, rowIndex)

            }
        }

        return table;
    }
}
