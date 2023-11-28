package com.example.cs250;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;

public class EditCanvas extends Controller{

    public EditCanvas(){
    }
    public EditCanvas(Canvas myCanvas, Slider mySlide, ColorPicker pencilColor, ToggleButton drawbutton, ToggleButton lineBtn, ToggleButton squareBtn, ToggleButton rectBtn, ToggleButton circleBtn, ToggleButton ellBtn, ToggleButton triBtn,
                      ToggleButton lineBtn1, ToggleButton squareBtn1, ToggleButton rectBtn1, ToggleButton circleBtn1, ToggleButton ellBtn1, ToggleButton triBtn1){
        super();
        this.myCanvas = myCanvas;
        this.mySlide = mySlide;
        this.drawButton = drawbutton;
        this.lineBtn = lineBtn;
        this.squareBtn = squareBtn;
        this.rectBtn = rectBtn;
        this.circleBtn = circleBtn;
        this.ellBtn = ellBtn;
        this.triBtn = triBtn;
        this.pencilColor = pencilColor;
    }


}
