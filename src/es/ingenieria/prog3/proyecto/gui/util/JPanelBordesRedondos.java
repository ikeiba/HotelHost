package es.ingenieria.prog3.proyecto.gui.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

//Esta clase ha sido generada por CHATGPT
@SuppressWarnings("serial")
public class JPanelBordesRedondos extends JPanel {
    private int cornerRadius;

    public JPanelBordesRedondos(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setOpaque(false); // Make the panel transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a rounded rectangle shape
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        
        // Set the background color
        g2d.setColor(getBackground());
        g2d.fill(roundedRectangle);
        
        // Dispose of the graphics context
        g2d.dispose();
    }
}
