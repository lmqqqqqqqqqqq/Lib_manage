package com.example.javafx; // Đảm bảo package này đúng với package dự án của bạn

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.io.IOException;

public class GoogleBooksAPIClient {

    private static final String API_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private final OkHttpClient client;
    private final Gson gson;

    public GoogleBooksAPIClient() {
        client = new OkHttpClient();
        gson = new Gson();
    }

    // Phương thức để tìm sách dựa trên tiêu đề
    public JsonObject searchBooks(String query) throws IOException {
        String url = API_BASE_URL + query + "&key=AIzaSyAt9UrpQ6vtwkYHb054rpYMx7-uZFdAk1E"; // Thay YOUR_API_KEY bằng API key của bạn
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Request failed with code: " + response.code());
                throw new IOException("Unexpected code " + response);
            }

            // In ra toàn bộ phản hồi JSON để kiểm tra
            String responseBody = response.body().string();
            System.out.println("API Response: " + responseBody);

            // Chuyển đổi phản hồi JSON thành đối tượng JsonObject
            return gson.fromJson(responseBody, JsonObject.class);
        }
    }

    // Phương thức để in ra thông tin cơ bản của phản hồi
    public void printBasicInfo(JsonObject response) {
        System.out.println("Kind: " + response.get("kind"));
        System.out.println("Total Items: " + response.get("totalItems"));
    }

    // Phương thức để in ra chi tiết về các sách tìm được
    public void printBookDetails(JsonObject response) {
        JsonArray items = response.getAsJsonArray("items");
        if (items != null && !items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                JsonObject book = items.get(i).getAsJsonObject();
                JsonObject volumeInfo = book.getAsJsonObject("volumeInfo");

                String title = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "No title";
                String authors = volumeInfo.has("authors") ? volumeInfo.getAsJsonArray("authors").toString() : "No authors available";
                System.out.println("Book #" + (i + 1) + ": ");
                System.out.println("Title: " + title);
                System.out.println("Authors: " + authors);
                System.out.println("----");
            }
        } else {
            System.out.println("No books found.");
        }
    }

    // Phương thức main để kiểm tra tìm kiếm
    public static void main(String[] args) {
        GoogleBooksAPIClient apiClient = new GoogleBooksAPIClient();

        try {
            // Tìm kiếm sách với từ khóa
            JsonObject response = apiClient.searchBooks("java");

            // In ra các thông tin cơ bản
            apiClient.printBasicInfo(response);

            // In ra chi tiết về sách
            apiClient.printBookDetails(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}