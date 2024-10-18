package es.ingenieria.prog3.proyecto.gui;

import java.awt.*;

import javax.swing.*;

public class Log1 extends JFrame {

	private static final long serialVersionUID = 1L;

	public Log1() {
		
		// Establecer el titulo de la ventana
		setTitle("LOG IN");
		// Establecer que pasa al cerrar la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Establecer el tamano inicial (por defecto tamano pantalla)
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// Establecer el layout principal 
		setLayout(new BorderLayout(10, 10));
		
        // Crear el panel para el centro del JFrame
		JPanel centroLog = new JPanel(new GridLayout(1, 2, 10, 10));
        // Anadir el panel al centro del JFrame
        this.add(centroLog, BorderLayout.CENTER);
        
        JPanel izquierdaCentroLog = new JPanel();
        izquierdaCentroLog.setLayout(new BoxLayout(izquierdaCentroLog, BoxLayout.Y_AXIS));
        
        centroLog.add(izquierdaCentroLog);
        centroLog.add(new JButton("HOla"));
		
        this.add(new JButton("HOLA"), BorderLayout.SOUTH);
        
        JLabel boton1 = new JLabel("HOLA BOTTON 1");
        JLabel boton2 = new JLabel("HOLA BOTTON 1");


        boton1.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        izquierdaCentroLog.add(Box.createVerticalGlue());
        
        
        izquierdaCentroLog.add(boton1);
        izquierdaCentroLog.add(boton2);

        izquierdaCentroLog.add(Box.createVerticalGlue());

        izquierdaCentroLog.add(Box.createVerticalStrut(50));
        izquierdaCentroLog.add(new JButton("HOLA"));
        



	}
	
	
	
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Log1 log1 = new Log1();
			log1.setVisible(true);
		});
	}
}
