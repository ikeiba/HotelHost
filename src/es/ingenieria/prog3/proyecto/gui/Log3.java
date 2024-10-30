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
		JLabel labelLogotipo = new JLabel();
		ImageIcon logotipoImage = new ImageIcon("resources/images/icono.png");
		labelLogotipo.setIcon(logotipoImage);
		
		this.add(labelLogotipo, BorderLayout.NORTH);
		
		// SECCION CENTRO (Formulario)
		Font fontLabel = new Font("Verdana", Font.BOLD, 18);
		
		JPanel panelCentral = new JPanel(new BorderLayout());
		JLabel labelTxtRecuperacion = new JLabel("Para recuperar su contraseña, rellene los siguientes datos:");
		labelTxtRecuperacion.setHorizontalAlignment(0);
		labelTxtRecuperacion.setFont(fontLabel);
		panelCentral.add(labelTxtRecuperacion, BorderLayout.NORTH);
		
		JPanel panelFormulario = new JPanel();
		panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
		
		JPanel panelEmail = new JPanel(new FlowLayout());
		JLabel labelEmail = new JLabel("          Email: ");
		labelEmail.setFont(fontLabel);
		JTextField textFieldEmail = new JTextField();
		textFieldEmail.setPreferredSize(new Dimension(300, 50)); // Ajustamos tamaño del TextField al standard
		textFieldEmail.setMaximumSize(new Dimension(300, 50));
		textFieldEmail.setFont(new Font("Verdana", Font.PLAIN, 18));
		panelEmail.add(labelEmail);
		panelEmail.add(textFieldEmail);
		
		JPanel panelTelefono = new JPanel(new FlowLayout());
		JLabel labelTelefono = new JLabel("Nº Teléfono: ");
		labelTelefono.setFont(fontLabel);
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
		cancelarButton.addActionListener(e -> this.cardLayout.show(this.mainPanel, "Log1"));
		botonesPanel.add(cancelarButton);
		
		panelFormulario.add(Box.createVerticalGlue());
		panelFormulario.add(panelEmail);
		panelFormulario.add(Box.createVerticalStrut(-5));
		panelFormulario.add(panelTelefono);
		panelFormulario.add(Box.createVerticalStrut(-5));
		panelFormulario.add(botonesPanel);
		panelFormulario.add(Box.createVerticalGlue());
		
		panelCentral.add(panelFormulario, BorderLayout.CENTER);
		
		this.add(panelCentral, BorderLayout.CENTER);
		
		// SECCION SUR (Copyright)
		JLabel labelCopyright = new JLabel("Hotel Host® 2024");
		this.add(labelCopyright, BorderLayout.SOUTH);
		
	}
}