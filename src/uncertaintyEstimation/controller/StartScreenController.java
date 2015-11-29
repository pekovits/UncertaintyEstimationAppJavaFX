package uncertaintyEstimation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class StartScreenController {
    @FXML private Text actiontarget;
    @FXML private TextField titleField;
    @FXML private TextField authorField;

    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        System.out.println(titleField.getText());
        System.out.println(authorField.getText());


    }
}
