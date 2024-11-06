package es.ingenieria.prog3.proyecto.gui.util;

import javax.swing.*;
import java.awt.*;

// IAG: CHATGPT (Toda la clase)
// Modificación: Si
@SuppressWarnings("serial")
public class JSeparatorPunteada extends JSeparator {

    public JSeparatorPunteada() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // Set stroke to create long dots with spaces in between
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, new float[]{(float) (Preferences.WINDOWWIDTH * 0.01), (float) (Preferences.WINDOWWIDTH * 0.01)}, 0.0f));
        g2d.setColor(getForeground());
        
        // Draw a dotted line
        if (getOrientation() == JSeparator.HORIZONTAL) {
            g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        } else {
            g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        }
    }
}