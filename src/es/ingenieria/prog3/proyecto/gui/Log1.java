package es.ingenieria.prog3.proyecto.gui;

import java.awt.*;
import javax.swing.*;

import es.ingenieria.prog3.proyecto.gui.util.JCheckBoxListener;
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
		logo.setBounds((int) ((1152 * 0.25) - (logo.getWidth() / 2)), (int) ((720 * 0.4) - (logo.getHeight() / 2)), logo.getWidth(), logo.getHeight());
		ImageIcon originalIcon = new ImageIcon("resources/images/Hotel Host.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(300, 225, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(scaledImage);
        logo.setIcon(resizedIcon);
        add(logo, BorderLayout.CENTER);
        
        //Creamos el JLabel que contendra una breve descripcion de la pagina
        JLabel labelDescripcion = new JLabel("<html><div style='text-align: center;'>Unete a Millones de usuarios que han disfrutado de viajes con nosotros", JLabel.CENTER);
        labelDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
        labelDescripcion.setVerticalAlignment(SwingConstants.TOP);
        labelDescripcion.setBounds(logo.getX(), logo.getY() + logo.getHeight() + 25, 300, 150);
        labelDescripcion.setFont(Preferences.FONT);
        labelDescripcion.setForeground(Color.WHITE);
        add(labelDescripcion, BorderLayout.CENTER);
        
        //Creamos el panel de la parte de la izquierda, el cual contendra el textfield para el email y la contraseña
        //asi como los diferentes botones
        JPanelBordesRedondos panelnuevousuario = new JPanelBordesRedondos(25);
        panelnuevousuario.setBounds(0, 0, (int) (1152 * 0.4), (int) ((720 * 0.6)));
        panelnuevousuario.setBounds((int) ((1152 * 0.7) - (panelnuevousuario.getWidth() / 2)), (int) ((720 * 0.5) - (panelnuevousuario.getHeight() / 2)) - 25, panelnuevousuario.getWidth(), panelnuevousuario.getHeight());
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
        

        JTextFieldDefaultText textFieldEmail = new JTextFieldDefaultText("Email");
        textFieldEmail.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 130, (int) (panelnuevousuario.getWidth() * 0.88), 50);
        panelnuevousuario.add(textFieldEmail);
        

        
        JCheckBox checkBoxContresena = new JCheckBox();
        checkBoxContresena.setBackground(Color.WHITE);
        checkBoxContresena.setIcon(new ImageIcon("resources/images/imagenVer.png"));
        checkBoxContresena.setSelectedIcon(new ImageIcon("resources/images/imagenNoVer.png"));
        checkBoxContresena.setBounds((int) (panelnuevousuario.getWidth() * 0.81), 200, (int) (panelnuevousuario.getWidth() * 0.22), 50);
        panelnuevousuario.add(checkBoxContresena);
        
        JPasswordFieldDefaultText textFieldContrasena = new JPasswordFieldDefaultText("Contraseña", checkBoxContresena);
        textFieldContrasena.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 200, (int) (panelnuevousuario.getWidth() * 0.68), 50);
        panelnuevousuario.add(textFieldContrasena);
        

      //Creamos y modificamos los botones de login, de haber olvidado la contrasena y el de registrarse
        JButton botonLogin = new JButton("LOG IN");
        textFieldContrasena.setBounds((int) (panelnuevousuario.getWidth() * 0.06), 200, (int) (panelnuevousuario.getWidth() * 0.68), 50);
        panelnuevousuario.add(textFieldContrasena);
      //Anadimos el listener para que si se pulsa el boton vaya a la pantalla de modificar contrasena
        botonLogin.addActionListener(e -> {
        	String inputUsuario = textFieldEmail.getText();
        	String inputContrasena = String.valueOf(textFieldContrasena.getPassword());
        	if (inputUsuario.equals("usuario") && inputContrasena.equals("contrasena")) {
        		System.out.println("Has llegado a la ventana de la aplicacion");
        		// this.cardLayout.show(this.mainPanel, "");
        	} else {
        		JOptionPane.showMessageDialog(null, "La contraseña es incorrecta", "CONTRASEÑA INCORRECTA", JOptionPane.WARNING_MESSAGE, null);
        	}
        });
        
        JButton botonOlvidarContrasena = new JButton("¿Has olvidado la contraseña?");
        botonOlvidarContrasena.setPreferredSize(new Dimension(250, 40));  // Ajusta el tamaño preferido
        botonOlvidarContrasena.setMaximumSize(new Dimension(250, 40));
        //Anadimos el listener para que si se pulsa el boton vaya a la pantalla de modificar contrasena
        botonOlvidarContrasena.addActionListener(e -> this.cardLayout.show(this.mainPanel, "Log3"));
        
        JButton botonRegistrarse = new JButton("Registrarse");
        botonRegistrarse.setPreferredSize(new Dimension(150, 40));  // Ajusta el tamaño preferido
        botonRegistrarse.setMaximumSize(new Dimension(150, 40));
      //Anadimos el listener para que si se pulsa el boton vaya a la pantalla de modificar contrasena
        botonRegistrarse.addActionListener(e -> this.cardLayout.show(this.mainPanel, "Log2"));
        
        
        add(panelnuevousuario, BorderLayout.CENTER);
        
		add(panelCentro, BorderLayout.CENTER); //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel
		
        // Panel principal sur
        add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
	}
		
}
