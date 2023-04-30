package code.example.code;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    View view = new View();

    @Override
    public void start(Stage stage) throws Exception {
        view.begin(stage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
