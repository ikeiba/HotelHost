package es.ingenieria.prog3.proyecto.domain;

import java.util.ArrayList;

public class Usuario {
	private int id;
	private String usuario;
	private String nombre;
	private String apellido;
	private long fechaNacimiento;
	private int genero;
	private String email;
	private String contrasena;
	private ArrayList<Reserva> reservas;
	private ArrayList<Valoracion> valoraciones;
	
	public Usuario(String usuario, String nombre, String apellido, long fechaNacimiento, int genero, String email, String contrasena, ArrayList<Reserva> reservas, ArrayList<Valoracion> valoraciones) {
		this.usuario = usuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.genero = genero;
		this.email = email;
		this.contrasena = contrasena;
		this.reservas = reservas;
		this.valoraciones = valoraciones;
	}

	public int getId() {
		return id;
	}

	public int setId() {
		return id;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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

	public int getGenero() {
		return genero;
	}

	public void setGenero(int genero) {
		this.genero = genero;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
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

	public long getFechaNacimiento() {
		return fechaNacimiento;
	}

	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + "]";
	}
	
	public static Usuario getUsuarioByNick(ArrayList<Usuario> usuarios, String nick) {
		Usuario usuarioFinal = usuarios.get(0);
		for (Usuario usuario : usuarios) {
			if (usuario.getUsuario().equals(nick)) {
				usuarioFinal = usuario;
				return usuarioFinal;
			}
		}
		return usuarioFinal;
	}
	
}
