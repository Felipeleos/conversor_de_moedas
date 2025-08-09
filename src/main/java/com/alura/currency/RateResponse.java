package com.alura.currency;

import java.util.Map;

public class RateResponse {
    private boolean success;
    private String base;
    private String date;
    private Map<String, Double> rates;

    public boolean isSuccess() { return success; }
    public String getBase() { return base; }
    public String getDate() { return date; }
    public Map<String, Double> getRates() { return rates; }
}
