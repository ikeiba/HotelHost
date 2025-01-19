package es.ingenieria.prog3.proyecto.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Habitacion {
	private int planta, numero, capacidad, id, id_hotel;
	private TipoHabitacion tipo;
	private double precio;
	private ArrayList<Reserva> reservas = new ArrayList<Reserva>();


	public Habitacion(int planta, int numero, int capacidad, TipoHabitacion tipo, double precio, int id_hotel) {
		super();
		this.id_hotel = id_hotel;
		this.planta = planta;
		this.numero = numero;
		this.capacidad = capacidad;
		this.tipo = tipo;
		this.precio = Math.round((precio + calculoSuplemento(tipo, precio)) * 100.0) / 100.0;;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		 this.id = id;
	}
	
	public int getIdHotel() {
		return id_hotel;
	}

	public void setIdHotel(int id_hotel) {
		this.id_hotel = id_hotel;
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
	
	public int getCapacidad() {
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
		double precioFinal = precio;
		switch (tipo) {
			case SUITE: {
				precioFinal = precio * 1.2;
			}
			case DOBLE: {
				precioFinal = precio * 1.1;
			}
			case PREMIUM: {
				precioFinal = precio * 1.3;
			}
			case SIMPLE: {
				precioFinal = precio * 1.05;
			}
		}
		return Math.round(precioFinal);
	}
	
	
}