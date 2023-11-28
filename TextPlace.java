package com.example.cs250;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Stack;

public class TextPlace extends Controller{
    public TextPlace(){
    }
    public TextPlace(Canvas canvas, ColorPicker colorPicker, ToggleButton toggleButton, Stack<WritableImage> snapshotStack, Stack<WritableImage> snapshotRedo, TextField textField){
        super();
        this.myCanvas = canvas;
        this.pencilColor = colorPicker;
        this.textButton = toggleButton;
        this.snapshotStack = snapshotStack;
        this.snapshotRedo = snapshotRedo;
        this.textField = textField;
    }
    public void text(){
        //gets graphic context
        GraphicsContext gc;
        gc = myCanvas.getGraphicsContext2D();
        //setting font size
        gc.setFont(javafx.scene.text.Font.font(20));
        //setting text color
        gc.setFill(Color.color(pencilColor.getValue().getRed(), pencilColor.getValue().getGreen(), pencilColor.getValue().getBlue()));
        //toggle button on
        if (textButton.isSelected()){
            //line drawing n' stuff
            myCanvas.setOnMousePressed(e -> {
                snapshotStack.push(myCanvas.snapshot(null, null));
                gc.fillText(textField.getText(), e.getX(), e.getY());
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });

        }
        //untoggle button
        else if (!textButton.isSelected()){
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
}
