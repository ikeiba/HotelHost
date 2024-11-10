package es.ingenieria.prog3.proyecto.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.*;

import es.ingenieria.prog3.proyecto.gui.util.EmailSender;
import es.ingenieria.prog3.proyecto.gui.util.JPanelBordesRedondos;
import es.ingenieria.prog3.proyecto.gui.util.JTextFieldDefaultText;
import es.ingenieria.prog3.proyecto.gui.util.Preferences;

public class Log3 extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private CardLayout cardLayout;
    private JPanel mainPanel;

    //Clase basada en la clase log1
	public Log3(CardLayout cardLayout, JPanel mainPanel) {
		
		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;
		
		//Ponemos el fondo del panel blanco y le asignamos el border layout como layoutManager
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());  
        
		//Creamos el panel principal centro con el color de fondo predeterminado y el layoutManager a null
		//esto nos permitira colocar los diferentes componentes por coordenadas mas facilmente
		JPanel panelCentro = new JPanel();
		panelCentro.setBackground(Preferences.COLORBACKGROUND);
		panelCentro.setLayout(null);
		
		//Creamos el logo , elegimos sus dimensiones y lo posicionamos en el lugar elegido
		JLabel logo = new JLabel();
		logo.setBounds(0, 0, 300, 225);
		logo.setBounds((int) ((Preferences.WINDOWWIDTH * 0.5) - (logo.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.2) - (logo.getHeight() / 2)), logo.getWidth(), logo.getHeight());
		ImageIcon originalIcon = new ImageIcon("resources/images/Hotel Host.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(300, 225, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(scaledImage);
        logo.setIcon(resizedIcon);
        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	cardLayout.show(mainPanel, "Log1");
            }
        }); 
        
        //Creamos el panel de la parte central, el cual contendra el textfield para el email y la nº de teléfono
        //asi como los diferentes botones
        JPanelBordesRedondos panelnuevousuario = new JPanelBordesRedondos(25);
        panelnuevousuario.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.4), (int) ((Preferences.WINDOWHEIGHT * 0.5)));
        panelnuevousuario.setBounds((int) ((Preferences.WINDOWWIDTH * 0.5) - (panelnuevousuario.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.68) - (panelnuevousuario.getHeight() / 2)) - 25, panelnuevousuario.getWidth(), panelnuevousuario.getHeight());
        panelnuevousuario.setBackground(Color.WHITE);
        panelnuevousuario.setLayout(null);
        
        //Creamos el label mostrando que estamos en la pagina de inicio de recuperado de contraseña
        JLabel labelnuevacuenta = new JLabel("Recupera tu contraseña");
        labelnuevacuenta.setHorizontalAlignment(SwingConstants.CENTER);
        labelnuevacuenta.setVerticalAlignment(SwingConstants.TOP);
        labelnuevacuenta.setBounds(0, 25, panelnuevousuario.getWidth(), 150);
        labelnuevacuenta.setFont(Preferences.FONT);
        panelnuevousuario.add(labelnuevacuenta);
        
        //Creamos el label mostrando un breve mensaje
        JLabel labelnuevacuentamensaje = new JLabel("Ingrese los datos para recupar la contraseña");
        labelnuevacuentamensaje.setHorizontalAlignment(SwingConstants.CENTER);
        labelnuevacuentamensaje.setVerticalAlignment(SwingConstants.TOP);
        labelnuevacuentamensaje.setBounds(0, 60, panelnuevousuario.getWidth(), 150);
        labelnuevacuentamensaje.setFont(new Font("Verdana", Font.PLAIN, 12));
        panelnuevousuario.add(labelnuevacuentamensaje);
        
        //Creamos una barra separadora para comenzar con los componentes editables
        JSeparator barraSeparadora = new JSeparator();
        barraSeparadora.setBackground(Color.LIGHT_GRAY);
        barraSeparadora.setBounds(0, 104, panelnuevousuario.getWidth(), 2);
        barraSeparadora.setOrientation(SwingConstants.HORIZONTAL);
        panelnuevousuario.add(barraSeparadora);
        
        //Creamos el textField para introducir el email
        JTextFieldDefaultText textFieldEmail = new JTextFieldDefaultText("Email");
        textFieldEmail.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 130, (int) (panelnuevousuario.getWidth() * 0.88), 50);
        panelnuevousuario.add(textFieldEmail);
        
        //Creamos el textField para introducir el telefono
        JTextFieldDefaultText textFieldTelefono = new JTextFieldDefaultText("Teléfono");
        textFieldTelefono.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 200, (int) (panelnuevousuario.getWidth() * 0.88), 50);
        panelnuevousuario.add(textFieldTelefono);
        
        //Creamos el boton que regresa a la pantalla de inicio de sesion
        JButton botonVolver = new JButton("Volver");
        botonVolver.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 270, (int) (panelnuevousuario.getWidth() * 0.42), 50);
        panelnuevousuario.add(botonVolver);
        
        //Anadimos el listener
        botonVolver.addActionListener(e -> this.cardLayout.show(this.mainPanel, "Log1"));
        
        //Creamos el boton que buscara coincidencias con los datos insegrados y en caso positivo avancara al crear nueva contraseña
        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.setBounds((int) (panelnuevousuario.getWidth() * 0.52), 270, (int) (panelnuevousuario.getWidth() * 0.42), 50);
        panelnuevousuario.add(botonAceptar);
        
        // Define an array of strings to match against
        String[] stringArray = {"@gmail.com", "@opendeusto.es", "@deusto.es"};

        // Add an ActionListener to the button as a lambda
        botonAceptar.addActionListener(e -> {
            String inputText = textFieldEmail.getText();
            
            // Check if inputText contains any part of the strings in stringArray
            boolean matchFound = false;
            for (String str : stringArray) {
                if (inputText.contains(str)) {
                    matchFound = true;
                    break;
                }
            }

            if (matchFound) {
                // Show JOptionPane with "Accept" and "Cancel" options
                int option = JOptionPane.showOptionDialog(
                		(JFrame) SwingUtilities.getWindowAncestor(botonAceptar),
                        "Reiniciar Contraseña",
                        "Quieres confirmar reiniciar tu contraseña?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Aceptar", "Cancelar"},
                        null
                );

                // If "Accept" is chosen, execute functions
                if (option == JOptionPane.YES_OPTION) {
                    Random random = new Random();
                    int codigo = 100000 + random.nextInt(900000);
                    EmailSender.sendEmail(textFieldEmail.getText(), "Código de confirmación de reinicio de contraseña", "Tu codigo es: " + codigo);
                    this.cardLayout.show(this.mainPanel, "Log5");
                    Log5.setCodigo(codigo);
                }
                // "Cancel" will close the dialog automatically
            }
        });
        
        // Creamos una barra horizontal estetica
        JSeparator barraSeparadora2 = new JSeparator();
        barraSeparadora2.setBounds(0, 0, (int) (panelnuevousuario.getWidth()*0.7), 90);
        barraSeparadora2.setBounds((int) ((panelnuevousuario.getWidth() * 0.5) - (barraSeparadora2.getWidth() / 2)), 340, barraSeparadora2.getWidth(), barraSeparadora2.getHeight());
        panelnuevousuario.add(barraSeparadora2);
        
        //Añadimos los componentes al panelCentro y el panelCentro al centro del Panel de la clase (this)
        panelCentro.add(logo);
		panelCentro.add(panelnuevousuario);
		this.add(panelCentro, BorderLayout.CENTER); //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel

        // Panel principal sur
        this.add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
	}
}