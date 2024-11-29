package es.ingenieria.prog3.proyecto.gui;

import javax.swing.*;

import es.ingenieria.prog3.proyecto.domain.Habitacion;
import es.ingenieria.prog3.proyecto.domain.Reserva;
import es.ingenieria.prog3.proyecto.domain.TipoHabitacion;
import es.ingenieria.prog3.proyecto.gui.util.DataStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class DialogReservar extends JDialog {
    
	private static final long serialVersionUID = 1L;
	
	public DialogReservar() {
		
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
			ArrayList<Reserva> reservas = habitacion.getReservas();
			Collections.sort(reservas);
			
			for (Reserva reserva : reservas) {
				Date fechaEntradaReserva = new Date(reserva.getFechaInicio());
				Date fechaSalidaReserva = new Date(reserva.getFechaFin());
				if (fechaEntradaReserva.after(fechaSalidaSeleccionada)) {
					habitacionesDisponibles.add(habitacion);
					break;
				} else {
					//if (fechaEntradaSeleccionada.)
				}
			}
			
		}
		return habitacionesDisponibles;
	} 
	
}
