package controller;

import fill.Pattern;
import fill.SeedFiller;
import model.Point2D;
import model.Polygon;
import view.Panel;

import java.awt.event.MouseEvent;
import java.util.Collection;

// kontroler zodpovědný za funkčnost seed fillu
public class FillController2D extends ControllerAdapter {
    private boolean enabled = true;
    private final Runnable drawScene;

    private final SeedFiller seedFill;

    public FillController2D(Panel panel, Collection<Polygon> polygonList, Runnable drawScene) {
        this.drawScene = drawScene;

        seedFill = new SeedFiller(panel.getRaster());

        seedFill.changePattern(Pattern.getGreenFace());

        InitListeners(panel);
    }

    public void clear() // vymazání seed fillem vyplněného rastru
    {
        seedFill.setSeed(null);
        seedFill.clearFill();
    }

    public void fillRaster() // vyplnění rastru seed fillem
    {
        seedFill.paint();
    }

    @Override
    public void mousePressed(MouseEvent e) { // Kolečkem myši se spustí seed fill
        if (!enabled) return;
        if (e.getButton() != MouseEvent.BUTTON2) return;

        seedFill.setSeed(new Point2D(e.getX(),e.getY()));
        seedFill.fill();
        drawScene.run();
    }
}
