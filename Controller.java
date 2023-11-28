package com.example.cs250;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Notifications;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;


public class Controller {
    @FXML
    private MenuItem timerOn;
    @FXML
    private MenuItem timerOff;
    @FXML
    public TextField rotDeg;
    @FXML
    public Label tmrLbl;
    @FXML
    public ToggleButton rotBtn;
    @FXML
    public ToggleButton selectButton;
    @FXML
    public ToggleButton copyBtn;
    @FXML
    public ToggleButton pasteBtn;
    @FXML
    private TextFlow numberOfSides;
    @FXML
    private TextFlow enterText;
    @FXML
    public TextField numField;
    @FXML
    public TextField textField;
    @FXML
    public ToggleButton textButton;
    @FXML
    public ToggleButton diamond;
    @FXML
    public ToggleButton diamond1;
    @FXML
    public ToggleButton polygon;
    @FXML
    public ToggleButton polygon1;
    @FXML
    private Button redo;
    @FXML
    private Button undo;
    @FXML
    private ToggleButton eraseBtn;
    @FXML
    private Button clearBtn;
    @FXML
    public MenuItem menuItemOpen;
    @FXML
    public MenuItem menuItemSave;
    @FXML
    public MenuItem menuItemSaveAs;
    @FXML
    public TabPane tabPane;
    @FXML
    public Slider mySlide;
    @FXML
    public Canvas myCanvas;
    @FXML
    public ToggleButton drawButton;
    @FXML
    public ToggleButton drawButton1;
    public Canvas myCan;
    @FXML
    public AnchorPane anchorid;
    @FXML
    public ToggleButton lineBtn;
    @FXML
    public ToggleButton squareBtn;
    @FXML
    public ToggleButton rectBtn;
    @FXML
    public ToggleButton circleBtn;
    @FXML
    public ToggleButton ellBtn;
    @FXML
    public ToggleButton triBtn;
    @FXML
    public ColorPicker pencilColor;
    @FXML
    public ToggleButton lineBtn1;
    @FXML
    public ToggleButton squareBtn1;

    public ToggleButton rectBtn1;
    @FXML
    public ToggleButton circleBtn1;
    @FXML
    public ToggleButton ellBtn1;
    @FXML
    public ToggleButton triBtn1;

    public boolean drawBool, draw1Bool, lineBool, line1Bool, sqrBool, sqr1Bool, rectBool, rect1Bool, circBool, circ1Bool, ellBool, ell1Bool, triBool, tri1Bool, rndBool, rnd1Bool, polyBool, poly1Bool, grabBool, eraseBool, txtBool, selBool, copyBool, pasteBool, selRotBool;

    public Stack<WritableImage> snapshotStack = new Stack<>();

    public Stack<WritableImage> snapshotRedo = new Stack<>();

    private static final String LOG_FILE_PATH = "C:/Users/ivanr/CS250/Cs250/src/main/resources/Media/log.txt";

    public Stack<WritableImage> snapshotCopy = new Stack<>();

    java.util.Timer timer = new Timer();

    public SingleSelectionModel<Tab> selectionModel;

    Boolean showTimer = true;

