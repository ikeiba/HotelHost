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
		this.precio = Math.round((precio + calculoSuplemento(tipo, precio)) * 100.0) / 100.0;;
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
	
	//int planta, int numero, int capacidad, TipoHabitacion tipo, double precio)
	//Cargar habitaciones a cada hotel
	public static void crearHabitaciones(ArrayList<Hotel> hoteles) {
		for (Hotel hotel : hoteles) {
			int numeroPlantas = (int)(Math.random() * (9 - 1 + 1)) + 1; //el numero de planta sera un numero aleatorio entre 1-9
			int numeroHabitaciones = (int)(Math.random() * (20 - 5 + 1)) + 5; //el numero de habitaciones que habra en cada planta ser un numero aleatorio entre 5-20 (todas las plantas mismo numero de habitaciones
			for (int i = 1; i <= numeroPlantas; i++) { 	
				for (int j = 1; j <= numeroHabitaciones; j++) {
					int numero;
					if (j > 9) {
						numero = Integer.valueOf(String.valueOf(i) + j);
					}else {
						numero = Integer.valueOf(String.valueOf(i) + 0 + j);

					}
					int capacidad = (int)(Math.random() * (6 - 1 + 1)) + 1; //la capacidad sera un numero entre 1-6
					int indiceTipoHabitacion = (int)(Math.random() * TipoHabitacion.values().length);
					TipoHabitacion tipoHabitacion = TipoHabitacion.values()[indiceTipoHabitacion];
					double precio = (Math.random() * (200 - 50 + 1)) + 50; //el precio sera un numero aleatorio entre 50 y 150
					Habitacion habitacion = new Habitacion(i, numero, capacidad, tipoHabitacion, precio);
					hotel.getHabitaciones().add(habitacion);
				}
			}
		}
	}
	
}