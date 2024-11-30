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
	
	public DialogReservar(Hotel hotel) {
		JPanel panelReserva = new JPanel(new GridLayout(3,1));

		JComboBox<TipoHabitacion> comboBoxTipoHabitacion = new JComboBox<TipoHabitacion>(TipoHabitacion.values());			
		
		habitacionesDisponibles = getHabitacionesPorFechas(getHabitacionesPorTipo(hotel.getHabitaciones(), (TipoHabitacion) comboBoxTipoHabitacion.getSelectedItem()));
		comboBoxHabitacionesDisponibles = new JComboBox<Habitacion>(habitacionesDisponibles.toArray(new Habitacion [0]));
		
		JComboBox<String> comboHuespedes = new JComboBox<>();
		Habitacion habitacionSeleccionada = (Habitacion) comboBoxHabitacionesDisponibles.getSelectedItem();
		for (int i = 1; i <= habitacionSeleccionada.getCapacidad(); i++) {
			comboHuespedes.addItem("Huesped " + i);
		}
		
		comboBoxTipoHabitacion.addActionListener(e -> {
			habitacionesDisponibles.clear();
			habitacionesDisponibles = getHabitacionesPorFechas(getHabitacionesPorTipo(hotel.getHabitaciones(), (TipoHabitacion) comboBoxTipoHabitacion.getSelectedItem()));
			comboBoxHabitacionesDisponibles.removeAllItems();
			for (Habitacion habitacion : habitacionesDisponibles) {
				comboBoxHabitacionesDisponibles.addItem(habitacion);
			}
		});
		
		comboBoxHabitacionesDisponibles.addActionListener(e -> {
			
		});
		
		
		
		
		
		panelReserva.add(comboBoxTipoHabitacion);
		panelReserva.add(comboBoxHabitacionesDisponibles);
		panelReserva.add(comboHuespedes);
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
