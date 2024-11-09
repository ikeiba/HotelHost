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

import es.ingenieria.prog3.proyecto.domain.Hotel;
import es.ingenieria.prog3.proyecto.gui.util.JPanelBordesRedondos;
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
        panelFiltro.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.3), (int) ((Preferences.WINDOWHEIGHT * 0.4)));
        panelFiltro.setBounds((int) ((Preferences.WINDOWWIDTH * 0.2) - (panelFiltro.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.6) - (panelFiltro.getHeight() / 2)) - 25, panelFiltro.getWidth(), panelFiltro.getHeight());
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
        sliderPrecio.setBounds(0, 0, (int) (panelFiltro.getWidth()*0.6), 20);
        sliderPrecio.setBounds((int) (panelFiltro.getWidth()*0.5 - (sliderPrecio.getWidth() / 2)), 60, sliderPrecio.getWidth(), sliderPrecio.getHeight());
        sliderPrecio.setBackground(Color.WHITE);
        sliderPrecio.setFocusable(false);

        // Creamos un label para mostrar en que rango de precios se encuentra 
        JLabel labelPrecioActual = new JLabel();
        labelPrecioActual.setHorizontalAlignment(SwingConstants.LEFT);
        labelPrecioActual.setVerticalAlignment(SwingConstants.TOP);
        labelPrecioActual.setBounds(0, 0, panelFiltro.getWidth(), 150);
        labelPrecioActual.setBounds((int) (sliderPrecio.getWidth()*0.35), 80, labelPrecioActual.getWidth(), labelPrecioActual.getHeight());
        
        // Listener que actualiza en que rango de precios se encuentra
        sliderPrecio.addChangeListener(e -> {
        	labelPrecioActual.setText("Precio minimo: " + sliderPrecio.getMinRange() + ", Precio maximo: " + sliderPrecio.getMaxRange());
        });
        
        //Anadimos el label y el slider al panel del filro
        panelFiltro.add(labelPrecioActual);
        panelFiltro.add(sliderPrecio);
        

        //Creamos el panel para la tabla y añadimos la tabla al panel
        JScrollPane panelScrollTablaHoteles = new JScrollPane(tablaHoteles);
        tablaHoteles.setRowHeight(45);
        panelScrollTablaHoteles.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.5), (int) ((Preferences.WINDOWHEIGHT * 0.9)));
        panelScrollTablaHoteles.setBounds((int) ((Preferences.WINDOWWIDTH * 0.7) - (panelScrollTablaHoteles.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.5) - (panelScrollTablaHoteles.getHeight() / 2)) - 25, panelScrollTablaHoteles.getWidth(), panelScrollTablaHoteles.getHeight());
        panelScrollTablaHoteles.setBackground(Color.WHITE);
       
        
        //Cargamos los hoteles
        this.actualizarHoteles();
        
        //Creamos el logo
        JLabel logo = new JLabel();
		logo.setBounds(0, 0, 200, 150);
		logo.setBounds((int) ((Preferences.WINDOWWIDTH * 0.20) - (logo.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.2) - (logo.getHeight() / 2)), logo.getWidth(), logo.getHeight());
		ImageIcon originalIcon = new ImageIcon("resources/images/Hotel Host.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(scaledImage);
        logo.setIcon(resizedIcon);
        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	cardLayout.show(mainPanel, "Home");
            }
        });
        
        
        //Añadimos los componentes al panelCentro y el panelCentro al centro del Panel de la clase (this)
        panelCentro.add(panelFiltro);
        panelCentro.add(panelScrollTablaHoteles);
        panelCentro.add(logo);

        //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel
		this.add(panelCentro, BorderLayout.CENTER); 
		
		// Panel principal sur
        this.add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
        
	}
	
	
	public void actualizarHoteles() {
		tablaHoteles.setModel(new HotelsTableModel(hoteles));	
		
		//Renderer para los hoteles
		HotelsRenderer rendererHotel = new HotelsRenderer();
		
		tablaHoteles.setDefaultRenderer(Object.class, rendererHotel);
		
		TableColumnModel columnModel = tablaHoteles.getColumnModel();
		
		columnModel.getColumn(0).setPreferredWidth(110);  // Set preferred width for column 0
		
		columnModel.getColumn(1).setPreferredWidth(100);  // Set preferred width for column 0
		
		columnModel.getColumn(2).setPreferredWidth(100);  // Set preferred width for column 0
	
		columnModel.getColumn(3).setPreferredWidth(285);
		
		columnModel.getColumn(4).setPreferredWidth(160);
		
		columnModel.getColumn(5).setPreferredWidth(100);

		tablaHoteles.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	}
		
}
