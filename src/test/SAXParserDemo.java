package test;

/**
 * Created by fykos on 22/11/15.
 */
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserDemo {
    public static void main(String[] args){

        try {
            File inputFile = new File("xmltest.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userhandler = new UserHandler();
            saxParser.parse(inputFile, userhandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class UserHandler extends DefaultHandler {

    boolean bFirstName = false;
    boolean bLastName = false;
    boolean bNickName = false;
    boolean bMarks = false;

    boolean sourceName = false;
    boolean typeName = false;
    boolean conditionName = false;
    boolean distributionName = false;
    boolean assumptionName = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("workflow")) {
            String rollNo = attributes.getValue("name");
            System.out.println("Workflow name : " + rollNo);
        } else if (qName.equalsIgnoreCase("name")) {
            sourceName = true;
        } else if (qName.equalsIgnoreCase("type")) {
            typeName = true;
        } else if (qName.equalsIgnoreCase("condition")) {
            conditionName = true;
        } else if (qName.equalsIgnoreCase("distribution")){
            distributionName = true;
        } else if (qName.equalsIgnoreCase("assumptions")) {
            assumptionName = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("workflow")) {
            System.out.println("End element :" + qName);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (sourceName) {
            System.out.println("Source name: " + new String(ch, start, length));
            sourceName = false;
        } else if (typeName) {
            System.out.println("Type: " + new String(ch, start, length));
            typeName = false;
        } else if (conditionName) {
            System.out.println("Condition: " + new String(ch, start, length));
            conditionName = false;
        } else if (distributionName) {
            System.out.println("Distribution: " + new String(ch, start, length));
            distributionName = false;
        } else if(assumptionName){
            System.out.println("Assumptions: " + new String(ch, start, length));
            assumptionName = false;
            System.out.println();
        }
    }
}