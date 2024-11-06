package es.ingenieria.prog3.proyecto.gui.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//IAG: CHATGPT (Toda la clase)
//Modificación: Si
@SuppressWarnings("serial")
public class JPasswordFieldDefaultText extends JPasswordField {
    @SuppressWarnings("unused")
    private String placeholder;
    private JCheckBox visibilityCheckBox;

    public JPasswordFieldDefaultText(String placeholder, JCheckBox checkBox) {
        super(placeholder);
        this.placeholder = placeholder;
        this.visibilityCheckBox = checkBox;
        setFont(Preferences.FONTPLAIN);
        setForeground(Color.GRAY);
        
        // Set initial visibility based on the checkbox state
        visibilityCheckBox.setSelected(true);
        setEchoChar((char) 0); // Initially show placeholder or text
        
        // Focus listener to handle placeholder display
        addFocusListener(new FocusListener() {
            @SuppressWarnings("deprecation")
			@Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setEchoChar(visibilityCheckBox.isSelected() ? '\u2022' : (char) 0);
                    setForeground(Color.BLACK); // Change color to indicate input mode
                }
            }

            @SuppressWarnings("deprecation")
			@Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setEchoChar((char) 0);
                    setText(placeholder);
                    setForeground(Color.GRAY); // Reset color to placeholder mode
                }
            }
        });

        // Action listener for the visibility toggle checkbox
        visibilityCheckBox.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
			@Override
            public void actionPerformed(ActionEvent e) {
                if (getText().equals(placeholder)) {
                    setEchoChar((char) 0); // Keep placeholder visible without hiding characters
                } else {
                    setEchoChar(visibilityCheckBox.isSelected() ? '\u2022' : (char) 0); // Hide or show entered text
                }
            }
        });
    }
}