package uncertaintyEstimation.view;

/**
 * Created by fykos on 28/11/15.
 */
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ReadXMLFile {

    public static void main(String argv[]) {

        try {

            File fXmlFile = new File("xmltest.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("workflow");

            System.out.println("----------------------------");
            String name = "";
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("name: " + name);
                    System.out.println("workflow: " + eElement.getAttribute("name"));

                    if (name.equalsIgnoreCase(eElement.getAttribute("name"))){
                        System.out.println("true");
//                        System.out.println("Workflow name : " + eElement.getAttribute("name"));
                        System.out.println("Source : " + eElement.getElementsByTagName("source").item(0).getTextContent());
                        System.out.println("Type : " + eElement.getElementsByTagName("type").item(0).getTextContent());
                        System.out.println("Condition : " + eElement.getElementsByTagName("condition").item(0).getTextContent());
                        System.out.println("Distribution : " + eElement.getElementsByTagName("distribution").item(0).getTextContent());
                        System.out.println("Assumptions : " + eElement.getElementsByTagName("assumptions").item(0).getTextContent());
                    }else{
                        System.out.println("false");
                        System.out.println("Workflow name : " + eElement.getAttribute("name"));
                        System.out.println("Source : " + eElement.getElementsByTagName("source").item(0).getTextContent());
                        System.out.println("Type : " + eElement.getElementsByTagName("type").item(0).getTextContent());
                        System.out.println("Condition : " + eElement.getElementsByTagName("condition").item(0).getTextContent());
                        System.out.println("Distribution : " + eElement.getElementsByTagName("distribution").item(0).getTextContent());
                        System.out.println("Assumptions : " + eElement.getElementsByTagName("assumptions").item(0).getTextContent());

                        name = eElement.getAttribute("name");
                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}