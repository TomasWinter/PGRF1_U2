package controller;

import adapters.ControllerAdapter;
import fill.ScanlineFiller;
import fill.SeedFiller;
import model.Line;
import model.Point2D;
import model.Polygon;
import view.Panel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collection;

public class FillController2D extends ControllerAdapter {
    private boolean enabled = true;
    private final Runnable drawScene;
    private final Collection<Polygon> polygonList;
    private final Panel panel;

    private final SeedFiller seedFill;
    private final ScanlineFiller scanFill;

    public FillController2D(Panel panel, Collection<Polygon> polygonList, Runnable drawScene) {
        this.polygonList = polygonList;
        this.drawScene = drawScene;
        this.panel = panel;

        seedFill = new SeedFiller(panel.getRaster());
        scanFill = new ScanlineFiller(panel.getRaster());

        InitListeners(panel);
    }

    public void clear()
    {
        seedFill.setSeed(null);
        seedFill.clearFill();
    }

    public void fillRaster()
    {
        seedFill.paint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!enabled) return;
        if (e.getButton() != MouseEvent.BUTTON2) return;

        seedFill.setSeed(new Point2D(e.getX(),e.getY()));
        seedFill.fill();
        drawScene.run();
    }
}
