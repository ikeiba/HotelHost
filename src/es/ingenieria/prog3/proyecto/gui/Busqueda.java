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
        JSlider sliderIzquierda = new JSlider(0, 100, 25); // Minimum thumb
        JSlider sliderDerecha = new JSlider(0, 100, 75); // Maximum thumb

        // Sincronizar ambos sliders para que el minimo no pueda ser mayor al maximo
        sliderIzquierda.addChangeListener(e -> {
            if (sliderIzquierda.getValue() > sliderDerecha.getValue()) {
                sliderIzquierda.setValue(sliderDerecha.getValue());
            }
        });

        sliderDerecha.addChangeListener(e -> {
            if (sliderDerecha.getValue() < sliderIzquierda.getValue()) {
                sliderDerecha.setValue(sliderIzquierda.getValue());
            }
        });
        
        sliderIzquierda.setBounds(15, 220, panelFiltro.getWidth() - 150, 30);
        sliderIzquierda.setFont(Preferences.FONT);
        sliderDerecha.setBounds(15, 220, panelFiltro.getWidth() - 150, 30);
        sliderDerecha.setFont(Preferences.FONT);
		
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(300, 300));
        layeredPane.add(sliderIzquierda, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(sliderDerecha, JLayeredPane.PALETTE_LAYER);
        
        panelCentro.add(layeredPane);
        //Añadimos los componentes al panelCentro y el panelCentro al centro del Panel de la clase (this)
        panelCentro.add(panelFiltro);

        //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel
		this.add(panelCentro, BorderLayout.CENTER); 
		
		// Panel principal sur
        this.add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
	}
	
}
