package com.example.cs250;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

import java.util.Stack;

public class TabControl extends Controller {
    private Canvas myCanvas;

    public TabControl(){
    }
    public TabControl(TabPane tabPane, SingleSelectionModel<Tab> selectionModel, Slider mySlide, AnchorPane anchorid, ToggleButton toggleButton, ColorPicker colorPicker, Stack<WritableImage> snapshotStack, Stack<WritableImage> snapshotRedo){
        super();
        this.anchorid = anchorid;
        this.mySlide = mySlide;
        this.tabPane = tabPane;
        this.selectionModel = selectionModel;
        this.lineBtn = toggleButton;
        this.pencilColor = colorPicker;
        this.snapshotStack = snapshotStack;
        this.snapshotRedo = snapshotRedo;
    }
    public void initialize(){
        addTab();//adds first tab on start
    }
    public void addTab() {
        //gets num of tabs and makes the next one
        int numTabs = tabPane.getTabs().size();
        Tab tab = new Tab("Tab "+(numTabs+1));
        setTab(tab);
        tabPane.getTabs().add(tab);

        //gets current anchorpane/canvas
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);

        //sets backround
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE); //whites over canvas
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    public void closeTab(){
        //gets current tab and closes it
        selectionModel = tabPane.getSelectionModel();
        Tab tab = selectionModel.getSelectedItem();
        tabPane.getTabs().remove(tab);
    }
    private void setTab(Tab tab){
        //sets up anchor pane and canvas
        int numTabs = tabPane.getTabs().size();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize((anchorid.getPrefWidth() - 108), anchorid.getPrefHeight());
        Canvas canvas = new Canvas(anchorPane.getPrefWidth(), anchorPane.getPrefHeight());

        //set id's
        anchorPane.setId("ap" + (numTabs+1));
        canvas.setId("can" + (numTabs+1));

        //adding cnavas to pane and pane to tab
        anchorPane.getChildren().add(canvas);
        tab.setContent(anchorPane);
    }
    public void openTabCanvas(){
        //getting current tab's stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);

        //setting canvas
        AnchorPane.setRightAnchor(canvas, 0.0);
        AnchorPane.setTopAnchor(canvas, 0.0);

        //opens picture
        MenuBarActions open = new MenuBarActions(canvas, anchorPane);
        open.open();

        //resets canvas size
        canvas.setWidth(anchorPane.getWidth());
        canvas.setHeight(anchorPane.getHeight());
    }
    public void saveCan(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);

        //saves canvas
        MenuBarActions open = new MenuBarActions(canvas, anchorPane);
        open.save();
    }
    public void saveAsCan(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);

        //save as canvas
        MenuBarActions open = new MenuBarActions(canvas, anchorPane);
        open.saveAs();
    }
    public void drawTab(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);

        //calls draw function
        PencilDraw draw = new PencilDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        draw.draw();
    }
    public void drawTab1(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);

        //calls dashed draw function
        PencilDraw draw = new PencilDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        draw.drawDash();
    }
    public void lineTab(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws straight line
        LineDraw lineDraw = new LineDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        lineDraw.line();
    }
    public void lineTab1(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws curved line
        LineDraw lineDraw = new LineDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        lineDraw.lineDash();

    }
    public void sqrTab(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws square
        SquareDraw squareDraw = new SquareDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        squareDraw.square();
    }
    public void sqrTab1(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws dashed square
        SquareDraw squareDashed = new SquareDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        squareDashed.squareDash();
    }
    public void rectTab(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws rectangle
        RectangleDraw rectangleDraw = new RectangleDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        rectangleDraw.rectangle();
    }
    public void rectTab1(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws dashed rectangle
        RectangleDraw rectangleDraw = new RectangleDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        rectangleDraw.rectangleDraw();
    }
    public void ellTab(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws ellipse
        EllipseDraw ellipseDraw = new EllipseDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        ellipseDraw.ellipse();
    }
    public void ellTab1(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws dashed elipse
        EllipseDraw ellipseDraw = new EllipseDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        ellipseDraw.ellipseDash();
    }
    public void circleTab(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws circle
        CircleDraw circleDraw = new CircleDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        circleDraw.circle();
    }
    public void circleTab1(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws dashed circle
        CircleDraw circleDraw = new CircleDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        circleDraw.circleDash();
    }
    public void triTab(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws triangle
        TriangleDraw triangleDraw = new TriangleDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        triangleDraw.triangle();
    }
    public void triTab1(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws dashed triangle
        TriangleDraw triangleDraw = new TriangleDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        triangleDraw.triangleDash();
    }
    public void diamondTab(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws rounded square
        DiamondDraw diamondDraw = new DiamondDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        diamondDraw.diamond();
    }
    public void diamondTab1(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws dashed rounded square
        DiamondDraw diamondDraw = new DiamondDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo);
        diamondDraw.diamondDash();
    }
    public void polygon(TextField textField){
        //gets number from textfield
        this.numField = textField;
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws polygon
        PolyDraw poly = new PolyDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo, textField);
        poly.polygon();
    }
    public void polygonDash(TextField textField){
        //gets number from textfield
        this.numField = textField;
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //draws dashed polygon
        PolyDraw poly = new PolyDraw(canvas, pencilColor, mySlide, lineBtn, snapshotStack, snapshotRedo, textField);
        poly.polygonDash();
    }
    public void textPlace(TextField textField){
        //gets text
        this.textField = textField;
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //places text
        TextPlace text = new TextPlace(canvas, pencilColor, lineBtn, snapshotStack, snapshotRedo, textField);
        text.text();
    }
    public void erase(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //erases
        Erase erase = new Erase(canvas, pencilColor, mySlide, lineBtn);
        erase.erase();
    }

    //gets child from anchorpane
    public Canvas getChildCanvasFromAnchorPane(AnchorPane anchorPane) {
        for (Node node : anchorPane.getChildren()) {
            if (node instanceof Canvas) {
                return (Canvas) node; // Return the first Canvas found
            }
        }

        return null; // Return null if no Canvas was found
    }
    public void getColors(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);

        canvas.setOnMousePressed(e->{
            double x = e.getX();
            double y = e.getY();
            WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
            // Get the color of the pixel at the mouse click position from the image
            javafx.scene.paint.Color pixelColor = image.getPixelReader().getColor((int) x, (int) y);

            pencilColor.setValue(pixelColor);
        });
    }
    public void clearCanvas(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //makes pop up and clears canvas
        PopUps clearCanvas = new PopUps(canvas, null);
        clearCanvas.clear();
    }
    public void checkSave(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //checks save
        PopUps checkClear = new PopUps(canvas, anchorPane);
        checkClear.checkSave();
    }
    public void redo(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //puts back snapshot
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (!snapshotRedo.isEmpty()){
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.drawImage(snapshotRedo.peek(), 0, 0);
        }
    }
    public void undo(){
        //gets current tab stuff
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //undos canvas store snapshot in stakc
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (!snapshotStack.isEmpty()){
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.drawImage(snapshotStack.peek(), 0, 0);
        }
    }
    public void copyAction(ToggleButton button, Stack<WritableImage> snapshotC){
        //gets current tab stuff
        this.copyBtn = button;
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //copys
        CopyPaste copy = new CopyPaste(canvas, button, snapshotStack,snapshotRedo, snapshotC, null, null, null);
        copy.copy();
    }
    public void pasteAction(ToggleButton button, Stack<WritableImage> snapshotC){
        //gets current tab stuff
        this.pasteBtn = button;
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //pastes
        CopyPaste paste = new CopyPaste(canvas, null, snapshotStack,snapshotRedo, snapshotC, button, null, null);
        paste.paste();
    }
    public void cutAction(ToggleButton button, Stack<WritableImage> snapshotC){
        //gets current tab stuff
        this.selectButton = button;
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //cuts
        CopyPaste cut = new CopyPaste(canvas, null, snapshotStack,snapshotRedo, snapshotC, null, button, null);
        cut.cut();
    }

    public Canvas getMyCanvas() {
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        return getChildCanvasFromAnchorPane(anchorPane);
    }
    public AnchorPane getSelectedAnchorPane(){
        Tab tab = selectionModel.getSelectedItem();
        return (AnchorPane) tab.getContent();
    }
    public void RotateCan(){
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        snapshotStack.push(canvas.snapshot(null, null));
        Rotate rotate = new Rotate(90, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.getTransforms().add(rotate);
        snapshotRedo.push(canvas.snapshot(null, null));
        Rotate correct = new Rotate(-90, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.getTransforms().add(correct);
        redo();
    }
    public void HorFlip(){
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        snapshotStack.push(canvas.snapshot(null, null));
        Scale flipHorizontal = new Scale(-1, 1, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.getTransforms().add(flipHorizontal);
        snapshotRedo.push(canvas.snapshot(null, null));
    }
    public void VertFlip(){
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        snapshotStack.push(canvas.snapshot(null, null));
        Scale flipHorizontal = new Scale(1, -1, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.getTransforms().add(flipHorizontal);
        snapshotRedo.push(canvas.snapshot(null, null));
    }
    public void rotPaste(ToggleButton button, Stack<WritableImage> snapshotC, TextField textField){

        //gets current tab stuff
        this.rotBtn = button;
        this.rotDeg = textField;
        int num = Integer.parseInt(rotDeg.getText());
        Tab tab = selectionModel.getSelectedItem();
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        Canvas canvas = getChildCanvasFromAnchorPane(anchorPane);
        //pastes
        CopyPaste paste = new CopyPaste(canvas, null, snapshotStack,snapshotRedo, snapshotC, null, null, button);
        paste.pasteRot(num);
    }
    public void setMyCanvas(Canvas myCanvas) {
        this.myCanvas = myCanvas;
    }

}
