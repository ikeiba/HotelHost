package es.ingenieria.prog3.proyecto.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import es.ingenieria.prog3.proyecto.domain.Habitacion;
import es.ingenieria.prog3.proyecto.domain.Hotel;
import es.ingenieria.prog3.proyecto.domain.Plan;
import es.ingenieria.prog3.proyecto.domain.TipoHabitacion;
import es.ingenieria.prog3.proyecto.domain.Usuario;
import es.ingenieria.prog3.proyecto.domain.Valoracion;


public class GestorBD {

	private final String PROPERTIES_FILE = "resources/config/app.properties";
	private final String CSV_HOTELES = "resources/data/hoteles.csv";
	private final String CSV_VALORACIONES = "resources/data/valoraciones.csv";
	private final String LOG_FOLDER = "resources/log";
	
	private Properties properties;
	private String driverName;
	private String databaseFile;
	private String connectionString;
	
	private static Logger logger = Logger.getLogger(GestorBD.class.getName());
	
	public GestorBD() {
		try (FileInputStream fis = new FileInputStream("resources/config/logger.properties")) {
			//Inicialización del Logger
			LogManager.getLogManager().readConfiguration(fis);
			
			//Lectura del fichero properties
			properties = new Properties();
			properties.load(new FileReader(PROPERTIES_FILE));
			
			driverName = properties.getProperty("driver");
			databaseFile = properties.getProperty("file");
			connectionString = properties.getProperty("connection");
			
			//Crear carpetas de log si no existe
			File dir = new File(LOG_FOLDER);
			
			if (!dir.exists()) {
				dir.mkdirs();
			}

			//Crear carpeta para la BBDD si no existe
			dir = new File(databaseFile.substring(0, databaseFile.lastIndexOf("/")));
			
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			//Cargar el diver SQLite
			Class.forName(driverName);
		} catch (Exception ex) {
			logger.warning(String.format("Error al cargar el driver de BBDD: %s", ex.getMessage()));
		}
	}
	
	/**
	 * Inicializa la BBDD leyendo los datos de los ficheros CSV 
	 */
	
	public void initilizeFromCSV() {
		//Sólo se inicializa la BBDD si la propiedad initBBDD es true.
		if (properties.get("loadCSV").equals("true")) {
			//Se borran los datos, si existía alguno
			this.borrarDatos();
			
			//Se leen los hoteles del CSV
			List<Hotel> hoteles = this.cargarHoteles(CSV_HOTELES);
			//Se insertan los hoteles en la BBDD
			this.insertarHoteles(hoteles.toArray(new Hotel[hoteles.size()]));
			
			//Se leen las valoraciones del CSV
			List<Valoracion> valoraciones = this.cargarValoraciones(CSV_VALORACIONES);	
			
			//Se enlazan los hoteles con sus valoraciones		
			enlazarHotelesValoraciones(hoteles, valoraciones); 
			//Se insertan las valoraciones en la BBDD
			this.insertarValoracion(valoraciones.toArray(new Valoracion[valoraciones.size()]));
			
			//Se crean y enlazan las habitaciones a los hoteles
			List<Habitacion> habitaciones = crearHabitaciones(hoteles);
			//Se insertan las habitaciones en la BBDD
			this.insertarHabitacion(habitaciones.toArray(new Habitacion[habitaciones.size()]));				
		}
	}

