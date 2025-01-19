module com.example.java_nikoloz_sologashvili {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;


    opens com.example.java_nikoloz_sologashvili to javafx.fxml;
    exports com.example.java_nikoloz_sologashvili;
}