package es.ingenieria.prog3.proyecto.gui.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

//Esta clase ha sido generada por CHATGPT
@SuppressWarnings("serial")
public class JTextFieldDefaultText extends JTextField {
    @SuppressWarnings("unused")
	private String placeholder;

    public JTextFieldDefaultText(String placeholder) {
        super(placeholder);
        this.placeholder = placeholder;
        setFont(Preferences.FONTPLAIN);
        setForeground(Color.GRAY);
       

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the placeholder text when the field gains focus
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(Color.BLACK); // Change color to indicate input mode
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restore the placeholder text if the field is empty
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(Color.GRAY); // Reset color to placeholder mode
                }
            }
        });
    }

    // Method to change placeholder text if needed
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        setText(placeholder);
        setForeground(Color.GRAY);
    }
}