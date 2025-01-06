package es.ingenieria.prog3.proyecto.domain;

import java.util.Objects;

public class Valoracion {
	private long fecha;
	private String comentario;
	private int puntuacion;
	private String autor;
	private int id;
	private int id_hotel;
	private String id_usuario;
	
	
	public Valoracion(String id_usuario, long fecha, String comentario, int puntuacion,  String autor, int id_hotel) {
		super();
		
		this.id_usuario = id_usuario;
		this.id_hotel = id_hotel;
		this.fecha = fecha;
		this.comentario = comentario;
		this.puntuacion = puntuacion;
		this.autor = autor;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		 this.id = id;
	}
	
	public int getIdHotel() {
		return id_hotel;
	}

	public void setIdHotel(int id_hotel) {
		this.id_hotel = id_hotel;
	}
	
	public String getId_Usuario() {
		return id_usuario;
	}
	
	public void setId_Usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	public long getFecha() {
		return fecha;
	}
	public String getComentario() {
		return comentario;
	}
	public int getPuntuacion() {
		return puntuacion;
	}
	public String getAutor() {
		return autor;
	}
	@Override
	public int hashCode() {
		return Objects.hash(autor, comentario, fecha, puntuacion);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Valoracion other = (Valoracion) obj;
		return Objects.equals(autor, other.autor) && Objects.equals(comentario, other.comentario)
				&& fecha == other.fecha && puntuacion == other.puntuacion;
	}
	
	@Override
	public String toString() {
		return this.autor.toString();
	}
	
	/*
	public static ArrayList<Valoracion> cargarValoraciones(String archivoCSV) {
        ArrayList<Valoracion> valoraciones = new ArrayList<>();
        String linea;

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            // Saltar la primera línea de encabezado
            br.readLine();
            
            // Leer cada línea del archivo
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                // Asignar valores de cada columna a las variables correspondientes
                String autor = datos[0].trim();
                int puntuacion = Integer.parseInt(datos[1].trim());
                String comentario = datos[2].trim();
                long fecha = Long.parseLong(datos[3].trim());

                // Crear una instancia de Valoracion y agregarla a la lista
                //Valoracion valoracion = new Valoracion(fecha, comentario, puntuacion, autor);
                //valoraciones.add(valoracion);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato de datos: " + e.getMessage());
        }

        return valoraciones;
    }
	*/
	
}
