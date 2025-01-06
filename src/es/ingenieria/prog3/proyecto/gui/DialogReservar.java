package es.ingenieria.prog3.proyecto.gui;

import javax.swing.*;

import es.ingenieria.prog3.proyecto.domain.Habitacion;
import es.ingenieria.prog3.proyecto.domain.Hotel;
import es.ingenieria.prog3.proyecto.domain.Huesped;
import es.ingenieria.prog3.proyecto.domain.Reserva;
import es.ingenieria.prog3.proyecto.domain.TipoHabitacion;
import es.ingenieria.prog3.proyecto.gui.util.DataStore;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;

public class DialogReservar extends JDialog {
    
	private static final long serialVersionUID = 1L;

	private ArrayList<Habitacion> habitacionesDisponibles;
	private JComboBox<Habitacion> comboBoxHabitacionesDisponibles;
	private Habitacion habitacionSeleccionada;
	private JComboBox<String> comboHuespedes;
	private int numeroHuespedes;
	long diferenciaMilisegundos;
    long diasDiferencia;
    
	public DialogReservar(Hotel hotel) {
		setLayout(new BorderLayout()); // Cambiar layout del diálogo principal
		 
        JPanel panelReserva = new JPanel(new GridLayout(8, 1, 5, 11));
        
        JLabel labelTipoHabitacion = new JLabel("Selecciona el tipo de habitación:");
		JComboBox<TipoHabitacion> comboBoxTipoHabitacion = new JComboBox<TipoHabitacion>(TipoHabitacion.values());			
        
		JLabel labelHabitaciones = new JLabel("Selecciona la habitación:");
		habitacionesDisponibles = getHabitacionesPorFechas(getHabitacionesPorTipo(hotel.getHabitaciones(), (TipoHabitacion) comboBoxTipoHabitacion.getSelectedItem()));
		comboBoxHabitacionesDisponibles = new JComboBox<Habitacion>(habitacionesDisponibles.toArray(new Habitacion [0]));
		
        JLabel labelHuespedes = new JLabel("Incluye a los huéspedes (al menos 1):");
		comboHuespedes = new JComboBox<>();
		habitacionSeleccionada = (Habitacion) comboBoxHabitacionesDisponibles.getSelectedItem();
		numeroHuespedes = habitacionSeleccionada.getCapacidad();
		for (int i = 1; i <= numeroHuespedes; i++) {
			comboHuespedes.addItem(String.format("Huesped - %d", i));
		}
		
        JLabel labelPrecio = new JLabel("PRECIO TOTAL:");
        JLabel labelPrecioTotal = new JLabel(String.format("%.2f €", habitacionSeleccionada.getPrecio())); // Precio inicial
        diferenciaMilisegundos = DataStore.getSelectedFechaFin().getTime() - DataStore.getSelectedFechaInicio().getTime();
        diasDiferencia = diferenciaMilisegundos / (1000 * 60 * 60 * 24);
		labelPrecioTotal.setText(String.format("Importe total (%d x %.2f €): %.2f €", diasDiferencia, habitacionSeleccionada.getPrecio(), (diasDiferencia*habitacionSeleccionada.getPrecio())));
		
		//Listener para el comboBox con los tipos de habitaciones 
		comboBoxTipoHabitacion.addActionListener(e -> {
			habitacionesDisponibles.clear();
			habitacionesDisponibles = getHabitacionesPorFechas(getHabitacionesPorTipo(hotel.getHabitaciones(), (TipoHabitacion) comboBoxTipoHabitacion.getSelectedItem()));
			comboBoxHabitacionesDisponibles.removeAllItems();
			for (Habitacion habitacion : habitacionesDisponibles) {
				comboBoxHabitacionesDisponibles.addItem(habitacion);
			}
			if (habitacionesDisponibles.size() == 0) {
				comboBoxHabitacionesDisponibles.setEnabled(false);
			}
			habitacionSeleccionada = (Habitacion) comboBoxHabitacionesDisponibles.getSelectedItem();			
		});
		
		//Listener para el comboBox con habitaciones disponibles
		comboBoxHabitacionesDisponibles.addActionListener(e -> {
			if (comboBoxHabitacionesDisponibles.getSelectedItem() != null) {
				habitacionSeleccionada = (Habitacion) comboBoxHabitacionesDisponibles.getSelectedItem();
				long diferenciaMilisegundos = DataStore.getSelectedFechaFin().getTime() - DataStore.getSelectedFechaInicio().getTime();
		        long diasDiferencia = diferenciaMilisegundos / (1000 * 60 * 60 * 24);
				labelPrecioTotal.setText(String.format("Importe total (%d x %.2f €): %.2f €", diasDiferencia, habitacionSeleccionada.getPrecio(), (diasDiferencia*habitacionSeleccionada.getPrecio())));
				
				int nuevoNumeroHuespedes = habitacionSeleccionada.getCapacidad();	
				if (nuevoNumeroHuespedes != numeroHuespedes) {
					if (nuevoNumeroHuespedes > numeroHuespedes) {
						for (int i=numeroHuespedes+1; i<=nuevoNumeroHuespedes; i++) {
							comboHuespedes.addItem(String.format("Huesped - %d", i));					
						}
					} else {
						for (int i=numeroHuespedes; i>nuevoNumeroHuespedes; i--) {
							comboHuespedes.removeItemAt(i-1);
						}
					}
				}
				numeroHuespedes = comboHuespedes.getItemCount();
			}	
		});
		
		//Listener para el comboBox con los huespedes
		comboHuespedes.addActionListener((e) -> {
			int position = ((JComboBox<?>) e.getSource()).getSelectedIndex();
			String passenger = ((JComboBox<?>) e.getSource()).getSelectedItem().toString();
			
			//Si se ha seleccionado una persona que no tiene datos personales
			if (passenger.contains("Huesped")) {
				JTextField firstName = new JTextField();
				firstName.setColumns(30);
				JTextField lastName = new JTextField();
				lastName.setColumns(30);
				
				JComponent[] inputs = new JComponent[] {
					new JLabel("Nombre: "),
					firstName,
					new JLabel("Apellidos: "),
					lastName,
				};
				
				//Se muestra un cuadro de diálogo para introducir nombre y apellidos
				int result = JOptionPane.showConfirmDialog(this, inputs, 
									String.format("Datos de la persona - %s", passenger.substring(0, passenger.indexOf(" "))), 
									JOptionPane.OK_CANCEL_OPTION,
									JOptionPane.PLAIN_MESSAGE, new ImageIcon("resources/images/passenger.png"));
				//Si se pulsa confirmar y los campos no están vacíos se actualiza el combo de personas
				if (result == JOptionPane.OK_OPTION && 
						(!firstName.getText().trim().isEmpty() || !lastName.getText().trim().isEmpty())) {
					String name;
				
					if (firstName.getText().trim().isEmpty() || lastName.getText().trim().isEmpty()) {
						name = String.format("%s, %s", lastName.getText().trim(), firstName.getText()).trim();
					} else {
						name = String.format("%s, %s", lastName.getText().trim(), firstName.getText()).trim();
					}
					if (!name.contains("#")) {						
						name = String.format("%d - %s", position + 1, name.toUpperCase());
						comboHuespedes.removeItemAt(position);
						comboHuespedes.insertItemAt(name, position);
						comboHuespedes.setSelectedIndex(position);
					}
				}
			}
		});
		
		// Panel para los botones
        JPanel panelBotones = new JPanel();
        JButton botonCancelar = new JButton("Cancelar");
        JButton botonProcesarPago = new JButton("Procesar Pago");
        
        botonCancelar.addActionListener(e -> dispose());
        
        botonProcesarPago.addActionListener(e -> {
        	if (!comprobarHuesped()) {
        		JOptionPane.showMessageDialog(null, "Tienes que añadir al menos un huesped", "SIN HUESPEDES", JOptionPane.WARNING_MESSAGE);
        	} else {
        		//Guardamos los huespedes seleccionados para poder usarlos en el dialog de pago
        		ArrayList<Huesped> huespedesReserva = new ArrayList<Huesped>();
        		for (int i = 0; i < comboHuespedes.getItemCount(); i++) {
        			String datosHuesped = comboHuespedes.getItemAt(i);
					String[] campos = datosHuesped.split(" ");
					
					//Comprobamos que haya informacion sobre el huesped seleccionado
					if (!campos[0].equals("Huesped")) {
						String apellido = campos[2];
						Huesped huesped = new Huesped(campos[3], apellido.substring(0, apellido.length()-1), -1);
						huespedesReserva.add(huesped);
					}	
					
				}
        		System.out.println(habitacionSeleccionada.getReservas());
        		new DialogPago(habitacionSeleccionada, huespedesReserva);
        		if (DataStore.getVisible()) {
        			DataStore.setVisible(false);
        			dispose();
        		}
        	}
        	
        });
        
        //Añadimos espacio y fuente a los labels
        JLabel[] labels = {labelTipoHabitacion, labelHabitaciones, labelHuespedes, labelPrecio};
        for (int i = 0; i < labels.length; i++) {
			labels[i].setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			labels[i].setFont(new Font("Verdana", Font.BOLD, 12));
		}
        labelPrecioTotal.setFont(new Font("Verdana", Font.BOLD, 11));
        
        //Añadir los componentes a los diferentes paneles
        panelBotones.add(botonCancelar);
        panelBotones.add(botonProcesarPago);
		
        panelReserva.add(labelTipoHabitacion);
        panelReserva.add(comboBoxTipoHabitacion);
        panelReserva.add(labelHabitaciones);
        panelReserva.add(comboBoxHabitacionesDisponibles);
        panelReserva.add(labelHuespedes);
        panelReserva.add(comboHuespedes);
        panelReserva.add(labelPrecio);
        panelReserva.add(labelPrecioTotal);
        
        //Añadir los paneles al JDialog
		add(panelReserva, BorderLayout.NORTH);
		add(panelBotones, BorderLayout.SOUTH);
		
		//Configurar las caracteristicas del JDialog
        setIconImage(new ImageIcon("resources/images/Hotel Host.png").getImage()); // Cambiar el Logo de la ventana y barra de tareas
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle(String.format("Reserva en el hotel '%s'", hotel.getNombre()));		
		setSize(500, 350);
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	public ArrayList<Habitacion> getHabitacionesPorTipo(ArrayList<Habitacion> habitaciones, TipoHabitacion tipo){
		ArrayList<Habitacion> habitacionesTipo = new ArrayList<Habitacion>();
		for (Habitacion habitacion : habitaciones) {
			if (habitacion.getTipo().equals(tipo))
				habitacionesTipo.add(habitacion);
		}
		return habitacionesTipo;
	}
	
	public ArrayList<Habitacion> getHabitacionesPorFechas(ArrayList<Habitacion> habitaciones){
		ArrayList<Habitacion> habitacionesDisponibles = new ArrayList<Habitacion>();
		Date fechaEntradaSeleccionada = DataStore.getSelectedFechaInicio();
		Date fechaSalidaSeleccionada = DataStore.getSelectedFechaFin();

		for (Habitacion habitacion : habitaciones) {
	        boolean disponible = true;
			ArrayList<Reserva> reservas = habitacion.getReservas();
			
			for (Reserva reserva : reservas) {
				Date fechaEntradaReserva = new Date(reserva.getFechaInicio());
				Date fechaSalidaReserva = new Date(reserva.getFechaFin());
					// Comprobar si las fechas se solapan
	            if (!(fechaSalidaSeleccionada.before(fechaEntradaReserva) || fechaEntradaSeleccionada.after(fechaSalidaReserva))) {
	                disponible = false;
	                break; // Si hay conflicto, no es necesario seguir comprobando
	            }
			}
			 // Si no hubo conflictos con ninguna reserva, añadir a la lista de disponibles
	        if (disponible) {
	            habitacionesDisponibles.add(habitacion);
	        }
		}
		return habitacionesDisponibles;
	}
	
	public boolean comprobarHuesped() {
		for (int i = 0; i < comboHuespedes.getItemCount(); i++) {
			if (!comboHuespedes.getItemAt(i).equals(String.format("Huesped - %d", i+1))) {
				return true;
			}
		}	
		return false;
	}
	
}
