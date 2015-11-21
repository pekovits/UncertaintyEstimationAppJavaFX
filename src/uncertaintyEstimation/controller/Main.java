package uncertaintyEstimation.controller;/**
 * Created by fykos on 17/11/15.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uncertaintyEstimation.model.Source;
import uncertaintyEstimation.view.UncertaintyInfoController;

public class Main extends Application {
    private Source source;


    @Override
    public void start(Stage primaryStage){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("../view/main_window1.fxml"));
            Scene scene = new Scene(root, 800, 470);
            primaryStage.setTitle("Uncertainty Estimation");
            primaryStage.setScene(scene);
            primaryStage.show();


        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
