package es.ingenieria.prog3.proyecto.gui.util;

import java.util.Date;

import es.ingenieria.prog3.proyecto.db.GestorBD;
import es.ingenieria.prog3.proyecto.domain.Hotel;
import es.ingenieria.prog3.proyecto.domain.Usuario;

public class DataStore {
	private static String ciudad;
	private static Date fechaInicio;
	private static Date fechaFin;
	private static boolean confirmado = false;
	private static Usuario usuarioActivo;
	private static Hotel hotelReserva;
	private static GestorBD gestorBD;


    public static String getSelectedCiudad() {
        return ciudad;
    }

    public static Date getSelectedFechaInicio() {
        return fechaInicio;
    }
    
    public static Date getSelectedFechaFin() {
        return fechaFin;
    }

    public static boolean getVisible() {
        return confirmado;
    }
    
    public static Usuario getUsuarioActivo() {
        return usuarioActivo;
    }
    
    public static void setSelectedCiudad(String value) {
        ciudad = value;
    }
    
    public static void setSelectedFechaInicio(Date inicio) {
    	fechaInicio = inicio;
    }
    
    public static void setSelectedFechaFin(Date fin) {
    	fechaFin = fin;
    }
    
    public static void setVisible(boolean value) {
    	confirmado = value;
    }
    
    public static void setUsuarioActivo(Usuario usuario) {
    	usuarioActivo = usuario;
    }

	public static Hotel getHotelReserva() {
		return hotelReserva;
	}

	public static void setHotelReserva(Hotel hotelReserva) {
		DataStore.hotelReserva = hotelReserva;
	}

	public static GestorBD getGestorBD() {
		return gestorBD;
	}

	public static void setGestorBD(GestorBD gestorBD) {
		DataStore.gestorBD = gestorBD;
	}
}
