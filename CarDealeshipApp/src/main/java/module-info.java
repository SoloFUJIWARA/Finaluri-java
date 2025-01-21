module com.example.cardealeshipapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.cardealeshipapp to javafx.fxml;
    exports com.example.cardealeshipapp;
}