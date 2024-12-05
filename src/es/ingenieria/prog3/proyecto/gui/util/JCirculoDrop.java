package es.ingenieria.prog3.proyecto.gui.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JCirculoDrop extends JComponent {
    private final int diameter;
    private final JPopupMenu popupMenu;

    public JCirculoDrop(int diameter, String[] options) {
        this.diameter = diameter;
        this.popupMenu = createPopupMenu(options);

        // Mouse Listener for click detection
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isInsideCircle(e.getX(), e.getY())) {
                    popupMenu.show(JCirculoDrop.this, e.getX(), e.getY());
                }
            }
        });
    }

    // Method to check if a point is inside the circle
    private boolean isInsideCircle(int x, int y) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = diameter / 2;
        return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= Math.pow(radius, 2);
    }

    // Create the popup menu with options
    private JPopupMenu createPopupMenu(String[] options) {
        JPopupMenu menu = new JPopupMenu();

        for (String option : options) {
            JMenuItem menuItem = new JMenuItem(option);
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showDialog(option);
                }
            });
            menu.add(menuItem);
        }
        return menu;
    }

    // Show a dialog when an option is selected
    private void showDialog(String option) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;
        g2.setColor(Color.GRAY);
        g2.fillOval(x, y, diameter, diameter);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(diameter + 20, diameter + 20);
    }
}