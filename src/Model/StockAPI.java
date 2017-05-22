package Model;

import java.util.Date;
import java.util.Map;

/**
 * Created by qwerty on 20-May-17.
 */
public class StockAPI {
    private String base;
    private Date date;
    Map<String,Double> rates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
