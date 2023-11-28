package com.example.cs250;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TriangleDraw extends Controller{
    private double startX;
    private double startY;
    public TriangleDraw(){
    }
    public TriangleDraw(Canvas canvas, ColorPicker colorPicker, Slider slider, ToggleButton toggleButton, Stack<WritableImage> snapshotStack, Stack<WritableImage> snapshotRedo){
        super();
        this.myCanvas = canvas;
        this.mySlide = slider;
        this.pencilColor = colorPicker;
        this.triBtn = toggleButton;
        this.snapshotStack = snapshotStack;
        this.snapshotRedo = snapshotRedo;
    }
    public void triangle() {
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
        if (triBtn.isSelected()) {
            gc.setLineDashes(0);//undashes lines
            //line drawing n' stuff
            myCanvas.setOnMousePressed(e -> {
                snapshotStack.push(myCanvas.snapshot(null, null));
                startX = e.getX();
                startY = e.getY();
            });

            myCanvas.setOnMouseDragged(e -> {
            });

            myCanvas.setOnMouseReleased(e -> {
                double endX = e.getX();
                double endY = e.getY();

                // Clear the canvas and draw the triangle
                gc.strokeLine(startX, startY, endX, startY);
                gc.strokeLine(startX, startY, startX, endY);
                gc.strokeLine(startX, endY, endX, startY);
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });

        }
        //untoggle button
        else if (!triBtn.isSelected()) {
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
    public void triangleDash() {
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
        if (triBtn.isSelected()) {
            gc.setLineDashes(10);//dashes lines
            //line drawing n' stuff
            myCanvas.setOnMousePressed(e -> {
                snapshotStack.push(myCanvas.snapshot(null, null));
                startX = e.getX();
                startY = e.getY();
            });

            myCanvas.setOnMouseDragged(e -> {
            });

            myCanvas.setOnMouseReleased(e -> {
                double endX = e.getX();
                double endY = e.getY();

                // Clear the canvas and draw the triangle
                gc.strokeLine(startX, startY, endX, startY);
                gc.strokeLine(startX, startY, startX, endY);
                gc.strokeLine(startX, endY, endX, startY);
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });

        }
        //untoggle button
        else if (!triBtn.isSelected()) {
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
}
