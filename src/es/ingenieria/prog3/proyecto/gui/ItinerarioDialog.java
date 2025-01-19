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
	
	private static final long serialVersionUID = 1L;

	private JSpinner spinnerCiudades = new JSpinner();
	
	private JButton buttonConfirm = new JButton("Confirmar");
	private JButton buttonCancel = new JButton("Cancelar");
	
	public ItinerarioDialog(String ciudad) {
		
        HashMap<String, String[]> ciudadesPorPais = new HashMap<>();
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
	
	//Metodo para generar itinerarios recursivos segun un presupuesto y un pais
	@SuppressWarnings("unused")
	private List<List<Hotel>> ItinerarioRecursivo(String Pais, double credit) {
		//Se recuperan todos los vuelos 
		List<Hotel> allHotels = new ArrayList<>();

		allHotels = DataStore.getGestorBD().getHoteles(); 
		
		//Se realiza la búsqueda recursiva de ida y vuelta.
        List<List<Hotel>> result = new ArrayList<>();		
        ItinerarioRecursivoAux(result, new ArrayList<>(), Pais, credit, allHotels, new ArrayList<>());
        
		return result;
	}
	
	//Funcion recursiva auxiliar para realizar los itinerarios

	private void ItinerarioRecursivoAux(List<List<Hotel>> result, List<Hotel> aux, String Pais, double credit, List<Hotel> allHotels, ArrayList<String> ciudadesHechas) {
		//CASO BASE 1: Se ha superado el presupuesto disponible -> ITINERARIO NO VALIDO
		if (credit < 0) {
			return;
		}
		
		//CASO BASE 2: aux no esta vacío AND
		if (!aux.isEmpty() &&
			//el itinerario no esta repetido
			!result.contains(aux)) {
			//Se añade el nuevo itinerario a la lista de resultados
			result.add(new ArrayList<>(aux));			
		//CASO RECURSIVO: Se van añadiendo itinerarios al itinerario aux  
		} else {
			//Se recorren los hoteles disponbles
			for (Hotel hotel : allHotels) {
				//Si el hotel esta en el pais deseado AND Esa ciudad no este ya abarcada en el itinerario
				if (hotel.getPais().equals(Pais) && !ciudadesHechas.contains(hotel.getCiudad())) {
					//Se añade el hotel a aux
					aux.add(hotel);
					ciudadesHechas.add(hotel.getCiudad());
					//Se realiza la invocación recursiva: se reduce credit y se añade la ciudad ya visitada
					ItinerarioRecursivoAux(result, 
									   	  aux,
									      Pais,
									      credit - hotel.getPrecioMinimo(),
									      allHotels,
									      ciudadesHechas);
					//Se elimina de aux el último hotel añadido y la ultima ciudad visitada de ciudadesHechas
					aux.remove(aux.size()-1);
					ciudadesHechas.remove(ciudadesHechas.size() -1);
				}
			}
		}
		
		
	}
}
