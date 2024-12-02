package es.ingenieria.prog3.proyecto.gui;

import javax.swing.*;

import es.ingenieria.prog3.proyecto.gui.util.DataStore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class DialogPago extends JDialog {
    
	private static final long serialVersionUID = 1L;
	private Thread hiloParpadeoTarjeta;
	
	public DialogPago() {
		setLayout(new BorderLayout()); // Cambiar layout del diálogo principal
		
        JPanel panelTemporizador = new JPanel();
		JLabel labelTemporizador = new JLabel();
		labelTemporizador.setFont(new Font("Verdana", Font.BOLD, 14));
		labelTemporizador.setToolTipText("Finaliza el pago antes que acabe el temporizador o tu reserva no se procesara");;

		
		Thread hiloTemporizador = new Thread(() -> {
			for (int i = 300; i >= 0; i--) {
				int tiempoRestante = i;
				SwingUtilities.invokeLater( () -> {
					labelTemporizador.setText(String.format("Tiempo restante para realizar el pago %02d:%02d minutos", tiempoRestante / 60, tiempoRestante % 60));
					if (tiempoRestante > 180){
						labelTemporizador.setForeground(new Color(0,128,0));
					} else if (tiempoRestante > 60) {
						labelTemporizador.setForeground(Color.ORANGE);
					} else {
						labelTemporizador.setForeground(Color.RED);
					}
				});
				try {
					// Se duerme el hilo durante 1 segundo
					Thread.sleep(100);
				} catch (InterruptedException e) {
					//
				}
			}
			JOptionPane.showMessageDialog(null, "Se ha acabado el tiempo", "FIN DEL TIEMPO", JOptionPane.WARNING_MESSAGE);
			Thread.currentThread().interrupt();
			dispose();
		});
		
		hiloTemporizador.start();
		
		JPanel panelPago = new JPanel(new GridLayout(6, 1, 20, 5));
        
        //Creamos todo lo relacionado con la tarjeta
        JLabel labelTarjeta = new JLabel("Introduce tu tarjeta: ");
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
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
        JLabel labelFechaCaducidad = new JLabel("Introduce la fecha de caducidad de tu tarjeta (MM/AA): ");
        JPanel panelFechaCaducidad = new JPanel();
        panelFechaCaducidad.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
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
        panelCodigoSeguridad.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        JTextField textFieldCodigoSeguridad = new JTextField();
        textFieldCodigoSeguridad.setColumns(5);
        //Imagen de candado
        JLabel imagenCodigoSeguridad = new JLabel();
        imagenCodigoSeguridad.setBounds(30, 30, 0, 0);
		ImageIcon originalIcon3 = new ImageIcon("resources/images/candado.png");
		Image scaledImage3 = originalIcon3.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon3 = new ImageIcon(scaledImage3);
		imagenCodigoSeguridad.setIcon(resizedIcon3);
		
		// Los textFields solo admitiran numeros y el caracter '/'
		KeyListener keyListenerTextFields = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				 char c = e.getKeyChar();
	                if (!Character.isDigit(c) && c != '/') {
	                    e.consume(); // Ignora la tecla
	                }				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		JTextField[] textFields = {textFieldTarjeta, textFieldFechaCaducidad, textFieldFechaCaducidad};
		for (int i = 0; i < textFields.length; i++) {
			textFields[i].addKeyListener(keyListenerTextFields);
		}
		
		//Creamos un listener para cada textField para que cuando este seleccionado el icono parpadee
		textFieldTarjeta.addFocusListener(new FocusListener() {
	            @Override
	            public void focusGained(FocusEvent e) {
	            	//Hilo para el parpadeo
	            	hiloParpadeoTarjeta = new ParpadeoThread(labelTarjeta);
	            	//Iniciamos el hilo
	            	hiloParpadeoTarjeta.start();
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
	            	// Interrumpir el hilo de parpadeo
	                if (hiloParpadeoTarjeta != null && hiloParpadeoTarjeta.isAlive()) {
	                    hiloParpadeoTarjeta.interrupt();
	                }
	                // Asegurarse de que el label sea visible
	                SwingUtilities.invokeLater(() -> labelTarjeta.setVisible(true));
	            }
	        });
		
		textFieldFechaCaducidad.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	//Hilo para el parpadeo
            	hiloParpadeoTarjeta = new ParpadeoThread(labelFechaCaducidad);
            	//Iniciamos el hilo
            	hiloParpadeoTarjeta.start();
            }

            @Override
            public void focusLost(FocusEvent e) {
            	// Interrumpir el hilo de parpadeo
                if (hiloParpadeoTarjeta != null && hiloParpadeoTarjeta.isAlive()) {
                	hiloParpadeoTarjeta.interrupt();
                }
                // Asegurarse de que el label sea visible
                SwingUtilities.invokeLater(() -> labelFechaCaducidad.setVisible(true));
            }
        });
		
		textFieldCodigoSeguridad.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	//Hilo para el parpadeo
            	hiloParpadeoTarjeta = new ParpadeoThread(labelCodigoSeguridad);
            	//Iniciamos el hilo
            	hiloParpadeoTarjeta.start();
            }

            @Override
            public void focusLost(FocusEvent e) {
            	// Interrumpir el hilo de parpadeo
                if (hiloParpadeoTarjeta != null && hiloParpadeoTarjeta.isAlive()) {
                	hiloParpadeoTarjeta.interrupt();
                }
                // Asegurarse de que el label sea visible
                SwingUtilities.invokeLater(() -> labelCodigoSeguridad.setVisible(true));
            }
        });
		
		//Añadimos un toolTipText a cada textField para que el usuario vea las condiciones del input
		textFieldTarjeta.setToolTipText("Introduce un numero de tarjeta valido (10 o mas numeros");
		textFieldFechaCaducidad.setToolTipText("Introduce la fecha de caducidad en el formato especificado (mm/aa)");
		textFieldCodigoSeguridad.setToolTipText("Introduce el codigo de seguridad (3 digitos)");
		
        // Panel para los botones
        JPanel panelBotones = new JPanel();
        JButton botonCancelar = new JButton("Cancelar");
        JButton botonConfirmar = new JButton("Confirmar");
        
        botonCancelar.addActionListener(e -> {
        	hiloTemporizador.interrupt();
        	dispose();
        });
        
        botonConfirmar.addActionListener(e -> {
        	if (textFieldTarjeta.getText().isEmpty() || textFieldFechaCaducidad.getText().isEmpty() || textFieldCodigoSeguridad.getText().isEmpty()) {
        		JOptionPane.showMessageDialog(null, "Atencion, debes rellenar todos los campos", "CAMPOS VACIOS", JOptionPane.WARNING_MESSAGE);
        	} else {
        		int mes = Integer.valueOf(textFieldFechaCaducidad.getText().split("/")[0]);
            	if (textFieldTarjeta.getText().length() >= 10 && (mes > 0 && mes <= 12) && textFieldCodigoSeguridad.getText().length() == 3) {
            		//Procesar la reserva en la base de datos
            		JOptionPane.showMessageDialog(null, "Felicidades, tu reserva se ha procesado correctamente", "RESERVA REALIZADA", JOptionPane.INFORMATION_MESSAGE);
        			DataStore.setVisible(true);
            		dispose();
            	} else {
            		JOptionPane.showMessageDialog(null, "Cuidado, alguno de los campos es erroneo", "CAMPOS ERRONEOS", JOptionPane.ERROR_MESSAGE);

            	}
        	}
        });
        
        //Añadimos espacio a los labels
        labelTarjeta.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        labelFechaCaducidad.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        labelCodigoSeguridad.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        //Añadir los componentes a los paneles
        panelTemporizador.add(labelTemporizador);
        
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
        add(panelTemporizador, BorderLayout.NORTH);
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
	
	class ParpadeoThread extends Thread {
	    private JLabel label;

	    public ParpadeoThread(JLabel label) {
	        this.label = label;
	    }

	    @Override
	    public void run() {
	    	try {
                while (!Thread.currentThread().isInterrupted()) {
                    // Cambiar la visibilidad (si estaba visible a no visible y viceversa)
                    SwingUtilities.invokeLater(() -> label.setVisible(!label.isVisible()));
                    Thread.sleep(500); // Pausar 500 ms para el efecto de parpadeo
                }
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt(); // Mantener el estado de interrupción
            }
	    }
	}
	
}