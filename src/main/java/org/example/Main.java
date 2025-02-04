package org.example; // Only 15, don't own a domain of my own yet so I'll leave it as this.
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main {
    public static void main(String[] args) {
        String city = "Los Angeles";

        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("OpenWeatherApiKey");
        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("API key is missing in .env file.");
            return;
        }

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Response: " + response.body().string());
            } else {
                System.out.println("Request failed: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}