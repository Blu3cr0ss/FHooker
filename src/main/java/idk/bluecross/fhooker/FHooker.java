package idk.bluecross.fhooker;

import idk.bluecross.fhooker.events.FieldChangedEvent;
import idk.bluecross.fhooker.events.SubmitAttackEvent;
import idk.bluecross.fhooker.logic.MainLogicKt;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FHooker extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FHooker.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("FHOOKER BY BLUECROSS");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Globals.scene = scene;
        starter();
    }

    private void starter(){
        FieldChangedEvent.start();
        SubmitAttackEvent.start();
    }

    public static void main(String[] args) {
        launch();
    }
}