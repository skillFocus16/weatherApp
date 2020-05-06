package com.naamini.weatherapp.model;

/**
 * Created by Naamini Yonazi on 06/05/20
 */
public class Region {

    private String name;
    private int regId;
    private double temp;
    private double windSpeed;
    private int humidity;
    private int pressure;
    private String mainDesc;
    private String countryName;

    public Region() {
    }

    public Region(String name, int regId, double temp, double windSpeed,
                  int humidity, int pressure, String mainDesc, String countryName) {
        this.name = name;
        this.regId = regId;
        this.temp = temp;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.pressure = pressure;
        this.mainDesc = mainDesc;
        this.countryName = countryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRegId() {
        return regId;
    }

    public void setRegId(int regId) {
        this.regId = regId;
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
}
