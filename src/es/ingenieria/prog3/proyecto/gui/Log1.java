package es.ingenieria.prog3.proyecto.gui;

import java.awt.*;
import javax.swing.*;

import es.ingenieria.prog3.proyecto.gui.util.JPanelBordesRedondos;
import es.ingenieria.prog3.proyecto.gui.util.JPasswordFieldDefaultText;
import es.ingenieria.prog3.proyecto.gui.util.JTextFieldDefaultText;
import es.ingenieria.prog3.proyecto.gui.util.Preferences;

//Clase basada en la clase log2
public class Log1 extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private CardLayout cardLayout;
    private JPanel mainPanel;

	public Log1(CardLayout cardLayout, JPanel mainPanel) {
		
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
		logo.setBounds((int) ((Preferences.WINDOWWIDTH * 0.25) - (logo.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.4) - (logo.getHeight() / 2)), logo.getWidth(), logo.getHeight());
		ImageIcon originalIcon = new ImageIcon("resources/images/Hotel Host.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(300, 225, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(scaledImage);
        logo.setIcon(resizedIcon);
        
        //Creamos el JLabel que contendra una breve descripcion de la pagina
        JLabel labelDescripcion = new JLabel("<html><div style='text-align: center;'>Unete a Millones de usuarios que han disfrutado de viajes con nosotros", JLabel.CENTER);
        labelDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
        labelDescripcion.setVerticalAlignment(SwingConstants.TOP);
        labelDescripcion.setBounds(logo.getX(), logo.getY() + logo.getHeight() + 25, 300, 150);
        labelDescripcion.setFont(Preferences.FONT);
        labelDescripcion.setForeground(Color.WHITE);
        
        //Creamos el panel de la parte de la izquierda, el cual contendra el textfield para el email y la contraseña
        //asi como los diferentes botones
        JPanelBordesRedondos panelnuevousuario = new JPanelBordesRedondos(25);
        panelnuevousuario.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.4), (int) ((Preferences.WINDOWHEIGHT * 0.6)));
        panelnuevousuario.setBounds((int) ((Preferences.WINDOWWIDTH * 0.7) - (panelnuevousuario.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.5) - (panelnuevousuario.getHeight() / 2)) - 25, panelnuevousuario.getWidth(), panelnuevousuario.getHeight());
        panelnuevousuario.setBackground(Color.WHITE);
        panelnuevousuario.setLayout(null);
        
        //Creamos el label mostrando que estamos en la pagina de inicio de sesion
        JLabel labelnuevacuenta = new JLabel("Iniciar sesion");
        labelnuevacuenta.setHorizontalAlignment(SwingConstants.CENTER);
        labelnuevacuenta.setVerticalAlignment(SwingConstants.TOP);
        labelnuevacuenta.setBounds(0, 25, panelnuevousuario.getWidth(), 150);
        labelnuevacuenta.setFont(Preferences.FONT);
        panelnuevousuario.add(labelnuevacuenta);
        
        //Creamos el label mostrando un breve mensaje
        JLabel labelnuevacuentamensaje = new JLabel("Accede a tu cuenta y disfruta");
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
        
        //Creamos el textField para introducir el email (el email es lo necesario para iniciar sesion)
        JTextFieldDefaultText textFieldEmail = new JTextFieldDefaultText("Email");
        textFieldEmail.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 130, (int) (panelnuevousuario.getWidth() * 0.88), 50);
        panelnuevousuario.add(textFieldEmail);
        
        //Creamos el checkbox para poder ver o dejar de ver la contraseña
        JCheckBox checkBoxContrasena = new JCheckBox();
        checkBoxContrasena.setBackground(Color.WHITE);
        checkBoxContrasena.setIcon(new ImageIcon("resources/images/imagenVer.png"));
        checkBoxContrasena.setSelectedIcon(new ImageIcon("resources/images/imagenNoVer.png"));
        checkBoxContrasena.setBounds((int) (panelnuevousuario.getWidth() * 0.81), 200, (int) (panelnuevousuario.getWidth() * 0.22), 50);
        panelnuevousuario.add(checkBoxContrasena);
        
        //Creamos el textField para introducir la contraseña 
        JPasswordFieldDefaultText textFieldContrasena = new JPasswordFieldDefaultText("Contraseña", checkBoxContrasena);
        textFieldContrasena.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 200, (int) (panelnuevousuario.getWidth() * 0.68), 50);
        panelnuevousuario.add(textFieldContrasena);
        
        //Creamos y modificamos los botones de login, de cambiar contrasena y el de registrarse
        JButton botonLogin = new JButton("LOG IN");
        botonLogin.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 270, (int) (panelnuevousuario.getWidth() * 0.42), 50);
        panelnuevousuario.add(botonLogin);
        
        //Anadimos el listener para que si se pulsa el boton vaya a la pantalla de modificar contrasena
        botonLogin.addActionListener(e -> {
        	String inputUsuario = textFieldEmail.getText();
        	String inputContrasena = String.valueOf(textFieldContrasena.getPassword());
        	if (inputUsuario.equals("Email") && inputContrasena.equals("123")) {
        		System.out.println("Has llegado a la ventana de la aplicacion");
        		this.cardLayout.show(this.mainPanel, "Home");
        	} else {
        		JOptionPane.showMessageDialog(null, "La contraseña es incorrecta", "CONTRASEÑA INCORRECTA", JOptionPane.ERROR_MESSAGE, null);
        	}
        });
        
        JButton botonOlvidarContrasena = new JButton("Recuperar Contraseña");
        botonOlvidarContrasena.setBounds((int) (panelnuevousuario.getWidth() * 0.52), 270, (int) (panelnuevousuario.getWidth() * 0.42), 50);
        panelnuevousuario.add(botonOlvidarContrasena);
        
        //Anadimos el listener para que si se pulsa el boton vaya a la pantalla de modificar contrasena
        botonOlvidarContrasena.addActionListener(e -> this.cardLayout.show(this.mainPanel, "Busqueda"));
        
        JButton botonRegistrarse = new JButton("Registrarse");
        botonRegistrarse.setBounds(0, 0, (int) (panelnuevousuario.getWidth()*0.5), 50);
        botonRegistrarse.setBounds((int) ((panelnuevousuario.getWidth() * 0.5) - (botonRegistrarse.getWidth() / 2)), 360, botonRegistrarse.getWidth(), botonRegistrarse.getHeight());
        panelnuevousuario.add(botonRegistrarse);
        //Anadimos el listener para que si se pulsa el boton vaya a la pantalla de modificar contrasena
        botonRegistrarse.addActionListener(e -> this.cardLayout.show(this.mainPanel, "Log2"));
        
        // Creamos una barra horizontal para separar los botones de login y olvidar contraseña del de registrarse
        JSeparator barraSeparadora2 = new JSeparator();
        barraSeparadora2.setBounds(0, 0, (int) (panelnuevousuario.getWidth()*0.7), 90);
        barraSeparadora2.setBounds((int) ((panelnuevousuario.getWidth() * 0.5) - (barraSeparadora2.getWidth() / 2)), 340, barraSeparadora2.getWidth(), barraSeparadora2.getHeight());
        panelnuevousuario.add(barraSeparadora2);
        
        //Añadimos los componentes al panelCentro y el panelCentro al centro del Panel de la clase (this)
        panelCentro.add(logo);
		panelCentro.add(panelnuevousuario);
		panelCentro.add(labelDescripcion);
		this.add(panelCentro, BorderLayout.CENTER); //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel

        // Panel principal sur
        this.add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
        
	}
		
}
