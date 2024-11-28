package es.ingenieria.prog3.proyecto.gui.util;

public class DataStore {
	private static String selectedValue;

    public static String getSelectedValue() {
        return selectedValue;
    }

    public static void setSelectedValue(String value) {
        selectedValue = value;
    }
}
