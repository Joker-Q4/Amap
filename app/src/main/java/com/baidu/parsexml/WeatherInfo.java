package com.baidu.parsexml;

/**
 * Created by zhangyazhou on 2017/11/26.
 */

public class WeatherInfo {

    private String currentCity;// 当前城市名称
    private String date;// 日期
    private String weather;// 天气
    private String wind;// 风
    private String temperature;// 温度

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
