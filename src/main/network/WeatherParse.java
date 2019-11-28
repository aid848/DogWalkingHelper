package network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class WeatherParse {
    private static final String apiKEY = "4106366abe039608c6868fd69de2c998";
    protected String city;
    private static final String apiCallerCurrentWeather = "http://api.openweathermap.org/data/2.5/weather?q=";

    //REQUIRES: an internet connection, api key must be valid, city must be valid
    //EFFECTS: checks internet for weather of particular city
    // and returns a console formatted string with weather description and temperature.
    public static String getCurrentWeatherOfCity(String city) throws Exception {
        String call = apiCallerCurrentWeather + city + "&APPID=" + apiKEY;
        URL url = new URL(call);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        StringBuilder jsonString = new StringBuilder();
        while ((line = in.readLine()) != null) {
            jsonString.append(line);
        }
        return parseCurrentWeatherFromJson(jsonString.toString(),city);
    }

    //REQUIRES: an internet connection, api key must be valid, city must be valid
    //EFFECTS:checks internet for weather of particular city
    //and returns a gui formatted string with weather description and temperature.
    public static String getCurrentWeatherOfCity(String city, Boolean gui) throws Exception {
        String call = apiCallerCurrentWeather + city + "&APPID=" + apiKEY;
        URL url = new URL(call);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        StringBuilder jsonString = new StringBuilder();
        while ((line = in.readLine()) != null) {
            jsonString.append(line);
        }
        if (!gui) {
            return parseCurrentWeatherFromJson(jsonString.toString(),city);
        } else {
            GsonBuilder build = new GsonBuilder();
            Gson gson = build.create();
            Map weatherData =  gson.fromJson(jsonString.toString(), Map.class);

            String p = getParticularWeatherAttributeNested(weatherData,"weather", "description").toString();
            double temp = (double) weatherAttribute(weatherData,"main","temp") - 273.15;
            return p + ";" + temp;
        }

    }

    private static String parseCurrentWeatherFromJson(String jsonString, String city) {
        GsonBuilder build = new GsonBuilder();
        Gson gson = build.create();
        String output = "The weather in " + city + " is currently ";
        Map weatherData =  gson.fromJson(jsonString, Map.class);
        output = output + getParticularWeatherAttributeNested(weatherData,"weather", "description").toString();
        double temp = (double) weatherAttribute(weatherData,"main","temp") - 273.15;
        String temperature = new java.text.DecimalFormat("0.00").format(temp).toString();
        output = output + "\nWith a temperature of " + temperature + " Degrees";
        return output;
    }

    private static <T> T weatherAttribute(Map jsonMap, String section, String attribute) {
        Map<String,T> weather = (Map<String, T>) jsonMap.get(section);
        return weather.get(attribute);
    }

    private static <T> T  getParticularWeatherAttributeNested(Map jsonMap,String section, String attribute) {
        Object weatherlist = ((ArrayList<String>) jsonMap.get(section)).get(0);
        Map<String,T> weather = (Map<String, T>) weatherlist;
        return weather.get(attribute);
    }
}
