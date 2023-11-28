package com.example.cs250;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;

public class Erase extends Controller{
    public Erase(){
    }
    public Erase(Canvas canvas, ColorPicker colorPicker, Slider slider, ToggleButton toggleButton){
        super();
        this.myCanvas = canvas;
        this.mySlide = slider;
        this.pencilColor = colorPicker;
        this.drawButton = toggleButton;
    }
    public void erase(){
        //gets graphic context
        GraphicsContext gc;
        gc = myCanvas.getGraphicsContext2D();
        //setting stroke color
        gc.setStroke(Color.WHITE);
        //gets the slider value as it updates throughout the program
        gc.setLineWidth(10);

        //toggle button on
        if (drawButton.isSelected()){
            gc.setLineDashes(0);//undashes lines
            //line drawing n' stuff
            myCanvas.setOnMousePressed(e->{
                gc.beginPath();
                gc.moveTo(e.getX(), e.getY());
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            });
            myCanvas.setOnMouseDragged(e->{
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            });
        }
        //untoggle button
        else if (!drawButton.isSelected()){
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
}
