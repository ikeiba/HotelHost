package es.ingenieria.prog3.proyecto.domain;

import java.util.Objects;

public class Valoracion {
	private long fecha;
	private String comentario;
	private int puntuacion;
	private Huesped autor;
	
	
	public Valoracion(long fecha, String comentario, int puntuacion, Huesped autor) {
		super();
		this.fecha = fecha;
		this.comentario = comentario;
		this.puntuacion = puntuacion;
		this.autor = autor;
	}
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
	@Override
	public int hashCode() {
		return Objects.hash(autor, comentario, fecha, puntuacion);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Valoracion other = (Valoracion) obj;
		return Objects.equals(autor, other.autor) && Objects.equals(comentario, other.comentario)
				&& fecha == other.fecha && puntuacion == other.puntuacion;
	}
	@Override
	public String toString() {
		return this.getAutor().getNombre();
	}
	
	
}
