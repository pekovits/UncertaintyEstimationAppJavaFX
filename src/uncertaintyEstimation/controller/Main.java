package uncertaintyEstimation.controller;/**
 * Created by fykos on 17/11/15.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uncertaintyEstimation.model.Source;

public class Main extends Application {
    private Source source;
    private Stage primaryStage;


    @Override
    public void start(Stage stage){
        try {
            primaryStage = stage;
            setStage(primaryStage);
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("../view/welcome.fxml"));
            Scene scene = new Scene(root, 500, 270);
            primaryStage.setTitle("Uncertainty Estimation");
            primaryStage.setScene(scene);
            primaryStage.show();


        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Stage getStage(){
        return primaryStage;
    }

    public void setStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
