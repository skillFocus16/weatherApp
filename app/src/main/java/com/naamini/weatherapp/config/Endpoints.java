package com.naamini.weatherapp.config;

/**
 * Created by Naamini Yonazi on 06/05/20
 */
public class Endpoints {

    public static String api_key="cb0f64f8b422aec3e9eabf8a28de2501";
    public static String baseUrl="http://api.openweathermap.org/data/2.5/group?id=";
    public static String endUrl="&units=metric";

    public static long morogoroId=153214;
    public static long tangaCityId=149595;
    public static long darEsSalaamCityId=160260;

    public static String fullUrl = baseUrl+morogoroId+","+tangaCityId+","+darEsSalaamCityId+endUrl+"&APPID="+api_key;;
    public static String getoneCity ="http://api.openweathermap.org/data/2.5/weather?id=160260&APPID=cb0f64f8b422aec3e9eabf8a28de2501";
    public static String get3Cities="http://api.openweathermap.org/data/2.5/group?id=149595,153214,160260&APPID=cb0f64f8b422aec3e9eabf8a28de2501";

}
