package es.ingenieria.prog3.proyecto.gui.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

@SuppressWarnings("serial")
public class JPasswordFieldDefaultText extends JPasswordField {
    @SuppressWarnings("unused")
	private String placeholder;
    @SuppressWarnings("unused")
	private JCheckBox checkBox;
    boolean selected = true;

    public JPasswordFieldDefaultText(String placeholder, JCheckBox checkBox) {
        super(placeholder);
        this.placeholder = placeholder;
        setFont(Preferences.FONTPLAIN);
        setForeground(Color.GRAY);
        setEchoChar((char) 0);
        
        checkBox.addActionListener(e -> {
        	if (checkBox.isSelected()) {selected = true;}
        	else {selected = true;}
        });

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the placeholder text when the field gains focus
                if (getText().equals(placeholder)) {
                    setText("");
                    if (selected == true) {setEchoChar('\u2022');}
                    else {setEchoChar((char) 0);}
                    setForeground(Color.BLACK); // Change color to indicate input mode
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restore the placeholder text if the field is empty
                if (getText().isEmpty()) {
                	setEchoChar((char) 0);
                    setText(placeholder);
                    setForeground(Color.GRAY); // Reset color to placeholder mode
                } else if (!getText().isEmpty() & selected == true) {setEchoChar('\u2022');}
                  else {setEchoChar((char) 0);}
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