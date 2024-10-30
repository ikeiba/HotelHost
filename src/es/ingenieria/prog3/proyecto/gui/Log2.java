package es.ingenieria.prog3.proyecto.gui;

import javax.swing.*;

import es.ingenieria.prog3.proyecto.gui.util.JTextFieldDefaultText;
import es.ingenieria.prog3.proyecto.gui.util.PanelBordesRedondos;
import es.ingenieria.prog3.proyecto.gui.util.Preferences;

import java.awt.*;

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
		panelCentro.setBackground(Preferences.COLORBACKGROUND);
		panelCentro.setLayout(null);
		
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
        labelDescripcion.setFont(Preferences.FONT);
        labelDescripcion.setForeground(Color.WHITE);
        add(labelDescripcion, BorderLayout.CENTER);
        
        PanelBordesRedondos panelnuevousuario = new PanelBordesRedondos(25);
        panelnuevousuario.setBounds(0, 0, (int) (1152 * 0.4), (int) ((720 * 0.8)));
        panelnuevousuario.setBounds((int) ((1152 * 0.7) - (panelnuevousuario.getWidth() / 2)), (int) ((720 * 0.5) - (panelnuevousuario.getHeight() / 2)) - 25, panelnuevousuario.getWidth(), panelnuevousuario.getHeight());
        panelnuevousuario.setBackground(Color.WHITE);
        panelnuevousuario.setLayout(null);
        
        JLabel labelnuevacuenta = new JLabel("Crea una nueva cuenta");
        labelnuevacuenta.setHorizontalAlignment(SwingConstants.CENTER);
        labelnuevacuenta.setVerticalAlignment(SwingConstants.TOP);
        labelnuevacuenta.setBounds(0, 25, panelnuevousuario.getWidth(), 150);
        labelnuevacuenta.setFont(Preferences.FONT);
        panelnuevousuario.add(labelnuevacuenta);
        
        JLabel labelnuevacuentamensaje = new JLabel("Es rápido y fácil");
        labelnuevacuentamensaje.setHorizontalAlignment(SwingConstants.CENTER);
        labelnuevacuentamensaje.setVerticalAlignment(SwingConstants.TOP);
        labelnuevacuentamensaje.setBounds(0, 60, panelnuevousuario.getWidth(), 150);
        labelnuevacuentamensaje.setFont(new Font("Verdana", Font.PLAIN, 12));
        panelnuevousuario.add(labelnuevacuentamensaje);
        
        JSeparator barraSeparadora = new JSeparator();
        barraSeparadora.setBackground(Color.LIGHT_GRAY);
        barraSeparadora.setBounds(0, 104, panelnuevousuario.getWidth(), 2);
        barraSeparadora.setOrientation(SwingConstants.HORIZONTAL);
        panelnuevousuario.add(barraSeparadora);
        
        JTextFieldDefaultText textFieldNombre = new JTextFieldDefaultText("Nombre");
        textFieldNombre.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 130, (int) (panelnuevousuario.getWidth() * 0.42), 50);
        panelnuevousuario.add(textFieldNombre);
        
        JTextFieldDefaultText textFieldApellidos = new JTextFieldDefaultText("Apellidos");
        textFieldApellidos.setBounds((int) (panelnuevousuario.getWidth() * 0.52), 130, (int) (panelnuevousuario.getWidth() * 0.42), 50);
        panelnuevousuario.add(textFieldApellidos);
        
        JLabel labelfechanacimiento = new JLabel("Fecha de nacimiento");
        labelfechanacimiento.setHorizontalAlignment(SwingConstants.LEFT);
        labelfechanacimiento.setVerticalAlignment(SwingConstants.TOP);
        labelfechanacimiento.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 210, panelnuevousuario.getWidth(), 150);
        labelfechanacimiento.setFont(new Font("Verdana", Font.PLAIN, 10));
        panelnuevousuario.add(labelfechanacimiento);
        
        Integer[] numerosdias = new Integer[31];
        for (int i = 1; i < (numerosdias.length + 1); i++) {
        	numerosdias[i - 1] = i;
        }
        JComboBox<Integer> JComboBoxdias = new JComboBox<>(numerosdias);
        JComboBoxdias.setBackground(Color.WHITE);
        JComboBoxdias.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 230, (int) (panelnuevousuario.getWidth() * 0.26), 50);
        panelnuevousuario.add(JComboBoxdias);
        
        String[] nombresmeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        JComboBox<String> JComboBoxmeses = new JComboBox<>(nombresmeses);
        JComboBoxmeses.setBackground(Color.WHITE);
        JComboBoxmeses.setBounds((int) (panelnuevousuario.getWidth() * 0.37), 230, (int) (panelnuevousuario.getWidth() * 0.26), 50);
        panelnuevousuario.add(JComboBoxmeses);
        
        int indicenumerosanos = 0;
        Integer[] numerosanos = new Integer[125];
        for (int i = 2024; i >= 1900; i--) {
        	numerosanos[indicenumerosanos] = i;
        	indicenumerosanos++;
        }
        JComboBox<Integer> JComboBoxanos = new JComboBox<>(numerosanos);
        JComboBoxanos.setBackground(Color.WHITE);
        JComboBoxanos.setBounds((int) (panelnuevousuario.getWidth() * 0.68), 230, (int) (panelnuevousuario.getWidth() * 0.26), 50);
        panelnuevousuario.add(JComboBoxanos);
        
        add(panelnuevousuario, BorderLayout.CENTER);
        
		add(panelCentro, BorderLayout.CENTER); //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel
		
        // Panel principal sur
        add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
    }
}