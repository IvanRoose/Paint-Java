package com.example.cs250;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApp.class.getResource("Viewer250.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Paint 1.0.2");
        stage.setScene(scene);
        stage.show();
        /*stage.setOnCloseRequest(event -> {
            System.out.println("cum");
            //Controller save = new Controller();
            //save.checkSave();
            //stop the stage from closing immediately
            event.consume();
        });*/
    }

    public static void main(String[] args) {
        launch();
    }
}