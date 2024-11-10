package es.ingenieria.prog3.proyecto.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class HotelsRenderer implements TableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean isSelected, boolean hasFocus, int row, int column) {		
		
		//Creamos el label con el color de fondo de la tabla y el texto que tiene el objeto en cada celda
		JLabel label = new JLabel();
		label.setBackground(table.getBackground());
		label.setText(value.toString());
        
		
		//0. Renderizacion de la columna con el nombre del hotel
		if (column == 0) {
			label.setFont(new Font("Arial", Font.BOLD, 12));
		}
		
		//1. Renderizacion de la columna de estrellas del hotel (dependiendo del numero de estrellas, ponemos una imagen)
		if(column == 1) {
			ImageIcon originalIcon = null;
			switch (label.getText()) {
			case "1": originalIcon = new ImageIcon("resources/images/1_estrella.png");
			break;
			case "2": originalIcon = new ImageIcon("resources/images/2_estrellas.png");
			break;
			case "3": originalIcon = new ImageIcon("resources/images/3_estrellas.png");
			break;
			case "4": originalIcon = new ImageIcon("resources/images/4_estrellas.png");
			break;
			case "5": originalIcon = new ImageIcon("resources/images/5_estrellas.png");
			break;
			default:
			break;
			}
			if (originalIcon != null) {
		        Image scaledImage = originalIcon.getImage().getScaledInstance(95, 22, Image.SCALE_SMOOTH);
		        ImageIcon resizedIcon = new ImageIcon(scaledImage);
		        label.setIcon(resizedIcon);
		        label.setText("");
		    }
		}
		
		
		//2. Renderizacion de la columna de Ciudades (con la bandera del pais al que pertenecen)
		if(column == 2) {
			ImageIcon originalIcon = null;
			switch (label.getText()) {
			case "New York": 
			case "Miami": 
			case "Las Vegas":
			case "Los Angeles": originalIcon = new ImageIcon("resources/images/Banderas/USA Flag.png");
			break;
			case "Paris": originalIcon = new ImageIcon("resources/images/Banderas/France Flag.png");
			break;
			case "Roma": originalIcon = new ImageIcon("resources/images/Banderas/Italy Flag.png");
			break;
			case "Bangkok":
			case "Pattaya": originalIcon = new ImageIcon("resources/images/Banderas/Thailand Flag.png");
			break;
			case "Hong Kong": originalIcon = new ImageIcon("resources/images/Banderas/China Flag.png");
			break;
			case "Hanoi": originalIcon = new ImageIcon("resources/images/Banderas/Vietnam Flag.png");
			break;
			default:
				break;
			}
			if (originalIcon != null) {
		        Image scaledImage = originalIcon.getImage().getScaledInstance(22, 12, Image.SCALE_SMOOTH);
		        ImageIcon resizedIcon = new ImageIcon(scaledImage);
		        label.setIcon(resizedIcon);
		        
		    }
		}
		
		//3. Renderizacion de la columna del precio medio
		if (column == 3) {
			label.setHorizontalAlignment(SwingConstants.CENTER);
		}

		//
		if (column == 4) {
			JButton JButton = new JButton("Reservar");
			return JButton;
		}

		//RENDERIZACIONES ADICIONALES
		
		//Si la celda está seleccionada se usa el color por defecto de selección de la tabla
		if (isSelected) {
			label.setBackground(table.getSelectionBackground());
			label.setForeground(table.getSelectionForeground());
		}
		
		//El VUELO, RESERVAS y ASIENTOS LIBRES se renderizan como texto ce trado

		/*
		if (column == 8) {
			JProgressBar barra = new JProgressBar(0, 100);
			barra.setString(value.toString() + "%");
			barra.setStringPainted(true);
			barra.setValue((int) Math.round((double)value));
			return barra;
			
		}
		*/
		
		label.setOpaque(true);
		
		return label;
	}
	
	
}
