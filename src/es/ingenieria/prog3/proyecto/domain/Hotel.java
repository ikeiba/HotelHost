package es.ingenieria.prog3.proyecto.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Hotel {
    
	private static int contador = 1;
	private int id, estrellas;
	private String nombre, ciudad, descripcion;
	private ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
	private ArrayList<Valoracion> valoraciones = new ArrayList<Valoracion>();
	private ArrayList<Plan> planes;
	private ArrayList<Reserva> reservas = new ArrayList<Reserva>();
	
	
	public Hotel(int estrellas, String nombre, String ciudad, String descripcion,
			ArrayList<Plan> planes) {
		
		super();
		this.id = contador;
		Hotel.contador ++;
		this.estrellas = estrellas;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.descripcion = descripcion;
		this.planes = planes;
	}


	public int getId() {
		return id;
	}


	public int getEstrellas() {
		return estrellas;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public ArrayList<Habitacion> getHabitaciones() {
		return habitaciones;
	}
	
	public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}
	
	public ArrayList<Valoracion> getValoraciones() {
		return valoraciones;
	}
	
	public void setValoraciones(ArrayList<Valoracion> valoraciones) {
		this.valoraciones = valoraciones;
	}
	
	public ArrayList<Plan> getPlanes() {
		return planes;
	}

	public ArrayList<Reserva> getReservas() {
		return reservas;
	}
	
	public void setReservas(ArrayList<Reserva> reservas) {
		this.reservas = reservas;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotel other = (Hotel) obj;
		return id == other.id;
	}


	@Override
	public String toString() {
		return this.getNombre();
	}
	 
	
	public double getPrecioMinimo() {
		double precioMin = 99999999;
		
		for (Habitacion habitacion : habitaciones) {
			if (habitacion.getPrecio() < precioMin) {
				precioMin = habitacion.getPrecio();
			}
		}
		
		return precioMin;
	}
	
	public double getPrecioMaximo() {
		double precioMax = -99999999;
		
		for (Habitacion habitacion : habitaciones) {
			if (habitacion.getPrecio() < precioMax) {
				precioMax = habitacion.getPrecio();
			}
		}
		
		return precioMax;
	}
	
	public static ArrayList<Hotel> cargarHoteles(String filePath) {
        ArrayList<Hotel> hoteles = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea;
            
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",", 5); // Dividir en cinco partes: nombre, estrellas, ciudad, descripcion, planes
                if (valores.length == 5) {
                    String nombre = valores[0].trim();
                    int estrellas = Integer.parseInt(valores[1].trim());
                    String ciudad = valores[2].trim();
                    String descripcion = valores[3].trim();
                    
                    // Convertir la lista de planes en enum Plan
                    ArrayList<Plan> planes = new ArrayList<>();
                    for (String plan : valores[4].split(";")) {
                        try {
                            planes.add(Plan.valueOf(plan.trim().toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            System.out.println(plan);
                        }
                    }

                    
                    Hotel hotel = new Hotel(estrellas, nombre, ciudad, descripcion, planes);
                    hoteles.add(hotel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hoteles;
    }
}

