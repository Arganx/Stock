package Model;

import View.ChartController;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by qwerty on 20-May-17.
 */
public class Downloading extends Task {

    private LocalDate start;
    private LocalDate end;
    private String currency;

    public Downloading(LocalDate startDate, LocalDate endDate, String currency) {
        this.start = startDate;
        this.end = endDate;
        this.currency = currency;
    }

    @Override
    protected Object call() throws Exception {
        System.out.println("Hi");
        ArrayList<Double> values = null;
        try {
            values = calendar();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Collections.sort(values);
        int max_index=values.size();
        System.out.println("Minimalna wartosc wynosila: "+values.get(0));
        System.out.println("Maxymalna wartosc wynosila: "+values.get(max_index-1));
        return values;
    }


    private StockAPI download(String adress) throws IOException {
        URL url = new URL(adress);
        URLConnection urlCon = url.openConnection();
        //urlCon.setConnectTimeout(1000);
        InputStreamReader in = new InputStreamReader(urlCon.getInputStream());

        ObjectMapper mapper = new ObjectMapper();

        StockAPI stockAPI = mapper.readValue(in, StockAPI.class);
        in.close();
        return stockAPI;
    }

    private ArrayList<Double> calendar() throws IOException, InterruptedException {

        Double max,min;
        LocalDate dmax,dmin;
        StockAPI stockAPI = null;
        ArrayList<Double> values = new ArrayList<Double>();
        ChartController.getSeries().getData().clear();
        dmin=start;
        dmax=start;
        String url = "http://api.fixer.io/"+start.toString();
        stockAPI=download("http://api.fixer.io/"+start.toString());
        max=stockAPI.getRates().get(currency);
        min=stockAPI.getRates().get(currency);
        for (LocalDate date = start; !date.isEqual(end.plusDays(1)); date = date.plusDays(1)) {
            url = "http://api.fixer.io/"+date.toString();
            stockAPI=download("http://api.fixer.io/"+date.toString());
            System.out.println(date.toString()+ " " + stockAPI.getRates().get(currency));
            if(stockAPI.getRates().get(currency)>max)
            {
                max=stockAPI.getRates().get(currency);
                dmax=date;
            }
            if(stockAPI.getRates().get(currency)<min)
            {
                min=stockAPI.getRates().get(currency);
                dmin=date;
            }
            values.add(stockAPI.getRates().get(currency));
            LocalDate finalDate = date;
            StockAPI finalStockAPI = stockAPI;
            Platform.runLater(
                    () -> {
                        ChartController.getSeries().getData().add(new XYChart.Data<String,Double>(finalDate.toString(), finalStockAPI.getRates().get(currency)));
                    }
            );
            //ChartController.getSeries().getData().add(new XYChart.Data<String,Double>(date.toString(),stockAPI.getRates().get(currency)));
            //ChartController.listener(date.toString(),stockAPI.getRates().get(currency));
            //ChartController.listener(date.toString(),stockAPI.getRates().get(currency));
            TimeUnit.MILLISECONDS.sleep(150);
            System.out.println("Wykonano dla daty: "+date.toString());
        }
        System.out.println("Data z minimalna wartoscia wynosila: "+dmin.toString());
        System.out.println("Data z max wartoscia wynosila: "+dmax.toString());
        return  values;
    }
}
