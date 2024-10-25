package es.ingenieria.prog3.proyecto.gui;

import java.awt.*;

import javax.swing.*;

public class Log1 extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private CardLayout cardLayout;
    private JPanel mainPanel;

	public Log1(CardLayout cardLayout, JPanel mainPanel) {
		
		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;
		
		
		
		this.setLayout(new BorderLayout(10, 10));

		// Crear el panel para el centro del JFrame
		JPanel panelCentroLog = new JPanel(new GridLayout(1, 2, 10, 10));
		
        // Anadir el panel al centro del JFrame
        this.add(panelCentroLog, BorderLayout.CENTER);
        
        // Anadir la etiqueta de copyright al south del panel
        this.add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
        
        // Nuevo panel para la parte izquierda del log in (un BoxLayout)
        JPanel panelIzquierdaCentroLog = new JPanel();
        panelIzquierdaCentroLog.setLayout(new BoxLayout(panelIzquierdaCentroLog, BoxLayout.Y_AXIS));
        panelCentroLog.add(panelIzquierdaCentroLog);
        
        // Nuevo panel para la parte derecha del log in (un BoxLayout)
        JPanel panelDerechaCentroLog = new JPanel();
        panelDerechaCentroLog.setLayout(new BoxLayout(panelDerechaCentroLog, BoxLayout.Y_AXIS));
        panelCentroLog.add(panelDerechaCentroLog);
        
        // Creamos los JLabel para la parte izquierda        
        JLabel logo = new JLabel();
        ImageIcon icono = new ImageIcon("icono.png");
        logo.setIcon(icono);
        JLabel labelDescripcion = new JLabel("TEXTO LARGO Y ABURRIDO");

        // Centramos los diferentes labels
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelIzquierdaCentroLog.add(Box.createVerticalGlue());
        panelIzquierdaCentroLog.add(logo);
        panelIzquierdaCentroLog.add(Box.createVerticalStrut(50));
        panelIzquierdaCentroLog.add(labelDescripcion);
        panelIzquierdaCentroLog.add(Box.createVerticalGlue());
        
        
		JPanel panelUsuario = new JPanel(new FlowLayout());
		JPanel panelContrasena = new JPanel(new FlowLayout());

		Font fuenteTextField = new Font("Verdana", Font.BOLD, 18);
		Font fuenteLabel = new Font("Verdana", Font.BOLD, 18);

        
        // Creamos los JLabel para la parte izquierda
		JLabel labelUsuario = new JLabel("       Usuario: ");
		labelUsuario.setFont(fuenteLabel);
		
        JTextField textFieldUsuario = new JTextField();
        textFieldUsuario.setPreferredSize(new Dimension(300, 50));
        textFieldUsuario.setMaximumSize(new Dimension(300, 50));
        textFieldUsuario.setFont(fuenteTextField);

		panelUsuario.add(labelUsuario);
		panelUsuario.add(textFieldUsuario);
		panelUsuario.setPreferredSize(new Dimension(500, 70));
		panelUsuario.setMaximumSize(new Dimension(500, 70));

		
		JLabel labelContrasena = new JLabel("Contraseña: ");
		labelContrasena.setFont(fuenteLabel);

		JTextField textFieldContrasena = new JTextField();
        textFieldContrasena.setPreferredSize(new Dimension(300, 50));
        textFieldContrasena.setMaximumSize(new Dimension(300, 50));
        textFieldContrasena.setFont(fuenteTextField);
        
        panelContrasena.add(labelContrasena);
        panelContrasena.add(textFieldContrasena);
        panelContrasena.setPreferredSize(new Dimension(500, 70));
        panelContrasena.setMaximumSize(new Dimension(500, 70));

		
		

       
        JButton botonLogin = new JButton("LOG IN");
        JButton botonOlvidarContrasena = new JButton("¿Has olvidado la contraseña?");

        
        // Centramos los diferentes labels
        textFieldUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        textFieldContrasena.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonOlvidarContrasena.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        panelDerechaCentroLog.add(Box.createVerticalGlue());
        panelDerechaCentroLog.add(panelUsuario);
        panelDerechaCentroLog.add(Box.createVerticalStrut(20));
        panelDerechaCentroLog.add(panelContrasena);
        panelDerechaCentroLog.add(Box.createVerticalStrut(20));
        panelDerechaCentroLog.add(botonLogin);
        panelDerechaCentroLog.add(Box.createVerticalStrut(20));
        panelDerechaCentroLog.add(botonOlvidarContrasena);
        panelDerechaCentroLog.add(Box.createVerticalGlue());
        
        
        //Hacemos lo mismo para la derecha
        
	}
		
}
