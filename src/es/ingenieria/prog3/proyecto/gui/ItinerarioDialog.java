package es.ingenieria.prog3.proyecto.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import es.ingenieria.prog3.proyecto.domain.Hotel;
import es.ingenieria.prog3.proyecto.gui.util.DataStore;

public class ItinerarioDialog extends JDialog{
	
	//CLASE CREADA POR CARLOSFIRVIDA. INTRODUCIDA POR INFIZA (Iñigo Infante) DEBIDO A PROBLEMAS CON GITHUB
	//
	
	private static final long serialVersionUID = 1L;

	private JSpinner spinnerCiudades = new JSpinner();
	private HashMap<String, String[]> ciudadesPorPais = new HashMap<>();
	private JButton buttonConfirm = new JButton("Confirmar");
	private JButton buttonCancel = new JButton("Cancelar");
	
	public ItinerarioDialog(String ciudad) {
		
        ciudadesPorPais.put("Estados Unidos", new String[]{"Las Vegas", "Los Angeles", "Miami", "New York", "San Francisco", "Washington DC"});
        ciudadesPorPais.put("España", new String[]{"Madrid", "Barcelona", "Bilbao", "Donosti", "Valencia", "Granada", "Sevilla", "Malaga", "Palma de Mallorca"});
        ciudadesPorPais.put("Alemania", new String[]{"Berlin", "Frankfurt", "Gelsenkirchen", "Hamburgo", "Munich"});
        ciudadesPorPais.put("Francia", new String[]{"Paris", "Bayona", "Biarritz", "Lille", "Lyon", "Marsella", "Niza"});
        ciudadesPorPais.put("Mexico", new String[]{"Cancun"});
        ciudadesPorPais.put("Italia", new String[]{"Roma", "Bolonia", "Cori", "Florencia", "Genova", "Milan", "Napoles", "Venecia"});
        ciudadesPorPais.put("Reino Unido", new String[]{"Birmingham", "Liverpool", "Londres", "Manchester"});
        ciudadesPorPais.put("Tailandia", new String[]{"Bangkok", "Pattaya"});
        ciudadesPorPais.put("China", new String[]{"Hong Kong"});
        ciudadesPorPais.put("Vietnam", new String[]{"Hanoi"});
		ciudadesPorPais.put("Paises Bajos", new String[]{"amsterdam", "Maastricht", "Roterdam"});
		ciudadesPorPais.put("Grecia", new String[]{"Atenas", "Creta", "Tesalonica"});
		ciudadesPorPais.put("Suiza", new String[]{"Basilea", "Ginebra", "Zurich"});
		ciudadesPorPais.put("Argentina", new String[]{"Buenos Aires"});
		ciudadesPorPais.put("Turquia", new String[]{"Estambul"});
		ciudadesPorPais.put("Irlanda", new String[]{"Galway", "Limerick"});
		ciudadesPorPais.put("Japon", new String[]{"Tokio", "Kioto", "Osaka", "Nara"});
		ciudadesPorPais.put("Australia", new String[]{"Melbourne", "Sidney"});
		ciudadesPorPais.put("India", new String[]{"Mumbai", "Nueva Delhi"});
		ciudadesPorPais.put("Brazil", new String[]{"Rio de Janeiro"});
		ciudadesPorPais.put("Canada", new String[]{"Toronto"});
		
		String pais = obtenerPaisDeCiudad(ciudad, ciudadesPorPais);
		
		//Creamos el panel central donde iran el panel de presupuesto y el de nº de ciudades
		JPanel panelCentral = new JPanel(new GridLayout(2, 1));
		panelCentral.setBorder(new TitledBorder(String.format("Datos del Itinerario por %s", pais)));
		
		
		//Creamos el panel para introducir el presupuesto
		JPanel panelDineroMax = new JPanel();
		JLabel labelDineroMax = new JLabel("Presupuesto:");
		JTextField textFieldDineroMax = new JTextField();
		textFieldDineroMax.setPreferredSize(new Dimension(100, 20));
		
		panelDineroMax.add(labelDineroMax);
		panelDineroMax.add(textFieldDineroMax);
		
		
		//Creamos el panel para introducir el nº de ciudades a visitar
		JPanel panelNumeroCiudades = new JPanel();
		JLabel labelNumeroCiudades = new JLabel("Nº de Ciudades:");
		spinnerCiudades = new JSpinner(new SpinnerNumberModel(1, 1, ciudadesPorPais.get(pais).length, 1));
		panelNumeroCiudades.add(labelNumeroCiudades);
		panelNumeroCiudades.add(spinnerCiudades);
		
		
		//Añadimos al panel central
		panelCentral.add(panelDineroMax);
		panelCentral.add(panelNumeroCiudades);
		
		
		//Eventos de los botones
		buttonCancel.addActionListener((e) -> setVisible(false));
		buttonConfirm.addActionListener((e) -> {
			 // Depuración inicial
		    System.out.println("Evento confirmado.");
		    System.out.println("Presupuesto ingresado: " + textFieldDineroMax.getText());
		    System.out.println("Número de ciudades: " + spinnerCiudades.getValue());
		    
		    try {
		        double presupuesto = Double.parseDouble(textFieldDineroMax.getText().trim());
		        int numCiudades = (int) spinnerCiudades.getValue();

		        System.out.println("Ciudad seleccionada: " + ciudad);
		        List<List<Hotel>> itinerarios = ItinerarioRecursivo(ciudad, presupuesto, numCiudades);

		        // Imprimir itinerarios encontrados
		        if (itinerarios.isEmpty()) {
		            System.out.println("No se encontraron itinerarios.");
		        } else {
		            itinerarios.forEach(itinerario -> {
		                System.out.println("Itinerario:");
		                itinerario.forEach(hotel -> System.out.println(hotel.getCiudad() + " --> " + hotel.toString()));
		                System.out.println("-----------------");
		            });
		        }
		    } catch (NumberFormatException ex) {
		        System.out.println("Error: el presupuesto ingresado no es válido.");
		    }

		    setVisible(false);
		});
		
		//Creamos el panel de los botones
		JPanel panelButtons = new JPanel();
		panelButtons.add(buttonCancel);
		panelButtons.add(buttonConfirm);
		
		
		add(new JPanel(), BorderLayout.NORTH);
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.WEST);
		add(panelCentral, BorderLayout.CENTER);
		add(panelButtons, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Crear Itinerario - Hotel Host");		
		setIconImage(new ImageIcon("resources/images/Hotel Host.png").getImage());		
		setSize(360, 185);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	// Generado por ChatGPT para obtener el pais al que pertenece una ciudad
	public static String obtenerPaisDeCiudad(String ciudad, HashMap<String, String[]> ciudadesPorPais) {
        for (String pais : ciudadesPorPais.keySet()) {
            String[] ciudades = ciudadesPorPais.get(pais);
            for (String c : ciudades) {
                if (c.equalsIgnoreCase(ciudad)) { // Ignorar diferencias de mayúsculas y minúsculas
                    return pais;
                }
            }
        }
        return null; // No se encontró la ciudad
    }
	
	//FUNCION RECURSIVA HECHA POR INFIZA (Iñigo Infante)
	
	//Metodo para generar itinerarios recursivos segun un presupuesto y un pais
	public List<List<Hotel>> ItinerarioRecursivo(String Ciudad, double credit, int num) {
		//Se recuperan todos los vuelos 
		List<Hotel> allHotels = new ArrayList<>();

		allHotels = DataStore.getGestorBD().getHoteles(); 
		String pais = obtenerPaisDeCiudad(Ciudad, ciudadesPorPais);
		
		
		//Se realiza la búsqueda recursiva de ida y vuelta.
        List<List<Hotel>> result = new ArrayList<>();		
        ItinerarioRecursivoAux(result, new ArrayList<>(), pais, Ciudad, credit, num, 0, allHotels, new ArrayList<>());
        
        /*System.out.println("Itinerarios generados: " + result.size());
        result.forEach(itinerario -> {
            System.out.println("Itinerario:");
            itinerario.forEach(hotel -> System.out.println(hotel));
        });*/
        
		return result;
	}
	
	//Funcion recursiva auxiliar para realizar los itinerarios

	private void ItinerarioRecursivoAux(List<List<Hotel>> result, List<Hotel> aux, String Pais, String Ciudad, double credit, int limite, int numciudad, List<Hotel> allHotels, ArrayList<String> ciudadesHechas) {
	    // CASO BASE 1: Se ha superado el presupuesto disponible o el número de ciudades visitadas es mayor al límite -> ITINERARIO NO VALIDO
	    if (credit < 0 || numciudad > limite) {
	        //System.out.println("Caso base: Fin recursión. Presupuesto: " + credit + ", Ciudades visitadas: " + numciudad);
	        return;
	    }
	    
	    // CASO BASE 2: aux no está vacío Y el itinerario no está repetido Y se ha alcanzado el límite de ciudades
	    //Dos itinerarios con ciudades en distinto orden no se consideran el mismo itinerario
	    if (!aux.isEmpty() &&
	            !result.contains(aux) && 
	            numciudad == limite && aux.get(0).getCiudad().equals(Ciudad)) {
	        result.add(new ArrayList<>(aux));
	    // CASO RECURSIVO: Se van añadiendo itinerarios al itinerario aux
	    } else {
	        // Se recorren los hoteles disponibles
	        for (Hotel hotel : allHotels) {
	        	//Se mira si aun no se ha añadido la ciudad de origen.
	        	if(aux.isEmpty() && hotel.getCiudad().equalsIgnoreCase(Ciudad.trim())){
	        		aux.add(hotel);
	                ciudadesHechas.add(hotel.getCiudad().trim());
	                ItinerarioRecursivoAux(result, aux, Pais, Ciudad, credit - hotel.getPrecioMinimo(), limite, numciudad + 1, allHotels, ciudadesHechas);
	                aux.remove(aux.size() - 1);
	                ciudadesHechas.remove(ciudadesHechas.size() - 1);
	                // Se realiza la invocación recursiva: se red
	        	}
	            // Si no se ha visitado esa ciudad y está en el país correcto
	            if (!ciudadesHechas.contains(hotel.getCiudad().trim()) &&
	                    obtenerPaisDeCiudad(hotel.getCiudad().trim(), ciudadesPorPais).equalsIgnoreCase(Pais.trim())) {
	                // Se añade el hotel a aux
	                aux.add(hotel);
	                ciudadesHechas.add(hotel.getCiudad().trim());
	                // Se realiza la invocación recursiva: se reduce el crédito y se añade la ciudad ya visitada
	                ItinerarioRecursivoAux(result, aux, Pais, Ciudad, credit - hotel.getPrecioMinimo(), limite, numciudad + 1, allHotels, ciudadesHechas);
	                // Se elimina de aux el último hotel añadido y la última ciudad visitada de ciudadesHechas
	                aux.remove(aux.size() - 1);
	                ciudadesHechas.remove(ciudadesHechas.size() - 1);
	            }
	        }
	    }
	}
	

}

