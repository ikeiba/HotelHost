package es.ingenieria.prog3.proyecto.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Huesped {
	private static int contador = 1;
	private int id;
	private String nombre, apellido;
	private ArrayList<Reserva> reservas;
	private ArrayList<Valoracion> valoraciones;
	
	
	
	public Huesped(String nombre, String apellido, ArrayList<Reserva> reservas,
			ArrayList<Valoracion> valoraciones) {
		
		super();
		this.id = contador;
		Huesped.contador ++;
		this.nombre = nombre;
		this.apellido = apellido;
		this.reservas = reservas;
		this.valoraciones = valoraciones;
	}
	
	public static int getContador() {
		return contador;
	}
	public int getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public ArrayList<Reserva> getReservas() {
		return reservas;
	}
	public void setReservas(ArrayList<Reserva> reservas) {
		this.reservas = reservas;
	}
	public ArrayList<Valoracion> getValoraciones() {
		return valoraciones;
	}
	public void setValoraciones(ArrayList<Valoracion> valoraciones) {
		this.valoraciones = valoraciones;
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
		Huesped other = (Huesped) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return this.getNombre();
	}
	
}