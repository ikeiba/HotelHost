package es.ingenieria.prog3.proyecto.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Reserva implements Comparable<Reserva> {
	
	private int id;
	private ArrayList<String> huespedes;
	private long fechaInicio;
	private long fechaFin;
	private int id_habitacion;
	private int id_usuario;

	
	
	public Reserva(int id_usuario, ArrayList<String> huespedes, long fechaInicio, long fechaFin, int id_habitacion) {
		super();
		
		this.huespedes = huespedes;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.id_habitacion = id_habitacion;
		this.id_usuario = id_usuario;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		 this.id = id;
	}
	
	public int getId_habitacion() {
		return id_habitacion;
	}

	public void setId_habitacion(int id_habitacion) {
		this.id_habitacion = id_habitacion;
	}
	
	public int getId_Usuario() {
		return id_usuario;
	}
	
	public void setId_Usuario(int id_usuario) {
		this.id_usuario = id_usuario;
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
		return String.valueOf(id_habitacion);
	}
	
    @Override
    public int compareTo(Reserva other) {
        return Long.compare(this.fechaInicio, other.fechaInicio);
    }
	
}