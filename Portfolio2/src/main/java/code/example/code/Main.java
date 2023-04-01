package code.example.code;

import javafx.application.Application;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
     public DB db = new DB();
    final double labelWidth = 140;
    final double labelTitleHeight = 70;

    Label departLabel = new Label("Departure");
    Label departLocalLabel = new Label("Location");
    ComboBox<String> departLocalBox = new ComboBox<>();

    Label text = new Label();

    @Override
    public void start(Stage stage) {
        text = new Label("");

        //DEPARTURE
        departLabel.setPrefWidth(labelWidth);
        departLabel.setPrefHeight(labelTitleHeight);


        for(String s: db.localArr){
            departLocalBox.getItems().add(s);
        }
        VBox departLocalHBox = new VBox(departLocalLabel,departLocalBox);

        Label departDateLabel = new Label("Date");
        ComboBox<String> departDateBox = new ComboBox<>();
        for(Integer i: db.startDateArr){
            departDateBox.getItems().add(db.parseDate(i));
        }
        VBox departDateHBox = new VBox(departDateLabel, departDateBox);

        HBox departContent = new HBox(departDateHBox, departLocalHBox);
        VBox departHBox = new VBox(departLabel, departContent);

        //ARRIVAL
        Label arrivalLabel = new Label("Arrival");
        arrivalLabel.setPrefWidth(labelWidth);
        arrivalLabel.setPrefHeight(labelTitleHeight);

        Label arrivalLocalLabel = new Label("Location");
        ComboBox<String> arrivalLocalBox = new ComboBox<>();
        for(String s: db.localArr){
            arrivalLocalBox.getItems().add(s);
        }
        VBox arrivalLocalHBox = new VBox(arrivalLocalLabel,arrivalLocalBox);

        Label arrivalDateLabel = new Label("Date");
        ComboBox<String> arrivalDateBox = new ComboBox<>();
        for(Integer i: db.startDateArr){
            arrivalDateBox.getItems().add(db.parseDate(i));
        }
        VBox arrivalDateHBox = new VBox(arrivalDateLabel, arrivalDateBox);

        HBox arrivalContent = new HBox(arrivalDateHBox, arrivalLocalHBox);
        VBox arrivalHBox = new VBox(arrivalLabel, arrivalContent);

        /*
        //Volume
        Label volText = new Label("Volume");
        volText.setPrefWidth(labelWidth);
        TextField volField = new TextField();
        HBox volIndex = new HBox(volText, volField);
         */

        //TextArea

        text.prefWidth(100);

        //SEARCH BUTTON
        Button searchButton = new Button("Search");
        searchButton.prefHeight(100);

        EventHandler<ActionEvent> event = actionEvent -> {
            text.setText("Start Location: " + departLocalBox.getValue());
        };

        searchButton.setOnAction(event);

        //the rest
        VBox left = new VBox(departHBox, arrivalHBox, searchButton);

        BorderPane root = new BorderPane();
        root.setLeft(left);
        root.setRight(text);

        Scene scene = new Scene(root, 500, 500);

        stage.setTitle("title");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}