package es.ingenieria.prog3.proyecto.gui;

import java.awt.*;

import javax.swing.*;

public class Log1 extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private CardLayout cardLayout;
    private JPanel mainPanel;

	public Log1(CardLayout cardLayout, JPanel mainPanel) {
		
		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;
		
		
		//El layout del panel sera un borderLayout
		this.setLayout(new BorderLayout(10, 10));

		// Crear el panel para el centro del JFrame
		JPanel panelCentroLog = new JPanel(new GridLayout(1, 2));
		
        // Anadir el panel al centro del JFrame
        this.add(panelCentroLog, BorderLayout.CENTER);
        
        // Anadir la etiqueta de copyright al south del panel
        this.add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
        
        //Color de los paneles
        Color colorPaneles = new Color(246, 233, 211);
        
        //PARTE IZQUIERDA
        //--------------------------------------------------------------
        // Nuevo panel para la parte izquierda del log in (un BoxLayout)
        JPanel panelIzquierdaCentro = new JPanel();
        panelIzquierdaCentro.setLayout(new BoxLayout(panelIzquierdaCentro, BoxLayout.Y_AXIS));
        
        // Creamos los JLabel para la parte izquierda y le anadimos el icono       
        JLabel logo = new JLabel();
        ImageIcon icono = new ImageIcon("resources/images/Hotel Host 200x200.png");
        logo.setIcon(icono);
        JLabel labelDescripcion = new JLabel("TEXTO LARGO Y ABURRIDO");

        
        // Centramos los diferentes labels
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Colocamos los componenetes donde queremos en el panel
        panelIzquierdaCentro.add(Box.createVerticalStrut(30));
        panelIzquierdaCentro.add(logo);
        panelIzquierdaCentro.add(Box.createVerticalStrut(30));
        panelIzquierdaCentro.add(labelDescripcion);
        panelIzquierdaCentro.setBackground(colorPaneles);
        
      //Poner el borde del layout (top, left, bottom, right, color)
        panelIzquierdaCentro.setBorder(BorderFactory.createMatteBorder(140, 80, 150, 80, Color.WHITE));
        panelCentroLog.add(panelIzquierdaCentro);
        //--------------------------------------------------------------
        //FIN PARTE IZQUIERDA 
        
        
        // PARTE DERECHA
        //--------------------------------------------------------------
        // Nuevo panel para la parte derecha del log in (un BoxLayout)
        JPanel panelDerechaCentro = new JPanel();
        panelDerechaCentro.setLayout(new BoxLayout(panelDerechaCentro, BoxLayout.Y_AXIS));
        
        //Crear dos paneles (uno para el textfield de usuario y el otro para el de contraseña)
		JPanel panelUsuario = new JPanel(new FlowLayout());
		JPanel panelContrasena = new JPanel(new FlowLayout());
		
		//Crear las fuentes
		Font fuenteTextField = new Font("Verdana", Font.BOLD, 18);
		Font fuenteLabel = new Font("Verdana", Font.BOLD, 18);

		//Crear el textField del usuario
        JTextField textFieldUsuario = new JTextField();
        textFieldUsuario.setPreferredSize(new Dimension(300, 50));
        textFieldUsuario.setMaximumSize(new Dimension(300, 50));
        textFieldUsuario.setFont(fuenteTextField);
        
        //Creamos los JLabel para la parte izquierda del TextField del usuario
		JLabel labelUsuario = new JLabel("Usuario: ");
		labelUsuario.setFont(fuenteLabel);
		
		//Anadir todo al panel del usuario y especificar las dimensiones y el fondo
		panelUsuario.add(labelUsuario);
		panelUsuario.add(textFieldUsuario);
		panelUsuario.setPreferredSize(new Dimension(500, 70));
		panelUsuario.setMaximumSize(new Dimension(500, 70));
		panelUsuario.setBackground(colorPaneles);
		
		//Crear el textField del usuario
		JPasswordField passwordFieldContrasena = new JPasswordField();
        passwordFieldContrasena.setPreferredSize(new Dimension(300, 50));
        passwordFieldContrasena.setMaximumSize(new Dimension(300, 50));
        passwordFieldContrasena.setFont(fuenteTextField);
        
        //Creamos los JLabel para la parte izquierda del passwordField de la contrasena
		JLabel labelContrasena = new JLabel("Contraseña: ");
		labelContrasena.setFont(fuenteLabel);

		//Creamos un checkBox que nos permitira visualizar o dejar de visualizar el contenido de la contraseña
        JCheckBox checkBox = new JCheckBox();
        //Creamos las imagenes y las añadimos (una para mostrar y otra para ocultar)
        ImageIcon  imagenNoVer = new ImageIcon("resources/images/imagenNoVer.png");
        ImageIcon imagenVer = new ImageIcon("resources/images/imagenVer.png");
        checkBox.setBackground(colorPaneles);
        checkBox.setIcon(imagenNoVer);
        checkBox.setSelectedIcon(imagenVer);
        //Anadimos el listener para que si el checkbox esta seleccionado muestre la contraseña y en caso contrario la oculte
        checkBox.addActionListener(e -> {
        	if (checkBox.isSelected())
        		passwordFieldContrasena.setEchoChar((char) 0);
        	else
        		passwordFieldContrasena.setEchoChar('\u2022');

        });
        
		//Anadir todo al panel de la contrasena y especificar las dimensiones y el fondo
        panelContrasena.add(labelContrasena);
        panelContrasena.add(passwordFieldContrasena);
        panelContrasena.add(checkBox);
        panelContrasena.setPreferredSize(new Dimension(600, 90));
        panelContrasena.setMaximumSize(new Dimension(600, 90));
        panelContrasena.setBackground(colorPaneles);
		

       //Creamos y modificamos los botones de login, de haber olvidado la contrasena y el de registrarse
        JButton botonLogin = new JButton("LOG IN");
        botonLogin.setPreferredSize(new Dimension(150, 40));  // Ajusta el tamaño preferido
        botonLogin.setMaximumSize(new Dimension(150, 40)); 
        
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
        
        // Centramos los diferentes labels
        textFieldUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordFieldContrasena.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonOlvidarContrasena.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonRegistrarse.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Creamos una barra horizontal para separar los botones de login y olvidar contraseña del de registrarse
        JSeparator barraSeparadora = new JSeparator();
        barraSeparadora.setBackground(Color.BLACK);
        barraSeparadora.setOrientation(SwingConstants.HORIZONTAL);
        
        //Colocamos los componenetes donde queremos en el panel
        panelDerechaCentro.add(Box.createVerticalStrut(90));
        panelDerechaCentro.add(panelUsuario);
        panelDerechaCentro.add(Box.createVerticalStrut(20));
        panelDerechaCentro.add(panelContrasena);
        panelDerechaCentro.add(Box.createVerticalStrut(0));
        panelDerechaCentro.add(botonLogin);
        panelDerechaCentro.add(Box.createVerticalStrut(30));
        panelDerechaCentro.add(botonOlvidarContrasena);
        panelDerechaCentro.add(Box.createVerticalStrut(15));
        panelDerechaCentro.add(barraSeparadora);
        panelDerechaCentro.add(Box.createVerticalStrut(10));
        panelDerechaCentro.add(botonRegistrarse);
        
        
      //Poner el borde del layout (top, left, bottom, right, color)
        panelDerechaCentro.setBorder(BorderFactory.createMatteBorder(100, 40, 100, 40, Color.WHITE));
        panelDerechaCentro.setBackground(new Color(246, 233, 211));
        panelCentroLog.add(panelDerechaCentro);
      //--------------------------------------------------------------
      //FIN PARTE DERECHA 
        
	}
		
}
