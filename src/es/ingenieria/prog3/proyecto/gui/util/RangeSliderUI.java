package es.ingenieria.prog3.proyecto.gui.util;
import java.awt.Graphics;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;

//Esta clase ha sido generada por CHATGPT
public class RangeSliderUI extends BasicSliderUI {

    public RangeSliderUI(JSlider slider) {
        super(slider);
    }

    // Expose xPositionForValue as a public method
    @Override
    public int xPositionForValue(int value) {
        return super.xPositionForValue(value);
    }

    // Expose valueForXPosition as a public method
    @Override
    public int valueForXPosition(int x) {
        return super.valueForXPosition(x);
    }

    // Override paintThumb to prevent the default thumb from being drawn
    @Override
    public void paintThumb(Graphics g) {
        // Intentionally empty to prevent default thumb rendering
    }
}
