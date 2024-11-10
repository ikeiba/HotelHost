package es.ingenieria.prog3.proyecto.gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.ingenieria.prog3.proyecto.domain.Valoracion;

public class ValoracionesTableModel extends DefaultTableModel{
	private static final long serialVersionUID = 1L;
	
	private List<Valoracion> valoraciones;
	
	private final List<String> headers = Arrays.asList(
			"AUTOR", 
			"PUNTUACION", 
			"COMENTARIO", 
			"FECHA");

	public ValoracionesTableModel(List<Valoracion> valoraciones) {
		this.valoraciones = valoraciones;
		

	}
	
	@Override
	public String getColumnName(int column) {
		return headers.get(column);
	}

	@Override
	public int getRowCount() {
		if (valoraciones != null) {
			return valoraciones.size();
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
		Valoracion valoracion = valoraciones.get(rowIndex);
		
		switch (columnIndex) {
			case 0: return valoracion.getAutor();
			case 1: return valoracion.getPuntuacion();
			case 2: return valoracion.getComentario();
			case 3: return valoracion.getFecha();
			default: return null;
		}
	}
}
