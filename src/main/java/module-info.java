module com.example.filmai {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.filmai to javafx.fxml;
    opens User to javafx.fxml;
    exports User;
    exports com.example.filmai;
}