package es.ingenieria.prog3.proyecto.gui.util;

import java.util.Date;

import es.ingenieria.prog3.proyecto.domain.Usuario;

public class DataStore {
	private static String ciudad;
	private static Date fechaInicio;
	private static Date fechaFin;
	private static boolean confirmado = false;
	private static Usuario usuarioActivo;


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
}
