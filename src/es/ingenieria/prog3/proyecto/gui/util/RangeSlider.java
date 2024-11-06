package es.ingenieria.prog3.proyecto.gui.util;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//Esta clase ha sido generada con CHATGPT
//Ha sido modificada para cambiar el color y la forma de los circulos
//de las esquinas del slider, asi como el color de la linea que 
//une el trazado seleccionado
public class RangeSlider extends JSlider {
 
	private static final long serialVersionUID = 1L;
	
	private int minRange;
    private int maxRange;

    private boolean draggingMinThumb = false;
    private boolean draggingMaxThumb = false;

    public RangeSlider(int min, int max) {
        super(min, max);
        minRange = min;
        maxRange = max;

        setMinimum(min);
        setMaximum(max);

        // Set the custom UI
        setUI(new RangeSliderUI(this));

        // Add mouse listeners for interaction
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int minThumbPos = getXPositionForValue(minRange);
                int maxThumbPos = getXPositionForValue(maxRange);

                // Determine which thumb is closer to the mouse click
                if (Math.abs(mouseX - minThumbPos) < Math.abs(mouseX - maxThumbPos)) {
                    draggingMinThumb = true;
                } else {
                    draggingMaxThumb = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                draggingMinThumb = false;
                draggingMaxThumb = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int mouseX = e.getX();
                int value = getValueForXPosition(mouseX);

                if (draggingMinThumb && value < maxRange) {
                    minRange = Math.min(value, maxRange - 1);  // Ensure min < max
                } else if (draggingMaxThumb && value > minRange) {
                    maxRange = Math.max(value, minRange + 1);  // Ensure max > min
                }

                repaint();
            }
        });
    }
    

    private int getXPositionForValue(int value) {
        RangeSliderUI ui = (RangeSliderUI) getUI();  // Cast the UI to RangeSliderUI
        return ui.xPositionForValue(value);
    }

    private int getValueForXPosition(int x) {
        RangeSliderUI ui = (RangeSliderUI) getUI();  // Cast the UI to RangeSliderUI
        return ui.valueForXPosition(x);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int minThumbPos = getXPositionForValue(minRange);
        int maxThumbPos = getXPositionForValue(maxRange);

        // Draw the range track
        g2.setColor(Color.GRAY);
        g2.fillRect(0, getHeight() / 2 - 2, getWidth(), 4); // Whole track

        // Highlight the selected range
        g2.setColor(Color.MAGENTA);
        g2.fillRect(minThumbPos, getHeight() / 2 - 2, maxThumbPos - minThumbPos, 4);

        // Draw the thumbs
        drawThumb(g2, minThumbPos, Color.BLACK);
        drawThumb(g2, maxThumbPos, Color.YELLOW);
    }

    private void drawThumb(Graphics2D g2, int x, Color color) {
        int thumbSize = 10;
        g2.setColor(color);
        g2.fillOval(x - thumbSize / 2, getHeight() / 2 - thumbSize / 2, thumbSize, thumbSize);
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }
}
