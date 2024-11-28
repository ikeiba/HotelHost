package es.ingenieria.prog3.proyecto.gui.util;

import java.util.Date;

public class DataStore {
	private static String ciudad;
	private static Date fechaInicio;
	private static Date fechaFin;
	private static boolean visible;


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
        return visible;
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
    	visible = value;
    }
}
