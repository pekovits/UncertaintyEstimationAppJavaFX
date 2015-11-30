package uncertaintyEstimation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;

public class StartScreenController {
    @FXML
    private Text actiontarget;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;

    public void typeOfData() {
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Information Dialog");
        alert1.setHeaderText(null);
        alert1.setContentText("Are these data qualitative or quantitative? "
                + "Please fill workflow element 'Uncertainty in evaluation data'");
        alert1.showAndWait();
    }

    public void forwardUncertainty() {
        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
        alert3.setTitle("Information Dialog");
        alert3.setHeaderText(null);
        alert3.setContentText("Please consider using forward uncertainty propagation methods "
                + "when filling the workflows ");
        alert3.showAndWait();
    }

    public void evaluationData() {
        Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
        alert4.setTitle("Information Dialog");
        alert4.setHeaderText(null);
        alert4.setContentText("Are these data qualitative or quantitative? "
                + "Please fill workflow element 'Uncertainty in evaluation data.'");
        alert4.showAndWait();
    }

    public void sure(){
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle("Information dialog");
        alert2.setHeaderText(null);
        alert2.setContentText("Are you sure?");

        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");

        alert2.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> result1 = alert2.showAndWait();
        if (result1.get() == yes) {
            forwardUncertainty();

        } else if (result1.get() == no) {
            evaluationData();
        }
    }

    public void showMainWindow(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("../view/main_window1.fxml"));
            Scene scene = new Scene(root, 800, 470);
            Stage newReportDialog = new Stage();
//            newReportDialog.setTitle("");
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

    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        System.out.println(titleField.getText());
        System.out.println(authorField.getText());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information dialog");
        alert.setHeaderText("Before you start");
        alert.setContentText("Do you have evaluation data?");

        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes) {
            typeOfData();

        } else if (result.get() == buttonNo) {
            sure();
        }

        showMainWindow(actionEvent);

    }
}
