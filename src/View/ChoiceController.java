package View;

import Model.Downloading;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by qwerty on 20-May-17.
 */
public class ChoiceController {

    @FXML
    private Button next;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private TextField currency;


    private static String cur;
    private static LocalDate begining;
    private static LocalDate end;


    public static LocalDate getEnd() {
        return end;
    }

    public static LocalDate getBegining() {
        return begining;
    }

    public static String getCur() {
        return cur;

    }

    @FXML
    private void nextPressed()
    {
        if(dateFrom.getValue()!=null && dateTo!=null && currency.getText()!="")
        {
            //Downloading downloading = new Downloading(dateFrom.getValue(),dateTo.getValue(),currency.getText());
            cur=currency.getText();
            begining=dateFrom.getValue();
            end=dateTo.getValue();
            //Thread thread = new Thread(downloading);
            //thread.start();
            Stage chartStage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("Chart.fxml"));
                chartStage.setTitle("Chart EUR to " + currency.getText());
                chartStage.setScene(new Scene(root, 600, 400));
                chartStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Daty poczatku oraz konca przeszukiwania musza zostac podane, jak rowniez skrot waluty");
            alert.setContentText("Prosze podac obydwie daty oraz skrot waluty");

            alert.showAndWait();
        }
    }
}
