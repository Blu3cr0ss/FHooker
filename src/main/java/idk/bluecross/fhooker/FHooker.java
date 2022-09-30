package idk.bluecross.fhooker;

import idk.bluecross.fhooker.events.FieldChangedEvent;
import idk.bluecross.fhooker.events.SubmitAttackEvent;
import idk.bluecross.fhooker.util.LoggerKt;
import idk.bluecross.fhooker.util.ProxyUtilKt;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class FHooker extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FHooker.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("FHooker by Bluecross");
        stage.getIcons().add(new Image("bbra.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Globals.scene = scene;
        starter();
    }

    private void starter() {
        LoggerKt.log("Here i am!");
        FieldChangedEvent.start();
        SubmitAttackEvent.start();
        ProxyUtilKt.startProxyService();
    }

    public static void main(String[] args) {
        launch();
    }
}