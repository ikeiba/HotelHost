package es.ingenieria.prog3.proyecto.domain;

import java.util.ArrayList;

public class Huesped {
	private static int contador = 1;
	private int id;
	private String nombre, apellido;
	private ArrayList<Reserva> reservas;
	private ArrayList<Valoracion> valoraciones;
	
}