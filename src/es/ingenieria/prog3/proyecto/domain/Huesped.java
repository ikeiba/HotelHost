package es.ingenieria.prog3.proyecto.domain;

public class Huesped {
	private String nombre, apellido;
	private int id_reserva;

	public Huesped(String nombre, String apellido, int id_reserva) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.id_reserva = id_reserva;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getId_reserva() {
		return id_reserva;
	}

	public void setId_reserva(int id_reserva) {
		this.id_reserva = id_reserva;
	}

	@Override
	public String toString() {
		return "Huesped [nombre=" + nombre + ", id_reserva=" + id_reserva + "]";
	}
	
	
}
