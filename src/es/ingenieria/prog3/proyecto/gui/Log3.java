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
		ImageIcon logotipoImage = new ImageIcon("icono.png");
		logotipoLabel.setIcon(logotipoImage);
		
		this.add(logotipoLabel, BorderLayout.NORTH);
		
		// SECCION CENTRO (Formulario)
		JPanel panelCentral = new JPanel(new BorderLayout());
		JLabel textoRecuperacion = new JLabel("Para recuperar rellena los siguientes datos:");
		panelCentral.add(textoRecuperacion, BorderLayout.NORTH);
		
		JPanel formularioPanel = new JPanel();
		formularioPanel.setLayout(new BoxLayout(formularioPanel, BoxLayout.Y_AXIS));
		
		JPanel emailPanel = new JPanel(new FlowLayout());
		JLabel emailLabel = new JLabel("Email: ");
		JTextField emailTextField = new JTextField();
		emailTextField.setPreferredSize(new Dimension(300, 50)); // Ajustamos tamaño del TextField al standard
		emailTextField.setMaximumSize(new Dimension(300, 50));
		emailTextField.setFont(new Font("Verdana", Font.PLAIN, 18));
		emailPanel.add(emailLabel);
		emailPanel.add(emailTextField);
		
		JPanel botonesPanel = new JPanel(new FlowLayout());
		JButton aceptarButton = new JButton("Aceptar");
		JButton cancelarButton = new JButton("Cancelar");
		botonesPanel.add(aceptarButton);
		botonesPanel.add(cancelarButton);
		
		formularioPanel.add(emailPanel);
		formularioPanel.add(botonesPanel);
		
		panelCentral.add(formularioPanel, BorderLayout.CENTER);
		
		this.add(panelCentral, BorderLayout.CENTER);
		
		// SECCION SUR (Copyright)
		JLabel copyrightLabel = new JLabel("Hotel Host® 2024");
		this.add(copyrightLabel, BorderLayout.SOUTH);
		
	}
}