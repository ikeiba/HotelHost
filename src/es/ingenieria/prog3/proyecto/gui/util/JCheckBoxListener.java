package es.ingenieria.prog3.proyecto.gui.util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JCheckBoxListener implements ActionListener {
    private final JCheckBox[] checkboxes;

    public JCheckBoxListener(JCheckBox... checkboxes) {
        this.checkboxes = checkboxes;
        addListeners();  // Add listeners upon initialization
    }

    // Method to add action listeners to the checkboxes
    private void addListeners() {
        for (JCheckBox checkbox : checkboxes) {
        	checkbox.setFocusable(false);
            checkbox.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBox source = (JCheckBox) e.getSource();
        if (source.isSelected()) {
            for (JCheckBox checkbox : checkboxes) {
                if (checkbox != source) {
                    checkbox.setSelected(false);
                }
            }
        }
    }
    
    // Method to get the index of the currently selected checkbox
    public int getSelectedCheckboxIndex() {
        for (int i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].isSelected()) {
                return i;  // Return the index of the selected checkbox
            }
        }
        return -1;  // Return -1 if no checkbox is selected
    }
}

//IAG: CHATGPT (Toda la clase)
//Modificación: Si