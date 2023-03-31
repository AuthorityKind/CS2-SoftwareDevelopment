module main {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens code.portfolio2 to javafx.fxml;
    exports code.portfolio2;
}