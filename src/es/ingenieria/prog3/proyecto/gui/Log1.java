package es.ingenieria.prog3.proyecto.gui;

import java.awt.*;

import javax.swing.*;

public class Log1 extends JFrame {

	private static final long serialVersionUID = 1L;

	public Log1() {
		
		// Establecer el titulo de la ventana
		setTitle("Calculadora Básica");
		// Establecer que pasa al cerrar la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Establecer el tamano inicial (por defecto tamano pantalla)
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// Establecer el layout principal 
		setLayout(new BorderLayout(10, 10));
        // Crear el panel para el centro del JFrame
		JPanel centroLog = new JPanel(new GridLayout(1, 2, 100, 100));
        // Anadir el panel al centro del JFrame
        this.add(centroLog, BorderLayout.CENTER);
        
		JPanel izquierdaCentroLog = new JPanel(new GridLayout(2, 1, 100, 100));
		centroLog.add(izquierdaCentroLog);
		
        this.add(new JButton("HOLA"), BorderLayout.SOUTH);
        centroLog.add(new JButton("HOLA"));
        JLabel boton1 = new JLabel("HOLA BOTTON 1");
        boton1.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 100));  
        izquierdaCentroLog.add(boton1);
        izquierdaCentroLog.add(new JButton("HOLA"));


	}
	
	
	
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Log1 log1 = new Log1();
			log1.setVisible(true);
		});
	}
}
