package es.ingenieria.prog3.proyecto.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Reserva {
	
	private static int contador = 1;
	private int id;
	private ArrayList<Huesped> huespedes;
	private long fecha;
	private Habitacion habitacion;
	
	
	
	public Reserva(ArrayList<Huesped> huespedes, long fecha, Habitacion habitacion) {
		super();
		
		this.id = contador;
		Reserva.contador ++;
		this.huespedes = huespedes;
		this.fecha = fecha;
		this.habitacion = habitacion;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(fecha, habitacion, huespedes, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		return fecha == other.fecha && Objects.equals(habitacion, other.habitacion)
				&& Objects.equals(huespedes, other.huespedes) && id == other.id;
	}

	@Override
	public String toString() {
		return String.valueOf(this.getHabitacion().getNumero());
	}
	
	
	
}
