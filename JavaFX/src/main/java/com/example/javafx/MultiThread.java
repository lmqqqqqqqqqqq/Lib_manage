package com.example.javafx;

import javafx.concurrent.Task;

import java.util.List;

public class MultiThread {
    /**
     * search on type key.
     * @param Query
     * @param key
     * @return
     */
    public static Task<List<Books>> keyType(String Query, String key) {
        return new Task<>() {
            @Override
            protected List<Books> call() throws Exception {
                String query = Query;
                return AdvancedSearch.search(query, List.of("%" + key + "%", "%" + key + "%"), DatabaseConnect.getconnect());
            }
        };
    }
    /**
     * Query without params.
     * @param Query
     * @return
     */
    public static Task<List<Books>> nParamsQ(String Query) {
        return new Task<>() {
            @Override
            protected List<Books> call() throws Exception {
                String query = Query;
                return AdvancedSearch.search(query, DatabaseConnect.getconnect());
            }
        };
    }

    /**
     * your books query.
     * @param Query
     * @param key
     * @return
     */
    public static Task<List<Books>> YourBooks(String Query, Object key) {
        return new Task<>() {
            @Override
            protected List<Books> call() throws Exception {
                String query = Query;
                return AdvancedSearch.search(query, key, DatabaseConnect.getconnect());
            }
        };
    }
}