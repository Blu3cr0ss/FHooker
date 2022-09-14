module idk.bluecross.fhooker {
    requires kotlin.stdlib;
    requires javafx.controls;
    requires javafx.fxml;
        requires javafx.web;

        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires org.kordamp.ikonli.javafx;
            requires org.kordamp.bootstrapfx.core;
            requires com.almasb.fxgl.all;
    requires java.desktop;

    opens idk.bluecross.fhooker to javafx.fxml;
    exports idk.bluecross.fhooker;
}