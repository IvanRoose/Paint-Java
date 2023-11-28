package com.example.cs250;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.Stack;

public class CopyPaste extends Controller{
    private Rectangle currentRectangle;
    private double globWidth;
    private double globHeight;

    public CopyPaste(){
    }
    public CopyPaste(Canvas canvas, ToggleButton toggleButton, Stack<WritableImage> snapshotStack, Stack<WritableImage> snapshotRedo, Stack<WritableImage> snapshotCopy, ToggleButton toggleButton1,ToggleButton toggleButton2, ToggleButton toggleButton3){
        super();
        this.myCanvas = canvas;
        this.copyBtn = toggleButton;
        this.pasteBtn = toggleButton1;
        this.snapshotStack = snapshotStack;
        this.snapshotRedo = snapshotRedo;
        this.snapshotCopy = snapshotCopy;
        this.selectButton = toggleButton2;
        this.rotBtn = toggleButton3;
    }
    public void copy(){
        //gets graphic context
        GraphicsContext gc;
        gc = myCanvas.getGraphicsContext2D();
        SnapshotParameters params = new SnapshotParameters();

        //gets the slider value as it updates throughout the program
        gc.setLineWidth(1);
        //toggle button on
        if (copyBtn.isSelected()){
            gc.setLineDashes(0);//undashes lines
            //line drawing n' stuff
            myCanvas.setOnMousePressed(e -> {
                snapshotStack.push(myCanvas.snapshot(null, null));
                currentRectangle = new Rectangle(e.getX(), e.getY(), 0, 0);
            });

            myCanvas.setOnMouseDragged(e -> {
                double width = e.getX() - currentRectangle.getX();
                double height = e.getY() - currentRectangle.getY();
                currentRectangle.setWidth(width);
                currentRectangle.setHeight(height);
            });

            myCanvas.setOnMouseReleased(e -> {
                params.setViewport(new javafx.geometry.Rectangle2D(currentRectangle.getX(), currentRectangle.getY(), currentRectangle.getWidth(), currentRectangle.getHeight()));
                snapshotCopy.push(myCanvas.snapshot(params, null));
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });

        }
        //untoggle button
        else if (!copyBtn.isSelected()){
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
    public void cut(){
        //gets graphic context
        GraphicsContext gc;
        gc = myCanvas.getGraphicsContext2D();
        SnapshotParameters params = new SnapshotParameters();

        //gets the slider value as it updates throughout the program
        gc.setLineWidth(1);
        //toggle button on
        if (selectButton.isSelected()){
            gc.setLineDashes(0);//undashes lines
            //line drawing n' stuff
            myCanvas.setOnMousePressed(e -> {
                snapshotStack.push(myCanvas.snapshot(null, null));
                currentRectangle = new Rectangle(e.getX(), e.getY(), 0, 0);
            });

            myCanvas.setOnMouseDragged(e -> {
                double width = e.getX() - currentRectangle.getX();
                double height = e.getY() - currentRectangle.getY();
                currentRectangle.setWidth(width);
                currentRectangle.setHeight(height);

                setGlobWidth(width);
                setHeight(height);

            });

            myCanvas.setOnMouseReleased(e -> {
                params.setViewport(new javafx.geometry.Rectangle2D(currentRectangle.getX(), currentRectangle.getY(), currentRectangle.getWidth(), currentRectangle.getHeight()));
                snapshotCopy.push(myCanvas.snapshot(params, null));
                gc.setFill(Color.WHITE);
                gc.fillRect(currentRectangle.getX(), currentRectangle.getY(), currentRectangle.getWidth(), currentRectangle.getHeight());
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });

        }
        //untoggle button
        else if (!selectButton.isSelected()){
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
    public void paste(){
        GraphicsContext gc;
        gc = myCanvas.getGraphicsContext2D();
        if (pasteBtn.isSelected()){
            myCanvas.setOnMousePressed(e -> {
                snapshotStack.push(myCanvas.snapshot(null, null));
                gc.drawImage(snapshotCopy.peek(), e.getX(), e.getY());
                snapshotRedo.push(myCanvas.snapshot(null, null));
            });
        }
        else if (!pasteBtn.isSelected()){
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }
    public void pasteRot(int num){
        GraphicsContext gc;
        gc = myCanvas.getGraphicsContext2D();
        SnapshotParameters params = new SnapshotParameters();

        //gets the slider value as it updates throughout the program
        gc.setLineWidth(1);
        //toggle button on
        if (rotBtn.isSelected()){
            gc.setLineDashes(0);//undashes lines
            //line drawing n' stuff
            myCanvas.setOnMousePressed(e -> {
                snapshotStack.push(myCanvas.snapshot(null, null));
                currentRectangle = new Rectangle(e.getX(), e.getY(), 0, 0);
            });

            myCanvas.setOnMouseDragged(e -> {
                double width = e.getX() - currentRectangle.getX();
                double height = e.getY() - currentRectangle.getY();
                currentRectangle.setWidth(width);
                currentRectangle.setHeight(height);

                setGlobWidth(width);
                setHeight(height);

            });

            myCanvas.setOnMouseReleased(e -> {
                params.setViewport(new javafx.geometry.Rectangle2D(currentRectangle.getX(), currentRectangle.getY(), currentRectangle.getWidth(), currentRectangle.getHeight()));
                snapshotCopy.push(myCanvas.snapshot(params, null));
                gc.setFill(Color.WHITE);
                gc.fillRect(currentRectangle.getX(), currentRectangle.getY(), currentRectangle.getWidth(), currentRectangle.getHeight());
                Rotate rotate = new Rotate(num, currentRectangle.getX() + getWidth() / 2, currentRectangle.getY() + getHeight() / 2);
                gc.save(); // Save the current state of the graphics context
                gc.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());
                gc.drawImage(snapshotCopy.peek(), currentRectangle.getX(), currentRectangle.getY(), currentRectangle.getWidth(), currentRectangle.getHeight());
                snapshotRedo.push(myCanvas.snapshot(null, null));

            });

        }
        //untoggle button
        else if (!rotBtn.isSelected()){
            myCanvas.setOnMousePressed(null);
            myCanvas.setOnMouseDragged(null);
        }
    }

    public double getHeight() {
        return globHeight;
    }
    public void setHeight(double height) {
        this.globHeight = height;
    }

    public double getWidth() {
        return globWidth;
    }

    public void setGlobWidth(double width) {
        this.globWidth = width;
    }
}
