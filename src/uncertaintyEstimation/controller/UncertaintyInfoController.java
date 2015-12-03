package uncertaintyEstimation.controller;

/**
 * Created by fykos on 19/11/15.
 */

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.*;
import uncertaintyEstimation.model.Source;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class UncertaintyInfoController {
    @FXML private TextField workflowField;
    @FXML private TextField sourceField;
    @FXML private ComboBox typeCombo;
    @FXML private ComboBox conditionCombo;
    @FXML private TextField distributionField;
    @FXML private TextArea assumptionField;
    @FXML private TreeItem rt = new TreeItem("Progress");
    @FXML private TreeView tree = new TreeView(rt);
    @FXML private TreeItem treeItem;
    @FXML private TreeItem temp;
    @FXML private TreeItem temper = new TreeItem("");

    private Source source = new Source();
    private TreeItem<String> parent;

    private ObservableList<String> comboData = FXCollections.observableArrayList();
    private ObservableList<String> condComboData = FXCollections.observableArrayList();

    private boolean workflow = false;
    private boolean firstdoc = false;
    private boolean treeClicked = false;
    private Element workElement;
    private XmlBuild xml = new XmlBuild();
    private WelcomeController contr;

    private String tempWorkflow;
    private String tempSource;
    private String tempType;
    private String tempCondition;
    private String tempDistribution;
    private String tempAssumption;



    /**
     * Default constructor
     */
    public UncertaintyInfoController() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        typeComboData();
        conditionComboData();
        // this method listens for selections in the treeview
        treeItemSelected();
    }


    /**
     * This method listens for the TreeItem to be clicked and it finds its children and stores them in
     * an ArrayList, then it finds the parent of the specific TreeItem
     * It passes the arraylist and the parent to a method that will set the textfields to these values
     */
    public void treeItemSelected() {
        final ArrayList<String> arr = new ArrayList<String>();
        final ArrayList<TreeItem> treeArr = new ArrayList<TreeItem>();

        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                TreeItem<String> clickedItem = (TreeItem<String>) t1;

                System.out.println("Clicked item: " + t1);

                for (TreeItem<String> tr : clickedItem.getChildren()) {
                    System.out.println("Child: " + tr);
                    parent = tr.getParent();
                    arr.add(tr.getValue());

                    treeArr.add(tr);
                }

                collectTreeItems(clickedItem, treeArr);

                System.out.println("Parent: " + tree.getRow(parent));

                // shows the treeitems to the textfields
                treeItemToTextField(parent, arr);

                // delete all the elements from the arraylist for the next elements
                arr.clear();

                tempWorkflow = workflowField.getText();
                tempSource = sourceField.getText();
                tempType = (String) typeCombo.getValue();
                tempCondition = (String) conditionCombo.getValue();
                tempDistribution = distributionField.getText();
                tempAssumption = assumptionField.getText();

                treeClicked = true;
            }
        });
    }


    public void collectTreeItems(TreeItem par, ArrayList<TreeItem> arr){

    }


    /**
     * Takes as input an ArrayList and a TreeItem that represents the parent and sets the values
     * in the textfields by indexing the arraylist and taking the value of the parent
     *
     * @param parent
     * @param ls
     */
    public void treeItemToTextField(TreeItem<String> parent, ArrayList<String> ls) {
        workflowField.setText(parent.getParent().getValue());
        sourceField.setText(parent.getValue());
        distributionField.setText(ls.get(2));
        assumptionField.setText(ls.get(3));
        conditionCombo.setValue(ls.get(1));
        typeCombo.setValue(ls.get(0));
    }


    /**
     * Sets up the type combobox
     */
    public void typeComboData() {
        comboData.add("Aleatory");
        comboData.add("Epistemic with probabilities");
        comboData.add("Epistemic with scenarios");

        typeCombo.setPromptText("Select a type");
        typeCombo.setItems(comboData);
    }


    /**
     * Sets up the condition combobox
     */
    public void conditionComboData() {
        condComboData.add("independet");
        condComboData.add("another");
        condComboData.add("and another");

        conditionCombo.setPromptText("Select a condition");
        conditionCombo.setItems(condComboData);
    }



    /**
     * This method reads the xml file, parses it and adds the nodes to the
     * treeview
     *
     * This method gets called when the main window is loaded.
     */
    public void populateTree(String filename){
        System.out.println("Inside populateTree");
        try {

            File fXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("workflow");

            TreeItem parent = (TreeItem) tree.getSelectionModel().getSelectedItem();
            if (parent == null) {
                parent = tree.getRoot();
                System.out.println("Parent: " + parent);
            }

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                System.out.println("node: " + nNode);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    if (temper.getValue().equals(eElement.getAttribute("name"))){
                        treeItem = makeBranch(eElement.getElementsByTagName("source").item(0).getTextContent(), temper);
                        makeBranch(eElement.getElementsByTagName("type").item(0).getTextContent(), treeItem);
                        makeBranch(eElement.getElementsByTagName("condition").item(0).getTextContent(), treeItem);
                        makeBranch(eElement.getElementsByTagName("distribution").item(0).getTextContent(), treeItem);
                        makeBranch(eElement.getElementsByTagName("assumptions").item(0).getTextContent(), treeItem);
                        tree.setShowRoot(false);
                    }else{
                        TreeItem workflow;
                        workflow = makeBranch(eElement.getAttribute("name"), parent);
                        treeItem = makeBranch(eElement.getElementsByTagName("source").item(0).getTextContent(), workflow);
                        makeBranch(eElement.getElementsByTagName("type").item(0).getTextContent(), treeItem);
                        makeBranch(eElement.getElementsByTagName("condition").item(0).getTextContent(), treeItem);
                        makeBranch(eElement.getElementsByTagName("distribution").item(0).getTextContent(), treeItem);
                        makeBranch(eElement.getElementsByTagName("assumptions").item(0).getTextContent(), treeItem);
                        tree.setShowRoot(false);

                        temper = treeItem.getParent();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets the values from the textfields and creates the source object
     * from the model package
     */
    public void setSourceContent() {
        source.setWorkflow(workflowField.getText());
        source.setSource(sourceField.getText());
        source.setType((String) typeCombo.getValue());
        source.setCondition((String) conditionCombo.getValue());
        source.setDistribution(distributionField.getText());
        source.setAssumptions(assumptionField.getText());
    }


    /**
     * Receives a parent TreeItem and the names of the children
     * and creates the hierarchy
     *
     * @param title
     * @param parent
     * @return
     */
    public TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        parent.getChildren().add(item);
        item.setExpanded(true);

        return item;
    }

    public void workflowTreeItem(TreeItem parent) {
        TreeItem workflow;
        workflow = makeBranch(source.getWorkflow(), parent);
        treeItem = makeBranch(source.getSource(), workflow);
        makeBranch(source.getType(), treeItem);
        makeBranch(source.getCondition(), treeItem);
        makeBranch(source.getDistribution(), treeItem);
        makeBranch(source.getAssumptions(), treeItem);
        tree.setShowRoot(false);
    }

    public void sourceTreeItem(TreeItem temporary) {
        treeItem = makeBranch(source.getSource(), temporary);
        makeBranch(source.getType(), treeItem);
        makeBranch(source.getCondition(), treeItem);
        makeBranch(source.getDistribution(), treeItem);
        makeBranch(source.getAssumptions(), treeItem);
        tree.setShowRoot(false);
    }

    /**
     * This method takes the inputs from the user and adds them to the treeview if the new source button is pressed.
     * This method is responsible for writing the new workflow and source. It also writes the other
     * sources that belong to the current workflow.
     */
    public void contentToTreeItems() {
        TreeItem parent = (TreeItem) tree.getSelectionModel().getSelectedItem();
        if (parent == null) {
            parent = tree.getRoot();
        }

        System.out.println("Workflow in contentToTreeItems: " + workflow);
        if (workflow) {
            // writes the sources that belong to the workflow
            sourceTreeItem(temp);
            sourceField.setText("");
            distributionField.setText("");
            assumptionField.setText("");
        } else {
            // writes workflow and source
            workflowTreeItem(parent);
            sourceField.setText("");
            distributionField.setText("");
            assumptionField.setText("");

            temp = treeItem.getParent();
            System.out.println("temp: " + temp);
            workflow = true;
        }

        // write content to the xml file
        Document doc = xml.docsCreated();
        Element work = workflowToXML(doc);
        if (firstdoc) {
            xml.addWorkflowNode(work);
        } else {
            xml.writeXML(doc, work);
            firstdoc = true;
        }
    }


    /**
     * This method writes the contents from the textfields to the
     * treeviews if the new workflow button is pressed.
     * This method is responsible for adding the last source of a workflow
     * and the first source of a new workflow.
     */
    public void contentToTreeItemsWorkflowDifferent() {
        TreeItem parent = (TreeItem) tree.getSelectionModel().getSelectedItem();
        if (parent == null) {
            parent = tree.getRoot();
        }


        if (workflow) {
            // writes the last source of a workflow if workflow button is pressed
            sourceTreeItem(temp);

            // clear textfields for next input
            workflowField.setText("");
            sourceField.setText("");
            distributionField.setText("");
            assumptionField.setText("");

            workflow = false;
        } else {
            // writes the new workflow and source if the new workflow button is pressed.
            workflowTreeItem(parent);

            // clear textfields for next input
            workflowField.setText("");
            sourceField.setText("");
            distributionField.setText("");
            assumptionField.setText("");

            temp = treeItem.getParent();
        }

        // write content to the xml file
        Document doc = xml.docsCreated();
        Element work = workflowToXML(doc);
        xml.addWorkflowNode(work);
    }


    /**
     * This method creates the workflow and source nodes in the xml file
     * @param doc
     * @return
     */
    public Element workflowToXML(Document doc) {

        try {
            workElement = doc.createElement("workflow");

            Attr attr = doc.createAttribute("name");
            attr.setValue(source.getWorkflow());
            workElement.setAttributeNode(attr);

            Element workflowName = doc.createElement("name");
            workflowName.appendChild(doc.createTextNode(source.getWorkflow()));
            workElement.appendChild(workflowName);

            Element workName = doc.createElement("source");
            workName.appendChild(doc.createTextNode(source.getSource()));
            workElement.appendChild(workName);

            Element carname = doc.createElement("type");
            carname.appendChild(doc.createTextNode(source.getType()));
            workElement.appendChild(carname);

            Element carname1 = doc.createElement("condition");
            carname1.appendChild(doc.createTextNode(source.getCondition()));
            workElement.appendChild(carname1);

            Element cond = doc.createElement("distribution");
            cond.appendChild(doc.createTextNode(source.getDistribution()));
            workElement.appendChild(cond);

            Element distr = doc.createElement("assumptions");
            distr.appendChild(doc.createTextNode(source.getAssumptions()));
            workElement.appendChild(distr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return workElement;
    }


    @FXML
    public void handleNewWorkfloButton(ActionEvent event) {
        setSourceContent();
        contentToTreeItemsWorkflowDifferent();

    }

    @FXML
    public void handlePreview() {

        Stage primaryStage = new Stage();
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();

            javax.xml.transform.Source xslDoc = new StreamSource("example.xsl");
            javax.xml.transform.Source xmlDoc = new StreamSource("xmltest.xml");

            String outputFileName = "test.html";
            OutputStream htmlFile = new FileOutputStream(outputFileName);

            Transformer transformer = tFactory.newTransformer(xslDoc);
            transformer.transform(xmlDoc, new StreamResult(htmlFile));

            WebView webView = new WebView();
            String filename = "test.html";
            final java.net.URI uri = java.nio.file.Paths.get(filename).toAbsolutePath().toUri();
            webView.getEngine().load(uri.toString());

            Scene scene = new Scene(webView, 400, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    public void handleNewSourceButton(ActionEvent event) {
        setSourceContent();

        if(treeClicked){

            if (!tempWorkflow.equalsIgnoreCase(source.getWorkflow())){
                System.out.println("New workflow: " + source.getWorkflow());
            }

            if (!tempSource.equalsIgnoreCase(source.getSource())){
                System.out.println("New source: " + source.getSource());
            }

        }else{
            System.out.println("Tree not clicked");
        }


        contentToTreeItems();
    }

}
