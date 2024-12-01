package es.ingenieria.prog3.proyecto.gui;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;


public class DialogPago extends JDialog {
    
	private static final long serialVersionUID = 1L;
	
	public DialogPago() {
		setLayout(new BorderLayout()); // Cambiar layout del diálogo principal
		 
        JPanel panelPago = new JPanel(new GridLayout(3, 1, 5, 8));
        
        JLabel labelTarjeta = new JLabel("Introduce tu tarjeta: ");
        JLabel labelFechaCaducidad = new JLabel("Introduce la fecha de caducidad de tu tarjeta: ");
        JLabel labelCodigoSeguridad = new JLabel("Introduce el CVC de tu tarjeta: ");
        
        JPanel panelTarjeta = new JPanel();
        JTextField textFieldTarjeta = new JTextField();
        textFieldTarjeta.setColumns(20);
        
        
        JPanel panelFechaCaducidad = new JPanel();
        JTextField textFieldFechaCaducidad = new JTextField();
        textFieldFechaCaducidad.setColumns(10);
        
        
        JPanel panelCodigoSeguridad = new JPanel();
        JTextField textFieldCodigoSeguridad = new JTextField();
        textFieldCodigoSeguridad.setColumns(5);

		
        
        
        // Panel para los botones
        JPanel panelBotones = new JPanel();
        JButton botonCancelar = new JButton("Cancelar");
        JButton botonConfirmar = new JButton("Confirmar");
        
        botonCancelar.addActionListener(e -> dispose());
        
        
        //Añadir los componentes a los paneles
        panelTarjeta.add(botonConfirmar);
        panelTarjeta.add(textFieldTarjeta);
        
        panelFechaCaducidad.add(botonConfirmar);
        panelFechaCaducidad.add(textFieldFechaCaducidad);

        panelCodigoSeguridad.add(botonConfirmar);
        panelCodigoSeguridad.add(textFieldCodigoSeguridad);

        panelPago.add(panelTarjeta);
        panelPago.add(panelFechaCaducidad);
        panelPago.add(panelCodigoSeguridad);
        
        panelBotones.add(botonCancelar);
        panelBotones.add(botonConfirmar);

        //Añadir los paneles al JDialog
        add(panelPago, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);
		
		//Configurar las caracteristicas del JDialog
        setIconImage(new ImageIcon("resources/images/Hotel Host.png").getImage()); // Cambiar el Logo de la ventana y barra de tareas
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Confirmar Pago");		
		setSize(500, 350);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
}