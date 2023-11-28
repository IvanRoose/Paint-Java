package com.example.cs250;

import com.example.cs250.Controller;
import com.example.cs250.EditCanvas;
import com.example.cs250.TabControl;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Stack;

public class SquareDraw extends Controller {
    private Rectangle currentSquare;
    public SquareDraw(){

    }
    public SquareDraw(Canvas canvas, ColorPicker colorPicker, Slider slider, ToggleButton toggleButton, Stack<WritableImage> snapshotStack, Stack<WritableImage> snapshotRedo){
        super();
        this.myCanvas = canvas;
        this.mySlide = slider;
        this.pencilColor = colorPicker;
        this.squareBtn = toggleButton;
        this.snapshotStack = snapshotStack;
        this.snapshotRedo = snapshotRedo;
    }
    public void square(){
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
        if (squareBtn.isSelected()){
            gc.setLineDashes(0);//undashes lines
            //line drawing n' stuff
            myCanvas.setOnMousePressed(e -> {
                snapshotStack.push(myCanvas.snapshot(null, null));
                currentSquare = new Rectangle(e.getX(), e.getY(), 0, 0);
            });

            myCanvas.setOnMouseDragged(e -> {
                double width = e.getX() - currentSquare.getX();
                currentSquare.setWidth(width);
                currentSquare.setHeight(width);
            });

            myCanvas.setOnMouseReleased(e -> {
                gc.strokeRect(currentSquare.getX(), currentSquare.getY(), currentSquare.getWidth(), currentSquare.getWidth());
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });

        }
        //untoggle button
        else if (!squareBtn.isSelected()){
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
    public void squareDash(){
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
        if (squareBtn.isSelected()){
            //line drawing n' stuff

            gc.setLineDashes(10);//dashes lines

            myCanvas.setOnMousePressed(e -> {
                snapshotStack.push(myCanvas.snapshot(null, null));
                currentSquare = new Rectangle(e.getX(), e.getY(), 0, 0);
            });

            myCanvas.setOnMouseDragged(e -> {
                double width = e.getX() - currentSquare.getX();
                currentSquare.setWidth(width);
                currentSquare.setHeight(width);
            });

            myCanvas.setOnMouseReleased(e -> {
                gc.strokeRect(currentSquare.getX(), currentSquare.getY(), currentSquare.getWidth(), currentSquare.getWidth());
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });

        }
        //untoggle button
        else if (!squareBtn.isSelected()){
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
}
