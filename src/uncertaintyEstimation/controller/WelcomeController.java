package uncertaintyEstimation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Created by fykos on 29/11/15.
 */
public class WelcomeController {
    @FXML private Hyperlink newReport;
    @FXML private Hyperlink existingReport;

    private Main mn ;
    private Stage fileDialog;
//    private Stage newReportDialog;


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
     * Sets the stage of this dialog.
     *
     * @param fileDialog
     */
    public void setDialogStage(Stage fileDialog) {
        this.fileDialog = fileDialog;
    }

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
        System.out.println("Existing report clicked");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(fileDialog);
    }
}
