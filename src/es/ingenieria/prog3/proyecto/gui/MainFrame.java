package es.ingenieria.prog3.proyecto.gui;


import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	   
	private static final long serialVersionUID = 1L;
	
	private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        // Set up the main frame properties
        setTitle("Hotel Host");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(960, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        //Inicializar los paneles
        


        // Add the main panel to the frame
        this.add(mainPanel);

        // Set frame visible
        setVisible(true);
        }
}

