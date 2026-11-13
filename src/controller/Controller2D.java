package controller;

import fill.ScanlineFiller;
import fill.SeedFiller;
import model.Line;
import model.Point2D;
import model.Polygon;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerTrivial;
import rasterize.PolygonRasterizer;
import view.Panel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Controller2D {
    private final Panel panel;

    public enum Modes {
        LINE, POLYGON
    }

    // Rasterizers
    private final LineRasterizer lineRasterizer;
    private final PolygonRasterizer polygonRasterizer;
    // Controllers
    final LineController2D lineController;
    final PolygonController2D polygonController;
    final EditController2D editController;
    final FillController2D fillController;

    private final List<Polygon> polygonList = new ArrayList<>();
    private final List<Line> lineList = new ArrayList<>();

    //Editing
    private Modes currentMode = Modes.LINE;

    public Controller2D(Panel panel) {
        this.panel = panel;

        // Vytvoření controllerů
        lineController = new LineController2D(panel,lineList,this::drawScene);
        polygonController = new PolygonController2D(panel,polygonList,this::drawScene);
        editController = new EditController2D(panel,lineList,polygonList,this,this::drawScene);
        fillController = new FillController2D(panel,polygonList,this::drawScene);
        lineController.setEnabled(true);

        lineRasterizer = new LineRasterizerTrivial(panel.getRaster());
        polygonRasterizer = new PolygonRasterizer(lineRasterizer);

        initListeners();
    }

    private void initListeners() {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Klávesa V změní jaký tvar se vytváří
                if (e.getKeyCode() == KeyEvent.VK_V) {
                    if (currentMode == Modes.LINE)
                    {
                        lineController.setEnabled(false);
                        polygonController.setEnabled(true);
                        currentMode = Modes.POLYGON;
                    }
                    else
                    {
                        polygonController.setEnabled(false);
                        lineController.setEnabled(true);
                        currentMode = Modes.LINE;
                    }
                    System.out.println("Switched editing to " + currentMode);
                }
            }
        });
    }

    private void drawScene() {
        panel.getRaster().clear();

        for (Line l : lineList)
            lineRasterizer.rasterize(l);
        for (Polygon p : polygonList)
            polygonRasterizer.rasterize(p);

        fillController.fillRaster();

        panel.repaint();
    }
}
