package code.example.code;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main extends Application {
    DB db = new DB();
    Section departure = new Section("Departure");
    Section arrival = new Section("Arrival");


    String[] chosenElements = new String[4];
    Label chosenText = new Label("""
            Chosen Elements:
            Departure Location:\s
            Departure Date:\s
            Arrival Location:\s
            Arrival Date:\s""");

    @Override
    public void start(Stage stage) {
        int width = 1200;
        int height = 800;

        //INITIALIZATION
        VBox voyageGUI = new VBox();

        /*
        //Volume
        Label volText = new Label("Volume");
        volText.setPrefWidth(labelWidth);
        TextField volField = new TextField();
        HBox volIndex = new HBox(volText, volField);
         */

        //CHOOSE BUTTON
        Button chooseButton = new Button("Choose");
        chooseButton.prefHeight(100);
        EventHandler<ActionEvent> chooseEvent = actionEvent -> displayChosenElements();
        chooseButton.setOnAction(chooseEvent);

        //SEARCH BUTTON
        Button searchButton = new Button("search");
        searchButton.prefHeight(100);
        EventHandler<ActionEvent> searchEvent = actionEvent -> displayVoyages(voyageGUI);
        searchButton.setOnAction(searchEvent);

        //ASSEMBLY
        VBox upperGUI = new VBox(departure.fullVBox, arrival.fullVBox, chooseButton);
        upperGUI.setPadding(new Insets(10));
        VBox lowerGUI = new VBox(chosenText, searchButton);
        lowerGUI.setPadding(new Insets(30, 10, 10, 10));
        VBox inputGUI = new VBox(upperGUI, lowerGUI);

        inputGUI.setMinWidth((double) width / 4);
        voyageGUI.setPadding(new Insets(10));
        voyageGUI.setMaxWidth(width * 0.75);

        HBox root = new HBox(inputGUI, voyageGUI);
        Scene scene = new Scene(root, width, height);

        //STAGE
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

        chosenText.setText(str);
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
            if ((index & 1) == 0) {
                chosenElements[index] = "'" + box.getValue() + "'";
            } else {
                chosenElements[index] = db.unparseDate(box.getValue());
            }
        }
    }

    private void displayVoyages(VBox vbox) {
        ArrayList<String> temp = db.getData(chosenElements);
        for (String s : temp) {
            vbox = addBooking(vbox, s);
        }
    }

    private VBox addBooking(VBox vbox, String str) {
        vbox.getChildren().add(new Button(str));
        return vbox;
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
        titleLabel.setMinHeight(30);
        titleLabel.setMinWidth(50);
        dateLabel.setText("Date");
        dateLabel.setMinWidth(30);
        localLabel.setText("Location");
        localLabel.setMinWidth(30);
        for (Integer i : db.startDateArr) {
            dateComboBox.getItems().add(db.parseDate(i));
        }
        for (String s : db.localArr) {
            localComboBox.getItems().add(s);
        }
        dateComboBox.setMaxWidth(100);
        localComboBox.setMaxWidth(135);
        dateVBox = new VBox(dateLabel, dateComboBox);
        localVBox = new VBox(localLabel, localComboBox);
        contentHBox = new HBox(dateVBox, localVBox);
        fullVBox = new VBox(titleLabel, contentHBox);
        fullVBox.setPadding(new Insets(20,0,0,0));
    }
}
