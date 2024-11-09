package es.ingenieria.prog3.proyecto.gui;

import java.awt.CardLayout;
import java.awt.KeyboardFocusManager;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import es.ingenieria.prog3.proyecto.domain.Habitacion;
import es.ingenieria.prog3.proyecto.domain.Hotel;

public class MainFrame extends JFrame {
	   
	private static final long serialVersionUID = 1L;
	
	private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
    	// Parametros del JFrame
        setTitle("Hotel Host"); // Cambiar el título
        setIconImage(new ImageIcon("resources/images/Hotel Host.png").getImage()); // Cambiar el Logo de la ventana y barra de tareas
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1152, 720); // El 60% de la pantalla de nuestros ordenadores
        setResizable(false); // No es posible cambiar el tamaño de la ventana
        setLocationRelativeTo(null); // Poner la ventana en el centro de la pantalla

        // Inicializar el CardLayout y mainPanel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        //CARGAR DATOS
        //Carga de hoteles
        ArrayList<Hotel> hoteles = Hotel.cargarHoteles("resources/data/Hoteles.csv");
        Habitacion.crearHabitaciones(hoteles);
        for (Habitacion habitacion : hoteles.get(0).getHabitaciones()) {
			System.out.println(habitacion.getPrecio());
		}
        
        // Inicializar los paneles
        mainPanel.add(new Log1(cardLayout, mainPanel), "Log1");
        mainPanel.add(new Log2(cardLayout, mainPanel), "Log2");
        mainPanel.add(new Log3(cardLayout, mainPanel), "Log3");
        mainPanel.add(new Log4(cardLayout, mainPanel), "Log4");
        mainPanel.add(new Log5(cardLayout, mainPanel), "Log5");
        mainPanel.add(new Home(cardLayout, mainPanel), "Home");
        mainPanel.add(new Busqueda(cardLayout, mainPanel, hoteles), "Busqueda");

        
        // Añadir el mainPanel al JFrame
        this.add(mainPanel);
        
        // Mostrar el JFrame
        setVisible(true);
        
        

        
        
        
        
        // Cambiar el focus para no centrarse en nada
        // IAG: CHATGPT (Próximas 2 líneas)
        // Modificación: Si
    	this.getRootPane().requestFocusInWindow();
    	KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
        }
}