package com.example.javafx;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.Alert;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConnectAPI {


    public JsonArray searchBookAPI(String query) throws Exception {
        String baseUrl = "https://www.googleapis.com/books/v1/volumes?q=";
        String apiKey = "&key=AIzaSyAt9UrpQ6vtwkYHb054rpYMx7-uZFdAk1E"; // Thay bằng API key của bạn
        StringBuilder queryBuilder = new StringBuilder(query);
        for(int i = 0; i<queryBuilder.length(); i++){
            if(queryBuilder.charAt(i)==' ') {
                queryBuilder.setCharAt(i,'+');
            }
        }
        String APIUrl = baseUrl + queryBuilder + apiKey;

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

    public  List<Books> getBooks(String query, String year) throws Exception {

        String id;
        String title ;
        String author;
        String created_date = "";
        String image = "";
        String description;
        String genre;
        String publisher;
        String ISBN = "";
        String language;
        String rating;

        JsonArray booksArray = searchBookAPI(query);
        List<Books> books = new ArrayList<>();
        if(booksArray == null || booksArray.isEmpty()) {
            return books;
        }
        for(int i = 0; i < booksArray.size(); i++) {
            JsonObject book = booksArray.get(i).getAsJsonObject();
            JsonObject volumeInfo = book.getAsJsonObject("volumeInfo");
            if(book.has("id")) {
                id = book.get("id").getAsString();
            } else {
                id = "No id available";
            }
            if(volumeInfo.has("title")) {
                title = volumeInfo.get("title").getAsString();
            } else {
                title = "No title available";
            }
            if(volumeInfo.has("authors")) {
                JsonArray authors = volumeInfo.get("authors").getAsJsonArray();
                List<String> authorList = new ArrayList<>();
                for(int j = 0; j < authors.size(); j++) {
                    authorList.add(authors.get(j).getAsString());
                }
                author = String.join(", ", authorList);
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
            if(volumeInfo.has("averageRating")) {
                rating = volumeInfo.get("averageRating").getAsString();
            } else {
                rating = "No Rating available";
            }
            if(volumeInfo.has("language")) {
                language = volumeInfo.get("language").getAsString();
            } else {
                language = "No language available";
            }
            if(volumeInfo.has("publishedDate")) {
                created_date = volumeInfo.get("publishedDate").getAsString();
            }
            if (year != null && !year.isEmpty() && !created_date.startsWith(year)) {
                continue;
            }

            books.add(new Books(id, title, description, author, genre, publisher, ISBN, language, created_date, image, rating, true, 0));
        }
        return books;
    }

    public  List<Books> getBooks(String query, String year, List<Books> dbBooks) throws Exception {

        String id;
        String title ;
        String author;
        String created_date = "";
        String image = "";
        String description;
        String genre;
        String publisher;
        String ISBN = "";
        String language;
        String rating;

        JsonArray booksArray = searchBookAPI(query);
        List<Books> books = new ArrayList<>();
        if(booksArray == null || booksArray.isEmpty()) {
            return books;
        }
        for(int i = 0; i < booksArray.size(); i++) {
            JsonObject book = booksArray.get(i).getAsJsonObject();
            JsonObject volumeInfo = book.getAsJsonObject("volumeInfo");
            if(book.has("id")) {
                id = book.get("id").getAsString();
            } else {
                id = "No id available";
            }
            if(volumeInfo.has("title")) {
                title = volumeInfo.get("title").getAsString();
            } else {
                title = "No title available";
            }
            if(volumeInfo.has("authors")) {
                JsonArray authors = volumeInfo.get("authors").getAsJsonArray();
                List<String> authorList = new ArrayList<>();
                for(int j = 0; j < authors.size(); j++) {
                    authorList.add(authors.get(j).getAsString());
                }
                author = String.join(", ", authorList);
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
            if(volumeInfo.has("averageRating")) {
                rating = volumeInfo.get("averageRating").getAsString();
            } else {
                rating = "No Rating available";
            }
            if(volumeInfo.has("language")) {
                language = volumeInfo.get("language").getAsString();
            } else {
                language = "No language available";
            }
            if(volumeInfo.has("publishedDate")) {
                created_date = volumeInfo.get("publishedDate").getAsString();
            }
            if (year != null && !year.isEmpty() && !created_date.startsWith(year)) {
                continue;
            }

            Books book1 = new Books(id, title, description, author, genre, publisher, ISBN, language, created_date, image, rating, true, 0);
            books.add(book1);
            for(Books b : dbBooks) {
                if(b.getId().equals(book1.getId())) {
                    books.remove(b);
                }
            }
        }
        return books;
    }


    public String createQuery(String title, String author, String genre, String publisher, String ISBN, String language, String sort) {
        StringBuilder query = new StringBuilder();
        if (title != null && !title.isEmpty()) {
            query.append("intitle:").append(title).append("+");
        }
        if (author != null && !author.isEmpty()) {
            query.append("inauthor:").append(author).append("+");
        }
        if (genre != null && !genre.isEmpty()) {
            query.append("subject:").append(genre).append("+");
        }
        if (publisher != null && !publisher.isEmpty()) {
            query.append("inpublisher:").append(publisher).append("+");
        }
        if (ISBN != null && !ISBN.isEmpty()) {
            query.append("isbn:").append(ISBN).append("+");
        }

        if(!query.isEmpty() && query.charAt(query.length() - 1) == '+') {
            query.deleteCharAt(query.length() - 1);
        }
        if(language != null && !language.isEmpty() && !language.equals("Language")) {
            query.append("&langRestrict=").append(language, 0, 2);
        }
        if(sort != null && !sort.isEmpty() && !sort.equals("Sort by")) {
            query.append("&orderBy=").append(sort);
        }
        return query.toString();
    }

}
