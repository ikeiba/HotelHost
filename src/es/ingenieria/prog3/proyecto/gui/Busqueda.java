package es.ingenieria.prog3.proyecto.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import es.ingenieria.prog3.proyecto.gui.util.JPanelBordesRedondos;
import es.ingenieria.prog3.proyecto.gui.util.Preferences;
import es.ingenieria.prog3.proyecto.gui.util.RangeSlider;

public class Busqueda extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private CardLayout cardLayout;
    private JPanel mainPanel;
	
	public Busqueda(CardLayout cardLayout, JPanel mainPanel) {
		
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
        
        
       
        
        //Añadimos los componentes al panelCentro y el panelCentro al centro del Panel de la clase (this)
        panelCentro.add(panelFiltro);

        //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel
		this.add(panelCentro, BorderLayout.CENTER); 
		
		// Panel principal sur
        this.add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
	}
	
}
