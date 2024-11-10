package es.ingenieria.prog3.proyecto.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
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
			
			int valor = Integer.valueOf(value.toString());
			barra.setString(valor*10 + "%");
			barra.setStringPainted(true);
			barra.setValue(valor*10);
			barra.setBackground(Color.WHITE);
			barra.setFont(new Font("Arial", Font.BOLD, 15));
			
			
			switch (valor) {
			case 0: 
			case 1: 
			case 2: 
			case 3: 
			case 4: barra.setForeground(Color.RED);
			barra.setBorder(new LineBorder(Color.RED, 2));
			break;
			case 5:
			case 6: 
			case 7: barra.setForeground(Color.ORANGE);
			barra.setBorder(new LineBorder(Color.ORANGE, 2));
			break;
			case 8: 
			case 9: 
			case 10: barra.setForeground(Color.GREEN);
			barra.setBorder(new LineBorder(Color.GREEN, 2));
			break;
			default: return null;
		}
			return barra;		
		}
		
		
		
		//2. Renderizacion de la columna del comentario
		if(column == 2) {
			
		}
		
		//3. Renderizacion de la columna de la fecha
		if(column == 3) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date fecha = new Date(Long.valueOf(value.toString()));
			label.setText(String.format(dateFormat.format(fecha)));
			label.setHorizontalAlignment(SwingConstants.CENTER);
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
