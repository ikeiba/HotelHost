package es.ingenieria.prog3.proyecto.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.ingenieria.prog3.proyecto.domain.Hotel;
import es.ingenieria.prog3.proyecto.domain.Valoracion;
import es.ingenieria.prog3.proyecto.gui.util.JPanelBordesRedondos;
import es.ingenieria.prog3.proyecto.gui.util.JTextFieldDefaultText;
import es.ingenieria.prog3.proyecto.gui.util.Preferences;
import es.ingenieria.prog3.proyecto.gui.util.RangeSlider;

public class Busqueda extends JPanel {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private CardLayout cardLayout;
    @SuppressWarnings("unused")
	private JPanel mainPanel;
    //Lista de hoteles a renderizar en la tabla
    private ArrayList<Hotel> hoteles;
    //Tabla de hoteles
    private JTable tablaHoteles = new JTable();
    //Modelo de datos de la tabla de hoteles
	private HotelsTableModel hotelsTableModel;
	//Tabla de valoraciones
	private JTable tablaValoraciones = new JTable();
	//Modelo de datos de la tabla de valoraciones
	private ValoracionesTableModel valoracionesTableModel;
	
	public Busqueda(CardLayout cardLayout, JPanel mainPanel, ArrayList<Hotel> hoteles) {
		
		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;
		this.hoteles = hoteles;

		
		//Ponemos el fondo del panel blanco y le asignamos el border layout como layoutManager
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());  
		
		//Creamos el panel principal centro con el color de fondo predeterminado y el layoutManager a null
		//esto nos permitira colocar los diferentes componentes por coordenadas mas facilmente
		JPanel panelCentro = new JPanel();
		panelCentro.setBackground(Preferences.COLORBACKGROUND);
		panelCentro.setLayout(null);
		
