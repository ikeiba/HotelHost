package es.ingenieria.prog3.proyecto.domain;

import java.util.ArrayList;

public class Reserva {
	
	private static int contador = 1;
	private int id;
	private ArrayList<Huesped> huespedes;
	private long fecha;
	private Habitacion habitacion;
	
	
	public int getId() {
		return id;
	}
	
	public ArrayList<Huesped> getHuespedes() {
		return huespedes;
	}
	
	public void setHuespedes(ArrayList<Huesped> huespedes) {
		this.huespedes = huespedes;
	}
	public long getFecha() {
		return fecha;
	}
	
	public void setFecha(long fecha) {
		this.fecha = fecha;
	}
	public Habitacion getHabitacion() {
		return habitacion;
	}
	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	
}
