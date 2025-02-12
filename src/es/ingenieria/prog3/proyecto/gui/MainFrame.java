package es.ingenieria.prog3.proyecto.gui;

import java.awt.CardLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import es.ingenieria.prog3.proyecto.db.GestorBD;
import es.ingenieria.prog3.proyecto.domain.Hotel;
import es.ingenieria.prog3.proyecto.domain.Usuario;
import es.ingenieria.prog3.proyecto.gui.util.DataStore;

public class MainFrame extends JFrame {
	   
	private static final long serialVersionUID = 1L;
	
	private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
    	GestorBD gestorBD = new GestorBD();
    	gestorBD.borrarBBDD();
    	gestorBD.crearBBDD();
    	gestorBD.borrarDatos();
    	gestorBD.initilizeFromCSV();
    	
    	DataStore.setGestorBD(gestorBD);
        ArrayList<Hotel> hoteles = gestorBD.getHoteles();
        ArrayList<Usuario> usuarios = gestorBD.getUsuarios();
        
        // Inicializar el CardLayout y mainPanel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
               
        // Añadimos una funcionalidad al panel Busqueda cuando sea el que esta visible
        //IAG (herramienta: ChatGPT)
        //SIN CAMBIOS
        Busqueda panelBusqueda = new Busqueda(cardLayout, mainPanel, hoteles);
        panelBusqueda.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
            	//Inicializa el filtro de manera que todos los hoteles esten dentro
            	panelBusqueda.filtrarHoteles("", 0, 100000);
            }
        });
        
        // Inicializar los paneles y añadirlos al cardLayout
        mainPanel.add(new Log1(cardLayout, mainPanel, usuarios), "Log1");
        mainPanel.add(new Log2(cardLayout, mainPanel), "Log2");
        mainPanel.add(new Log3(cardLayout, mainPanel), "Log3");
        mainPanel.add(new Log4(cardLayout, mainPanel), "Log4");
        mainPanel.add(new Log5(cardLayout, mainPanel), "Log5");
        mainPanel.add(new Home(cardLayout, mainPanel), "Home");
        mainPanel.add(panelBusqueda, "Busqueda");
        
        // Añadir el mainPanel al JFrame
        this.add(mainPanel);
        

        // Cambiar el focus para no centrarse en nada
        // IAG: CHATGPT (Próximas 2 líneas)
        // SIN CAMBIOS
    	this.getRootPane().requestFocusInWindow();
    	KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
    	
    	
    	// Parametros del JFrame
        setTitle("Hotel Host"); // Cambiar el título
        setIconImage(new ImageIcon("resources/images/Hotel Host.png").getImage()); // Cambiar el Logo de la ventana y barra de tareas
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1152, 720); // El 60% de la pantalla de nuestros ordenadores
        setResizable(false); // No es posible cambiar el tamaño de la ventana
        setLocationRelativeTo(null); // Poner la ventana en el centro de la pantalla
        setVisible(true); //Mostrar el JFrame
        }
}