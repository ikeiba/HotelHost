package es.ingenieria.prog3.proyecto.domain;

import java.util.ArrayList;

public class Habitacion {
	private int planta, numero, camas;
	private TipoHabitacion tipo;
	private ArrayList<Huesped> huespedes;

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
}