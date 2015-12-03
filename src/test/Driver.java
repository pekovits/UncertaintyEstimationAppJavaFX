package test;

/**
 * Created by fykos on 01/12/15.
 */
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.*;
import java.net.URL;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

public class Driver {

    public static void main(String[] args)
    {
        try
        {
            TransformerFactory tFactory = TransformerFactory.newInstance();

            Source xslDoc = new StreamSource("example.xsl");
            Source xmlDoc = new StreamSource("xmltest.xml");

            String outputFileName = "test_cr.html";
            OutputStream htmlFile = new FileOutputStream(outputFileName);

            Transformer transformer = tFactory.newTransformer(xslDoc);
            transformer.transform(xmlDoc, new StreamResult(htmlFile));


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}