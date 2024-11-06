package es.ingenieria.prog3.proyecto.gui.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

// IAG: CHATGPT (Toda la clase)
// Modificación: Si
@SuppressWarnings("serial")
public class JPanelBordesRedondos extends JPanel {
    private int cornerRadius;

    public JPanelBordesRedondos(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setOpaque(false); // Make the panel transparent to enable custom painting
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Cast Graphics to Graphics2D and enable anti-aliasing for smooth corners
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a rounded rectangle shape to clip and fill
        Shape roundedRectangle = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        
        // Fill the background with the rounded rectangle shape
        g2d.setColor(getBackground());
        g2d.fill(roundedRectangle);

        // Clip graphics to the rounded rectangle to restrict drawing area
        g2d.setClip(roundedRectangle);
        
        // Dispose of the graphics context
        g2d.dispose();
    }

    @Override
    protected void paintChildren(Graphics g) {
        // Create a rounded rectangle shape for clipping
        Shape clipShape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Cast Graphics to Graphics2D and set the clip to the rounded rectangle
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setClip(clipShape);

        // Paint children within the clipped area
        super.paintChildren(g2d);

        // Dispose of the graphics context
        g2d.dispose();
    }
}