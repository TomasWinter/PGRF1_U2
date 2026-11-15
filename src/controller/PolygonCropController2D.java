package controller;

import crop.PolygonCrop;
import model.Polygon;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collection;

// Kontroler pro ořezávání polygonu jiným polygonem
public class PolygonCropController2D extends PolygonController2D {

    private Polygon cropVictim;
    private Polygon cropper;

    public PolygonCropController2D(JPanel panel, Collection<Polygon> polygonList, Runnable drawScene) {
        super(panel, polygonList, drawScene);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Ukončení editace polygonu
        if (!enabled) return;
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            currentPolygon = null;
            if (cropVictim != null && cropper != null) // oříznutí polygonu při stisku enter
            {
                PolygonCrop.cropPolygon(cropVictim, cropper);
                cropVictim.setEdgeColor(0xffffff);
                polygonList.remove(cropper); // vymazání ořezávacího polygonu
                cropVictim = null;
                cropper = null;
                drawScene.run();
            }
        }
    }

    // přepsaná metoda createPolygon
    // originální metoda vytvoří nový polygon
    // přepsaná metoda si vytvořený polygon vezme a udělá si z něj ořezávací polygon
    @Override
    protected void createPolygon() {
        super.createPolygon();
        if (cropVictim == null) // nastavení polygonu na oříznutí
        {
            cropVictim = currentPolygon;
            cropVictim.setEdgeColor(0x008080);
            cropVictim.setFilled(false);
        } else if (cropper == null) // nastavení řezacího polygonu
        {
            cropper = currentPolygon;
            cropper.setEdgeColor(0xFF0000);
            cropper.setFilled(false);
        }
    }
}
