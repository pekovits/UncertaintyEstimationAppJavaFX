package test;

/**
 * Created by fykos on 20/11/15.
 */
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;


public class TreeExampleController {
    private @FXML TreeView<String> tree ;
    private @FXML Button addButton ;
    private @FXML Button removeButton ;

    private int count ;

    public void initialize() {
        removeButton.disableProperty().bind(new BooleanBinding() {
            {
                super.bind(tree.getSelectionModel().selectedItemProperty());
            }
            @Override
            protected boolean computeValue() {
                TreeItem<String> selectedNode = tree.getSelectionModel().getSelectedItem();
                return selectedNode == null || selectedNode == tree.getRoot();
            }

        });
        count = 1 ;
    }

    public void addNode() {
        TreeItem<String> parent = tree.getSelectionModel().getSelectedItem();
        if (parent==null) {
            parent = tree.getRoot();
        }
        count++ ;
        final TreeItem<String> newNode = new TreeItem<String>("Node "+count);
        parent.getChildren().add(newNode);
        parent.setExpanded(true);
        tree.getSelectionModel().select(newNode);
    }

    public void removeNode() {
        TreeItem<String> selectedNode = tree.getSelectionModel().getSelectedItem();
        if (selectedNode != null) {
            TreeItem<String> parentNode = selectedNode.getParent();
            if (parentNode != null) {
                parentNode.getChildren().remove(selectedNode);
            }
        }
    }
}