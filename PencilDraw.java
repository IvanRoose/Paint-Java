package com.example.cs250;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Stack;

public class PencilDraw extends Controller{
    public PencilDraw(){
    }
    public PencilDraw(Canvas canvas, ColorPicker colorPicker, Slider slider, ToggleButton toggleButton, Stack<WritableImage> snapshotStack, Stack<WritableImage> snapshotRedo){
        super();
        this.myCanvas = canvas;
        this.mySlide = slider;
        this.pencilColor = colorPicker;
        this.drawButton = toggleButton;
        this.snapshotStack = snapshotStack;
        this.snapshotRedo = snapshotRedo;
    }
    public void draw(){
        //gets graphic context
        GraphicsContext gc;
        gc = myCanvas.getGraphicsContext2D();
        //setting stroke color
        gc.setStroke(Color.color(pencilColor.getValue().getRed(), pencilColor.getValue().getGreen(), pencilColor.getValue().getBlue()));
        //gets the slider value as it updates throughout the program
        mySlide.valueProperty().addListener((observable, oldValue, newValue) -> {
            gc.setLineWidth(newValue.doubleValue());
        });
        //toggle button on
        if (drawButton.isSelected()){
            gc.setLineDashes(0);//undashes lines
            //line drawing n' stuff
            myCanvas.setOnMousePressed(e->{
                snapshotStack.push(myCanvas.snapshot(null, null));
                gc.beginPath();
                gc.moveTo(e.getX(), e.getY());
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            });
            myCanvas.setOnMouseDragged(e->{
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            });
            myCanvas.setOnMouseReleased(e->{
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });
        }
        //untoggle button
        else if (!drawButton.isSelected()){
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
    public void drawDash(){
        //gets graphic context
        GraphicsContext gc;
        gc = myCanvas.getGraphicsContext2D();
        //setting stroke color
        gc.setStroke(Color.color(pencilColor.getValue().getRed(), pencilColor.getValue().getGreen(), pencilColor.getValue().getBlue()));
        //gets the slider value as it updates throughout the program
        mySlide.valueProperty().addListener((observable, oldValue, newValue) -> {
            gc.setLineWidth(newValue.doubleValue());
        });
        //toggle button on
        if (drawButton.isSelected()){
            gc.setLineDashes(10);
            //line drawing n' stuff
            myCanvas.setOnMousePressed(e->{
                snapshotStack.push(myCanvas.snapshot(null, null));
                gc.beginPath();
                gc.moveTo(e.getX(), e.getY());
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            });
            myCanvas.setOnMouseDragged(e->{
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            });
            myCanvas.setOnMouseReleased(e->{
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });
        }
        //untoggle button
        else if (!drawButton.isSelected()){
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
}
