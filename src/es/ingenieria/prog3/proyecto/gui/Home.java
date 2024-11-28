package es.ingenieria.prog3.proyecto.gui;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import es.ingenieria.prog3.proyecto.gui.util.DataStore;
import es.ingenieria.prog3.proyecto.gui.util.JPanelBordesRedondos;
import es.ingenieria.prog3.proyecto.gui.util.JSeparatorPunteada;
import es.ingenieria.prog3.proyecto.gui.util.Preferences;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Home extends JPanel {

	private CardLayout cardLayout;
	private JPanel mainPanel;

	public Home(CardLayout cardLayout, JPanel mainPanel) {
		
		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());  
        
		//Panel principal centro
		JPanel panelCentro = new JPanel();
		panelCentro.setBackground(Preferences.COLORBACKGROUND);
		panelCentro.setLayout(null);
		
		JLabel logo = new JLabel();
		logo.setBounds(0, 0, 50, 35);
		logo.setBounds(40, 18, logo.getWidth(), logo.getHeight());
		ImageIcon originalIcon = new ImageIcon("resources/images/Hotel Host.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(50, 35, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(scaledImage);
        logo.setIcon(resizedIcon);
        add(logo, BorderLayout.CENTER);
        
        JSeparatorPunteada barraSeparadora = new JSeparatorPunteada();
        barraSeparadora.setBackground(Color.BLACK);
        barraSeparadora.setBounds(0, 75, Preferences.WINDOWWIDTH, 2);
        barraSeparadora.setOrientation(SwingConstants.HORIZONTAL);
        add(barraSeparadora, BorderLayout.CENTER);
        
        JLabel labelorganiza = new JLabel("Organiza tu viaje");
        labelorganiza.setHorizontalAlignment(SwingConstants.LEFT);
        labelorganiza.setVerticalAlignment(SwingConstants.TOP);
        labelorganiza.setBounds(40, 115, 500, 150);
        labelorganiza.setFont(new Font("Verdana", Font.BOLD, 35));
        labelorganiza.setForeground(Color.WHITE);
        add(labelorganiza, BorderLayout.CENTER);
        
        JPanelBordesRedondos panelbuscar = new JPanelBordesRedondos(25);
        panelbuscar.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.92), 70);
        panelbuscar.setBounds((int) ((Preferences.WINDOWWIDTH * 0.5) - (panelbuscar.getWidth() / 2)) - 5, 175, panelbuscar.getWidth() - 10, panelbuscar.getHeight());
        panelbuscar.setBackground(Color.WHITE);
        panelbuscar.setLayout(null);
        
        String[] nombrespaises = {"", "Estados Unidos", "Francia", "Italia", "Tailandia", "China", "Vietnam"};
        JComboBox<String> JComboBoxPaises = new JComboBox<>(nombrespaises);
        JComboBoxPaises.setBackground(Color.WHITE);
        JComboBoxPaises.setBounds((int) (panelbuscar.getWidth() * 0.01), 10, (int) (panelbuscar.getWidth() * 0.19), 50);
        panelbuscar.add(JComboBoxPaises);
        
        String[] nombresciudades = {"", "Paris", "Las Vegas", "Los Angeles", "Roma", "Bangkok", "Hanoi", "Hong Kong", "Miami", "Nueva York", "Pattaya"};
        JComboBox<String> JComboBoxCiudades = new JComboBox<>(nombresciudades);
        JComboBoxCiudades.setBackground(Color.WHITE);
        JComboBoxCiudades.setBounds((int) (panelbuscar.getWidth() * 0.21), 10, (int) (panelbuscar.getWidth() * 0.19), 50);
        JComboBoxCiudades.setEnabled(false);
        panelbuscar.add(JComboBoxCiudades);
        

        // Mapa de países y sus ciudades asociadas
        HashMap<String, String[]> ciudadesPorPais = new HashMap<>();
        ciudadesPorPais.put("Estados Unidos", new String[]{"Las Vegas", "Los Angeles", "Miami", "Nueva York"});
        ciudadesPorPais.put("Francia", new String[]{"Paris"});
        ciudadesPorPais.put("Italia", new String[]{"Roma"});
        ciudadesPorPais.put("Tailandia", new String[]{"Bangkok", "Pattaya"});
        ciudadesPorPais.put("China", new String[]{"Hong Kong"});
        ciudadesPorPais.put("Vietnam", new String[]{"Hanoi"});

        // Listener para el JComboBox de países
        JComboBoxPaises.addActionListener( e -> {

        	String paisSeleccionado = (String) JComboBoxPaises.getSelectedItem();

            // Limpiar el JComboBox de ciudades
            JComboBoxCiudades.removeAllItems();

            if (paisSeleccionado != null && !paisSeleccionado.isEmpty()) {
                // Poner las ciudades correspondientes
                JComboBoxCiudades.setEnabled(true);
                if (ciudadesPorPais.containsKey(paisSeleccionado)) {
                	// Si hay mas de una ciudad poner una opcion para todas
                	if (ciudadesPorPais.get(paisSeleccionado).length > 1)
                    	JComboBoxCiudades.addItem("-");
                	//Añadir las ciudades al comboBox
                    for (String ciudad : ciudadesPorPais.get(paisSeleccionado)) {
                        JComboBoxCiudades.addItem(ciudad);
                    }
                }
            } else {
                // Deshabilitar el JComboBoxCiudades si no hay país seleccionado
                JComboBoxCiudades.setEnabled(false);
            }
        });
        
        
        JDateChooser dateChooserinicio = new JDateChooser();
        dateChooserinicio.setDateFormatString("dd/MM/yyyy");
        dateChooserinicio.setDate(new Date());
        dateChooserinicio.setBounds((int) (panelbuscar.getWidth() * 0.41), 10, (int) (panelbuscar.getWidth() * 0.19), 50);
        panelbuscar.add(dateChooserinicio);
        
        // Impide que el calendario se pueda modificar mediante el teclado (Codigo creado con la ayuda de ChatGPT)
        dateChooserinicio.getDateEditor().getUiComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                e.consume();  // Ignora cualquier tecla presionada
            }
        });
        
        JDateChooser dateChooserfinal = new JDateChooser();
        dateChooserfinal.setDateFormatString("dd/MM/yyyy");
        dateChooserfinal.setDate(new Date());
        dateChooserfinal.setBounds((int) (panelbuscar.getWidth() * 0.61), 10, (int) (panelbuscar.getWidth() * 0.19), 50);
        panelbuscar.add(dateChooserfinal);
        
        // Impide que el calendario se pueda modificar mediante el teclado (Codigo creado con la ayuda de ChatGPT)
        dateChooserfinal.getDateEditor().getUiComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                e.consume();  // Ignora cualquier tecla presionada
            }
        });
        
        // Listeners para asegurar que la seleccion de fechas es correcta (logica)
        // Codigo creado con la ayuda de ChatGPT
        dateChooserinicio.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Date fechaInicio = dateChooserinicio.getDate();
                Date fechaFinal = dateChooserfinal.getDate();

                // Si fechaInicio no es null y es después de fechaFinal
                if (fechaInicio != null && fechaFinal != null && fechaInicio.after(fechaFinal)) {
                    JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser posterior a la fecha final.", "Error", JOptionPane.WARNING_MESSAGE);
                    dateChooserinicio.setDate(fechaFinal); // Ajustar fecha de inicio
                }
            }
        });

        // Listener para validar la fecha de salida
        dateChooserfinal.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Date fechaInicio = dateChooserinicio.getDate();
                Date fechaFinal = dateChooserfinal.getDate();

                // Si fechaFinal no es null y es antes de fechaInicio
                if (fechaInicio != null && fechaFinal != null && fechaFinal.before(fechaInicio)) {
                    JOptionPane.showMessageDialog(null, "La fecha final no puede ser anterior a la fecha de inicio.", "Error", JOptionPane.WARNING_MESSAGE);
                    dateChooserfinal.setDate(fechaInicio); // Ajustar fecha final
                }
            }
        });

        
        
        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.setBounds((int) (panelbuscar.getWidth() * 0.81), 10, (int) (panelbuscar.getWidth() * 0.18), 50);
        botonBuscar.addActionListener(e -> {
        	if (!JComboBoxPaises.getSelectedItem().equals("")) {
        		String seleccionado = (String) JComboBoxCiudades.getSelectedItem();
        		System.out.println(seleccionado);
        		DataStore.setSelectedValue(seleccionado);
        		this.cardLayout.show(this.mainPanel, "Busqueda");
        	} else {
        		JOptionPane.showMessageDialog(null, "Debes filtrar al menos por un pais", "NINGUN PAIS SELECCIONADO", JOptionPane.ERROR_MESSAGE, null);
        	}
        });
        panelbuscar.add(botonBuscar);
        
        JSeparatorPunteada barraSeparadora2 = new JSeparatorPunteada();
        barraSeparadora2.setBackground(Color.BLACK);
        barraSeparadora2.setBounds(0, 300, Preferences.WINDOWWIDTH, 2);
        barraSeparadora2.setOrientation(SwingConstants.HORIZONTAL);
        add(barraSeparadora2, BorderLayout.CENTER);
        
        JLabel labelpopular = new JLabel("Destinos más populares");
        labelpopular.setHorizontalAlignment(SwingConstants.LEFT);
        labelpopular.setVerticalAlignment(SwingConstants.TOP);
        labelpopular.setBounds(40, 340, 500, 150);
        labelpopular.setFont(new Font("Verdana", Font.BOLD, 35));
        labelpopular.setForeground(Color.WHITE);
        add(labelpopular, BorderLayout.CENTER);
        
        JPanelBordesRedondos panelciudad = new JPanelBordesRedondos(25);
        panelciudad.setBounds(40, 420, 250, 200);
        panelciudad.setBackground(Color.WHITE);
        panelciudad.setLayout(null);
        
        JLabel bandera = new JLabel();
        bandera.setBounds(10, 10, 30, 20);
        ImageIcon banderaIcon = new ImageIcon("resources/images/Banderas/France Flag.png");
		Image banderaScaled = banderaIcon.getImage().getScaledInstance(30, 20, Image.SCALE_SMOOTH);
		ImageIcon banderaResized = new ImageIcon(banderaScaled);
		bandera.setIcon(banderaResized);
		panelciudad.add(bandera);
		
        JLabel labelciudad = new JLabel("Paris");
        labelciudad.setHorizontalAlignment(SwingConstants.LEFT);
        labelciudad.setVerticalAlignment(SwingConstants.TOP);
        labelciudad.setBounds(50, 10, 500, 150);
        labelciudad.setFont(new Font("Verdana", Font.BOLD, 15));
        labelciudad.setForeground(Color.WHITE);
        add(labelciudad, BorderLayout.CENTER);
		panelciudad.add(labelciudad);
        
        JLabel ciudad = new JLabel();
        ciudad.setBounds(0, 0, 250, 200);
        ImageIcon ciudadIcon = new ImageIcon("resources/images/Ciudades/Paris.jpg");
		Image ciudadScaled = ciudadIcon.getImage().getScaledInstance((int) ciudadIcon.getIconWidth() / (ciudadIcon.getIconHeight() / 200), 200, Image.SCALE_SMOOTH);
		ImageIcon ciudadResized = new ImageIcon(ciudadScaled);
		ciudad.setIcon(ciudadResized);
		ciudad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	DataStore.setSelectedValue("Paris");
            	cardLayout.show(mainPanel, "Busqueda");
            }
        });
		panelciudad.add(ciudad);
		
		add(panelciudad, BorderLayout.CENTER);
        
        JPanelBordesRedondos panelciudad2 = new JPanelBordesRedondos(25);
        panelciudad2.setBounds(305, 420, 250, 200);
        panelciudad2.setBackground(Color.WHITE);
        panelciudad2.setLayout(null);
        add(panelciudad2, BorderLayout.CENTER);
        
        JLabel bandera2 = new JLabel();
        bandera2.setBounds(10, 10, 30, 20);
        ImageIcon banderaIcon2 = new ImageIcon("resources/images/Banderas/USA Flag.png");
		Image banderaScaled2 = banderaIcon2.getImage().getScaledInstance(30, 20, Image.SCALE_SMOOTH);
		ImageIcon banderaResized2 = new ImageIcon(banderaScaled2);
		bandera2.setIcon(banderaResized2);
		panelciudad2.add(bandera2);
		
        JLabel labelciudad2 = new JLabel("Nueva York");
        labelciudad2.setHorizontalAlignment(SwingConstants.LEFT);
        labelciudad2.setVerticalAlignment(SwingConstants.TOP);
        labelciudad2.setBounds(50, 10, 500, 150);
        labelciudad2.setFont(new Font("Verdana", Font.BOLD, 15));
        labelciudad2.setForeground(Color.WHITE);
        add(labelciudad2, BorderLayout.CENTER);
		panelciudad2.add(labelciudad2);
        
        JLabel ciudad2 = new JLabel();
        ciudad2.setBounds(0, 0, 250, 200);
        ImageIcon ciudadIcon2 = new ImageIcon("resources/images/Ciudades/New York.jpg");
		Image ciudadScaled2 = ciudadIcon2.getImage().getScaledInstance((int) ciudadIcon2.getIconWidth() / (ciudadIcon2.getIconHeight() / 200), 200, Image.SCALE_SMOOTH);
		ImageIcon ciudadResized2 = new ImageIcon(ciudadScaled2);
		ciudad2.setIcon(ciudadResized2);
		ciudad2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	DataStore.setSelectedValue("Newy York");
            	cardLayout.show(mainPanel, "Busqueda");
            }
        });
		panelciudad2.add(ciudad2);
		
		add(panelciudad2, BorderLayout.CENTER);
        
        JPanelBordesRedondos panelciudad3 = new JPanelBordesRedondos(25);
        panelciudad3.setBounds(570, 420, 250, 200);
        panelciudad3.setBackground(Color.WHITE);
        panelciudad3.setLayout(null);
        add(panelciudad3, BorderLayout.CENTER);
        
        JLabel bandera3 = new JLabel();
        bandera3.setBounds(10, 10, 30, 20);
        ImageIcon banderaIcon3 = new ImageIcon("resources/images/Banderas/Italy Flag.png");
		Image banderaScaled3 = banderaIcon3.getImage().getScaledInstance(30, 20, Image.SCALE_SMOOTH);
		ImageIcon banderaResized3 = new ImageIcon(banderaScaled3);
		bandera3.setIcon(banderaResized3);
		panelciudad3.add(bandera3);
		
        JLabel labelciudad3 = new JLabel("Roma");
        labelciudad3.setHorizontalAlignment(SwingConstants.LEFT);
        labelciudad3.setVerticalAlignment(SwingConstants.TOP);
        labelciudad3.setBounds(50, 10, 500, 150);
        labelciudad3.setFont(new Font("Verdana", Font.BOLD, 15));
        labelciudad3.setForeground(Color.WHITE);
        add(labelciudad3, BorderLayout.CENTER);
		panelciudad3.add(labelciudad3);
        
        JLabel ciudad3 = new JLabel();
        ciudad3.setBounds(0, 0, 250, 200);
        ImageIcon ciudadIcon3 = new ImageIcon("resources/images/Ciudades/Rome.jpg");
		Image ciudadScaled3 = ciudadIcon3.getImage().getScaledInstance((int) ciudadIcon3.getIconWidth() / (ciudadIcon3.getIconHeight() / 200), 200, Image.SCALE_SMOOTH);
		ImageIcon ciudadResized3 = new ImageIcon(ciudadScaled3);
		ciudad3.setIcon(ciudadResized3);
		ciudad3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	DataStore.setSelectedValue("Roma");
            	cardLayout.show(mainPanel, "Busqueda");
            }
        });
		panelciudad3.add(ciudad3);
        
        JPanelBordesRedondos panelciudad4 = new JPanelBordesRedondos(25);
        panelciudad4.setBounds(835, 420, 250, 200);
        panelciudad4.setBackground(Color.WHITE);
        panelciudad4.setLayout(null);
        add(panelciudad4, BorderLayout.CENTER);
        
        JLabel bandera4 = new JLabel();
        bandera4.setBounds(10, 10, 30, 20);
        ImageIcon banderaIcon4 = new ImageIcon("resources/images/Banderas/China Flag.png");
		Image banderaScaled4 = banderaIcon4.getImage().getScaledInstance(30, 20, Image.SCALE_SMOOTH);
		ImageIcon banderaResized4 = new ImageIcon(banderaScaled4);
		bandera4.setIcon(banderaResized4);
		panelciudad4.add(bandera4);
		
        JLabel labelciudad4 = new JLabel("Hong Kong");
        labelciudad4.setHorizontalAlignment(SwingConstants.LEFT);
        labelciudad4.setVerticalAlignment(SwingConstants.TOP);
        labelciudad4.setBounds(50, 10, 500, 150);
        labelciudad4.setFont(new Font("Verdana", Font.BOLD, 15));
        labelciudad4.setForeground(Color.WHITE);
        add(labelciudad4, BorderLayout.CENTER);
		panelciudad4.add(labelciudad4);
        
        JLabel ciudad4 = new JLabel();
        ciudad4.setBounds(0, 0, 250, 200);
        ImageIcon ciudadIcon4 = new ImageIcon("resources/images/Ciudades/Hong Kong.jpg");
		Image ciudadScaled4 = ciudadIcon4.getImage().getScaledInstance((int) ciudadIcon4.getIconWidth() / (ciudadIcon4.getIconHeight() / 200), 200, Image.SCALE_SMOOTH);
		ImageIcon ciudadResized4 = new ImageIcon(ciudadScaled4);
		ciudad4.setIcon(ciudadResized4);
		ciudad4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	DataStore.setSelectedValue("Hong Kong");
            	cardLayout.show(mainPanel, "Busqueda");
            }
        });
		panelciudad4.add(ciudad4);
        
        add(panelbuscar, BorderLayout.CENTER);
        
		add(panelCentro, BorderLayout.CENTER); //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel
	
        // Panel principal sur
        add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
    }
}