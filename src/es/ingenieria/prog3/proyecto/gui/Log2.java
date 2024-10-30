package es.ingenieria.prog3.proyecto.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

@SuppressWarnings("serial")
public class Log2 extends JPanel {

	@SuppressWarnings("unused")
	private CardLayout cardLayout;
    @SuppressWarnings("unused")
	private JPanel mainPanel;

	public Log2(CardLayout cardLayout, JPanel mainPanel) {
		
		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());  
        
		//Panel principal centro
		JPanel panelCentro = new JPanel();
		panelCentro.setBackground(Color.WHITE);
		panelCentro.setLayout(null);
		
		JLabel logo = new JLabel();
		logo.setBounds(0, 0, 400, 400);
		ImageIcon originalIcon = new ImageIcon("resources/images/Hotel Host.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(scaledImage);
        logo.setIcon(resizedIcon);
        add(logo, BorderLayout.CENTER);
        
        JLabel labelDescripcion = new JLabel("Aligned Text", JLabel.CENTER);
        labelDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
        labelDescripcion.setVerticalAlignment(SwingConstants.TOP);
        labelDescripcion.setBounds(0, 0, 400, 50);
        add(labelDescripcion, BorderLayout.CENTER);
        
		add(panelCentro, BorderLayout.CENTER); //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel
		
        // Panel principal sur
        add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
    }
}