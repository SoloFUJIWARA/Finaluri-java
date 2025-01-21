module com.example.cardealershipapp_lombok {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;


    opens com.example.cardealershipapp_lombok to javafx.fxml;
    exports com.example.cardealershipapp_lombok;
}