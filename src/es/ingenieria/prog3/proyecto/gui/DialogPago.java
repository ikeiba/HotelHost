package es.ingenieria.prog3.proyecto.gui;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;


public class DialogPago extends JDialog {
    
	private static final long serialVersionUID = 1L;
	
	public DialogPago() {
		setLayout(new BorderLayout()); // Cambiar layout del diálogo principal
		 
        JPanel panelPago = new JPanel(new GridLayout(6, 1, 5, 5));
           
        //Creamos todo lo relacionado con la tarjeta
        JLabel labelTarjeta = new JLabel("Introduce tu tarjeta: ");
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setLayout(new FlowLayout(FlowLayout.LEFT));
        JTextField textFieldTarjeta = new JTextField();
        textFieldTarjeta.setColumns(20);
        //Imagen de tarjeta
        JLabel imagenTarjeta = new JLabel();
        imagenTarjeta.setBounds(30, 30, 0, 0);
		ImageIcon originalIcon = new ImageIcon("resources/images/tarjeta.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(scaledImage);
		imagenTarjeta.setIcon(resizedIcon);
        
        //Creamos todo lo relacionado con la fecha de caducidad
        JLabel labelFechaCaducidad = new JLabel("Introduce la fecha de caducidad de tu tarjeta: ");
        JPanel panelFechaCaducidad = new JPanel();
        panelFechaCaducidad.setLayout(new FlowLayout(FlowLayout.LEFT));
        JTextField textFieldFechaCaducidad = new JTextField();
        textFieldFechaCaducidad.setColumns(10);
        //Imagen de calendario
        JLabel imagenFechaCaducidad = new JLabel();
        imagenFechaCaducidad.setBounds(30, 30, 0, 0);
		ImageIcon originalIcon2 = new ImageIcon("resources/images/calendario.png");
		Image scaledImage2 = originalIcon2.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon2 = new ImageIcon(scaledImage2);
		imagenFechaCaducidad.setIcon(resizedIcon2);
        
        //Creamos todo lo relacionado con el codigo de seguridad
        JLabel labelCodigoSeguridad = new JLabel("Introduce el CVC de tu tarjeta: ");
        JPanel panelCodigoSeguridad = new JPanel();
        panelCodigoSeguridad.setLayout(new FlowLayout(FlowLayout.LEFT));
        JTextField textFieldCodigoSeguridad = new JTextField();
        textFieldCodigoSeguridad.setColumns(5);
        //Imagen de candado
        JLabel imagenCodigoSeguridad = new JLabel();
        imagenCodigoSeguridad.setBounds(30, 30, 0, 0);
		ImageIcon originalIcon3 = new ImageIcon("resources/images/candado.png");
		Image scaledImage3 = originalIcon3.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon3 = new ImageIcon(scaledImage3);
		imagenCodigoSeguridad.setIcon(resizedIcon3);
		
        // Panel para los botones
        JPanel panelBotones = new JPanel();
        JButton botonCancelar = new JButton("Cancelar");
        JButton botonConfirmar = new JButton("Confirmar");
        
        botonCancelar.addActionListener(e -> dispose());
        
        
        //Añadir los componentes a los paneles
        panelTarjeta.add(imagenTarjeta);
        panelTarjeta.add(textFieldTarjeta);
        
        panelFechaCaducidad.add(imagenFechaCaducidad);
        panelFechaCaducidad.add(textFieldFechaCaducidad);

        panelCodigoSeguridad.add(imagenCodigoSeguridad);
        panelCodigoSeguridad.add(textFieldCodigoSeguridad);

        panelPago.add(labelTarjeta);
        panelPago.add(panelTarjeta);
        panelPago.add(labelFechaCaducidad);
        panelPago.add(panelFechaCaducidad);
        panelPago.add(labelCodigoSeguridad);
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