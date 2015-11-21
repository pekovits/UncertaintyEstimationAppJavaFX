package uncertaintyEstimation.view;

/**
 * Created by fykos on 19/11/15.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    @FXML private TreeView tree;
    @FXML private TreeItem treeItem;

    private Stage infoStage;
    private Source source = new Source();

    private boolean newSourceClicked = false;
    private boolean newWorkflowClicked = false;
    private boolean finishClicked = false;

    private boolean workflow = false;
    private boolean tempWork = false;
    @FXML private TreeItem temp;

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


    //Create branches
    public TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

    /**
     * This method takes the inputs from the user and adds them to the treeview
     */
    public void contentToTreeItems(){
        TreeItem work ;
        TreeItem parent = (TreeItem) tree.getSelectionModel().getSelectedItem();
        if (parent==null) {
            parent = tree.getRoot();
        }



        if (workflow){
            treeItem = makeBranch(source.getSource(), temp);
            makeBranch(source.getType(), treeItem);
            makeBranch(source.getCondition(), treeItem);
            makeBranch(source.getDistribution(), treeItem);
            makeBranch(source.getAssumptions(), treeItem);
            tree.setShowRoot(false);

            System.out.println("The parent: " + treeItem.getParent().getValue());
            System.out.println(parent.getChildren());

            sourceField.setText("");
            distributionField.setText("");
            assumptionField.setText("");
        }else{

            work = makeBranch(source.getWorkflow(), parent);
            treeItem = makeBranch(source.getSource(), work);
            makeBranch(source.getType(), treeItem);
            makeBranch(source.getCondition(), treeItem);
            makeBranch(source.getDistribution(), treeItem);
            makeBranch(source.getAssumptions(), treeItem);
            tree.setShowRoot(false);

            System.out.println("The parent: " + treeItem.getParent().getValue());
            System.out.println(parent.getChildren());

            sourceField.setText("");
            distributionField.setText("");
            assumptionField.setText("");

            temp = treeItem.getParent();
            workflow = true;

        }

    }

    public void contentToTreeItemsWorkflowDifferent(){
        TreeItem work;
        TreeItem parent = (TreeItem) tree.getSelectionModel().getSelectedItem();
        if (parent==null) {
            parent = tree.getRoot();
        }

        if (workflow){
            treeItem = makeBranch(source.getSource(), temp);
            makeBranch(source.getType(), treeItem);
            makeBranch(source.getCondition(), treeItem);
            makeBranch(source.getDistribution(), treeItem);
            makeBranch(source.getAssumptions(), treeItem);
            tree.setShowRoot(false);

            System.out.println("The parent: " + treeItem.getParent().getValue());
            System.out.println(parent.getChildren());

            workflowField.setText("");
            sourceField.setText("");
            distributionField.setText("");
            assumptionField.setText("");

            workflow = false;
        }else{

            work = makeBranch(source.getWorkflow(), parent);
            treeItem = makeBranch(source.getSource(), work);
            makeBranch(source.getType(), treeItem);
            makeBranch(source.getCondition(), treeItem);
            makeBranch(source.getDistribution(), treeItem);
            makeBranch(source.getAssumptions(), treeItem);
            tree.setShowRoot(false);

            System.out.println("The parent: " + treeItem.getParent().getValue());
            System.out.println(parent.getChildren());

            workflowField.setText("");
            sourceField.setText("");
            distributionField.setText("");
            assumptionField.setText("");

            temp = treeItem.getParent();
        }


    }


    @FXML
    public void handleNewWorkfloButton(ActionEvent event){
        setSourceContent();

        contentToTreeItemsWorkflowDifferent();

    }


    @FXML
    public void handleNewSourceButton(ActionEvent event){
        setSourceContent();

        contentToTreeItems();

    }

}
