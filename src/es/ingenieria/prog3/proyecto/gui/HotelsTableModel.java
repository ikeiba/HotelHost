package es.ingenieria.prog3.proyecto.gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.ingenieria.prog3.proyecto.domain.Hotel;


public class HotelsTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private List<Hotel> hotels;
	
	private final List<String> headers = Arrays.asList(
			"HOTEL", 
			"ESTRELLAS", 
			"CIUDAD",  
			"RANGO PRECIOS", 
			"RESERVAR");

	public HotelsTableModel(List<Hotel> hotels) {
		this.hotels = hotels;
		

	}
	
	@Override
	public String getColumnName(int column) {
		return headers.get(column);
	}

	@Override
	public int getRowCount() {
		if (hotels != null) {
			return hotels.size();
		} else { 
			return 0;
		}
	}

	@Override
	public int getColumnCount() {
		return headers.size(); 
	}
	
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    @Override
    public void setValueAt(Object aValue, int row, int column) {    	
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Hotel hotel = hotels.get(rowIndex);
		
		switch (columnIndex) {
			case 0: return hotel;
			case 1: return hotel.getEstrellas();
			case 2: return hotel.getCiudad();
			case 3: return (hotel.getPrecioMinimo() + "€" + " - " + hotel.getPrecioMaximo() + "€");
			case 4: return "reserva";
			
			default: return null;
		}
	}
	
	
	public void setHotels(List<Hotel> hotels) {
	    this.hotels = hotels;
	    fireTableDataChanged(); // Notifica a la tabla que los datos han cambiado
	}
}