package uncertaintyEstimation.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by fykos on 01/12/15.
 */
public class MenuController {



    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
//        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
//
//        if (file != null) {
//            mainApp.loadPersonDataFromFile(file);
//        }
    }

    @FXML
    public void handlePreview(){

        Stage primaryStage = null;
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();

            Source xslDoc = new StreamSource("report.xsl");
            Source xmlDoc = new StreamSource("xmltest.xml");

            String outputFileName = "test.html";
            OutputStream htmlFile = new FileOutputStream(outputFileName);

            Transformer transformer = tFactory.newTransformer(xslDoc);
            transformer.transform(xmlDoc, new StreamResult(htmlFile));

            primaryStage.setTitle("java-buddy.blogspot.com");

            WebView webView = new WebView();
            String filename = "test.html";
            final java.net.URI uri = java.nio.file.Paths.get(filename).toAbsolutePath().toUri();
            webView.getEngine().load(uri.toString());

            Scene scene = new Scene(webView, 400,400);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }





    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
