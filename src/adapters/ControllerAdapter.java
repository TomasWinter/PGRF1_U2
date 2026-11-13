package adapters;

import javax.swing.*;
import java.awt.event.*;

// Tato třída implementuje 3 různé listener interfacy
// veškeré implamentace metod jsou kompletně prázdné
public abstract class ControllerAdapter implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {

    protected void InitListeners(JPanel panel)
    {
        panel.addKeyListener(this);
        panel.addMouseMotionListener(this);
        panel.addMouseListener(this);
        panel.addMouseWheelListener(this);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
