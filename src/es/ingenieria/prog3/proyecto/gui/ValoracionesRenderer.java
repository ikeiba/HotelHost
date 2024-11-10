package es.ingenieria.prog3.proyecto.gui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ValoracionesRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		//Creamos el label con el color de fondo de la tabla y el texto que tiene el objeto en cada celda
		JLabel label = new JLabel();
		label.setBackground(table.getBackground());
		label.setText(value.toString());
		
		
		//0. Renderizacion de la columna con el nombre del autor
		if (column == 0) {
			label.setFont(new Font("Arial", Font.BOLD, 12));
		}
		
		//1. Renderizacion de la columna de puntuacion del hotel (dependiendo de la puntuacion, la progress bar avanza mas o menos)
		if (column == 1) {
			JProgressBar barra = new JProgressBar(0, 100);
			barra.setString(value.toString() + "%");
			barra.setStringPainted(true);
			barra.setValue((int) Math.round((double)value));
			return barra;
			
		}
		
		
		
		//2. Renderizacion de la columna del comentario
		if(column == 2) {
			
		}
		

		//RENDERIZACIONES ADICIONALES
		
		//Si la celda está seleccionada se usa el color por defecto de selección de la tabla
		if (isSelected) {
			label.setBackground(table.getSelectionBackground());
			label.setForeground(table.getSelectionForeground());
		}
		
		label.setOpaque(true);
		
		return label;
	}

}
