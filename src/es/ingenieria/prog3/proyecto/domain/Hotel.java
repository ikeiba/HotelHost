package es.ingenieria.prog3.proyecto.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Hotel implements Comparable<Hotel> {
    
	private int id, estrellas;
	private String nombre, ciudad, descripcion;
	private ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
	private ArrayList<Valoracion> valoraciones = new ArrayList<Valoracion>();
	private ArrayList<Plan> planes;
	
	
	public Hotel(int id, int estrellas, String nombre, String ciudad, String descripcion,
			ArrayList<Plan> planes) {
		
		super();
		this.id = id;
		this.estrellas = estrellas;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.descripcion = descripcion;
		this.planes = planes;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
			if (habitacion.getPrecio() > precioMax) {
				precioMax = habitacion.getPrecio();
			}
		}
		
		return precioMax;
	}
	
	
	public static double precioMaximoHoteles(ArrayList<Hotel> hoteles) {
		double precioMaximo = -9999999;
		for (Hotel hotel : hoteles) {
			if (hotel.getPrecioMaximo() > precioMaximo) {
				precioMaximo = hotel.getPrecioMaximo();
			}
		}
		return precioMaximo;
	}
	
	public static double precioMinimoHoteles(ArrayList<Hotel> hoteles) {
		double precioMinimo = 9999999;
		for (Hotel hotel : hoteles) {
			if (hotel.getPrecioMinimo() < precioMinimo) {
				precioMinimo = hotel.getPrecioMinimo();
			}
		}
		return precioMinimo;
	}
	
	public String getPais() {
	HashMap<String, String[]> ciudadesPorPais = new HashMap<>();
	        ciudadesPorPais.put("Estados Unidos", new String[]{"Las Vegas", "Los Angeles", "Miami", "New York", "San Francisco", "Washington DC"});
	        ciudadesPorPais.put("España", new String[]{"Madrid", "Barcelona", "Bilbao", "Donosti", "Valencia", "Granada", "Sevilla", "Malaga", "Palma de Mallorca"});
	        ciudadesPorPais.put("Alemania", new String[]{"Berlin", "Frankfurt", "Gelsenkirchen", "Hamburgo", "Munich"});
	        ciudadesPorPais.put("Francia", new String[]{"Paris", "Bayona", "Biarritz", "Lille", "Lyon", "Marsella", "Niza"});
	        ciudadesPorPais.put("Mexico", new String[]{"Cancun"});
	        ciudadesPorPais.put("Italia", new String[]{"Roma", "Bolonia", "Cori", "Florencia", "Genova", "Milan", "Napoles", "Venecia"});
	        ciudadesPorPais.put("Reino Unido", new String[]{"Birmingham", "Liverpool", "Londres", "Manchester"});
	        ciudadesPorPais.put("Tailandia", new String[]{"Bangkok", "Pattaya"});
	        ciudadesPorPais.put("China", new String[]{"Hong Kong"});
	        ciudadesPorPais.put("Vietnam", new String[]{"Hanoi"});
			ciudadesPorPais.put("Paises Bajos", new String[]{"amsterdam", "Maastricht", "Roterdam"});
			ciudadesPorPais.put("Grecia", new String[]{"Atenas", "Creta", "Tesalonica"});
			ciudadesPorPais.put("Suiza", new String[]{"Basilea", "Ginebra", "Zurich"});
			ciudadesPorPais.put("Argentina", new String[]{"Buenos Aires"});
			ciudadesPorPais.put("Turquia", new String[]{"Estambul"});
			ciudadesPorPais.put("Irlanda", new String[]{"Galway", "Limerick"});
			ciudadesPorPais.put("Japon", new String[]{"Tokio", "Kioto", "Osaka", "Nara"});
			ciudadesPorPais.put("Australia", new String[]{"Melbourne", "Sidney"});
			ciudadesPorPais.put("India", new String[]{"Mumbai", "Nueva Delhi"});
			ciudadesPorPais.put("Brazil", new String[]{"Rio de Janeiro"});
			ciudadesPorPais.put("Canada", new String[]{"Toronto"});
			
			
		    for (String pais : ciudadesPorPais.keySet()) {
		         String[] ciudades = ciudadesPorPais.get(pais);
		         for (String c : ciudades) {
		            if (c.equalsIgnoreCase(ciudad)) { // Compara ignorando mayúsculas/minúsculas
		                 return pais;
		            }
		        }
		 }
	return null;
				
	}


	@Override
	public int compareTo(Hotel o) {
		// TODO Auto-generated method stub
		return this.nombre.compareTo(o.getNombre());
	}
}


