package es.ingenieria.prog3.proyecto.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Habitacion {
	private int planta, numero, capacidad;
	private TipoHabitacion tipo;
	private double precio;
	private ArrayList<Reserva> reservas = new ArrayList<Reserva>();


	public Habitacion(int planta, int numero, int capacidad, TipoHabitacion tipo, double precio) {
		super();
		this.planta = planta;
		this.numero = numero;
		this.capacidad = capacidad;
		this.tipo = tipo;
		this.precio = precio + calculoSuplemento(tipo, precio);
	}

	public int getPlanta() {
		return planta;
	}
	
	public int getNumero() {
		return numero;
	}
	
	
	public double getPrecio() {
		return precio;
	}
	
	public int getCamas() {
		return capacidad;
	}
	
	public TipoHabitacion getTipo() {
		return tipo;
	}

	public ArrayList<Reserva> getReservas() {
		return reservas;
	}
	
	public void setReservas(ArrayList<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(capacidad, numero, planta, tipo);
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
		return capacidad == other.capacidad && numero == other.numero
				&& planta == other.planta && tipo == other.tipo;
	}

	@Override
	public String toString() {
		return String.valueOf(this.getNumero());
	}
	
	public double calculoSuplemento(TipoHabitacion tipo, double precio) {
		switch (tipo) {
			case SUITE: {
				return (precio * 0.5);
			}
			case DOBLE: {
				return (precio * 0.2);
			}
			case PREMIUM: {
				return (precio * 0.7);
			}
			case SIMPLE: {
				return (precio * 0.1);
			}
		}
		return 0;
	}
	
	
}