package uncertaintyEstimation.controller;/**
 * Created by fykos on 17/11/15.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class StartScreenController {
    @FXML private Text actiontarget;

    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        actiontarget.setText("Sign in button pressed");

    }
}