    public void initialize() {
        //creates first tab
        TabControl newTab = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, null, null, null, null);
        newTab.addTab();
        //sets color
        pencilColor.setValue(Color.BLACK);
        //create a KeyCodeCombination
        KeyCodeCombination openShortcut = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
        KeyCodeCombination save = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        KeyCodeCombination saveAs = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);
        //set the accelerator
        menuItemOpen.setAccelerator(openShortcut);
        menuItemSave.setAccelerator(save);
        menuItemSaveAs.setAccelerator(saveAs);
        //timer stuffs
        timer.scheduleAtFixedRate(new TimerTask() {
            int count = 0;
            @Override
            public void run() {
                //once 60 secs gets hit save
                if (count == 60 && showTimer) {
                    MenuBarActions save = new MenuBarActions(newTab.getMyCanvas(), newTab.getSelectedAnchorPane());
                    Platform.runLater(() -> {
                        save.saveTemp();
                        tmrLbl.setText("autosaving");
                        count = 0;
                        log("Autosaved");
                        showNotification();
                    });
                } else if (count != 60 && showTimer){
                    //timer counter
                    Platform.runLater(() -> tmrLbl.setText("autosave: " + count++));
                }
                else if (count == 60) {
                    MenuBarActions save = new MenuBarActions(newTab.getMyCanvas(), newTab.getSelectedAnchorPane());
                    Platform.runLater(() -> {
                        tmrLbl.setText("");
                        save.saveTemp();
                        count = 0;
                    });
                } else {
                    tmrLbl.setText("");
                    //timer counter
                    Platform.runLater(() -> count++);
                }

            }
        }, 0, 1000); // label updates every second
    }

    //button actions
    @FXML
    public boolean OpenMenuAction() {
        TabControl tabGet = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, null, null, null, null);
        tabGet.openTabCanvas();
        log("Image Opened");
        return true;
    }

    //Save method
    @FXML
    public boolean SaveMenuAction() {
        TabControl save = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, null, null, null, null);
        save.saveCan();
        log("Saved");
        showNotification();
        return true;
    }

    //Save as method
    @FXML
    public boolean SaveAsMenuAction() {
        TabControl saveAs = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, null, null, null, null);
        saveAs.saveAsCan();
        log("SaveAs");
        showNotification();
        return true;
    }

    //draw method
    @FXML
    private void draw(ActionEvent event) {

        TabControl draw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, drawButton, pencilColor, snapshotStack, snapshotRedo);
        draw.getMyCanvas();
        draw.getSelectedAnchorPane();
        draw.drawTab();
        log("Curved Line Selected");
    }

    @FXML
    private void draw1(ActionEvent event) {
        TabControl draw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, drawButton1, pencilColor, snapshotStack, snapshotRedo);
        draw.drawTab1();
        log("Dashed Curved Line Selected");
    }

    @FXML
    private void addTab(ActionEvent event) {
        TabControl newTab = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, null, null, null, null);
        newTab.addTab();
        log("Tab Added");
    }

    @FXML
    private void closeTab(ActionEvent event) {
        TabControl close = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, null, null, null, null);
        close.closeTab();
        log("Tab Deleted");
    }

    @FXML
    private void lineBtnPress(ActionEvent event) {
        TabControl lineDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, lineBtn, pencilColor, snapshotStack, snapshotRedo);
        lineDraw.lineTab();
        log("Line Selected");
    }

    @FXML
    private void lineBtnPress1(ActionEvent event) {
        TabControl lineDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, lineBtn1, pencilColor, snapshotStack, snapshotRedo);
        lineDraw.lineTab1();
        log("Dashed Line Selected");
    }

    @FXML
    private void squareBtnPress(ActionEvent event) {
        TabControl sqrDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, squareBtn, pencilColor, snapshotStack, snapshotRedo);
        sqrDraw.sqrTab();
        log("Square Selected");
    }

    @FXML
    private void squareBtnPress1(ActionEvent event) {
        TabControl sqrDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, squareBtn1, pencilColor, snapshotStack, snapshotRedo);
        sqrDraw.sqrTab1();
        log("Dashed Square Selected");
    }

    @FXML
    private void rectBtnPress(ActionEvent event) {
        TabControl rectDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, rectBtn, pencilColor, snapshotStack, snapshotRedo);
        rectDraw.rectTab();
        log("Rectangle Selected");
    }

    @FXML
    private void rectBtnPress1(ActionEvent event) {
        TabControl rectDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, rectBtn1, pencilColor, snapshotStack, snapshotRedo);
        rectDraw.rectTab1();
        log("Dashed Rectangle Selected");
    }

    @FXML
    private void circleBtnPress(ActionEvent event) {
        TabControl circleDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, circleBtn, pencilColor, snapshotStack, snapshotRedo);
        circleDraw.circleTab();
        log("Circle Selected");
    }

    @FXML
    private void circleBtnPress1(ActionEvent event) {
        TabControl circleDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, circleBtn1, pencilColor, snapshotStack, snapshotRedo);
        circleDraw.circleTab1();
        log("Dashed Circle Selected");
    }

    @FXML
    private void ellBtnPress(ActionEvent event) {
        TabControl ellDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, ellBtn, pencilColor, snapshotStack, snapshotRedo);
        ellDraw.ellTab();
        log( "Ellipse Selected");
    }

    @FXML
    private void ellBtnPress1(ActionEvent event) {
        TabControl ellDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, ellBtn1, pencilColor, snapshotStack, snapshotRedo);
        ellDraw.ellTab1();
        log("Dashed Ellipse Selected");
    }

    @FXML
    private void triBtnPress(ActionEvent event) {
        TabControl triDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, triBtn, pencilColor, snapshotStack, snapshotRedo);
        triDraw.triTab();
        log("Triangle Selected");
    }

    @FXML
    private void triBtnPress1(ActionEvent event) {
        TabControl triDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, triBtn1, pencilColor, snapshotStack, snapshotRedo);
        triDraw.triTab1();
        log("Dashed Triangle Selected");
    }

    @FXML
    private void diamondBtn(ActionEvent event){
        TabControl diamondDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, diamond, pencilColor, snapshotStack, snapshotRedo);
        diamondDraw.diamondTab();
        log("Rounded Square Selected");
    }

    @FXML
    private void diamondBtn1(ActionEvent event){
        TabControl diamondDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, diamond1, pencilColor, snapshotStack, snapshotRedo);
        diamondDraw.diamondTab1();
        log("Dashed Rounded Square Selected");
    }

    @FXML
    private void polyBtn(ActionEvent event){
        TabControl polyDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, polygon, pencilColor, snapshotStack, snapshotRedo);
        polyDraw.polygon(numField);
        log("Polygon Selected");
    }

    @FXML
    private void polyBtn1(ActionEvent event){
        TabControl polyDraw = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, polygon1, pencilColor, snapshotStack, snapshotRedo);
        polyDraw.polygonDash(numField);
        log("Dashed Polygon Selected");
    }

    @FXML
    private void GrabColor(ActionEvent event) {
        TabControl color = new TabControl(tabPane, tabPane.getSelectionModel(), mySlide, anchorid, triBtn, pencilColor, snapshotStack, snapshotRedo);
        color.getColors();
        log("Color Grab Selected");
    }

    @FXML
    private void CanvasClear(ActionEvent event) {
        TabControl clear = new TabControl(tabPane, tabPane.getSelectionModel(), null, anchorid, null, null, snapshotStack, snapshotRedo);
        clear.clearCanvas();
        log("Clear Selected");
    }

    @FXML
    private void erase(ActionEvent event) {
        TabControl erase = new TabControl(tabPane, tabPane.getSelectionModel(), null, anchorid, eraseBtn, null, snapshotStack, snapshotRedo);
        erase.erase();
        log("Erase Selected");
    }

    @FXML
    private void redoBtn(ActionEvent event) {
        TabControl redo = new TabControl(tabPane, tabPane.getSelectionModel(), null, anchorid, eraseBtn, null, snapshotStack, snapshotRedo);
        redo.redo();
        log("Redo Selected");
    }

    @FXML
    private void undoBtn(ActionEvent event) {
        TabControl undo = new TabControl(tabPane, tabPane.getSelectionModel(), null, anchorid, eraseBtn, null, snapshotStack, snapshotRedo);
        undo.undo();
        log("Undo Selected");
    }
    @FXML
    private void txtBtn(ActionEvent event){
        TabControl txt = new TabControl(tabPane, tabPane.getSelectionModel(), null, anchorid, textButton, pencilColor, snapshotStack, snapshotRedo);
        txt.textPlace(textField);
        log("Text Selected");
    }
    @FXML
    private void SelectCanvas(ActionEvent event){
        TabControl cut = new TabControl(tabPane, tabPane.getSelectionModel(), null, anchorid, textButton, pencilColor, snapshotStack, snapshotRedo);
        cut.cutAction(selectButton, snapshotCopy);
        log("Cut Selected");
    }
    @FXML
    private void CopyPress(ActionEvent event){
        TabControl copy = new TabControl(tabPane, tabPane.getSelectionModel(), null, anchorid, textButton, pencilColor, snapshotStack, snapshotRedo);
        copy.copyAction(copyBtn, snapshotCopy);
        log("Copy Selected");
    }
    @FXML
    private void PastePress(ActionEvent event){
        TabControl paste = new TabControl(tabPane, tabPane.getSelectionModel(), null, anchorid, textButton, pencilColor, snapshotStack, snapshotRedo);
        paste.pasteAction(pasteBtn, snapshotCopy);
        log("Paste Selected");
    }
    @FXML
    public boolean TimerOn(ActionEvent event){
        showTimer = true;
        return true;
    }
    @FXML
    public boolean TimerOff(ActionEvent event){
        showTimer = false;
        return false;
    }
    @FXML
    private void VerticalFlip(ActionEvent event){
        TabControl vert = new TabControl(tabPane, tabPane.getSelectionModel(), null, anchorid, null, null, snapshotStack, snapshotRedo);
        vert.VertFlip();
        log("Canvas Vertically Flipped");
    }
    @FXML
    private void HorizontalFlip(ActionEvent event){
        TabControl horz = new TabControl(tabPane, tabPane.getSelectionModel(), null, anchorid, null, null, snapshotStack, snapshotRedo);
        horz.HorFlip();
        log("Canvas Horizontally Flipped");
    }
    @FXML
    private void RotateCanvas(ActionEvent event){
        TabControl rotate = new TabControl(tabPane, tabPane.getSelectionModel(), null, anchorid, null, null, snapshotStack, snapshotRedo);
        rotate.RotateCan();
        log("Canvas Rotated");
    }
    @FXML
    private void SelectRotate(ActionEvent event){
        TabControl rotate = new TabControl(tabPane, tabPane.getSelectionModel(), null, anchorid, rotBtn, null, snapshotStack, snapshotRedo);
        rotate.rotPaste(rotBtn, snapshotCopy, rotDeg);
        log("SelectRotate was selected");
    }
    private void log(String content) {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Format the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        //logs the time and action
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write("\n" + formattedDateTime + " " + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showNotification() {
        Notifications.create()
                .title("Save")
                .text("Image Saved.")
                .darkStyle()
                .show();
    }
}