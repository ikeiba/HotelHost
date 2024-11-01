package es.ingenieria.prog3.proyecto.domain;


public class Valoracion {
	private long fecha;
	private String comentario;
	private int puntuacion;
	private Huesped autor;
	
	public long getFecha() {
		return fecha;
	}
	public String getComentario() {
		return comentario;
	}
	public int getPuntuacion() {
		return puntuacion;
	}
	public Huesped getAutor() {
		return autor;
	}
}
