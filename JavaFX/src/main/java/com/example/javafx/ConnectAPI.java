package com.example.javafx;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ConnectAPI {
    DatabaseConnect db = new DatabaseConnect();


    public JsonArray searchBookAPI(String query) throws Exception {
        String baseUrl = "https://www.googleapis.com/books/v1/volumes?q=";
        String apiKey = "&key=AIzaSyAt9UrpQ6vtwkYHb054rpYMx7-uZFdAk1E"; // Thay bằng API key của bạn
        String APIUrl = baseUrl + query + "&key=" + apiKey;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(APIUrl)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

            if (jsonResponse.has("items"))
                return jsonResponse.getAsJsonArray("items");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getBooks(String query) throws Exception {

        String id = "";
        String title = "";
        String author = "";
        String created_date = "";
        String image = "";
        String description = "";
        String genre = "";
        String publisher = "";
        String ISBN = "";
        String language = "";

        JsonArray booksArray = searchBookAPI(query);
        List<Books> books = new ArrayList<>();
        for(int i = 0; i < booksArray.size(); i++) {
            JsonObject book = booksArray.get(i).getAsJsonObject();
            JsonObject volumeInfo = book.getAsJsonObject("volumeInfo");
            if(volumeInfo.has("id")) {
                id = volumeInfo.get("id").getAsString();
            } else {
                id = "No id available";
            }
            if(volumeInfo.has("title")) {
                title = volumeInfo.get("title").getAsString();
            } else {
                title = "No title available";
            }
            if(volumeInfo.has("authors")) {
                author = volumeInfo.get("authors").getAsString();
            } else {
                author = "No author available";
            }
            if(volumeInfo.has("publishedDate")) {
                created_date = volumeInfo.get("publishedDate").getAsString();
            } else {
                created_date = "No Published Date available";
            }
            if(volumeInfo.has("imageLinks")) {
                JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
                if(imageLinks.has("thumbnail")) {
                    image = imageLinks.get("thumbnail").getAsString();
                } else {
                    image = "No image available";
                }
            } else {
                image = "No image available";
            }
            if(volumeInfo.has("description")) {
                description = volumeInfo.get("description").getAsString();
            } else {
                description = "No description available";
            }
            if(volumeInfo.has("categories")) {
                genre = volumeInfo.get("categories").getAsString();
            } else {
                genre = "No genre available";
            }
            if(volumeInfo.has("publisher")) {
                publisher = volumeInfo.get("publisher").getAsString();
            } else {
                publisher = "No publisher available";
            }
            if(volumeInfo.has("industryIdentifiers")) {
                JsonArray industryIdentifiers = volumeInfo.get("industryIdentifiers").getAsJsonArray();
                for (int j = 0; j < industryIdentifiers.size() ; j++) {
                    JsonObject industryIdentifier = industryIdentifiers.get(j).getAsJsonObject();
                    if(industryIdentifier.get("type").getAsString().equals("ISBN_13") ) {
                        ISBN = industryIdentifier.get("identifier").getAsString();
                        break;
                    } else {
                        ISBN = "No ISBN available";
                    }
                }
            } else {
                ISBN = "No ISBN available";
            }
            if(volumeInfo.has("language")) {
                language = volumeInfo.get("language").getAsString();
            } else {
                language = "No language available";
            }
        }
        books.add(new Books(id, title, description, author, genre, publisher, ISBN, language, created_date));
    }

}
