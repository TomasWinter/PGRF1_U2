package controller;

import adapters.ControllerAdapter;
import model.Point2D;
import model.Polygon;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

// Tato třída je kontroler sloužící pro vytváření polygonu
public class PolygonController2D extends ControllerAdapter {
    private boolean enabled = false;
    private final Runnable drawScene;
    private final Collection<Polygon> polygonList;

    private Polygon currentPolygon;
    private Point2D dragPoint;

    public PolygonController2D(JPanel panel, Collection<Polygon> polygonList, Runnable drawScene) {
        this.drawScene = drawScene;
        this.polygonList = polygonList;
        InitListeners(panel);
    }

    // Metoda pro přidání bodu do polygonu
    @Override
    public void mousePressed(MouseEvent e) {
        if (!enabled) return;
        if (e.getButton() != MouseEvent.BUTTON1) return;

        if (currentPolygon == null) // Vytvoření nového polygonu
        {
            currentPolygon = new Polygon();
            polygonList.add(currentPolygon);
        }

        Point2D point = new Point2D(e.getX(), e.getY());

        if (currentPolygon.getSize() > 2) // když má polygon alespoň 3 body, následující bod se přidá na existující hranu
        {
            int index = currentPolygon.getClosestEdge(e.getX(), e.getY());
            if (index > -1) {
                currentPolygon.addPoint(point, index + 1);
                dragPoint = point;
            }
        }
        else // Jinak se bod přidá na konec
        {
            currentPolygon.addPoint(point, currentPolygon.getSize());
            dragPoint = point;
        }

        drawScene.run();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Ukončení editace polygonu
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            currentPolygon = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!enabled) return;
        if (dragPoint == null) return;
        // Posunutí vytvořeného bodu
        dragPoint.set(e.getX(),e.getY());
        drawScene.run();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragPoint = null;
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        dragPoint = null;
        this.enabled = enabled;
    }
}
