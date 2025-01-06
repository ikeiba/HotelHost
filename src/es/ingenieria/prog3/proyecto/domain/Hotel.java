package es.ingenieria.prog3.proyecto.domain;

import java.util.ArrayList;

import java.util.Objects;

public class Hotel {
    
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
	
	
	/*
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

                    
                    //Hotel hotel = new Hotel(estrellas, nombre, ciudad, descripcion, planes);
                    //hoteles.add(hotel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hoteles;
    }
	*/
	
	
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
	
}

