package es.ingenieria.prog3.proyecto.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Habitacion {
	private int planta, numero, camas;
	private TipoHabitacion tipo;
	private ArrayList<Huesped> huespedes;
	
	

	public Habitacion(int planta, int numero, int camas, TipoHabitacion tipo, ArrayList<Huesped> huespedes) {
		super();
		this.planta = planta;
		this.numero = numero;
		this.camas = camas;
		this.tipo = tipo;
		this.huespedes = huespedes;
	}

	public int getPlanta() {
		return planta;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public int getCamas() {
		return camas;
	}
	
	public TipoHabitacion getTipo() {
		return tipo;
	}
	
	public ArrayList<Huesped> getHuespedes() {
		return huespedes;
	}
	
	public void setHuespedes(ArrayList<Huesped> huespedes) {
		this.huespedes = huespedes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(camas, huespedes, numero, planta, tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Habitacion other = (Habitacion) obj;
		return camas == other.camas && Objects.equals(huespedes, other.huespedes) && numero == other.numero
				&& planta == other.planta && tipo == other.tipo;
	}

	@Override
	public String toString() {
		return String.valueOf(this.getNumero());
	}
	
}