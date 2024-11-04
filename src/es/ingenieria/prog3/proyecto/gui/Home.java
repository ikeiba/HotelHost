package es.ingenieria.prog3.proyecto.gui;

import javax.swing.*;

import es.ingenieria.prog3.proyecto.gui.util.JSeparatorPunteada;
import es.ingenieria.prog3.proyecto.gui.util.Preferences;

import java.awt.*;

@SuppressWarnings("serial")
public class Home extends JPanel {

	private CardLayout cardLayout;
	private JPanel mainPanel;

	public Home(CardLayout cardLayout, JPanel mainPanel) {
		
		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());  
        
		//Panel principal centro
		JPanel panelCentro = new JPanel();
		panelCentro.setBackground(Preferences.COLORBACKGROUND);
		panelCentro.setLayout(null);
		
		JLabel logo = new JLabel();
		logo.setBounds(0, 0, 66, 50);
		logo.setBounds(30, 15, logo.getWidth(), logo.getHeight());
		ImageIcon originalIcon = new ImageIcon("resources/images/Hotel Host.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(66, 50, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(scaledImage);
        logo.setIcon(resizedIcon);
        add(logo, BorderLayout.CENTER);
        
        JSeparatorPunteada barraSeparadora = new JSeparatorPunteada();
        barraSeparadora.setBackground(Color.BLACK);
        barraSeparadora.setBounds(0, 90, Preferences.WINDOWWIDTH, 2);
        barraSeparadora.setOrientation(SwingConstants.HORIZONTAL);
        add(barraSeparadora, BorderLayout.CENTER);
        
		add(panelCentro, BorderLayout.CENTER); //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel
		
        // Panel principal sur
        add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
    }
}