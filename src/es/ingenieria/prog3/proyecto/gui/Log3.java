package es.ingenieria.prog3.proyecto.gui;

import java.awt.*;

import javax.swing.*;

public class Log3 extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private CardLayout cardLayout;
    private JPanel mainPanel;

	public Log3(CardLayout cardLayout, JPanel mainPanel) {
		
		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;
		
		// Establecer el layout principal 
		setLayout(new BorderLayout(10, 10));
		setBackground(Color.WHITE);
		
		//		SECCIONES DEL LAYOUT		//
		
		// SECCION NORTE (Logotipo)
		JLabel logotipoLabel = new JLabel();
		ImageIcon logotipoImage = new ImageIcon("resources/images/icono.png");
		logotipoLabel.setIcon(logotipoImage);
		
		this.add(logotipoLabel, BorderLayout.NORTH);
		
		// SECCION CENTRO (Formulario)
		Font fuenteLabel = new Font("Verdana", Font.BOLD, 18);
		
		JPanel panelCentral = new JPanel(new BorderLayout());
		JLabel textoRecuperacion = new JLabel("Para recuperar su contraseña, rellene los siguientes datos:");
		textoRecuperacion.setHorizontalAlignment(0);
		textoRecuperacion.setFont(fuenteLabel);
		panelCentral.add(textoRecuperacion, BorderLayout.NORTH);
		
		JPanel formularioPanel = new JPanel();
		formularioPanel.setLayout(new BoxLayout(formularioPanel, BoxLayout.Y_AXIS));
		
		JPanel emailPanel = new JPanel(new FlowLayout());
		JLabel emailLabel = new JLabel("          Email: ");
		emailLabel.setFont(fuenteLabel);
		JTextField emailTextField = new JTextField();
		emailTextField.setPreferredSize(new Dimension(300, 50)); // Ajustamos tamaño del TextField al standard
		emailTextField.setMaximumSize(new Dimension(300, 50));
		emailTextField.setFont(new Font("Verdana", Font.PLAIN, 18));
		emailPanel.add(emailLabel);
		emailPanel.add(emailTextField);
		
		JPanel panelTelefono = new JPanel(new FlowLayout());
		JLabel labelTelefono = new JLabel("Nº Teléfono: ");
		labelTelefono.setFont(fuenteLabel);
		JTextField textFieldTelefono = new JTextField();
		textFieldTelefono.setPreferredSize(new Dimension(300, 50)); // Ajustamos tamaño del TextField al standard
		textFieldTelefono.setMaximumSize(new Dimension(300, 50));
		textFieldTelefono.setFont(new Font("Verdana", Font.PLAIN, 18));
		panelTelefono.add(labelTelefono);
		panelTelefono.add(textFieldTelefono);
		
		JPanel botonesPanel = new JPanel(new FlowLayout());
		JButton aceptarButton = new JButton("Aceptar");
		JButton cancelarButton = new JButton("Cancelar");
		botonesPanel.add(aceptarButton);
		botonesPanel.add(cancelarButton);
		
		formularioPanel.add(Box.createVerticalGlue());
		formularioPanel.add(emailPanel);
		formularioPanel.add(Box.createVerticalStrut(-5));
		formularioPanel.add(panelTelefono);
		formularioPanel.add(Box.createVerticalStrut(-5));
		formularioPanel.add(botonesPanel);
		formularioPanel.add(Box.createVerticalGlue());
		
		panelCentral.add(formularioPanel, BorderLayout.CENTER);
		
		this.add(panelCentral, BorderLayout.CENTER);
		
		// SECCION SUR (Copyright)
		JLabel copyrightLabel = new JLabel("Hotel Host® 2024");
		this.add(copyrightLabel, BorderLayout.SOUTH);
		
	}
}