package controller;

import adapters.ControllerAdapter;
import model.Line;
import model.Point2D;
import model.Polygon;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collection;

// Tato třída je kontroler který upravuje tvary (přesunití, vymazání)
public class EditController2D extends ControllerAdapter {
    private boolean enabled = true;
    private final Runnable drawScene;
    private final Collection<Line> lineList;
    private final Collection<Polygon> polygonList;
    private final Controller2D parentController;

    private Point2D currentPoint = null;
    private Polygon currentEditPolygon = null;
    private Line currentEditLine = null;

    public EditController2D(JPanel panel, Collection<Line> lineList, Collection<Polygon> polygonList,Controller2D parentController ,Runnable drawScene) {
        this.lineList = lineList;
        this.polygonList = polygonList;
        this.drawScene = drawScene;
        this.parentController = parentController;
        InitListeners(panel);
    }



    @Override
    public void mousePressed(MouseEvent e){
        if (!enabled) return;
        if (e.getButton() != MouseEvent.BUTTON3) return;
        // Kliknutím pravým tlačítkem se označí nejbližší bod v určitém dosahu pro editaci
        GetClosestPoint(e.getX(),e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Přesunutí bodu
        if (currentPoint != null) {
            currentPoint.set(e.getX(), e.getY());
            drawScene.run();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentPoint = null;
        currentEditPolygon = null;
        currentEditLine = null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C) // Smazání aktivních prvků
        {
            lineList.clear();
            polygonList.clear();
            parentController.fillController.clear();
            drawScene.run();
        }
        else if (e.getKeyCode() == KeyEvent.VK_DELETE) // Klávesou DELETE se smaže editovaný bod (Pouze pro polygon)
        {
            if (currentPoint == null || currentEditPolygon == null) return;
            currentEditPolygon.removePoint(currentPoint);
            currentPoint = null;
            currentEditPolygon = null;
            drawScene.run();
        }
    }

    // Funkce najde nejbližší bod ze všech tvarů
    private void GetClosestPoint(int x,int y) {
        Point2D closestPoint = null;
        double closestPointDistance = Double.POSITIVE_INFINITY;

        for (Line l : lineList) {
            // Vzdálenost nejbližšího bodu úsečky se porovná s momentálně nejbližšim bodem
            Point2D point = l.getClosestPoint(x, y);
            double magnitude = Point2D.getMagnitudeBetween(point.getX(), point.getY(), x, y);
            if (magnitude <= closestPointDistance) {
                closestPointDistance = magnitude;
                closestPoint = point;
                currentEditLine = l;
            }
        }

        for (Polygon p : polygonList) {
            // Vzdálenost nejbližšího bodu polygonu se porovná s momentálně nejbližšim bodem
            Point2D point = p.getClosestPoint(x, y);
            double magnitude = Point2D.getMagnitudeBetween(point.getX(), point.getY(), x, y);
            if (magnitude <= closestPointDistance) {
                closestPointDistance = magnitude;
                closestPoint = point;
                currentEditPolygon = p; // nejbližší polygon se přidá do currentEditPolygon, aby se z něj mohli smazat body, pokud uživatel zmáčkne delete
                currentEditLine = null;
            }
        }

        if (closestPointDistance > 5) {
            currentEditPolygon = null;
            currentEditLine = null;
            currentPoint = null;
        }
        else
            currentPoint = closestPoint;
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
