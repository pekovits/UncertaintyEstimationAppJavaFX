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
import javafx.scene.control.*;
import org.w3c.dom.*;
import uncertaintyEstimation.model.Source;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;


public class UncertaintyInfoController {
    @FXML
    private TextField workflowField;
    @FXML
    private TextField sourceField;
    @FXML
    private ComboBox typeCombo;
    @FXML
    private ComboBox conditionCombo;
    @FXML
    private TextField distributionField;
    @FXML
    private TextArea assumptionField;
    @FXML
    private TreeView tree;
    @FXML
    private TreeItem treeItem;
    @FXML
    private TreeItem temp;
    @FXML private TreeItem temper = new TreeItem("");

    private Source source = new Source();
    private TreeItem<String> parpar;
    private boolean workflow = false;

    private ObservableList<String> comboData = FXCollections.observableArrayList();
    private ObservableList<String> condComboData = FXCollections.observableArrayList();

    private Element workElement;
    private boolean firstdoc = false;
    private boolean firstxmldoc = false;
    private XmlBuild xml = new XmlBuild();
    private boolean fill = false;

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
//        populateTree();
        treeItemSelected();
    }


    /**
     * This method listens for the TreeItem to be clicked and it finds its children and stores them in
     * an ArrayList, then it finds the parent of the specific TreeItem
     * It passes the arraylist and the parent to a method that will set the textfields to these values
     */
    public void treeItemSelected() {
        final ArrayList<String> arr = new ArrayList<String>();

        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                TreeItem<String> clickedItem = (TreeItem<String>) t1;
                System.out.println("selected text: " + clickedItem.getChildren());
                for (TreeItem<String> tr : clickedItem.getChildren()) {
                    parpar = tr.getParent();
                    arr.add(tr.getValue());

                    System.out.println("Parent: " + tr.getParent());
                    System.out.println("Items " + tr.getValue());

                }

                treeItemToTextField(parpar, arr);

                // delete all the elements from the arraylist for the next elements
                arr.clear();
            }
        });
    }


    /**
     * Takes as input an ArrayList and a TreeItem that represents the parent and sets the values
     * in the textfields by indexing the arraylist and taking the value of the parent
     *
     * @param parent
     * @param ls
     */
    public void treeItemToTextField(TreeItem<String> parent, ArrayList<String> ls) {
        System.out.println("index" + ls.get(1));
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


    public void populateTree(){
        try {

            File fXmlFile = new File("xmltest.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("workflow");

            System.out.println("----------------------------");
            TreeItem parent = (TreeItem) tree.getSelectionModel().getSelectedItem();
            if (parent == null) {
                parent = tree.getRoot();
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
                        System.out.println("Temper: " + temper);
//                        fill = true;
                    }

//                    if (fill) {
//                        treeItem = makeBranch(eElement.getElementsByTagName("source").item(0).getTextContent(), temper);
//                        makeBranch(eElement.getElementsByTagName("type").item(0).getTextContent(), treeItem);
//                        makeBranch(eElement.getElementsByTagName("condition").item(0).getTextContent(), treeItem);
//                        makeBranch(eElement.getElementsByTagName("distribution").item(0).getTextContent(), treeItem);
//                        makeBranch(eElement.getElementsByTagName("assumptions").item(0).getTextContent(), treeItem);
//                        tree.setShowRoot(false);
//                    } else {
//                        TreeItem workflow;
//                        workflow = makeBranch(eElement.getAttribute("name"), parent);
//                        treeItem = makeBranch(eElement.getElementsByTagName("source").item(0).getTextContent(), workflow);
//                        makeBranch(eElement.getElementsByTagName("type").item(0).getTextContent(), treeItem);
//                        makeBranch(eElement.getElementsByTagName("condition").item(0).getTextContent(), treeItem);
//                        makeBranch(eElement.getElementsByTagName("distribution").item(0).getTextContent(), treeItem);
//                        makeBranch(eElement.getElementsByTagName("assumptions").item(0).getTextContent(), treeItem);
//                        tree.setShowRoot(false);
//
//                        temper = treeItem.getParent();
//                        System.out.println("Temper: " + temper);
//                        fill = true;
//                    }
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

    public void sourceTreeItem() {
        treeItem = makeBranch(source.getSource(), temp);
        makeBranch(source.getType(), treeItem);
        makeBranch(source.getCondition(), treeItem);
        makeBranch(source.getDistribution(), treeItem);
        makeBranch(source.getAssumptions(), treeItem);
        tree.setShowRoot(false);
    }

    /**
     * This method takes the inputs from the user and adds them to the treeview
     */
    public void contentToTreeItems() {
        TreeItem parent = (TreeItem) tree.getSelectionModel().getSelectedItem();
        if (parent == null) {
            parent = tree.getRoot();
        }

        System.out.println("Workflow in contentToTreeItems: " + workflow);
        if (workflow) {
            sourceTreeItem();
            sourceField.setText("");
            distributionField.setText("");
            assumptionField.setText("");
        } else {
            workflowTreeItem(parent);
            sourceField.setText("");
            distributionField.setText("");
            assumptionField.setText("");

            temp = treeItem.getParent();
            System.out.println("temp: " + temp);
            workflow = true;
        }

        Document doc = docsCreated();
        Element work = workflowToXML(doc);
        if (firstdoc) {
            xml.addWorkflowNode(work);
        } else {
            writeXML(doc, work);
            firstdoc = true;
        }
    }


    public void contentToTreeItemsWorkflowDifferent() {

        TreeItem parent = (TreeItem) tree.getSelectionModel().getSelectedItem();
        if (parent == null) {
            parent = tree.getRoot();
        }

        System.out.println("Workflow in contentToTreeItemsWorkflowDifferent: " + workflow);
        if (workflow) {
            sourceTreeItem();
            workflowField.setText("");
            sourceField.setText("");
            distributionField.setText("");
            assumptionField.setText("");

            workflow = false;

        } else {
            workflowTreeItem(parent);
            workflowField.setText("");
            sourceField.setText("");
            distributionField.setText("");
            assumptionField.setText("");

            temp = treeItem.getParent();
        }

        Document doc = docsCreated();
        Element work = workflowToXML(doc);
        xml.addWorkflowNode(work);
    }

    public Element sourceToXML(Document doc) {
        Element sourceElement = null;
        try {

            sourceElement = doc.createElement("source");

            Element workName = doc.createElement("name");
            workName.appendChild(doc.createTextNode(source.getSource()));
            sourceElement.appendChild(workName);

            Element carname = doc.createElement("type");
            carname.appendChild(doc.createTextNode(source.getType()));
            sourceElement.appendChild(carname);

            Element carname1 = doc.createElement("condition");
            carname1.appendChild(doc.createTextNode(source.getCondition()));
            sourceElement.appendChild(carname1);

            Element cond = doc.createElement("distribution");
            cond.appendChild(doc.createTextNode(source.getDistribution()));
            sourceElement.appendChild(cond);

            Element distr = doc.createElement("assumptions");
            distr.appendChild(doc.createTextNode(source.getAssumptions()));
            sourceElement.appendChild(distr);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return sourceElement;
    }

    public Element workflowToXML(Document doc) {

        try {
            workElement = doc.createElement("workflow");

            Attr attr = doc.createAttribute("name");
            attr.setValue(source.getWorkflow());
            workElement.setAttributeNode(attr);

//            Element sourceElement = doc.createElement("source");
//            workElement.appendChild(sourceElement);

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

    public void writeXML(Document doc, Element workElem) {
        try {

            // root element
            Element rootElement = doc.createElement("application");
            doc.appendChild(rootElement);

            rootElement.appendChild(workElem);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("xmltest.xml"));
            transformer.transform(source, result);

            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Document docsCreated() {
        Document doc = null;
        try {
            File file = new File("xmltest.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            if (file.exists()) {
                doc = dBuilder.parse(file);
            } else {
                doc = dBuilder.newDocument();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return doc;
    }


    @FXML
    public void handleNewWorkfloButton(ActionEvent event) {
        setSourceContent();
        contentToTreeItemsWorkflowDifferent();

    }


    @FXML
    public void handleNewSourceButton(ActionEvent event) {
        setSourceContent();
        contentToTreeItems();
    }

}