		//Creamos el panel que contendra el filtro del precio
		JPanelBordesRedondos panelFiltro = new JPanelBordesRedondos(25);
		panelFiltro.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.92), 70);
		panelFiltro.setBounds((int) ((Preferences.WINDOWWIDTH * 0.5) - (panelFiltro.getWidth() / 2)) - 5, 80, panelFiltro.getWidth() - 10, panelFiltro.getHeight());
		panelFiltro.setBackground(Color.WHITE);
		panelFiltro.setLayout(null);
		
        //Creamos el label diciendo que vamos a filtrar
        JLabel labelFiltrar = new JLabel("Filtrar:");
        labelFiltrar.setHorizontalAlignment(SwingConstants.LEFT);
        labelFiltrar.setVerticalAlignment(SwingConstants.TOP);
        labelFiltrar.setBounds(15, 20, panelFiltro.getWidth(), 150);
        labelFiltrar.setFont(Preferences.FONT);
        panelFiltro.add(labelFiltrar);
		
        // Queria crear un JSlider con dos "cabezas", y al no encontrar una clase así 
        // pregunte a chatGPT si existia. Me dio la opcion de utilizar una libreria externa como
        // SwingX o aplicar dos sliders. Con su ayuda, implemente la segunda opcion:
        RangeSlider sliderPrecio = new RangeSlider(1, 100);
        sliderPrecio.setBounds(0, 0, (int) (panelFiltro.getWidth()*0.2), 20);
        sliderPrecio.setBounds((int) (panelFiltro.getWidth()*0.25 - (sliderPrecio.getWidth() / 2)), 30, sliderPrecio.getWidth(), sliderPrecio.getHeight());
        sliderPrecio.setBackground(Color.WHITE);
        sliderPrecio.setFocusable(false);

        // Creamos un label para mostrar en que rango de precios se encuentra 
        JLabel labelPrecioActual = new JLabel();
        labelPrecioActual.setHorizontalAlignment(SwingConstants.LEFT);
        labelPrecioActual.setVerticalAlignment(SwingConstants.TOP);
        labelPrecioActual.setBounds(0, 0, panelFiltro.getWidth(), 150);
        labelPrecioActual.setBounds((int) (sliderPrecio.getWidth()*0.75), 50, labelPrecioActual.getWidth(), labelPrecioActual.getHeight());
        
        // Listener que actualiza en que rango de precios se encuentra
        sliderPrecio.addChangeListener(e -> {
        	labelPrecioActual.setText("Precio minimo: " + sliderPrecio.getMinRange() + ", Precio maximo: " + sliderPrecio.getMaxRange());
        });
        
        //Anadimos el label y el slider al panel del filro
        panelFiltro.add(labelPrecioActual);
        panelFiltro.add(sliderPrecio);
        
        //Creamos el panel para la tabla de hoteles y añadimos la tabla al panel
        JScrollPane panelScrollTablaHoteles = new JScrollPane(tablaHoteles);
        panelScrollTablaHoteles.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.5), (int) ((Preferences.WINDOWHEIGHT * 0.6)));
        panelScrollTablaHoteles.setBounds((int) ((Preferences.WINDOWWIDTH * 0.7) - (panelScrollTablaHoteles.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.65) - (panelScrollTablaHoteles.getHeight() / 2)) - 25, panelScrollTablaHoteles.getWidth(), panelScrollTablaHoteles.getHeight());
        panelScrollTablaHoteles.setBackground(Color.WHITE);
       
        //TABLA DE HOTELES
        //Cargamos los hoteles
        this.cargarTablaHoteles();
        
        //Filtro para el nombre del hotel
        JTextFieldDefaultText textFieldFiltroHotel = new JTextFieldDefaultText("Nombre Hotel");
        textFieldFiltroHotel.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.5), 40);
        textFieldFiltroHotel.setBounds((int) ((Preferences.WINDOWWIDTH * 0.7) - (panelScrollTablaHoteles.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.65) - (panelScrollTablaHoteles.getHeight() / 2)) - 75, textFieldFiltroHotel.getWidth(), textFieldFiltroHotel.getHeight());
        
        //Listener para filtrar
        DocumentListener documentListener = new DocumentListener() {
            
        	@Override
            public void insertUpdate(DocumentEvent e) {
        		filtrarHotelesPorNombre(textFieldFiltroHotel.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            	filtrarHotelesPorNombre(textFieldFiltroHotel.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            	filtrarHotelesPorNombre(textFieldFiltroHotel.getText());
            }

        };
		
		textFieldFiltroHotel.getDocument().addDocumentListener(documentListener);        
        
		//Fin listener filtrar
		//FIN TABLA DE HOTELES
		
		//TABLA DE VALORACIONES
        //Creamos el panel para la tabla de valoraciones y añadimos la tabla al panel
        JScrollPane panelScrollTablaValoraciones = new JScrollPane(tablaValoraciones);
        panelScrollTablaValoraciones.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.4), (int) ((Preferences.WINDOWHEIGHT * 0.4)));
        panelScrollTablaValoraciones.setBounds((int) ((Preferences.WINDOWWIDTH * 0.25) - (panelScrollTablaValoraciones.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.65) - (panelScrollTablaValoraciones.getHeight() / 2)) - 25, panelScrollTablaValoraciones.getWidth(), panelScrollTablaValoraciones.getHeight());
        panelScrollTablaValoraciones.setBackground(Color.WHITE);
       
		
		
		
		
        //Creamos el logo
        JLabel logo = new JLabel();
		logo.setBounds(0, 0, 50, 35);
		logo.setBounds(40, 18, logo.getWidth(), logo.getHeight());
		ImageIcon originalIcon = new ImageIcon("resources/images/Hotel Host.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(50, 35, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(scaledImage);
        logo.setIcon(resizedIcon);
        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	cardLayout.show(mainPanel, "Home");
            }
        });
       
        //Añadimos los componentes al panelCentro y el panelCentro al centro del Panel de la clase (this)
        panelCentro.add(textFieldFiltroHotel);
        panelCentro.add(panelFiltro);
        panelCentro.add(panelScrollTablaHoteles);
        panelCentro.add(logo);
        panelCentro.add(panelScrollTablaValoraciones);
        
        //Añadimos el panel panelCentro al BorderLayout.CENTER del Panel de la clase (this)
		this.add(panelCentro, BorderLayout.CENTER); 
		
		//Panel principal sur
        this.add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
        
	}
	
	
	//Metodos tabla hotel
	public void cargarTablaHoteles() {
		this.hotelsTableModel = new HotelsTableModel(hoteles);
		tablaHoteles.setModel(this.hotelsTableModel);	
        
		tablaHoteles.setRowHeight(45);

		//Renderer para los hoteles
		HotelsRenderer rendererHotel = new HotelsRenderer();
		
		tablaHoteles.setDefaultRenderer(Object.class, rendererHotel);
		
		TableColumnModel columnModel = tablaHoteles.getColumnModel();
		
		columnModel.getColumn(0).setPreferredWidth(135);  // Set preferred width for column 0
		
		columnModel.getColumn(1).setPreferredWidth(100);  // Set preferred width for column 0
		
		columnModel.getColumn(2).setPreferredWidth(100);  // Set preferred width for column 0
	
		columnModel.getColumn(3).setPreferredWidth(285);
		
		columnModel.getColumn(4).setPreferredWidth(160);
		
		columnModel.getColumn(5).setPreferredWidth(100);

		tablaHoteles.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		this.tablaHoteles.getSelectionModel().addListSelectionListener(e -> {
			// Cuando se selecciona una fila, se actualiza la tabla de valoraciones
			Hotel hotelSeleccionado = (Hotel) (tablaHoteles.getValueAt(tablaHoteles.getSelectedRow(), 0));
			this.cargarTablaValoraciones(((Hotel) hotelSeleccionado).getValoraciones());
		});

	}
	
	//Metodo para filtrar los hoteles segun el nombre
	public void filtrarHotelesPorNombre(String filtro) {
	    // Crea una lista filtrada con los hoteles que coinciden con el filtro
	    ArrayList<Hotel> hotelesFiltrados = new ArrayList<>();
	    
	    hoteles.forEach(hotel -> {
	        if (hotel.getNombre().toLowerCase().contains(filtro.toLowerCase())) {
	            hotelesFiltrados.add(hotel);
	        }
	    });
	    // Actualiza el modelo de datos de la tabla con la lista filtrada
	    hotelsTableModel.setHotels(hotelesFiltrados);
	}
	//Fin metodos tabla hotel
	
	
	//Metodos tabla valoracion
	public void cargarTablaValoraciones(ArrayList<Valoracion> valoraciones) {
		this.valoracionesTableModel = new ValoracionesTableModel(valoraciones);
		tablaValoraciones.setModel(this.valoracionesTableModel);	
        
		tablaValoraciones.setRowHeight(45);

		//Renderer para los hoteles
		HotelsRenderer rendererHotel = new HotelsRenderer();
		
		tablaValoraciones.setDefaultRenderer(Object.class, rendererHotel);
		
		TableColumnModel columnModel = tablaValoraciones.getColumnModel();
		
		columnModel.getColumn(0).setPreferredWidth(135);  // Set preferred width for column 0
		
		columnModel.getColumn(1).setPreferredWidth(100);  // Set preferred width for column 0
		
		columnModel.getColumn(2).setPreferredWidth(100);  // Set preferred width for column 0
	
		columnModel.getColumn(3).setPreferredWidth(80);

		tablaValoraciones.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	}
	
	
	
	
	//Fin metodos tabla valoracion
	
}
