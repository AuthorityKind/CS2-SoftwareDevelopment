module com.example.code {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens code.example.code to javafx.fxml;
    exports code.example.code;
}