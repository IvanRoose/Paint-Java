package com.example.cs250;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.stage.StageStyle;



public class PopUps extends Controller{

    public PopUps(){
    }
    public PopUps(Canvas canvas, AnchorPane anchorPane){
        super();
        this.myCanvas = canvas;
        this.anchorid = anchorPane;
    }
    public void clear() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UTILITY);
        popupStage.setTitle("Confirmation");

        // Create the text
        Text text = new Text("Are you sure?");

        // Create buttons
        Button yesButton = new Button("Continue");
        yesButton.setOnAction(e -> {
            GraphicsContext gc = myCanvas.getGraphicsContext2D();
            gc.setFill(Color.WHITE); //whites over canvas
            gc.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
            popupStage.close();
        });

        Button noButton = new Button("Cancel");
        noButton.setOnAction(e -> {
            popupStage.close();
        });

        // Create a layout for buttons
        VBox buttonLayout = new VBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(yesButton, noButton);

        // Create a layout for the popup content
        VBox popupLayout = new VBox(20);
        popupLayout.setAlignment(Pos.CENTER);
        popupLayout.getChildren().addAll(text, buttonLayout);

        // Create the popup scene
        Scene popupScene = new Scene(popupLayout, 250, 150);

        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
    public void checkSave(){
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UTILITY);
        popupStage.setTitle("Confirmation");

        // Create the text
        Text text = new Text("Would you like to save first?");

        // Create buttons
        Button yesButton = new Button("Save");
        yesButton.setOnAction(e -> {
            Controller controller = new Controller();
            controller.SaveMenuAction();
            Scene scene = anchorid.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.close();
        });

        Button noButton = new Button("Cancel");
        noButton.setOnAction(e -> {
            Scene scene = anchorid.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.close();
        });

        // Create a layout for buttons
        VBox buttonLayout = new VBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(yesButton, noButton);

        // Create a layout for the popup content
        VBox popupLayout = new VBox(20);
        popupLayout.setAlignment(Pos.CENTER);
        popupLayout.getChildren().addAll(text, buttonLayout);

        // Create the popup scene
        Scene popupScene = new Scene(popupLayout, 250, 150);

        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
}
