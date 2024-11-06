package es.ingenieria.prog3.proyecto.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Hotel {
    
	private static int contador = 1;
	private int id, estrellas;
	private String nombre, ciudad, descripcion;
	private ArrayList<Habitacion> habitaciones;
	private ArrayList<Valoracion> valoraciones;
	private ArrayList<Plan> planes;
	private ArrayList<Reserva> reservas;
	
	
	public Hotel(int estrellas, String nombre, String ciudad, String descripcion,
			ArrayList<Habitacion> habitaciones, ArrayList<Valoracion> valoraciones, ArrayList<Plan> planes,
			ArrayList<Reserva> reservas) {
		
		super();
		this.id = contador;
		Hotel.contador ++;
		this.estrellas = estrellas;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.descripcion = descripcion;
		this.habitaciones = habitaciones;
		this.valoraciones = valoraciones;
		this.planes = planes;
		this.reservas = reservas;
	}


	public int getId() {
		return id;
	}


	public int getEstrellas() {
		return estrellas;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public ArrayList<Habitacion> getHabitaciones() {
		return habitaciones;
	}
	
	public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}
	
	public ArrayList<Valoracion> getValoraciones() {
		return valoraciones;
	}
	
	public void setValoraciones(ArrayList<Valoracion> valoraciones) {
		this.valoraciones = valoraciones;
	}
	
	public ArrayList<Plan> getPlanes() {
		return planes;
	}

	public ArrayList<Reserva> getReservas() {
		return reservas;
	}
	
	public void setReservas(ArrayList<Reserva> reservas) {
		this.reservas = reservas;
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
		Hotel other = (Hotel) obj;
		return id == other.id;
	}


	@Override
	public String toString() {
		return this.getNombre();
	}
	 
	
	public double precioMinimo() {
		double precioMin = Double.POSITIVE_INFINITY;
		
		for (Habitacion habitacion : habitaciones) {
			if (habitacion.getPrecio() < precioMin) {
				precioMin = habitacion.getPrecio();
			}
		}
		
		return precioMin;
	}
	
}

