module com.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;
    requires mysql.connector.j;
    requires java.net.http;
    requires com.google.gson;
    requires okhttp3;

    opens com.example.javafx to javafx.fxml;
    exports com.example.javafx;

}