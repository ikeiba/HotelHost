package es.ingenieria.prog3.proyecto.gui;

import javax.swing.*;

import es.ingenieria.prog3.proyecto.gui.util.JCheckBoxListener;
import es.ingenieria.prog3.proyecto.gui.util.JTextFieldDefaultText;
import es.ingenieria.prog3.proyecto.gui.util.JPanelBordesRedondos;
import es.ingenieria.prog3.proyecto.gui.util.JPasswordFieldDefaultText;
import es.ingenieria.prog3.proyecto.gui.util.Preferences;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	cardLayout.show(mainPanel, "Log1");
            }
        });
        
        add(logo, BorderLayout.CENTER);
        
        JLabel labelDescripcion = new JLabel("<html><div style='text-align: center;'>Unete a Millones de usuarios que han disfrutado de viajes con nosotros", JLabel.CENTER);
        labelDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
        labelDescripcion.setVerticalAlignment(SwingConstants.TOP);
        labelDescripcion.setBounds(logo.getX(), logo.getY() + logo.getHeight() + 25, 300, 150);
        labelDescripcion.setFont(Preferences.FONT);
        labelDescripcion.setForeground(Color.WHITE);
        add(labelDescripcion, BorderLayout.CENTER);
        
        JPanelBordesRedondos panelnuevousuario = new JPanelBordesRedondos(25);
        panelnuevousuario.setBounds(0, 0, (int) (1152 * 0.4), (int) ((720 * 0.9)));
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
        labelfechanacimiento.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 200, panelnuevousuario.getWidth(), 150);
        labelfechanacimiento.setFont(new Font("Verdana", Font.PLAIN, 10));
        panelnuevousuario.add(labelfechanacimiento);
        
        Integer[] numerosdias = new Integer[31];
        for (int i = 1; i < (numerosdias.length + 1); i++) {
        	numerosdias[i - 1] = i;
        }
        JComboBox<Integer> JComboBoxdias = new JComboBox<>(numerosdias);
        JComboBoxdias.setBackground(Color.WHITE);
        JComboBoxdias.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 220, (int) (panelnuevousuario.getWidth() * 0.26), 50);
        panelnuevousuario.add(JComboBoxdias);
        
        String[] nombresmeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        JComboBox<String> JComboBoxmeses = new JComboBox<>(nombresmeses);
        JComboBoxmeses.setBackground(Color.WHITE);
        JComboBoxmeses.setBounds((int) (panelnuevousuario.getWidth() * 0.37), 220, (int) (panelnuevousuario.getWidth() * 0.26), 50);
        panelnuevousuario.add(JComboBoxmeses);
        
        int indicenumerosanos = 0;
        Integer[] numerosanos = new Integer[125];
        for (int i = 2024; i >= 1900; i--) {
        	numerosanos[indicenumerosanos] = i;
        	indicenumerosanos++;
        }
        JComboBox<Integer> JComboBoxanos = new JComboBox<>(numerosanos);
        JComboBoxanos.setBackground(Color.WHITE);
        JComboBoxanos.setBounds((int) (panelnuevousuario.getWidth() * 0.68), 220, (int) (panelnuevousuario.getWidth() * 0.26), 50);
        panelnuevousuario.add(JComboBoxanos);
        
        JLabel labelgenero = new JLabel("Genero");
        labelgenero.setHorizontalAlignment(SwingConstants.LEFT);
        labelgenero.setVerticalAlignment(SwingConstants.TOP);
        labelgenero.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 290, panelnuevousuario.getWidth(), 150);
        labelgenero.setFont(new Font("Verdana", Font.PLAIN, 10));
        panelnuevousuario.add(labelgenero);
        
        JCheckBox checkboxgenerohombre = new JCheckBox("Hombre");
        checkboxgenerohombre.setBackground(Color.WHITE);
        checkboxgenerohombre.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        checkboxgenerohombre.setBorderPainted(true);
        checkboxgenerohombre.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 310, (int) (panelnuevousuario.getWidth() * 0.26), 50);
        panelnuevousuario.add(checkboxgenerohombre);
        
        JCheckBox checkboxgeneromujer = new JCheckBox("Mujer");
        checkboxgeneromujer.setBackground(Color.WHITE);
        checkboxgeneromujer.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        checkboxgeneromujer.setBorderPainted(true);
        checkboxgeneromujer.setBounds((int) (panelnuevousuario.getWidth() * 0.37), 310, (int) (panelnuevousuario.getWidth() * 0.26), 50);
        panelnuevousuario.add(checkboxgeneromujer);
        
        JCheckBox checkboxgenerootro = new JCheckBox("Otro");
        checkboxgenerootro.setBackground(Color.WHITE);
        checkboxgenerootro.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        checkboxgenerootro.setBorderPainted(true);
        checkboxgenerootro.setBounds((int) (panelnuevousuario.getWidth() * 0.68), 310, (int) (panelnuevousuario.getWidth() * 0.26), 50);
        panelnuevousuario.add(checkboxgenerootro);
        
        JCheckBox[] checkboxgenero = {checkboxgenerohombre, checkboxgeneromujer, checkboxgenerootro};
        new JCheckBoxListener(checkboxgenero);
        
        JTextFieldDefaultText textFieldEmail = new JTextFieldDefaultText("Email");
        textFieldEmail.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 380, (int) (panelnuevousuario.getWidth() * 0.88), 50);
        panelnuevousuario.add(textFieldEmail);
        
        JTextFieldDefaultText textFieldTelefono = new JTextFieldDefaultText("Telefono");
        textFieldTelefono.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 440, (int) (panelnuevousuario.getWidth() * 0.88), 50);
        panelnuevousuario.add(textFieldTelefono);
        
        JCheckBox checkBoxContresena = new JCheckBox();
        checkBoxContresena.setBackground(Color.WHITE);
        checkBoxContresena.setIcon(new ImageIcon("resources/images/imagenNoVer.png"));
        checkBoxContresena.setSelectedIcon(new ImageIcon("resources/images/imagenVer.png"));
        
        JPasswordFieldDefaultText textFieldContraseña = new JPasswordFieldDefaultText("Contraseña", checkboxgenerootro);
        textFieldContraseña.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 500, (int) (panelnuevousuario.getWidth() * 0.68), 50);
        panelnuevousuario.add(textFieldContraseña);
        
        JButton botonVolver = new JButton("Volver");
        botonVolver.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 570, (int) (panelnuevousuario.getWidth() * 0.42), 50);
        botonVolver.addActionListener(e -> this.cardLayout.show(this.mainPanel, "Log1"));
        panelnuevousuario.add(botonVolver);
        
        JButton botonCrearCuenta = new JButton("Crear Cuenta");
        botonCrearCuenta.setBounds((int) (panelnuevousuario.getWidth() * 0.52), 570, (int) (panelnuevousuario.getWidth() * 0.42), 50);
        panelnuevousuario.add(botonCrearCuenta);
        
        add(panelnuevousuario, BorderLayout.CENTER);
        
		add(panelCentro, BorderLayout.CENTER); //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel
		
        // Panel principal sur
        add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
    }
}