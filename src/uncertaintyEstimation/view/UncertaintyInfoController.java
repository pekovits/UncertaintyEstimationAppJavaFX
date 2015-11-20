package uncertaintyEstimation.view;

/**
 * Created by fykos on 19/11/15.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uncertaintyEstimation.controller.Main;
import uncertaintyEstimation.model.Source;


public class UncertaintyInfoController {
    @FXML private TextField workflowField;
    @FXML private TextField sourceField;
    @FXML private ComboBox typeCombo;
    @FXML private ComboBox conditionCombo;
    @FXML private TextField distributionField;
    @FXML private TextArea assumptionField;

    private Stage infoStage;
    private Source source = new Source();

    private boolean newSourceClicked = false;
    private boolean newWorkflowClicked = false;
    private boolean finishClicked = false;

    private Main mainapp = new Main();
    private ObservableList<String> comboData = FXCollections.observableArrayList();
    private ObservableList<String> condComboData = FXCollections.observableArrayList();
    public UncertaintyInfoController(){

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        typeComboData();
        conditionComboData();
    }

    public void typeComboData(){
        comboData.add("Aleatory");
        comboData.add("Epistemic with probabilities");
        comboData.add("Epistemic with scenarios");

        typeCombo.setPromptText("Select a type");
        typeCombo.setItems(comboData);
    }

    public void conditionComboData(){
        condComboData.add("independet");
        condComboData.add("another");
        condComboData.add("and another");

        conditionCombo.setPromptText("Select a condition");
        conditionCombo.setItems(condComboData);
    }

    public void setSourceContent(){

        source.setWorkflow(workflowField.getText());
        source.setSource(sourceField.getText());
        source.setType((String)typeCombo.getValue());
        source.setCondition((String) conditionCombo.getValue());
        source.setDistribution(distributionField.getText());
        source.setAssumptions(assumptionField.getText());

    }



    @FXML
    public void handleNewSourceButton(ActionEvent event){
        setSourceContent();

        System.out.println(source.getWorkflow());
        System.out.println(source.getSource());
        System.out.println(source.getType());
        System.out.println(source.getCondition());
        System.out.println(source.getAssumptions());

    }

}
