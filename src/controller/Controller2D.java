package controller;

import model.Line;
import model.Polygon;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerTrivial;
import rasterize.PolygonRasterizer;
import view.Panel;
import view.Window;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Controller2D {
    private final Panel panel;
    private final JLabel label;

    public enum Modes {
        LINE, POLYGON, LINE_CROP, POLYGON_CROP
    }

    // Rasterizers
    private final LineRasterizer lineRasterizer;
    private final PolygonRasterizer polygonRasterizer;
    // Controllers
    final LineController2D lineController;
    final PolygonController2D polygonController;
    final EditController2D editController;
    final FillController2D fillController;
    final LineCropController2D lineCropController;
    final PolygonCropController2D polygonCropController;

    private final List<Polygon> polygonList = new ArrayList<>();
    private final List<Line> lineList = new ArrayList<>();

    //Editing
    private Modes currentMode = Modes.LINE;

    public Controller2D(Window window) {
        this.panel = window.getPanel();
        this.label = window.getEditModeLabel();

        // Vytvoření controllerů
        lineController = new LineController2D(panel, lineList, this::drawScene);
        polygonController = new PolygonController2D(panel, polygonList, this::drawScene);
        editController = new EditController2D(panel, lineList, polygonList, this, this::drawScene);
        fillController = new FillController2D(panel, polygonList, this::drawScene);
        lineCropController = new LineCropController2D(panel, panel.getRaster(), lineList, this::drawScene);
        polygonCropController = new PolygonCropController2D(panel, polygonList, this::drawScene);
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
                    if (currentMode == Modes.LINE) // POLYGON MODE
                    {
                        lineController.setEnabled(false);
                        polygonController.setEnabled(true);
                        editController.setEnabled(true);
                        lineCropController.setEnabled(false);
                        polygonCropController.setEnabled(false);
                        currentMode = Modes.POLYGON;
                    } else if (currentMode == Modes.POLYGON) // LINE CROP MODE
                    {
                        lineController.setEnabled(false);
                        polygonController.setEnabled(false);
                        editController.setEnabled(false);
                        lineCropController.setEnabled(true);
                        polygonCropController.setEnabled(false);
                        currentMode = Modes.LINE_CROP;
                    } else if (currentMode == Modes.LINE_CROP) // POLYGON CROP MODE
                    {
                        lineController.setEnabled(false);
                        polygonController.setEnabled(false);
                        editController.setEnabled(true);
                        lineCropController.setEnabled(false);
                        polygonCropController.setEnabled(true);
                        currentMode = Modes.POLYGON_CROP;
                    } else if (currentMode == Modes.POLYGON_CROP) // LINE MODE
                    {
                        lineController.setEnabled(true);
                        polygonController.setEnabled(false);
                        editController.setEnabled(true);
                        lineCropController.setEnabled(false);
                        polygonCropController.setEnabled(false);
                        currentMode = Modes.LINE;
                    }
                    drawScene();
                    System.out.println("Switched editing to " + currentMode);
                    label.setText("Edit mode: " + currentMode);
                }
            }
        });
    }

    private void drawScene() {
        panel.getRaster().clear();

        fillController.fillRaster();

        for (Line l : lineList)
            lineRasterizer.rasterize(l);
        for (Polygon p : polygonList)
            polygonRasterizer.rasterize(p);

        if (currentMode == Modes.LINE_CROP)
            lineCropController.renderCropData();

        panel.repaint();
    }
}
