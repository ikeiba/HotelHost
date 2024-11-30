package es.ingenieria.prog3.proyecto.gui;

import javax.swing.*;

import es.ingenieria.prog3.proyecto.domain.Habitacion;
import es.ingenieria.prog3.proyecto.domain.Hotel;
import es.ingenieria.prog3.proyecto.domain.Reserva;
import es.ingenieria.prog3.proyecto.domain.TipoHabitacion;
import es.ingenieria.prog3.proyecto.gui.util.DataStore;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;

public class DialogReservar extends JDialog {
    
	private static final long serialVersionUID = 1L;

	private ArrayList<Habitacion> habitacionesDisponibles;
	private JComboBox<Habitacion> comboBoxHabitacionesDisponibles;
	private Habitacion habitacionSeleccionada;
	private int numeroHuespedes;
	
	public DialogReservar(Hotel hotel) {
        JPanel panelReserva = new JPanel(new GridLayout(8, 1, 5, 5)); // Espaciado adicional
        
        JLabel labelTipoHabitacion = new JLabel("Selecciona el tipo de habitación:");
		JComboBox<TipoHabitacion> comboBoxTipoHabitacion = new JComboBox<TipoHabitacion>(TipoHabitacion.values());			
        
		JLabel labelHabitaciones = new JLabel("Selecciona la habitación:");
		habitacionesDisponibles = getHabitacionesPorFechas(getHabitacionesPorTipo(hotel.getHabitaciones(), (TipoHabitacion) comboBoxTipoHabitacion.getSelectedItem()));
		comboBoxHabitacionesDisponibles = new JComboBox<Habitacion>(habitacionesDisponibles.toArray(new Habitacion [0]));
		
        JLabel labelHuespedes = new JLabel("Incluye a los huéspedes (al menos 1):");
		JComboBox<String> comboHuespedes = new JComboBox<>();
		habitacionSeleccionada = (Habitacion) comboBoxHabitacionesDisponibles.getSelectedItem();
		numeroHuespedes = habitacionSeleccionada.getCapacidad();
		for (int i = 1; i <= numeroHuespedes; i++) {
			comboHuespedes.addItem(String.format("Huesped - %d", i));
		}
		
        JLabel labelPrecio = new JLabel("PRECIO TOTAL:");
        
        JLabel labelPrecioTotal = new JLabel(String.format("%.2f €", habitacionSeleccionada.getPrecio())); // Precio inicial

		
		comboBoxTipoHabitacion.addActionListener(e -> {
			habitacionesDisponibles.clear();
			habitacionesDisponibles = getHabitacionesPorFechas(getHabitacionesPorTipo(hotel.getHabitaciones(), (TipoHabitacion) comboBoxTipoHabitacion.getSelectedItem()));
			comboBoxHabitacionesDisponibles.removeAllItems();
			for (Habitacion habitacion : habitacionesDisponibles) {
				comboBoxHabitacionesDisponibles.addItem(habitacion);
			}
			if (habitacionesDisponibles.size() == 0) {
				comboBoxHabitacionesDisponibles.setEnabled(false);
			}
			habitacionSeleccionada = (Habitacion) comboBoxHabitacionesDisponibles.getSelectedItem();
			long diferenciaMilisegundos = DataStore.getSelectedFechaFin().getTime() - DataStore.getSelectedFechaInicio().getTime();
	        long diasDiferencia = diferenciaMilisegundos / (1000 * 60 * 60 * 24);
	        System.out.println("Días entre las fechas: " + diasDiferencia);
		});
		
		comboBoxHabitacionesDisponibles.addActionListener(e -> {
			if (comboBoxHabitacionesDisponibles.getSelectedItem() != null) {
				habitacionSeleccionada = (Habitacion) comboBoxHabitacionesDisponibles.getSelectedItem();
				int nuevoNumeroHuespedes = habitacionSeleccionada.getCapacidad();
				
				if (nuevoNumeroHuespedes != numeroHuespedes) {
					if (nuevoNumeroHuespedes > numeroHuespedes) {
						for (int i=numeroHuespedes+1; i<=nuevoNumeroHuespedes; i++) {
							comboHuespedes.addItem(String.format("Huesped - %d", i));					
						}
					} else {
						for (int i=numeroHuespedes; i>nuevoNumeroHuespedes; i--) {
							comboHuespedes.removeItemAt(i-1);
						}
					}
				}
				numeroHuespedes = comboHuespedes.getItemCount();
			}	
		});
		
		
		
		
		
        panelReserva.add(labelTipoHabitacion);
        panelReserva.add(comboBoxTipoHabitacion);
        panelReserva.add(labelHabitaciones);
        panelReserva.add(comboBoxHabitacionesDisponibles);
        panelReserva.add(labelHuespedes);
        panelReserva.add(comboHuespedes);
        panelReserva.add(labelPrecio);
        panelReserva.add(labelPrecioTotal);
        
		add(panelReserva);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle(String.format("Reserva en el hotel '%s'", hotel.getNombre()));		
		setIconImage(new ImageIcon("resources/images/tickets.png").getImage());		
		setSize(500, 350);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	public ArrayList<Habitacion> getHabitacionesPorTipo(ArrayList<Habitacion> habitaciones, TipoHabitacion tipo){
		ArrayList<Habitacion> habitacionesTipo = new ArrayList<Habitacion>();
		for (Habitacion habitacion : habitaciones) {
			if (habitacion.getTipo().equals(tipo))
				habitacionesTipo.add(habitacion);
		}
		return habitacionesTipo;
	}
	
	public ArrayList<Habitacion> getHabitacionesPorFechas(ArrayList<Habitacion> habitaciones){
		ArrayList<Habitacion> habitacionesDisponibles = new ArrayList<Habitacion>();
		Date fechaEntradaSeleccionada = DataStore.getSelectedFechaInicio();
		Date fechaSalidaSeleccionada = DataStore.getSelectedFechaFin();

		for (Habitacion habitacion : habitaciones) {
	        boolean disponible = true;
			ArrayList<Reserva> reservas = habitacion.getReservas();
			
			for (Reserva reserva : reservas) {
				Date fechaEntradaReserva = new Date(reserva.getFechaInicio());
				Date fechaSalidaReserva = new Date(reserva.getFechaFin());
					// Comprobar si las fechas se solapan
	            if (!(fechaSalidaSeleccionada.before(fechaEntradaReserva) || fechaEntradaSeleccionada.after(fechaSalidaReserva))) {
	                disponible = false;
	                break; // Si hay conflicto, no es necesario seguir comprobando
	            }
			}
			 // Si no hubo conflictos con ninguna reserva, añadir a la lista de disponibles
	        if (disponible) {
	            habitacionesDisponibles.add(habitacion);
	        }
		}
		return habitacionesDisponibles;
	}
	
}
