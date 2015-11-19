package uncertaintyEstimation.controller;/**
 * Created by fykos on 17/11/15.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/start_screen.fxml"));
        Scene scene = new Scene(root, 800, 470);

        primaryStage.setTitle("Uncertainty Estimation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
