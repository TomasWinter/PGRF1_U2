package controller;

import crop.LineCrop;
import model.Line;
import model.Rectangle;
import raster.Raster;
import rasterize.RectangleRasterizer;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collection;

// třída pro ovládání ořezávání
public class LineCropController2D extends ControllerAdapter {
    private boolean enabled = false;
    private final Runnable drawScene;
    private final Collection<Line> lineList;

    // ořez čar
    private final RectangleRasterizer rectangleRasterizer;
    private boolean isDraggingArea = false;
    private Rectangle cropArea;

    public LineCropController2D(JPanel panel, Raster raster, Collection<Line> lineList, Runnable drawScene) {
        this.lineList = lineList;
        this.drawScene = drawScene;
        this.rectangleRasterizer = new RectangleRasterizer(raster);
        rectangleRasterizer.setLineColor(0xff0000);

        InitListeners(panel);
    }

    @Override
    public void mousePressed(MouseEvent e) { // začátek vytváření ořezávacího obdélníku
        if (!enabled) return;
        if (e.getButton() != MouseEvent.BUTTON1) return;

        isDraggingArea = true;
        cropArea = new Rectangle(e.getX(), e.getY(), e.getX(), e.getY());
        drawScene.run();
    }

    @Override
    public void mouseDragged(MouseEvent e) { // změna velikosti ořezávacího obdélníku
        if (!enabled) return;
        if (!isDraggingArea) return;

        cropArea.setX2(e.getX());
        cropArea.setY2(e.getY());

        drawScene.run();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isDraggingArea = false;
    }

    @Override
    public void keyPressed(KeyEvent e) { // po stisku enter se oříznou všechny čáry na plátně
        if (!enabled) return;
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            lineList.removeIf(line -> LineCrop.CropLine(line, cropArea));
            drawScene.run();
        }
    }

    public void renderCropData() // ořezávací obdélník se renderuje zvlášť od ostatních tvarů
    {
        rectangleRasterizer.rasterize(cropArea);
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
