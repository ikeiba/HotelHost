package es.ingenieria.prog3.proyecto.domain;

import java.util.ArrayList;

public class Huesped {
	private static int contador = 1;
	private int id;
	private String nombre, apellido;
	private ArrayList<Reserva> reservas;
	private ArrayList<Valoracion> valoraciones;
	
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
	
}