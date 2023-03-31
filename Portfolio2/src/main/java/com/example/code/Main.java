package com.example.code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        final double labelWidth = 70;

        //From Port
        Label fromPortText = new Label("From Port");
        fromPortText.setPrefWidth(labelWidth);

        ComboBox fromPortBox = new ComboBox();
        fromPortBox.getItems().add("Esbjerg");
        fromPortBox.getItems().add("Aalborg");
        fromPortBox.getItems().add("København");

        HBox fromPortIndex = new HBox(fromPortText, fromPortBox);

        //To Port
        Label toPortText = new Label("To Port");
        toPortText.setPrefWidth(labelWidth);

        ComboBox toPortBox = new ComboBox();
        toPortBox.getItems().add("Esbjerg");
        toPortBox.getItems().add("Aalborg");
        toPortBox.getItems().add("København");

        HBox toPortIndex = new HBox(toPortText, toPortBox);

        //Date
        Label dateText = new Label("Date");
        dateText.setPrefWidth(labelWidth);

        TextField dateField = new TextField();

        HBox dateIndex = new HBox(dateText, dateField);

        //Volume
        Label volText = new Label("Volume");
        volText.setPrefWidth(labelWidth);

        TextField volField = new TextField();

        HBox volIndex = new HBox(volText, volField);

        //TextArea
        TextArea textarea = new TextArea("Textarea where you can write some text");
        textarea.setPrefWidth(300);

        //search

        //book voyage

        //the rest
        VBox left = new VBox(fromPortIndex, toPortIndex, dateIndex, volIndex);

        BorderPane root = new BorderPane();
        root.setLeft(left);
        root.setRight(textarea);


        Scene scene = new Scene(root, 500, 500);

        stage.setTitle("vain");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();
        DB db = new DB();
        db.dudeidk();
    }
}