package com.naamini.weatherapp.config;

/**
 * Created by Naamini Yonazi on 06/05/20
 */
public class Endpoints {

    public static String weatherIconUrl ="http://openweathermap.org/img/wn/";
    private static String api_key="cb0f64f8b422aec3e9eabf8a28de2501";
    private static String baseUrl="http://api.openweathermap.org/data/2.5/group?id=";
    private static String endUrl="&units=metric&APPID=";
    private static long morogoroCityId =153214;
    private static long tangaCityId=149595;
    private static long darEsSalaamCityId=160260;
    public static String getThreeCitiesFullUrl = baseUrl+ morogoroCityId +","+tangaCityId+","+darEsSalaamCityId+endUrl+api_key;
}
