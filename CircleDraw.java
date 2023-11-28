package com.example.cs250;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Stack;

public class CircleDraw extends Controller{
    private Circle currentCircle;

    public CircleDraw(){
    }
    public CircleDraw(Canvas canvas, ColorPicker colorPicker, Slider slider, ToggleButton toggleButton, Stack<WritableImage> snapshotStack, Stack<WritableImage> snapshotRedo){
        super();
        this.myCanvas = canvas;
        this.mySlide = slider;
        this.pencilColor = colorPicker;
        this.circleBtn = toggleButton;
    }
    public boolean circle() {
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
        if (circleBtn.isSelected()) {
            gc.setLineDashes(0);//undashes lines
            //line drawing n' stuff
            myCanvas.setOnMousePressed(e -> {
                snapshotStack.push(myCanvas.snapshot(null, null));
                currentCircle = new Circle(e.getX(), e.getY(), 0);
                currentCircle.setCenterX(e.getX());
                currentCircle.setCenterY(e.getY());
            });

            myCanvas.setOnMouseDragged(e -> {
                // Calculate the radius based on the distance from the center to the current mouse position
                double radiusX = Math.abs(e.getX() - currentCircle.getCenterX());
                double radiusY = Math.abs(e.getY() - currentCircle.getCenterY());
                // Set the radius as the maximum of the two distances to create a circle
                currentCircle.setRadius(Math.max(radiusX, radiusY));
            });

            myCanvas.setOnMouseReleased(e -> {
                gc.strokeOval(currentCircle.getCenterX() - currentCircle.getRadius(), currentCircle.getCenterY() - currentCircle.getRadius(), 2 * currentCircle.getRadius(), 2 * currentCircle.getRadius());
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });

        }
        //untoggle button
        else if (!circleBtn.isSelected()) {
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
        return true;
    }
    public void circleDash() {
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
        if (circleBtn.isSelected()) {

            gc.setLineDashes(10);//dashes lines
            //line drawing n' stuff
            myCanvas.setOnMousePressed(e -> {
                snapshotStack.push(myCanvas.snapshot(null, null));
                currentCircle = new Circle(e.getX(), e.getY(), 0);
                currentCircle.setCenterX(e.getX());
                currentCircle.setCenterY(e.getY());
            });

            myCanvas.setOnMouseDragged(e -> {
                // Calculate the radius based on the distance from the center to the current mouse position
                double radiusX = Math.abs(e.getX() - currentCircle.getCenterX());
                double radiusY = Math.abs(e.getY() - currentCircle.getCenterY());
                // Set the radius as the maximum of the two distances to create a circle
                currentCircle.setRadius(Math.max(radiusX, radiusY));
            });

            myCanvas.setOnMouseReleased(e -> {
                gc.strokeOval(currentCircle.getCenterX() - currentCircle.getRadius(), currentCircle.getCenterY() - currentCircle.getRadius(), 2 * currentCircle.getRadius(), 2 * currentCircle.getRadius());
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });

        }
        //untoggle button
        else if (!circleBtn.isSelected()) {
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
}
