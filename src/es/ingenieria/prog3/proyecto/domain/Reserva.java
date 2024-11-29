package es.ingenieria.prog3.proyecto.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Reserva implements Comparable<Reserva> {
	
	private static int contador = 1;
	private int id;
	private ArrayList<String> huespedes;
	private long fechaInicio;
	private long fechaFin;
	private Habitacion habitacion;
	
	
	
	public Reserva(ArrayList<String> huespedes, long fechaInicio, long fechaFin, Habitacion habitacion) {
		super();
		
		this.id = contador;
		Reserva.contador ++;
		this.huespedes = huespedes;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.habitacion = habitacion;
	}

	public int getId() {
		return id;
	}
	
	public ArrayList<String> getHuespedes() {
		return huespedes;
	}
	
	public void setHuespedes(ArrayList<String> huespedes) {
		this.huespedes = huespedes;
	}
	public long getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public long getFechaFin() {
		return fechaFin;
	}
	
	public void setFechaFin(long fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Habitacion getHabitacion() {
		return habitacion;
	}
	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return id == other.id;
	}

	@Override
	public String toString() {
		return String.valueOf(this.getHabitacion().getNumero());
	}
	
    @Override
    public int compareTo(Reserva other) {
        return Long.compare(this.fechaInicio, other.fechaInicio);
    }
	
}
