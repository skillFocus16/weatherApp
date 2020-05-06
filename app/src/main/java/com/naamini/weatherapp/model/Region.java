package com.naamini.weatherapp.model;

/**
 * Created by Naamini Yonazi on 06/05/20
 */
public class Region {

    private String name;
    private int weatherId;
    private double temp;
    private double windSpeed;
    private int humidity;
    private int pressure;
    private String mainDesc;
    private String countryName;
    private String icon;

    public Region() {
    }

    public Region(String name, int weatherId, double temp, double windSpeed,
                  int humidity, int pressure, String mainDesc, String countryName, String icon) {
        this.name = name;
        this.weatherId = weatherId;
        this.temp = temp;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.pressure = pressure;
        this.mainDesc = mainDesc;
        this.countryName = countryName;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public String getMainDesc() {
        return mainDesc;
    }

    public void setMainDesc(String mainDesc) {
        this.mainDesc = mainDesc;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
