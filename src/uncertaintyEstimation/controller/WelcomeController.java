package uncertaintyEstimation.controller;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by fykos on 29/11/15.
 */
public class WelcomeController {
    @FXML private Hyperlink newReport;
    @FXML private Hyperlink existingReport;
    private Stage fileDialog;


    /**
     * Default constructor
     */
    public WelcomeController() {

    }


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    private void initialize() {

    }

    /**
     * This method handles the action event on the create new report link
     * it loads the start screen window and closes the current one
     * @param event
     */
    @FXML
    public void handleNewReportLink(ActionEvent event) {
        System.out.println("new report clicked");

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("../view/start_screen.fxml"));
            Scene scene = new Scene(root, 500, 270);
            Stage newReportDialog = new Stage();
            newReportDialog.setTitle("Start");
            newReportDialog.setScene(scene);
            newReportDialog.show();

            // the following lines close the current window
            Node source = (Node) event.getSource();
            Stage st = (Stage) source.getScene().getWindow();
            st.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleExistingReportLink(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Node node = (Node) event.getSource();
        File file = fileChooser.showOpenDialog(node.getScene().getWindow());

        if (file != null){
            System.out.println(file);
        }

        try {
            FXMLLoader mainloader = new FXMLLoader(getClass().getResource("../view/main_window1.fxml"));
            Parent root = mainloader.load();

            Scene scene = new Scene(root, 800, 470);
            Stage newReportDialog = new Stage();
            newReportDialog.setScene(scene);

            // how to load the controller and call the populateTree method is taken from here
            // http://stackoverflow.com/questions/14187963/passing-parameters-javafx-fxml
            UncertaintyInfoController info = mainloader.getController();
            info.populateTree(file.getAbsolutePath());

            newReportDialog.show();

            // The following lines close the previous window when the new one opens
            Node source = (Node) event.getSource();
            Stage st = (Stage) source.getScene().getWindow();
            st.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
