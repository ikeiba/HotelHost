package es.ingenieria.prog3.proyecto.gui;


import java.awt.CardLayout;

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
        // el 60% de la pantalla
        setSize(1152, 720); 
        setResizable(false);
        setLocationRelativeTo(null);

        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        //Inicializar los paneles
        mainPanel.add(new Log1(cardLayout, mainPanel), "Log1");
        mainPanel.add(new Log2(cardLayout, mainPanel), "Log2");
        mainPanel.add(new Log3(cardLayout, mainPanel), "Log3");

        // Add the main panel to the frame
        this.add(mainPanel);

        // Set frame visible
        setVisible(true);
        }
}


// Tamaño JTextFields --> 300, 50 (Preferred y Maximum)
// Letra JTextFields --> Font("Verdana", Font.PLAIN, 18)

