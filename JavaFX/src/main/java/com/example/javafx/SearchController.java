package com.example.javafx;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class SearchController {

    /**
     * lay sach tu db.
     * @param query Q
     * @param params tham so
     * @param connect conect
     * @return list<book>
     * @throws SQLException sth error.
     */
    public abstract List<Books> search(String query, List<Object> params, Connection connect) throws SQLException;

    public abstract void resetSearch();

}
