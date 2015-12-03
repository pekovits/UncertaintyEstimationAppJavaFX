package test;

/**
 * Created by fykos on 01/12/15.
 */
import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @web http://java-buddy.blogspot.com/
 */
public class JavaFX_Browser extends Application {

    private Scene scene;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("java-buddy.blogspot.com");

        WebView webView = new WebView();
        String filename = "test_cr.html";
        final java.net.URI uri = java.nio.file.Paths.get(filename).toAbsolutePath().toUri();
        webView.getEngine().load(uri.toString());

        Scene scene = new Scene(webView, 400,400);


        primaryStage.setScene(scene);
        primaryStage.show();
    }


}