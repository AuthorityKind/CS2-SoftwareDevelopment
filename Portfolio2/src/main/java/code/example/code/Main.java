package code.example.code;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    //it's a bit random what variables are initialized in the main class, as opposed to the
    //start() method, but these were needed to be accessed out of the method's scope

    DB db = new DB();
    Section departure = new Section("Departure");
    Section arrival = new Section("Arrival");

    //this array is used as the interface between main and DB
    String[] chosenElements = new String[6];
    Label chosenText = new Label("Chosen Elements \n" +
            "\nDeparture Date: " +
            "\nArrival Date: " +
            "\nCompany Name: " +
            "\nDeparture Location: " +
            "\nArrival Location: " +
            "\nVolume of Containers: ");
    TextField volumeField = new TextField();

    @Override
    public void start(Stage stage) {
        checkCapacity();
        checkOverlappingVoyages();

        int width = 1200;
        int height = 800;

        //INITIALIZATION
        VBox voyageGUI = new VBox();
        VBox volumeGUI;


        //VOLUME
        Label volumeText = new Label("Volume");
        volumeField.setPadding(new Insets(10, 0, 0, 0));
        volumeGUI = new VBox(volumeText, volumeField);
        volumeGUI.setPadding(new Insets(0, 0, 30, 0));


        //CHOOSE BUTTON
        Button chooseButton = new Button("Choose");
        chooseButton.prefHeight(100);
        EventHandler<ActionEvent> chooseEvent = actionEvent -> displayChosenElements();
        chooseButton.setOnAction(chooseEvent);


        //SEARCH BUTTON
        Button searchButton = new Button("Search");
        searchButton.prefHeight(100);
        EventHandler<ActionEvent> searchEvent = actionEvent -> displayVoyages(voyageGUI);
        searchButton.setOnAction(searchEvent);


        //GUI ASSEMBLY
        VBox upperGUI = new VBox(departure.fullVBox, arrival.fullVBox);
        upperGUI.setPadding(new Insets(10));
        VBox midGUI = new VBox(volumeGUI, chooseButton);
        midGUI.setPadding(new Insets(10));
        VBox lowerGUI = new VBox(chosenText, searchButton);
        lowerGUI.setPadding(new Insets(30, 10, 10, 10));
        VBox inputGUI = new VBox(upperGUI, midGUI, lowerGUI);

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

    //updates the "chosen elements" panel on the application. Note; there is not added the company yet
    public void displayChosenElements() {
        String str = "Chosen Elements \n" +
                "\nDeparture Date: " + displayElement(departure.dateComboBox) +
                "\nArrival Date: " + displayElement(arrival.dateComboBox) +
                "\nCompany Name: " +
                "\nDeparture Location: " + displayElement(departure.localComboBox) +
                "\nArrival Location: " + displayElement(arrival.localComboBox) +
                "\nVolume of Containers: " + displayElement(volumeField);

        addElement(departure.dateComboBox, 0);
        addElement(arrival.dateComboBox, 1);
        //COMPANY NAME HERE
        addElement(departure.localComboBox, 3);
        addElement(arrival.localComboBox, 4);
        addElement(volumeField, 5);

        chosenText.setText(str);
    }

    //gets the element for the method above, or nothing if the element is null
    private String displayElement(ComboBox<String> box) {
        if (box.getValue() != null) {
            return box.getValue();
        } else {
            return "";
        }
    }

    //overloaded method to take textfields
    private String displayElement(TextField textField) {
        if (!textField.getText().equals("") && textField.getText().matches("-?\\d+")) {
            return textField.getText();
        } else {
            return "";
        }
    }

    //is used to put the chosen elements into the array they belong
    private void addElement(ComboBox<String> box, int index) {
        if (box.getValue() != null) {
            if (index > 1) {
                chosenElements[index] = "'" + box.getValue() + "'";
            } else {
                chosenElements[index] = db.unparseDate(box.getValue());
            }
        }
    }

    //overloaded once again to take textfields
    private void addElement(TextField textField, int index) {
        if (!textField.getText().equals("")) {
            chosenElements[index] = textField.getText();
        }
    }

    //this method is called when the chosen elements are searched for, and returns a set of buttons that,
    //when clicked, books a shipment on the chosen vessel, shown as value in the textfield being added to
    //the chosen ships "current capacity"
    //Note; if you do not put a number into the volume field, the program crashes. I am aware of this bug,
    //but haven't gotten around to solve it yet
    private void displayVoyages(VBox vbox) {
        vbox.getChildren().clear();
        ArrayList<String> temp = db.getData(chosenElements);
        for (String s : temp) {
            vbox = addBooking(vbox, s, db.getBookingQuery(chosenElements));
        }
    }

    //updates the vbox that holds the buttons with the new voyages that fits the newest initiated
    //search
    private VBox addBooking(VBox vbox, String str, String bookQuery) {
        vbox.getChildren().add(getBookingButton(str, bookQuery));
        return vbox;
    }

    private Button getBookingButton(String str, String bookQuery) {
        EventHandler<ActionEvent> bookEvent = actionEvent -> db.runQuery(bookQuery);
        Button tempBut = new Button(str);
        tempBut.setOnAction(bookEvent);
        return tempBut;
    }

    //runs and prints the result for part 3 of the assignment
    private void checkCapacity(){
        ArrayList<String> capacityList = db.verifyCapacity();
        if(capacityList.isEmpty()){
            System.out.println("No ship exceeds their capacity\n");
        } else{
            for(String s: capacityList){
                System.out.println(s);
            }
        }
    }

    //runs and prints the result for part 4 of the assignment
    private void checkOverlappingVoyages(){
        ArrayList<String> capacityList = db.verifyVoyages();
        if(capacityList.isEmpty()){
            System.out.println("No voyage overlaps\n");
        } else{
            for(String s: capacityList){
                System.out.println(s);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//used for the drop-down menus for departure and arrival, mostly implemented for
//the same of cleanliness and trimming of bloat in the start() method.
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
        for (Integer i : db.departDateArr) {
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
        fullVBox.setPadding(new Insets(20, 0, 0, 0));
    }
}