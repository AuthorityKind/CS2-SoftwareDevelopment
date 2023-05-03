package code.example.code;

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

public class View {
    Section departure = new Section("Departure");
    Section arrival = new Section("Arrival");
    ArrayList<QueryValue> chosenElements = new ArrayList<>();
    Label chosenText = new Label("""
            Departure Date:\s
            Arrival Date:\s
            Departure Location:\s
            Arrival Location:\s
            Volume of Containers:\s"""
            );
    TextField volumeField = new TextField();
    VBox voyageGUI = new VBox();

    //the "main" method of the view class, running the window, and it's content
    //functions as the backbone of the program
    //it initializes a bunch of javaFX GUI variables that are needed, then runs the necessary methods
    public void begin(Stage stage) {
        Controller.checkCapacity();
        Controller.checkOverlappingVoyages();

        int width = 1200;
        int height = 800;

        //INITIALIZATION
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
        EventHandler<ActionEvent> searchEvent = actionEvent -> displayVoyages();
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
        stage.setTitle("Booking program");
        stage.setScene(scene);
        stage.show();
    }

    //updates and shows the chosen elements the user have decided upon
    //to give a visual feedback of what's going on to the user
    //takes the selected data from the interactive UI and adds it to the chosenElements array and displays the info as well
    public void displayChosenElements() {
        String str = "Chosen Elements" +
                "\nDeparture Date: " + displayElement(departure.dateComboBox.getValue()) +
                "\nArrival Date: " + displayElement(arrival.dateComboBox.getValue()) +
                "\nDeparture Location: " + displayElement(departure.localComboBox.getValue()) +
                "\nArrival Location: " + displayElement(arrival.localComboBox.getValue()) +
                "\nVolume of Containers: " + displayElement(volumeField.getText());

        addElement(departure.dateComboBox.getValue(), "departDate");
        addElement(arrival.dateComboBox.getValue(), "arrivalDate");
        addElement(departure.localComboBox.getValue(), "departLocal");
        addElement(arrival.localComboBox.getValue(), "arrivalLocal");
        addElement(volumeField.getText(), "curCap");

        chosenText.setText(str);
    }

    //returns either the given parameters or a blank string
    //used to insure a valid element to display
    //checks of the input meets the criteria
    private String displayElement(String box) {
        if (box != null && !box.equals("")) {
            return box;
        } else {
            return "";
        }
    }

    //adds an element, as a QueryValue object, to the chosenElements list
    //depending on the input type, it is formatted different, which is needed for querying
    public void addElement(String box, String type) {
        if(box != null){
            chosenElements.removeIf(qval -> qval.type.equals(type));
            String val;
            switch (type) {
                case "departDate", "arrivalDate" -> val = Controller.unparseDate(box);
                case "curCap" -> val = box;
                default -> val = "'" + box.substring(0, box.length()-1) + "'";
            }
            chosenElements.add(new QueryValue(val, type));
        }
    }

    //displays all matching shipments with what was searched for
    //uses the selected elements as points to add to the query
    private void displayVoyages() {
        voyageGUI.getChildren().clear();
        String[] temp = Controller.getData(chosenElements.toArray(QueryValue[]::new));
        for (String s : temp) {
            addBooking(s, Controller.getBookingQuery(chosenElements.toArray(QueryValue[]::new)));
        }
    }

    //a method that adds a button for each possible booking
    //is done so to fluently add all possible shipments to the screen
    //adds a "child" to the selected VBox with the getBookingButton method
    private void addBooking(String str, String bookQuery) {
        voyageGUI.getChildren().add(getBookingButton(str, bookQuery));
    }

    //creates and returns a button that books a specific shipment, based on the parameters it was given
    //meant to be used in the addBooking method
    //creates an action event handler and adds that to a button, which is then returned as the output
    private Button getBookingButton(String str, String bookQuery) {
        EventHandler<ActionEvent> bookEvent = actionEvent -> Controller.runQuery(bookQuery);
        Button tempBut = new Button(str);
        tempBut.setOnAction(bookEvent);
        return tempBut;
    }
}

//this class is used to create objects that made creating and handling the elements in the GUI a lot easier
class Section {
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

        for (String string : Controller.getList("departDate"))
            dateComboBox.getItems().add(Controller.parseDate(string));
        for (String s : Controller.getList("departLocal"))
            localComboBox.getItems().add(s);

        dateComboBox.setMaxWidth(100);
        localComboBox.setMaxWidth(135);

        dateVBox = new VBox(dateLabel, dateComboBox);
        localVBox = new VBox(localLabel, localComboBox);
        contentHBox = new HBox(dateVBox, localVBox);

        fullVBox = new VBox(titleLabel, contentHBox);
        fullVBox.setPadding(new Insets(20, 0, 0, 0));
    }
}