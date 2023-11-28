package com.example.cs250;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;

public class PolyDraw extends Controller{
    private Polygon currentPolygon;
    public PolyDraw(){
    }
    public PolyDraw(Canvas canvas, ColorPicker colorPicker, Slider slider, ToggleButton toggleButton, Stack<WritableImage> snapshotStack, Stack<WritableImage> snapshotRedo, TextField textField){
        super();
        this.myCanvas = canvas;
        this.mySlide = slider;
        this.pencilColor = colorPicker;
        this.polygon = toggleButton;
        this.numField = textField;
    }
    public void polygon() {
        //gets graphic context
        GraphicsContext gc;
        gc = myCanvas.getGraphicsContext2D();
        //setting stroke color
        gc.setStroke(Color.color(pencilColor.getValue().getRed(), pencilColor.getValue().getGreen(), pencilColor.getValue().getBlue()));
        int arraySize, n;
        try {
            arraySize = Integer.parseInt(numField.getText());
        } catch (NumberFormatException e) {
            arraySize = 0;
        }
        n = arraySize;
        //gets the slider value as it updates throughout the program
        mySlide.valueProperty().addListener((observable, oldValue, newValue) -> {
            gc.setLineWidth(newValue.doubleValue());
        });
        double[] xValues = new double[arraySize];
        double[] yValues = new double[arraySize];
        AtomicReference<Double> x0 = new AtomicReference<>((double) 0);
        AtomicReference<Double> y0 = new AtomicReference<>((double) 0);
        AtomicReference<Double> x1 = new AtomicReference<>((double) 0);
        AtomicReference<Double> y1 = new AtomicReference<>((double) 0);
        AtomicReference<Double> x2 = new AtomicReference<>((double) 0);
        AtomicReference<Double> y2 = new AtomicReference<>((double) 0);
        AtomicReference<Double> radius = new AtomicReference<>((double) 0);
        //toggle button on
        if (polygon.isSelected()) {
            gc.setLineDashes(0);//undashes lines
            //line drawing n' stuff
            myCanvas.setOnMousePressed(e -> {
                snapshotStack.push(myCanvas.snapshot(null, null));
                currentPolygon = new Polygon();
                x1.set(e.getX());
                y1.set(e.getY());
            });

            myCanvas.setOnMouseDragged(e -> {
            });

            myCanvas.setOnMouseReleased(e -> {
                x2.set(e.getX());
                y2.set(e.getY());
                x0.set((x1.get() + x2.get()) / 2); // x-coordinate of circle center
                y0.set((y1.get() + y2.get()) / 2); // y-coordinate of circle center
                radius.set(Math.sqrt(Math.pow(x2.get() - x0.get(), 2) + Math.pow(y2.get() - y0.get(), 2))); // radius of the circle
                for (int i = 0; i < n; i++) {
                    double angle = 2 * Math.PI * i / n;
                    double x = x0.get() + radius.get() * Math.cos(angle);
                    double y = y0.get() + radius.get() * Math.sin(angle);
                    xValues[i] = x;
                    yValues[i] = y;
                }
                gc.strokePolygon(xValues, yValues, n);
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });

        }
        //untoggle button
        else if (!polygon.isSelected()) {
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
    public void polygonDash() {
        //gets graphic context
        GraphicsContext gc;
        gc = myCanvas.getGraphicsContext2D();
        //setting stroke color
        gc.setStroke(Color.color(pencilColor.getValue().getRed(), pencilColor.getValue().getGreen(), pencilColor.getValue().getBlue()));
        int arraySize, n;
        try {
            arraySize = Integer.parseInt(numField.getText());
        } catch (NumberFormatException e) {
            arraySize = 0;
        }
        n = arraySize;
        //gets the slider value as it updates throughout the program
        mySlide.valueProperty().addListener((observable, oldValue, newValue) -> {
            gc.setLineWidth(newValue.doubleValue());
        });
        double[] xValues = new double[arraySize];
        double[] yValues = new double[arraySize];
        AtomicReference<Double> x0 = new AtomicReference<>((double) 0);
        AtomicReference<Double> y0 = new AtomicReference<>((double) 0);
        AtomicReference<Double> x1 = new AtomicReference<>((double) 0);
        AtomicReference<Double> y1 = new AtomicReference<>((double) 0);
        AtomicReference<Double> x2 = new AtomicReference<>((double) 0);
        AtomicReference<Double> y2 = new AtomicReference<>((double) 0);
        AtomicReference<Double> radius = new AtomicReference<>((double) 0);
        //toggle button on
        if (polygon.isSelected()) {
            gc.setLineDashes(10);//undashes lines
            //line drawing n' stuff
            myCanvas.setOnMousePressed(e -> {
                snapshotStack.push(myCanvas.snapshot(null, null));
                currentPolygon = new Polygon();
                x1.set(e.getX());
                y1.set(e.getY());
            });

            myCanvas.setOnMouseDragged(e -> {
            });

            myCanvas.setOnMouseReleased(e -> {
                x2.set(e.getX());
                y2.set(e.getY());
                x0.set((x1.get() + x2.get()) / 2); // x-coordinate of circle center
                y0.set((y1.get() + y2.get()) / 2); // y-coordinate of circle center
                radius.set(Math.sqrt(Math.pow(x2.get() - x0.get(), 2) + Math.pow(y2.get() - y0.get(), 2))); // radius of the circle
                for (int i = 0; i < n; i++) {
                    double angle = 2 * Math.PI * i / n;
                    double x = x0.get() + radius.get() * Math.cos(angle);
                    double y = y0.get() + radius.get() * Math.sin(angle);
                    xValues[i] = x;
                    yValues[i] = y;
                }
                gc.strokePolygon(xValues, yValues, n);
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });

        }
        //untoggle button
        else if (!polygon.isSelected()) {
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
}
