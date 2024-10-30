package es.ingenieria.prog3.proyecto.gui;

import javax.swing.*;

import es.ingenieria.prog3.proyecto.gui.functions.PanelBordesRedondos;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

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
		panelCentro.setBackground(new Color(243, 241, 228));
		panelCentro.setLayout(null);
		
		//Crear las fuentes
		Font fuenteTexto = new Font("Verdana", Font.BOLD, 18);
		
		JLabel logo = new JLabel();
		logo.setBounds(0, 0, 300, 225);
		logo.setBounds((int) ((1152 * 0.25) - (logo.getWidth() / 2)), (int) ((720 * 0.4) - (logo.getHeight() / 2)), logo.getWidth(), logo.getHeight());
		ImageIcon originalIcon = new ImageIcon("resources/images/Hotel Host.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(300, 225, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(scaledImage);
        logo.setIcon(resizedIcon);
        add(logo, BorderLayout.CENTER);
        
        JLabel labelDescripcion = new JLabel("<html><div style='text-align: center;'>Unete a Millones de usuarios que han disfrutado de viajes con nosotros", JLabel.CENTER);
        labelDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
        labelDescripcion.setVerticalAlignment(SwingConstants.TOP);
        labelDescripcion.setBounds(logo.getX(), logo.getY() + logo.getHeight() + 25, 300, 150);
        labelDescripcion.setFont(fuenteTexto);
        add(labelDescripcion, BorderLayout.CENTER);
        
        PanelBordesRedondos panelnuevousuario = new PanelBordesRedondos(25);
        panelnuevousuario.setBounds(0, 0, (int) (1152 * 0.4), (int) ((720 * 0.8)));
        panelnuevousuario.setBounds((int) ((1152 * 0.7) - (panelnuevousuario.getWidth() / 2)), (int) ((720 * 0.5) - (panelnuevousuario.getHeight() / 2)) - 25, panelnuevousuario.getWidth(), panelnuevousuario.getHeight());
        panelnuevousuario.setBackground(Color.WHITE);
        add(panelnuevousuario, BorderLayout.CENTER);
        
		add(panelCentro, BorderLayout.CENTER); //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel
		
        // Panel principal sur
        add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
    }
}