package code.example.code;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application{

    DB db = new DB();

    Section departure = new Section("Departure");
    Section arrival = new Section("Arrival");

    String[] chosenElements = new String[4];
    Label text;
    Label voyages = new Label();

    @Override
    public void start(Stage stage) {

        /*
        //Volume
        Label volText = new Label("Volume");
        volText.setPrefWidth(labelWidth);
        TextField volField = new TextField();
        HBox volIndex = new HBox(volText, volField);
         */

        //TextArea
        text = new Label();
        text.prefWidth(200);

        //SEARCH BUTTON
        Button searchButton = new Button("Search");
        searchButton.prefHeight(100);
        EventHandler<ActionEvent> searchEvent = actionEvent -> {
            displayChosenElements();
        };
        searchButton.setOnAction(searchEvent);

        //COMMIT BUTTON
        Button commitButton = new Button("Commit");
        commitButton.prefHeight(100);
        EventHandler<ActionEvent> commitEvent = actionEvent -> {
            displayVoyages();
        };
        commitButton.setOnAction(commitEvent);

        //the rest
        VBox left = new VBox(departure.fullVBox, arrival.fullVBox, searchButton);
        VBox right = new VBox(text, commitButton, voyages);
        BorderPane root = new BorderPane();
        root.setLeft(left);
        root.setRight(right);

        Scene scene = new Scene(root, 500, 500);

        stage.setTitle("title");
        stage.setScene(scene);
        stage.show();
    }

    public void displayChosenElements() {
        String str = "Chosen Elements:" +
                "\nDeparture Location: " + displayElement(departure.localComboBox) +
                "\nDeparture Date: " + displayElement(departure.dateComboBox) +
                "\nArrival Location: " + displayElement(arrival.localComboBox) +
                "\nArrival Date: " + displayElement(arrival.dateComboBox);

        addElement(departure.localComboBox, 0);
        addElement(departure.dateComboBox, 1);
        addElement(arrival.localComboBox, 2);
        addElement(arrival.dateComboBox, 3);

        text.setText(str);
    }

    private String displayElement(ComboBox<String> box) {
        if (box.getValue() != null) {
            return box.getValue();
        } else {
            return "";
        }
    }

    private void addElement(ComboBox<String> box, int index) {
        if (box.getValue() != null) {
            if((index & 1) == 0){
                chosenElements[index] = "'" + box.getValue() + "'";
            } else{
                chosenElements[index] = db.unparseDate(box.getValue());
            }
        }
    }

    private void displayVoyages() {
        ArrayList<String> temp = db.getData(chosenElements);
        for(String s: temp){
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Section {
    DB db = new DB();

    Label titleLabel = new Label();
    Label dateLabel = new Label();
    Label localLabel = new Label();
    ComboBox<String> dateComboBox = new ComboBox<>();
    ComboBox<String> localComboBox = new ComboBox<>();
    VBox localVBox;
    VBox dateVBox;
    HBox contentHBox;
    VBox fullVBox;

    public Section(String title) {
        titleLabel.setText(title);
        titleLabel.setPrefHeight(70);
        titleLabel.setPrefWidth(140);
        dateLabel.setText("Date");
        localLabel.setText("Location");
        for (Integer i : db.startDateArr) {
            dateComboBox.getItems().add(db.parseDate(i));
        }
        for (String s : db.localArr) {
            localComboBox.getItems().add(s);
        }
        dateVBox = new VBox(dateLabel, dateComboBox);
        localVBox = new VBox(localLabel, localComboBox);
        contentHBox = new HBox(dateVBox, localVBox);
        fullVBox = new VBox(titleLabel, contentHBox);
    }
}
