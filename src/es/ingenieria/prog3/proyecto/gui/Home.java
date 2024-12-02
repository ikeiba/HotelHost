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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

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
        ciudadesPorPais.put("Estados Unidos", new String[]{"Las Vegas", "Los Angeles", "Miami", "New York"});
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
        dateChooserinicio.setSelectableDateRange(new Date(), null);  // El primer parámetro es la fecha de inicio (hoy)
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
        //dateChooserfinal.setDate(new Date());
        dateChooserfinal.setBounds((int) (panelbuscar.getWidth() * 0.61), 10, (int) (panelbuscar.getWidth() * 0.19), 50);
        dateChooserfinal.setSelectableDateRange(new Date(), null);  // El primer parámetro es la fecha de inicio (hoy)
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
        	if (JComboBoxPaises.getSelectedItem().equals("")) {
        		JOptionPane.showMessageDialog(null, "No has seleccionado ningun pais", "NINGUN PAIS SELECCIONADO", JOptionPane.ERROR_MESSAGE, null);
        	} else if (dateChooserinicio.getDate() == null || dateChooserfinal.getDate() == null) {
        		JOptionPane.showMessageDialog(null, "Debes seleccionar fechas validas", "FECHAS NO VALIDAS", JOptionPane.ERROR_MESSAGE, null);
        	} else {
        		String seleccionado = (String) JComboBoxCiudades.getSelectedItem();
        		DataStore.setSelectedCiudad(seleccionado);
        		Date fechaInicio = dateChooserinicio.getDate();
        		DataStore.setSelectedFechaInicio(fechaInicio);
        		Date fechaFin = dateChooserfinal.getDate();
        		DataStore.setSelectedFechaFin(fechaFin);
        		this.cardLayout.show(this.mainPanel, "Busqueda");
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
		
		add(panelciudad, BorderLayout.CENTER);
        
        JPanelBordesRedondos panelciudad2 = new JPanelBordesRedondos(25);
        panelciudad2.setBounds(305, 420, 250, 200);
        panelciudad2.setBackground(Color.WHITE);
        panelciudad2.setLayout(null);
        add(panelciudad2, BorderLayout.CENTER);
        
		
		add(panelciudad2, BorderLayout.CENTER);
        
        JPanelBordesRedondos panelciudad3 = new JPanelBordesRedondos(25);
        panelciudad3.setBounds(570, 420, 250, 200);
        panelciudad3.setBackground(Color.WHITE);
        panelciudad3.setLayout(null);
        add(panelciudad3, BorderLayout.CENTER);
        
     
        JPanelBordesRedondos panelciudad4 = new JPanelBordesRedondos(25);
        panelciudad4.setBounds(835, 420, 250, 200);
        panelciudad4.setBackground(Color.WHITE);
        panelciudad4.setLayout(null);
        add(panelciudad4, BorderLayout.CENTER);
        
        ArrayList<ImageData> imagenes = new ArrayList<ImageData>();
        imagenes.add(new ImageData("resources/images/Ciudades/Bangkok.jpg", "Bangkok", "resources/images/Banderas/Thailand Flag.png"));
        imagenes.add(new ImageData("resources/images/Ciudades/Hanoi.jpg", "Hanoi", "resources/images/Banderas/Vietnam Flag.png"));
        imagenes.add(new ImageData("resources/images/Ciudades/Hong Kong.jpg", "Hong Kong", "resources/images/Banderas/China Flag.png"));
        imagenes.add(new ImageData("resources/images/Ciudades/Las Vegas.jpg", "Las Vegas", "resources/images/Banderas/USA Flag.png"));
        imagenes.add(new ImageData("resources/images/Ciudades/Los Angeles.jpg", "Los Angeles", "resources/images/Banderas/USA Flag.png"));
        imagenes.add(new ImageData("resources/images/Ciudades/Miami.jpg", "Miami", "resources/images/Banderas/USA Flag.png"));
        imagenes.add(new ImageData("resources/images/Ciudades/New York.jpg", "New York", "resources/images/Banderas/USA Flag.png"));
        imagenes.add(new ImageData("resources/images/Ciudades/Paris.jpg", "Paris", "resources/images/Banderas/France Flag.png"));
        imagenes.add(new ImageData("resources/images/Ciudades/Pattaya.jpg", "Pattaya", "resources/images/Banderas/Thailand Flag.png"));
        imagenes.add(new ImageData("resources/images/Ciudades/Rome.jpg", "Roma", "resources/images/Banderas/Italy Flag.png"));

        JPanel[] paneles = new JPanel[4];
        paneles[0] = panelciudad;
        paneles[1] = panelciudad2;
        paneles[2] = panelciudad3;
        paneles[3] = panelciudad4;
        
        CarrouselImagenes(paneles, imagenes);
        
        add(panelbuscar, BorderLayout.CENTER);
        
		add(panelCentro, BorderLayout.CENTER); //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel
	
        // Panel principal sur
        add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
    }
	
	private static class ImageData {
	    private final String imagePath;
	    private final String cityName;
	    private final String flagPath;

	    public ImageData(String imagePath, String cityName, String flagPath) {
	        this.imagePath = imagePath;
	        this.cityName = cityName;
	        this.flagPath = flagPath;
	    }

	    public String getImagePath() {
	        return imagePath;
	    }

	    public String getCityName() {
	        return cityName;
	    }

	    public String getFlagPath() {
	        return flagPath;
	    }
	}
	
	private void CarrouselImagenes(JPanel[] panels, ArrayList<ImageData> allImages) {
	    Thread thread = new Thread(() -> {
	        Random random = new Random();
	        
	        ArrayList<ImageData> availableImages = new ArrayList<>(allImages);

	        while (true) {
	            try {
	                synchronized (this) {
	                    if (availableImages.isEmpty()) {
	                        availableImages = new ArrayList<>(allImages); 
	                    }
	                    for (JPanel panel : panels) {
	                        if (availableImages.isEmpty()) {
	                            break; 
	                        }	                        
	                        int index = random.nextInt(availableImages.size());
	                        ImageData selectedImage = availableImages.remove(index);
	            
	                        SwingUtilities.invokeLater(() -> updateImagen(panel, selectedImage));
	                        panel.addMouseListener(new MouseAdapter() {
	                            @Override
	                            public void mouseClicked(MouseEvent e) {
	                            	DataStore.setSelectedCiudad(selectedImage.cityName);
	                            	cardLayout.show(mainPanel, "Busqueda");
	                            }
	                        });
	                    }
	                }	        
	                Thread.sleep(5000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    });

	    thread.start(); 
	}

	private void updateImagen(JPanel panel, ImageData imageData) {
	    ImageIcon mainImageIcon = new ImageIcon(imageData.getImagePath());
	    ImageIcon flagImageIcon = new ImageIcon(imageData.getFlagPath());

	    Image mainImage = mainImageIcon.getImage().getScaledInstance(panel.getWidth(), panel.getHeight() - 50, Image.SCALE_SMOOTH);
	    Image flagImage = flagImageIcon.getImage().getScaledInstance(50, 30, Image.SCALE_SMOOTH);
	   
	    panel.removeAll();
	    panel.setLayout(new BorderLayout());

	    JLabel imageLabel = new JLabel(new ImageIcon(mainImage));
	    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

	    JLabel cityLabel = new JLabel(imageData.getCityName(), SwingConstants.CENTER);
	    cityLabel.setForeground(Color.BLACK);
	    cityLabel.setFont(new Font("Arial", Font.BOLD, 16));

	    JLabel flagLabel = new JLabel(new ImageIcon(flagImage));
	    flagLabel.setHorizontalAlignment(SwingConstants.CENTER);

	    panel.add(imageLabel, BorderLayout.CENTER);
	    panel.add(cityLabel, BorderLayout.NORTH);
	    panel.add(flagLabel, BorderLayout.SOUTH);

	    panel.revalidate();
	    panel.repaint();
	}
}

