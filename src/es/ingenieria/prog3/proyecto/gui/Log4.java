package es.ingenieria.prog3.proyecto.gui;

import java.awt.*;

import javax.swing.*;

	public class Log4 extends JPanel{
		
		private static final long serialVersionUID = 1L;
		private CardLayout cardLayout;
	    private JPanel mainPanel;
	    
	    Font fuenteTextField = new Font("Verdana", Font.BOLD, 18);
		Font fuenteLabel = new Font("Verdana", Font.BOLD, 18);
	    
		public Log4(CardLayout cardLayout1, JPanel mainPanel) {
			
			// Zona norte (logo)
			JLabel logo = new JLabel();
			ImageIcon imagen = new ImageIcon("resources/images/icono.png");
			logo.setIcon(imagen);
			
			this.add(logo, BorderLayout.NORTH);
			
			// SECCION CENTRO (Formulario)
			JPanel panelCentral = new JPanel(new BorderLayout());
			JLabel textoRecuperacion = new JLabel("Reestablecer contraseña");
			textoRecuperacion.setHorizontalAlignment(0);
			panelCentral.add(textoRecuperacion, BorderLayout.NORTH);
			JPanel textosPanel = new JPanel();
	        textosPanel.setLayout(new GridLayout(4,3,10,10));
			
			//creamos la nueva seccion para establecer una nueva contraseña
			JLabel labelNewContra = new JLabel("Nueva Contraseña: ");
			labelNewContra.setFont(fuenteLabel);
			labelNewContra.setPreferredSize(new Dimension(300, 50));
			labelNewContra.setMaximumSize(new Dimension(300, 50));
			JPasswordField passwordFieldNewContrasena = new JPasswordField();
	        passwordFieldNewContrasena.setPreferredSize(new Dimension(300, 50));
	        passwordFieldNewContrasena.setMaximumSize(new Dimension(300, 50));
	        passwordFieldNewContrasena.setFont(fuenteTextField);


	        //creamos la nueva seccion para repetir la nueva contraseña
	        JLabel labelRepeatContra = new JLabel("Repite la contraseña: ");
	        labelRepeatContra.setFont(fuenteLabel);
	        JPasswordField passwordFieldRepContrasena = new JPasswordField();
	        passwordFieldRepContrasena.setPreferredSize(new Dimension(300, 50));
	        passwordFieldRepContrasena.setMaximumSize(new Dimension(300, 50));
	        passwordFieldRepContrasena.setFont(fuenteTextField);
	        
	        JCheckBox checkBox = new JCheckBox();
	        ImageIcon  imagenNoVer = new ImageIcon("resources/images/imagenNoVer.png");
	        ImageIcon imagenVer = new ImageIcon("resources/images/imagenVer.png");
	        checkBox.setIcon(imagenNoVer);
	        checkBox.setSelectedIcon(imagenVer);
	        checkBox.addActionListener(e -> {
	        	if (checkBox.isSelected()) {
	        		passwordFieldNewContrasena.setEchoChar((char) 0);
	        		passwordFieldRepContrasena.setEchoChar((char) 0);
	        	}
	        	else {
	        		passwordFieldNewContrasena.setEchoChar('\u2022');
	        		passwordFieldRepContrasena.setEchoChar('\u2022');
	        	}

	        });
	        textosPanel.add(Box.createVerticalGlue());
	        textosPanel.add(labelNewContra);
	        textosPanel.add(Box.createVerticalStrut(-1));
	        textosPanel.add(passwordFieldNewContrasena);
	        textosPanel.add(Box.createVerticalStrut(-1));
	        textosPanel.add(labelRepeatContra);
	        textosPanel.add(Box.createVerticalStrut(-1));
	        textosPanel.add(passwordFieldRepContrasena);
	        textosPanel.add(Box.createVerticalStrut(-1));
	        textosPanel.add(checkBox);
	        textosPanel.add(Box.createVerticalStrut(-1));
	        textosPanel.setPreferredSize(new Dimension(600, 90));
	        textosPanel.add(Box.createVerticalStrut(-5));
	        textosPanel.setMaximumSize(new Dimension(600, 90));
	        
	        
	        
			
			
	        //creamos botones de aceptar y cancelar
			JButton aceptarButton = new JButton("Aceptar");
			JButton cancelarButton = new JButton("Cancelar");
			
			//añadimos todos los elemtnos anteriores al panel
			textosPanel.add(aceptarButton);
			textosPanel.add(cancelarButton);
			panelCentral.add(textosPanel, BorderLayout.SOUTH);
			
			this.add(panelCentral, BorderLayout.CENTER);
			
			// SECCION SUR (Copyright)
			JLabel copyrightLabel = new JLabel("Hotel Host® 2024");
			this.add(copyrightLabel, BorderLayout.SOUTH);
			
		}
	
}
