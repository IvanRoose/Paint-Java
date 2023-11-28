package com.example.cs250;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.util.Stack;

public class EllipseDraw extends Controller{
    private Ellipse currentEllipse;

    public EllipseDraw(){
    }
    public EllipseDraw(Canvas canvas, ColorPicker colorPicker, Slider slider, ToggleButton toggleButton, Stack<WritableImage> snapshotStack, Stack<WritableImage> snapshotRedo){
        super();
        this.myCanvas = canvas;
        this.mySlide = slider;
        this.pencilColor = colorPicker;
        this.ellBtn = toggleButton;
        this.snapshotStack = snapshotStack;
        this.snapshotRedo = snapshotRedo;
    }
    public void ellipse(){
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
        if (ellBtn.isSelected()){
            gc.setLineDashes(0);//undashes lines
            //line drawing n' stuff
            myCanvas.setOnMousePressed(e -> {
                snapshotStack.push(myCanvas.snapshot(null, null));
                currentEllipse = new Ellipse(e.getX(), e.getY(), 0, 0);
                currentEllipse.setCenterX(e.getX());
                currentEllipse.setCenterY(e.getY());
            });

            myCanvas.setOnMouseDragged(e -> {
                // Calculate the radius based on the distance from the center to the current mouse position
                double radiusX = Math.abs(e.getX() - currentEllipse.getCenterX());
                double radiusY = Math.abs(e.getY() - currentEllipse.getCenterY());
                currentEllipse.setRadiusX(radiusX);
                currentEllipse.setRadiusY(radiusY);
            });

            myCanvas.setOnMouseReleased(e -> {
                gc.strokeOval(currentEllipse.getCenterX() - currentEllipse.getRadiusX(), currentEllipse.getCenterY() - currentEllipse.getRadiusY(), 2 * currentEllipse.getRadiusX(), 2 * currentEllipse.getRadiusY());
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });

        }
        //untoggle button
        else if (!ellBtn.isSelected()){
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
    public void ellipseDash(){
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
        if (ellBtn.isSelected()){

            gc.setLineDashes(10);//dashes lines

            //line drawing n' stuff
            myCanvas.setOnMousePressed(e -> {
                snapshotStack.push(myCanvas.snapshot(null, null));
                currentEllipse = new Ellipse(e.getX(), e.getY(), 0, 0);
                currentEllipse.setCenterX(e.getX());
                currentEllipse.setCenterY(e.getY());
            });

            myCanvas.setOnMouseDragged(e -> {
                // Calculate the radius based on the distance from the center to the current mouse position
                double radiusX = Math.abs(e.getX() - currentEllipse.getCenterX());
                double radiusY = Math.abs(e.getY() - currentEllipse.getCenterY());
                currentEllipse.setRadiusX(radiusX);
                currentEllipse.setRadiusY(radiusY);
            });

            myCanvas.setOnMouseReleased(e -> {
                gc.strokeOval(currentEllipse.getCenterX() - currentEllipse.getRadiusX(), currentEllipse.getCenterY() - currentEllipse.getRadiusY(), 2 * currentEllipse.getRadiusX(), 2 * currentEllipse.getRadiusY());
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });

        }
        //untoggle button
        else if (!ellBtn.isSelected()){
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
}
