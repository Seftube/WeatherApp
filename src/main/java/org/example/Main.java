package org.example; // Only 15, don't own a domain of my own yet so I'll leave it as this.

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main {
    public static void main(String[] args) {
        String cityName = "Los Angeles";
        Dotenv dotenv;

        // Ensures that a .env file exists
        try {
            dotenv = Dotenv.load();
        } catch(Exception e){
            System.out.println("No .env file found, please create one and give a value to \"OpenWeatherApiKey\"");
            return;
        }

        // Ensures that an API key is inside the .env file
        String apiKey = dotenv.get("OpenWeatherApiKey");
        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("API key is missing in .env file.");
            return;
        }

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiKey;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Response: " + response.body().string());
            } else {
                System.out.println("Request failed: " + response.code());
            }
        } catch (Exception e) {
            System.out.println("Something went wrong, please try again later.");
        }
    }
}