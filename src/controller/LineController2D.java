package controller;

import adapters.ControllerAdapter;
import model.Line;
import model.Point2D;
import raster.Raster;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

// Tato třída je kontroler co slouží pro vytváření čar
public class LineController2D extends ControllerAdapter {
    private boolean enabled = false;
    private final Runnable drawScene;
    private final Collection<Line> lineList;

    private boolean holdingShift = false;
    private Line currentLine = null;
    private Point2D focusPoint = null;
    private Point2D secondaryPoint = null;

    public LineController2D(JPanel panel, Collection<Line> lineList, Runnable drawScene) {
        this.lineList = lineList;
        this.drawScene = drawScene;
        InitListeners(panel);
    }

    // Metoda pro založení nové čáry
    @Override
    public void mousePressed(MouseEvent e) {
        if (!enabled) return;
        if (e.getButton() != MouseEvent.BUTTON1) return;

        Line newLine = new Line();
        int startX = e.getX();
        int startY = e.getY();
        int endX = e.getX();
        int endY = e.getY();
        newLine.setStart(startX, startY);
        newLine.setEnd(endX, endY);
        lineList.add(newLine);

        currentLine = newLine;
        focusPoint = newLine.getEnd();
        secondaryPoint = newLine.getStart();

        drawScene.run();
    }

    // Posouvání koncového bodu čáry
    @Override
    public void mouseDragged(MouseEvent e) {
        if (!enabled) return;
        if (focusPoint == null) return;

        if (holdingShift) // Snap
        {
            double angle = (secondaryPoint.getY() - e.getY()) / (double)(secondaryPoint.getX() - e.getX());
            if (Math.abs(angle) > 1.3) // Vertikální čára
                focusPoint.set(secondaryPoint.getX(),e.getY());
            else if (Math.abs(angle) < 0.7) // Horizontální čára
                focusPoint.set(e.getX(),secondaryPoint.getY());
            else // Diagonální čara
            {
                int diffX = (secondaryPoint.getX() - e.getX()) * -(int)Math.signum(angle);
                int diffY = (secondaryPoint.getY() - e.getY()) * -(int)Math.signum(angle);

                if (Math.abs(diffX) > Math.abs(diffY))
                    focusPoint.set(e.getX(),secondaryPoint.getY()+diffX);
                else
                    focusPoint.set(secondaryPoint.getX()+diffY,e.getY());
            }
        }
        else // Normální režim
        {
            focusPoint.set(e.getX(),e.getY());
        }
        drawScene.run();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentLine = null;
        focusPoint = null;
        secondaryPoint = null;
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        currentLine = null;
        focusPoint = null;
        secondaryPoint = null;
        this.enabled = enabled;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            holdingShift = true;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            holdingShift = false;
    }
}
