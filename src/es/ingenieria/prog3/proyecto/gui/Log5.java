package es.ingenieria.prog3.proyecto.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import es.ingenieria.prog3.proyecto.gui.util.JPanelBordesRedondos;
import es.ingenieria.prog3.proyecto.gui.util.JTextFieldDefaultText;
import es.ingenieria.prog3.proyecto.gui.util.Preferences;

public class Log5 extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private CardLayout cardLayout;
    private JPanel mainPanel;
	private static int codigo;

    public static void setCodigo(int codigo) {
		Log5.codigo = codigo;
	}

	//Clase basada en la clase log1
	@SuppressWarnings("unlikely-arg-type")
	public Log5(CardLayout cardLayout, JPanel mainPanel) {
		
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
        
        //Creamos el panel de la parte de la izquierda, el cual contendra el textfield para el email y la contraseña
        //asi como los diferentes botones
        JPanelBordesRedondos panelnuevousuario = new JPanelBordesRedondos(25);
        panelnuevousuario.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.4), (int) ((Preferences.WINDOWHEIGHT * 0.4)));
        panelnuevousuario.setBounds((int) ((Preferences.WINDOWWIDTH * 0.5) - (panelnuevousuario.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.68) - (panelnuevousuario.getHeight() / 2)) - 25, panelnuevousuario.getWidth(), panelnuevousuario.getHeight());
        panelnuevousuario.setBackground(Color.WHITE);
        panelnuevousuario.setLayout(null);
        
        //Creamos el label mostrando que estamos en la pagina de inicio de sesion
        JLabel labelnuevacuenta = new JLabel("Verifica tu identidad");
        labelnuevacuenta.setHorizontalAlignment(SwingConstants.CENTER);
        labelnuevacuenta.setVerticalAlignment(SwingConstants.TOP);
        labelnuevacuenta.setBounds(0, 25, panelnuevousuario.getWidth(), 150);
        labelnuevacuenta.setFont(Preferences.FONT);
        panelnuevousuario.add(labelnuevacuenta);
        
        //Creamos el label mostrando un breve mensaje
        JLabel labelnuevacuentamensaje = new JLabel("Introduce el código recibido por email");
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
        
        JLabel labelfechanacimiento = new JLabel("Introduce tu codigo");
        labelfechanacimiento.setHorizontalAlignment(SwingConstants.LEFT);
        labelfechanacimiento.setVerticalAlignment(SwingConstants.TOP);
        labelfechanacimiento.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 130, panelnuevousuario.getWidth(), 150);
        labelfechanacimiento.setFont(new Font("Verdana", Font.PLAIN, 10));
        panelnuevousuario.add(labelfechanacimiento);
        
        //Creamos el textField para introducir el codigo (el email es lo necesario para iniciar sesion)
        JTextFieldDefaultText textFieldCodigo = new JTextFieldDefaultText("Codigo");
        textFieldCodigo.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 150, (int) (panelnuevousuario.getWidth() * 0.88), 50);
        panelnuevousuario.add(textFieldCodigo);
        
        //Creamos y modificamos los botones de login, de cambiar contrasena y el de registrarse
        JButton botonVolver = new JButton("Volver");
        botonVolver.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 220, (int) (panelnuevousuario.getWidth() * 0.42), 50);
        panelnuevousuario.add(botonVolver);
        
        //Anadimos el listener para que si se pulsa el boton vaya a la pantalla de modificar contrasena
        botonVolver.addActionListener(e -> this.cardLayout.show(this.mainPanel, "Log3"));
        
        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.setBounds((int) (panelnuevousuario.getWidth() * 0.52), 220, (int) (panelnuevousuario.getWidth() * 0.42), 50);
        panelnuevousuario.add(botonAceptar);
        
        //Anadimos el listener para que si se pulsa el boton vaya a la pantalla de modificar contrasena
        botonAceptar.addActionListener(e -> {
            if (textFieldCodigo.getText().equals(Integer.toString(codigo))) {
            	this.cardLayout.show(this.mainPanel, "Log4");
            }
        });
        
        // Creamos una barra horizontal para separar los botones de login y olvidar contraseña del de registrarse
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