	public void crearBBDD() {
		//Sólo se crea la BBDD si la propiedad initBBDD es true.
		if (properties.get("createBBDD").equals("true")) {
			//La base de datos tiene 5 tablas: Hotel, Valoracion, Reserva, Habitacion y Usuario
			String sql1 = "CREATE TABLE IF NOT EXISTS Hotel (\n"
			        + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
			        + " nombre TEXT NOT NULL,\n"
			        + " ciudad TEXT NOT NULL,\n"
			        + " descripcion TEXT NOT NULL,\n"
			        + " estrellas INTEGER NOT NULL CHECK (estrellas BETWEEN 1 AND 5)\n"
			        + ");";

			String sql2 = "CREATE TABLE IF NOT EXISTS Valoracion (\n"
			        + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
			        + " autor TEXT NOT NULL,\n"
			        + " comentario TEXT NOT NULL,\n"
			        + " puntuacion INTEGER NOT NULL,\n"
			        + " id_hotel INTEGER NOT NULL,\n"
			        + " fecha INTEGER NOT NULL,\n"
			        + " id_usuario INTEGER,\n"   
			        + " FOREIGN KEY (id_hotel) REFERENCES Hotel(id) ON DELETE CASCADE\n"
			        + " FOREIGN KEY (id_usuario) REFERENCES Usuario(id) ON DELETE CASCADE\n"
			        + ");";

			String sql3 = "CREATE TABLE IF NOT EXISTS Reserva (\n"
			        + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
			        + " fechaInicio INTEGER NOT NULL,\n"
			        + " fechaFin INTEGER NOT NULL,\n"
			        + " id_habitacion INTEGER NOT NULL,\n"
			        + " id_usuario INTEGER,\n"
			        + " FOREIGN KEY (id_habitacion) REFERENCES Habitacion(id) ON DELETE CASCADE\n"
			        + " FOREIGN KEY (id_usuario) REFERENCES Usuario(id) ON DELETE CASCADE\n"
			        + ");";

			String sql4 = "CREATE TABLE IF NOT EXISTS Habitacion (\n"
			        + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
			        + " planta INTEGER NOT NULL,\n"
			        + " numero INTEGER NOT NULL,\n"
			        + " capacidad INTEGER NOT NULL CHECK (capacidad > 0),\n"
			        + " precio DOUBLE NOT NULL CHECK (precio >= 0),\n"
			        + " tipoHabitacion TEXT NOT NULL,\n"
			        + " id_hotel INTEGER NOT NULL,\n"
			        + " FOREIGN KEY (id_hotel) REFERENCES Hotel(id) ON DELETE CASCADE\n"
			        + ");";
			
			String sql5 = "CREATE TABLE IF NOT EXISTS Usuario (\n"
			        + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
			        + " usuario TEXT NOT NULL,\n"
			        + " nombre TEXT NOT NULL,\n"
			        + " apellido TEXT NOT NULL,\n"
			        + " email TEXT NOT NULL,\n"
			        + " contraseña TEXT NOT NULL,\n"
			        + " fechaNacimiento INTEGER NOT NULL,\n"
			        + " genero INTEGER NOT NULL\n"
			        + ");";

			
	        //Se abre la conexión y se crea un PreparedStatement para crer cada tabla
			//Al abrir la conexión, si no existía el fichero por defecto, se crea.
			try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt5 = con.prepareStatement(sql5);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
				 PreparedStatement pStmt4 = con.prepareStatement(sql4)) {
				
				//Se ejecutan las sentencias de creación de las tablas
		        if (!pStmt1.execute() && !pStmt5.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute()) {
		        	logger.info("Se han creado las tablas");
		        }
			} catch (Exception ex) {
				logger.warning(String.format("Error al crear las tablas: %s", ex.getMessage()));
			}
		}
	}
	
	/**
	 * Borra las tablas y el fichero de la BBDD.
	 */
	public void borrarBBDD() {
		//Sólo se borra la BBDD si la propiedad deleteBBDD es true
		if (properties.get("deleteBBDD").equals("true")) {	
			String sql1 = "DROP TABLE IF EXISTS Hotel;";
			String sql2 = "DROP TABLE IF EXISTS Valoracion;";
			String sql3 = "DROP TABLE IF EXISTS Habitacion";
			String sql4 = "DROP TABLE IF EXISTS Reserva;";
			String sql5 = "DROP TABLE IF EXISTS Usuario;";
			
	        //Se abre la conexión y se crea un PreparedStatement para borrar cada tabla
			try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
				 PreparedStatement pStmt4 = con.prepareStatement(sql4);
				 PreparedStatement pStmt5 = con.prepareStatement(sql5)) {
				
				//Se ejecutan las sentencias de borrado de las tablas
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute() && !pStmt5.execute()) {
		        	logger.info("Se han borrado las tablas");
		        }
			} catch (Exception ex) {
				logger.warning(String.format("Error al borrar las tablas: %s", ex.getMessage()));
			}
			
			try {
				//Se borra físicamente el fichero de la BBDD
				Files.delete(Paths.get(databaseFile));
				logger.info("Se ha borrado el fichero de la BBDD");
			} catch (Exception ex) {
				logger.warning(String.format("Error al borrar el fichero de la BBDD: %s", ex.getMessage()));
			}
		}
	}
	
	/**
	 * Borra los datos de la BBDD.
	 */
	public void borrarDatos() {
		//Sólo se borran los datos si la propiedad cleanBBDD es true
		if (properties.get("cleanBBDD").equals("true")) {	
			String sql1 = "DELETE FROM Hotel;";
			String sql2 = "DELETE FROM Usuario;";
			String sql3 = "DELETE FROM Valoracion;";
			String sql4 = "DELETE FROM Habitacion;";
			String sql5 = "DELETE FROM Reserva;";
			
	        //Se abre la conexión y se crea un PreparedStatement para borrar los datos de cada tabla
			try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
				 PreparedStatement pStmt4 = con.prepareStatement(sql4);
				 PreparedStatement pStmt5 = con.prepareStatement(sql5)) {
				
				//Se ejecutan las sentencias de borrado de las tablas
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute() && !pStmt5.execute()) {
		        	logger.info("Se han borrado los datos");
		        }
			} catch (Exception ex) {
				logger.warning(String.format("Error al borrar los datos: %s", ex.getMessage()));
			}
		}
	}

	public void insertarHoteles(Hotel... hoteles) {
		//Se define la plantilla de la sentencia SQL
		String sql = "INSERT INTO Hotel (nombre, ciudad, descripcion, estrellas) VALUES (?, ?, ?, ?);";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
									
			//Se recorren los clientes y se insertan uno a uno
			for (Hotel h : hoteles) {
				//Se añaden los parámetros al PreparedStatement
				pStmt.setString(1, h.getNombre());
				pStmt.setString(2, h.getCiudad());
				pStmt.setString(3, h.getDescripcion());
				pStmt.setInt(4, h.getEstrellas());
				
				if (pStmt.executeUpdate() != 1) {					
					logger.warning(String.format("No se ha insertado el Hotel: %s", h.getNombre()));
				} else {
					//IMPORTANTE: El valor del ID del personaje se establece automáticamente al
					//insertarlo en la BBDD. Por lo tanto, después de insertar un personaje, 
					//se recupera de la BBDD para establecer el campo ID en el objeto que está
					//en memoria.
					h.setId(this.getHotelByNombre(h.getNombre()).getId());					
					logger.info(String.format("Se ha insertado el Hotel: %s", h.getNombre()));
				}
			}
			
			logger.info(String.format("%d Personajes insertados en la BBDD", hoteles.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar personajes: %s", ex.getMessage()));
		}			
	}
	
	/**
	 * Inserta Comics en la BBDD
	 */
	public void insertarValoracion(Valoracion... valoraciones) {
		//Se define la plantilla de la sentencia SQL			
		String sql = "INSERT INTO Valoracion (autor, comentario, puntuacion, id_hotel, id_usuario, fecha) VALUES (?, ?, ?, ?, ?, ?);";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
			
			//Se recorren los clientes y se insertan uno a uno
			for (Valoracion v : valoraciones) {
				//Se definen los parámetros de la sentencia SQL
				pStmt.setString(1, v.getAutor().toString());
				pStmt.setString(2, v.getComentario());
				pStmt.setInt(3, v.getPuntuacion());
				pStmt.setInt(4, v.getIdHotel());
				pStmt.setInt(5, -1);
				pStmt.setLong(6, v.getFecha());
				


				
				if (pStmt.executeUpdate() != 1) {					
					logger.warning(String.format("No se ha insertado la Valoracion: %s", v.getAutor()));
				} else {
					//IMPORTANTE: El valor del ID del comic se establece automáticamente al
					//insertarlo en la BBDD. Por lo tanto, después de insertar un comic, 
					//se recupera de la BBDD para establecer el campo ID en el objeto que está
					//en memoria.
					//c.setId(this.getComicByTitulo(c.getTitulo()).getId());										
					
					logger.info(String.format("Se ha insertado la Valoracion: %s", v.getAutor()));
				}
			}
			
			logger.info(String.format("%d Valoraciones insertadas en la BBDD", valoraciones.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar valoracion: %s", ex.getMessage()));
		}				
	}
		

	public void insertarHabitacion(Habitacion... habitaciones) {
		//Se define la plantilla de la sentencia SQL			
		String sql = "INSERT INTO Habitacion (planta, numero, capacidad, precio, tipoHabitacion, id_hotel) VALUES (?, ?, ?, ?, ?, ?);";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
			
			//Se recorren los clientes y se insertan uno a uno
			for (Habitacion h : habitaciones) {
				//Se definen los parámetros de la sentencia SQL
				pStmt.setInt(1, h.getPlanta());
				pStmt.setInt(2, h.getNumero());
				pStmt.setInt(3, h.getCapacidad());
				pStmt.setDouble(4, h.getPrecio());
				pStmt.setString(5, h.getTipo().toString());
				pStmt.setInt(6, h.getIdHotel());
				


				
				if (pStmt.executeUpdate() != 1) {					
					logger.warning(String.format("No se ha insertado la Valoracion: %s", h.getNumero()));
				} else {
					//IMPORTANTE: El valor del ID del comic se establece automáticamente al
					//insertarlo en la BBDD. Por lo tanto, después de insertar un comic, 
					//se recupera de la BBDD para establecer el campo ID en el objeto que está
					//en memoria.
					//c.setId(this.getComicByTitulo(c.getTitulo()).getId());										
					
					logger.info(String.format("Se ha insertado la Valoracion: %s", h.getNumero()));
				}
			}
			
			logger.info(String.format("%d Valoraciones insertadas en la BBDD", habitaciones.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar valoracion: %s", ex.getMessage()));
		}				
	}

	public void insertarUsuario(Usuario... usuarios) {
		//Se define la plantilla de la sentencia SQL			
		String sql = "INSERT INTO Usuario (usuario, nombre, apellido, email, contraseña, fechaNacimiento, genero) VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
			
			//Se recorren los clientes y se insertan uno a uno
			for (Usuario u : usuarios) {
				//Se definen los parámetros de la sentencia SQL
				pStmt.setString(1, u.getUsuario());
				pStmt.setString(2, u.getNombre());
				pStmt.setString(3, u.getApellido());
				pStmt.setString(4, u.getEmail());
				pStmt.setString(5, u.getContrasena());
				pStmt.setFloat(6, u.getFechaNacimiento());
				pStmt.setInt(7, u.getGenero());

				if (pStmt.executeUpdate() != 1) {					
					logger.warning(String.format("No se ha insertado el Usuario: %s", u.getUsuario()));
				} else {
					//IMPORTANTE: El valor del ID del comic se establece automáticamente al
					//insertarlo en la BBDD. Por lo tanto, después de insertar un comic, 
					//se recupera de la BBDD para establecer el campo ID en el objeto que está
					//en memoria.
					//c.setId(this.getComicByTitulo(c.getTitulo()).getId());										
					
					logger.info(String.format("Se ha insertado el Usuario: %s", u.getUsuario()));
				}
			}
			
			logger.info(String.format("%d Usuarios insertados en la BBDD", usuarios.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar usuario: %s", ex.getMessage()));
		}				
	}
	
	
	private void enlazarHotelesValoraciones(List<Hotel> hoteles, List<Valoracion> valoraciones) {		
		// Asignamos secuencialmente las primeras valoraciones a los hoteles
        for (int i = 0; i < hoteles.size(); i++) {
            // Asignamos la valoración i al hotel i
        	valoraciones.get(i).setIdHotel(hoteles.get(i).getId());
            hoteles.get(i).getValoraciones().add(valoraciones.get(i));
        }
        //Asignamos el resto de valoraciones aleatoriamente
        for (int i = hoteles.size(); i < valoraciones.size(); i++) {
            // Asignamos las valoraciones restantes aleatoriamente
            int posicionHotel = (int) (Math.random() * hoteles.size());
        	valoraciones.get(i).setIdHotel(hoteles.get(posicionHotel).getId());
            hoteles.get(posicionHotel).getValoraciones().add(valoraciones.get(i));
        }		
	}

	public ArrayList<Hotel> getHoteles() {
		ArrayList<Hotel> hoteles = new ArrayList<>();
		String sql = "SELECT * FROM Hotel";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se ejecuta la sentencia y se obtiene el ResultSet
			ResultSet rs = pStmt.executeQuery();			
			Hotel hotel;
			
			//Se recorre el ResultSet y se crean objetos
			while (rs.next()) {
				hotel = new Hotel(rs.getInt("id"), rs.getInt("estrellas"), rs.getString("nombre"), rs.getString("ciudad"), rs.getString("descripcion"), null);
				
				ArrayList<Habitacion> habitaciones = this.getHabitacionByHotel(hotel);
				ArrayList<Valoracion> valoraciones = this.getValoracionByHotel(hotel);
				
				hotel.setHabitaciones(habitaciones);
				hotel.setValoraciones(valoraciones);
				//Se inserta cada nuevo cliente en la lista de clientes
				hoteles.add(hotel);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			logger.info(String.format("Se han recuperado %d personajes.", hoteles.size()));			
		} catch (Exception ex) {
			logger.warning(String.format("Error recuperar los personajes: %s", ex.getMessage()));						
		}		
		
		return hoteles;
	}
	
	public Hotel getHotelByNombre(String nombre) {
		Hotel hotel = null;
		String sql = "SELECT * FROM Hotel WHERE nombre = ? LIMIT 1";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se definen los parámetros de la sentencia SQL
			pStmt.setString(1, nombre);
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();			

			//Se procesa el único resultado
			if (rs.next()) {
				hotel = new Hotel(rs.getInt("id"), rs.getInt("estrellas"), (rs.getString("nombre")), rs.getString("ciudad"), rs.getString("descripcion"), null);

				//Se recuperan las valoraciones del hotel
				ArrayList<Valoracion> valoraciones = this.getValoracionByHotel(hotel);
				hotel.setValoraciones(valoraciones);
				
				//Se recuperan las habitaciones del hotel
				
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			logger.info(String.format("Se ha recuperado el hotel %s", hotel.getNombre()));			
		} catch (Exception ex) {
			logger.warning(String.format("Error recuperar el comic con nombre %s: %s", hotel.getNombre(), ex.getMessage()));						
		}		

		return hotel;
	}

	
	
	public ArrayList<Valoracion> getValoracionByHotel(Hotel hotel) {
		ArrayList<Valoracion> valoraciones = new ArrayList<Valoracion>();
		String sql = "SELECT * FROM Valoracion WHERE id_hotel = ?";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se definen los parámetros de la sentencia SQL
			pStmt.setInt(1, hotel.getId());
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();			

			while (rs.next()) {
			Valoracion valoracion = new Valoracion(rs.getInt("id_usuario"), rs.getInt("fecha"), rs.getString("comentario"), rs.getInt("puntuacion"), rs.getString("autor"), rs.getInt("id_hotel"));
			
			
			valoraciones.add(valoracion);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			logger.info(String.format("Se ha recuperado el hotel %s", hotel.getNombre()));			
		} catch (Exception ex) {
			logger.warning(String.format("Error recuperar el comic con nombre %s: %s", hotel.getNombre(), ex.getMessage()));						
		}		
		
		return valoraciones;
	}
	
	
	public ArrayList<Habitacion> getHabitacionByHotel(Hotel hotel) {
		ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
		String sql = "SELECT * FROM Habitacion WHERE id_hotel = ?";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se definen los parámetros de la sentencia SQL
			pStmt.setInt(1, hotel.getId());
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();			

			while (rs.next()) {
			Habitacion habitacion = new Habitacion(rs.getInt("planta"), rs.getInt("numero"), rs.getInt("capacidad"), TipoHabitacion.valueOf(rs.getString("tipoHabitacion")), rs.getDouble("precio"), rs.getInt("id_hotel"));
			
			
			habitaciones.add(habitacion);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			logger.info(String.format("Se ha recuperado el hotel %s", hotel.getNombre()));			
		} catch (Exception ex) {
			logger.warning(String.format("Error recuperar el comic con nombre %s: %s", hotel.getNombre(), ex.getMessage()));						
		}		
		
		return habitaciones;
	}
	
	
	public ArrayList<Hotel> cargarHoteles(String filePath) {
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
                        	
                        }
                    }

                    
                    Hotel hotel = new Hotel(-1, estrellas, nombre, ciudad, descripcion, planes);
                    hoteles.add(hotel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hoteles;
    }
	

	public ArrayList<Valoracion> cargarValoraciones(String archivoCSV) {
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
                Valoracion valoracion = new Valoracion(-1, fecha, comentario, puntuacion, autor, -1);
                valoraciones.add(valoracion);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato de datos: " + e.getMessage());
        }

        return valoraciones;
    }
	
	public ArrayList<Habitacion> crearHabitaciones(List<Hotel> hoteles) {
		ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
		for (Hotel hotel : hoteles) {
			int numeroPlantas = (int)(Math.random() * (3 - 1 + 1)) + 1; //el numero de planta sera un numero aleatorio entre 1-9
			int numeroHabitaciones = (int)(Math.random() * (7 - 5 + 1)) + 5; //el numero de habitaciones que habra en cada planta ser un numero aleatorio entre 5-20 (todas las plantas mismo numero de habitaciones
			for (int i = 1; i <= numeroPlantas; i++) { 	
				for (int j = 1; j <= numeroHabitaciones; j++) {
					int numero;
					if (j > 9) {
						numero = Integer.valueOf(String.valueOf(i) + j);
					}else {
						numero = Integer.valueOf(String.valueOf(i) + 0 + j);

					}
					int capacidad = (int)(Math.random() * (4 - 1 + 1)) + 1; //la capacidad sera un numero entre 1-6
					int indiceTipoHabitacion = (int)(Math.random() * TipoHabitacion.values().length);
					TipoHabitacion tipoHabitacion = TipoHabitacion.values()[indiceTipoHabitacion];
					double precio = (Math.random() * (400 - 50 + 1)) + 50; //el precio sera un numero aleatorio entre 30 y 400
					Habitacion habitacion = new Habitacion(i, numero, capacidad, tipoHabitacion, precio, hotel.getId());
					hotel.getHabitaciones().add(habitacion);
					habitaciones.add(habitacion);
				}
			}
		}
		return habitaciones;
	}
	
}
