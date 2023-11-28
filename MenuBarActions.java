package com.example.cs250;

import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import javafx.embed.swing.SwingFXUtils;

public class MenuBarActions extends Controller{
    private File file;

    public MenuBarActions(){
        myCanvas = null;
        anchorid = null;
    }
    public MenuBarActions(Canvas canvas, AnchorPane anchorPane){
        super();
        this.myCan = canvas;
        this.anchorid = anchorPane;
    }
    //resize method
    public void resize(File file, Canvas myCanvas){
        Image userImage = new Image(file.getPath());
        //resizing canvas
        myCanvas.setHeight(userImage.getHeight());
        myCanvas.setWidth(userImage.getWidth());

    }
    //open method
    public void open(){
        //Open the file explorer and choose a file
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);


        file = fileChooser.showOpenDialog(anchorid.getScene().getWindow());
        if (file != null){
            //make an image and get the path
            Image userImage = new Image(file.getPath());

            //resizes everything
            //resize(file, myCan);

            //puts the  image in the canvas
            GraphicsContext gc = myCan.getGraphicsContext2D();
            gc.setFill(Color.WHITE); //whites over canvas
            gc.fillRect(0, 0, myCan.getWidth(), myCan.getHeight());
            gc.drawImage(userImage, myCan.getWidth()/2 - userImage.getWidth()/2, myCan.getHeight()/2 - userImage.getHeight()/2, userImage.getWidth(), userImage.getHeight());

        }

    }
    public void save(){
        //turns canvas into writable image and takes a snapshot
        WritableImage canvasImage = new WritableImage((int) myCan.getWidth(), (int) myCan.getHeight());
        myCan.snapshot(null, canvasImage);

        //getting the extension of the file
        String extension = "png";
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        if (!(index > 0)) {
            extension = fileName.substring(index+1);
        }

        //saving the image
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(canvasImage, null), extension, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveAs(){
        //opening file exploerer
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);

        //getting file and the draw panel
        File file = fileChooser.showSaveDialog(anchorid.getScene().getWindow());

        //making the canvas a writable image and snapshot
        WritableImage canvasImage = new WritableImage((int) myCan.getWidth(), (int) myCan.getHeight());
        myCan.snapshot(null, canvasImage);


        //getting file extension
        String extension = "png";
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        if (!(index > 0)) {
            extension = fileName.substring(index+1);
        }
        //saving the image
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(canvasImage, null), extension, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveTemp(){
        //turns canvas into writable image and takes a snapshot
        WritableImage canvasImage = new WritableImage((int) myCan.getWidth(), (int) myCan.getHeight());
        myCan.snapshot(null, canvasImage);

        try {
            // create temp file
            File tempFile = File.createTempFile("tempImage", ".png");


            // save to temp file
            ImageIO.write(SwingFXUtils.fromFXImage(canvasImage, null), ".png", tempFile);

            System.out.println("Temporary file created at: " + tempFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
