package es.ingenieria.prog3.proyecto.gui;

import javax.swing.*;

import es.ingenieria.prog3.proyecto.gui.util.JPanelBordesRedondos;
import es.ingenieria.prog3.proyecto.gui.util.JSeparatorPunteada;
import es.ingenieria.prog3.proyecto.gui.util.Preferences;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		logo.setBounds(0, 0, 50, 35);
		logo.setBounds(40, 15, logo.getWidth(), logo.getHeight());
		ImageIcon originalIcon = new ImageIcon("resources/images/Hotel Host.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(50, 35, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(scaledImage);
        logo.setIcon(resizedIcon);
        add(logo, BorderLayout.CENTER);
        
        JSeparatorPunteada barraSeparadora = new JSeparatorPunteada();
        barraSeparadora.setBackground(Color.BLACK);
        barraSeparadora.setBounds(0, 75, Preferences.WINDOWWIDTH, 2);
        barraSeparadora.setOrientation(SwingConstants.HORIZONTAL);
        add(barraSeparadora, BorderLayout.CENTER);
        
        JLabel labelorganiza = new JLabel("Organiza tu viaje");
        labelorganiza.setHorizontalAlignment(SwingConstants.LEFT);
        labelorganiza.setVerticalAlignment(SwingConstants.TOP);
        labelorganiza.setBounds(40, 115, 500, 150);
        labelorganiza.setFont(new Font("Verdana", Font.BOLD, 35));
        labelorganiza.setForeground(Color.WHITE);
        add(labelorganiza, BorderLayout.CENTER);
        
        JPanelBordesRedondos panelbuscar = new JPanelBordesRedondos(25);
        panelbuscar.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.92), 70);
        panelbuscar.setBounds((int) ((Preferences.WINDOWWIDTH * 0.5) - (panelbuscar.getWidth() / 2)) - 5, 175, panelbuscar.getWidth() - 10, panelbuscar.getHeight());
        panelbuscar.setBackground(Color.WHITE);
        panelbuscar.setLayout(null);
        
        JSeparatorPunteada barraSeparadora2 = new JSeparatorPunteada();
        barraSeparadora2.setBackground(Color.BLACK);
        barraSeparadora2.setBounds(0, 300, Preferences.WINDOWWIDTH, 2);
        barraSeparadora2.setOrientation(SwingConstants.HORIZONTAL);
        add(barraSeparadora2, BorderLayout.CENTER);
        
        JLabel labelpopular = new JLabel("Destinos más populares");
        labelpopular.setHorizontalAlignment(SwingConstants.LEFT);
        labelpopular.setVerticalAlignment(SwingConstants.TOP);
        labelpopular.setBounds(40, 340, 500, 150);
        labelpopular.setFont(new Font("Verdana", Font.BOLD, 35));
        labelpopular.setForeground(Color.WHITE);
        add(labelpopular, BorderLayout.CENTER);
        
        JPanelBordesRedondos panelciudad = new JPanelBordesRedondos(25);
        panelciudad.setBounds(40, 420, 250, 200);
        panelciudad.setBackground(Color.WHITE);
        panelciudad.setLayout(null);
        
        JLabel ciudad = new JLabel();
        ciudad.setBounds(0, 0, 250, 200);
        ImageIcon ciudadIcon = new ImageIcon("resources/images/Ciudades/Paris.jpg");
		Image ciudadScaled = ciudadIcon.getImage().getScaledInstance((int) ciudadIcon.getIconWidth() / (ciudadIcon.getIconHeight() / 200), 200, Image.SCALE_SMOOTH);
		ImageIcon ciudadResized = new ImageIcon(ciudadScaled);
		ciudad.setIcon(ciudadResized);
		ciudad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	cardLayout.show(mainPanel, "Log1");
            }
        });
		panelciudad.add(ciudad);
		
		add(panelciudad, BorderLayout.CENTER);
        
        JPanelBordesRedondos panelciudad2 = new JPanelBordesRedondos(25);
        panelciudad2.setBounds(305, 420, 250, 200);
        panelciudad2.setBackground(Color.WHITE);
        panelciudad2.setLayout(null);
        add(panelciudad2, BorderLayout.CENTER);
        
        JPanelBordesRedondos panelciudad3 = new JPanelBordesRedondos(25);
        panelciudad3.setBounds(570, 420, 250, 200);
        panelciudad3.setBackground(Color.WHITE);
        panelciudad3.setLayout(null);
        add(panelciudad3, BorderLayout.CENTER);
        
        JPanelBordesRedondos panelciudad4 = new JPanelBordesRedondos(25);
        panelciudad4.setBounds(835, 420, 250, 200);
        panelciudad4.setBackground(Color.WHITE);
        panelciudad4.setLayout(null);
        add(panelciudad4, BorderLayout.CENTER);
        
        add(panelbuscar, BorderLayout.CENTER);
        
		add(panelCentro, BorderLayout.CENTER); //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel
		
        // Panel principal sur
        add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
    }
}