package uncertaintyEstimation.controller;

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

/**
 * Created by fykos on 26/11/15.
 */
public class XmlBuild {
    private Element workElement;
    boolean firstdoc = false;
    boolean firstxmldoc = false;

    @FXML private TextField workflowField;
    @FXML private TextField sourceField;
    @FXML private ComboBox typeCombo;
    @FXML private ComboBox conditionCombo;
    @FXML private TextField distributionField;
    @FXML private TextArea assumptionField;
    @FXML private TreeView tree;
    @FXML private TreeItem treeItem;
    @FXML private TreeItem temp;

    private Source source = new Source();
    private TreeItem<String> parpar;
    private boolean workflow = false;

    public XmlBuild(){

    }

    public void writeXML(Document doc, Element workElem){
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

    public void addWorkflowNode(Element sourceNode){
        try {
            File file = new File("xmltest.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);


            Element root = document.getDocumentElement();
            Node nd = document.getDocumentElement();


            nd.appendChild(document.importNode(sourceNode, true));

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("xmltest.xml");
            transformer.transform(source, result);


        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void addXmlNode(Element sourceNode){
        try {
            File file = new File("xmltest.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);


            Element root = document.getDocumentElement();
            Node nd = document.getDocumentElement().getFirstChild();


            nd.appendChild(document.importNode(sourceNode, true));

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("xmltest.xml");
            transformer.transform(source, result);


        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Element workflowToXML(Document doc){

        try {
            workElement = doc.createElement("workflow");

            Attr attr = doc.createAttribute("name");
            attr.setValue(source.getWorkflow());
            workElement.setAttributeNode(attr);

            Element sourceElement = doc.createElement("source");
            workElement.appendChild(sourceElement);

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


        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return workElement;
    }

    public Element sourceToXML(Document doc){
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

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return sourceElement;
    }

    public String attributeName(){
        String name = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new File("xmltest.xml"));
            NodeList nodeList = document.getElementsByTagName("workflow");
            for (int x = 0, size = nodeList.getLength(); x < size; x++) {
                System.out.println("Name: " + nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue());
                name = nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue();
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return name;
    }

    public void writeNewWorkflow(){
        try {
            File file = new File("xmltest.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            Element rootElement = document.createElement("application");
            document.appendChild(rootElement);

            Node nd = document.getDocumentElement().getFirstChild();
            System.out.println("Node: " + nd.getNodeName());
            Element work = workflowToXML(document);

            nd.appendChild(document.importNode(work, true));

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("xmltest.xml");
            transformer.transform(source, result);

        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    public Document docsCreated(){
        Document doc = null;
        try {
            File file = new File("xmltest.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            if(file.exists()){
                doc = dBuilder.parse(file);
            }else{
                doc = dBuilder.newDocument();
            }

        }catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return doc;
    }
}
