package es.ingenieria.prog3.proyecto.gui;

import java.awt.*;

import javax.swing.*;

public class Log3 extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public Log3() {
		
		// Establecer el titulo de la ventana
		setTitle("RECOVER ACCOUNT");
		// Establecer que pasa al cerrar la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Establecer el tamano inicial (por defecto tamano pantalla)
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// Establecer el layout principal 
		setLayout(new BorderLayout(10, 10));
		
		//		SECCIONES DEL LAYOUT		//
		
		// SECCION NORTE (Logotipo)
		JLabel logotipoLabel = new JLabel();
		ImageIcon logotipoImage = new ImageIcon("icono.png");
		logotipoLabel.setIcon(logotipoImage);
		
		this.add(logotipoLabel, BorderLayout.NORTH);
		
		// SECCION CENTRO (Formulario)
		JPanel panelCentral = new JPanel(new BorderLayout());
		JLabel textoRecuperacion = new JLabel("Para recuperar rellena los siguientes datos:");
		textoRecuperacion.setHorizontalAlignment(0);
		panelCentral.add(textoRecuperacion, BorderLayout.NORTH);
		
		JPanel formularioPanel = new JPanel();
		formularioPanel.setLayout(new BoxLayout(formularioPanel, BoxLayout.Y_AXIS));
		
		JPanel emailPanel = new JPanel(new FlowLayout());
		JLabel emailLabel = new JLabel("Email: ");
		JTextField emailTextField = new JTextField();
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
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Log3 log3 = new Log3();
			log3.setVisible(true);
		});
	}
}