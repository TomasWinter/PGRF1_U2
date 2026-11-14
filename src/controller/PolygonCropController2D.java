package controller;

import crop.PolygonCrop;
import model.Polygon;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collection;

public class PolygonCropController2D extends PolygonController2D {

    private Polygon cropVictim;
    private Polygon cropper;

    public PolygonCropController2D(JPanel panel, Collection<Polygon> polygonList, Runnable drawScene) {
        super(panel, polygonList, drawScene);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Ukončení editace polygonu
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            currentPolygon = null;
            if (cropVictim != null && cropper != null)
            {
                PolygonCrop.cropPolygon(cropVictim, cropper);
                cropVictim.setEdgeColor(0xffffff);
                polygonList.remove(cropper);
                cropVictim = null;
                cropper = null;
                drawScene.run();
            }
        }
    }

    @Override
    protected void createPolygon() {
        super.createPolygon();
        if (cropVictim == null)
        {
            cropVictim = currentPolygon;
            cropVictim.setEdgeColor(0x008080);
            cropVictim.setFilled(false);
        }
        else if (cropper == null)
        {
            cropper = currentPolygon;
            cropper.setEdgeColor(0xFF0000);
            cropper.setFilled(false);
        }
    }
}
