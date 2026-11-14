package view;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private final Panel panel;
    private final JLabel editModeLabel;

    public Window(int width, int height) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("PGRF1 2025/2026");
        setVisible(true);

        editModeLabel = new JLabel("Edit mode: LINE");
        add(editModeLabel, BorderLayout.NORTH);

        panel = new Panel(width, height);
        add(panel, BorderLayout.CENTER);
        pack();

        panel.setFocusable(true);
        panel.grabFocus();
    }

    public Panel getPanel() {
        return panel;
    }

    public JLabel getEditModeLabel() {
        return editModeLabel;
    }
}
