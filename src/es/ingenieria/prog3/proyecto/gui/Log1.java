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
		JPanel centroLog = new JPanel(new GridLayout(1, 2, 10, 10));
        // Anadir el panel al centro del JFrame
        this.add(centroLog, BorderLayout.CENTER);
        
        // Anadir la etiqueta de copyright al south del panel
        this.add(new JButton("HOLA"), BorderLayout.SOUTH);
        
        // Nuevo panel para la parte izquierda del log in (un BoxLayout)
        JPanel izquierdaCentroLog = new JPanel();
        izquierdaCentroLog.setLayout(new BoxLayout(izquierdaCentroLog, BoxLayout.Y_AXIS));
        centroLog.add(izquierdaCentroLog);
        
        // Nuevo panel para la parte derecha del log in (un BoxLayout)
        JPanel derechaCentroLog = new JPanel();
        derechaCentroLog.setLayout(new BoxLayout(derechaCentroLog, BoxLayout.Y_AXIS));
        centroLog.add(derechaCentroLog);
        
        // Creamos los JLabel para la parte izquierda        
        JLabel logo = new JLabel();
        ImageIcon icono = new ImageIcon("icono.png");
        logo.setIcon(icono);
        JLabel descripcion = new JLabel("TEXTO LARGO Y ABURRIDO");

        // Centramos los diferentes labels
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        descripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        izquierdaCentroLog.add(Box.createVerticalGlue());
        izquierdaCentroLog.add(logo);
        izquierdaCentroLog.add(Box.createVerticalStrut(50));
        izquierdaCentroLog.add(descripcion);
        izquierdaCentroLog.add(Box.createVerticalGlue());
        
        
        // Creamos los JLabel para la parte izquierda        
        JTextField login_usuario = new JTextField();
        JTextField login_contraseña = new JTextField();
        
        login_usuario.setPreferredSize(new Dimension(300, 50));
        login_usuario.setMaximumSize(new Dimension(300, 50));
        login_usuario.setFont(new Font("Verdana", Font.PLAIN, 18));


        JButton boton_login = new JButton("LOG IN");
        JButton boton_olvidar_contrasena = new JButton("¿Has olvidado la contraseña?");

        
        // Centramos los diferentes labels
        login_usuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        login_contraseña.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton_login.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton_olvidar_contrasena.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        derechaCentroLog.add(Box.createVerticalGlue());
        derechaCentroLog.add(login_usuario);
        derechaCentroLog.add(Box.createVerticalStrut(50));
        derechaCentroLog.add(login_contraseña);
        derechaCentroLog.add(Box.createVerticalStrut(50));
        derechaCentroLog.add(boton_login);
        derechaCentroLog.add(Box.createVerticalStrut(50));
        derechaCentroLog.add(boton_olvidar_contrasena);
        derechaCentroLog.add(Box.createVerticalGlue());
        
        
        //Hacemos lo mismo para la derecha
        
	}
		
}
