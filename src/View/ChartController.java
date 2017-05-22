package View;

import Model.Downloading;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.util.concurrent.TimeUnit;

/**
 * Created by qwerty on 20-May-17.
 */
public class ChartController {

    @FXML
    private LineChart<String,Double> chart;

    private static XYChart.Series<String,Double> series = new XYChart.Series<String,Double>();

    public static XYChart.Series<String, Double> getSeries() {
        return series;
    }

    public static void listener(String x, Double y)
    {
            series.getData().add(new XYChart.Data<String,Double>(x,y));
    }


    @FXML
    private void initialize() {
        chart.setTitle("Euro to: "+ ChoiceController.getCur());
        series.setName("Kurs");
        chart.getData().clear();
        chart.getData().add(series);
        Downloading downloading = new Downloading(ChoiceController.getBegining(),ChoiceController.getEnd(),ChoiceController.getCur());
        Thread thread = new Thread(downloading);
        synchronized (thread)
        {
            thread.start();
        }
    }

}
