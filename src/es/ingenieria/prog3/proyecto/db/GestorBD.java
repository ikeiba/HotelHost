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
import es.ingenieria.prog3.proyecto.domain.Huesped;
import es.ingenieria.prog3.proyecto.domain.Plan;
import es.ingenieria.prog3.proyecto.domain.Reserva;
import es.ingenieria.prog3.proyecto.domain.TipoHabitacion;
import es.ingenieria.prog3.proyecto.domain.Usuario;
import es.ingenieria.prog3.proyecto.domain.Valoracion;


public class GestorBD {

	private final String PROPERTIES_FILE = "resources/config/app.properties";
	private final String CSV_HOTELES = "resources/data/hoteles.csv";
	private final String CSV_VALORACIONES = "resources/data/valoraciones.csv";
	private final String CSV_USUARIOS = "resources/data/users.csv";

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
			
			//Se crean las habitaciones a los hoteles
			List<Habitacion> habitaciones = crearHabitaciones(hoteles);
			//Se insertan las habitaciones en la BBDD
			this.insertarHabitacion(habitaciones.toArray(new Habitacion[habitaciones.size()]));	
			//Se enlazan los hoteles con sus habitaciones		
			enlazarHotelesHabitaciones(hoteles, getHabitaciones());
			
			//Se leen los usuarios del CSV
			List<Usuario> usuarios = this.cargarUsuarios(CSV_USUARIOS);
			//Se insertan los usuarios en la BBDD
			this.insertarUsuario(usuarios.toArray(new Usuario[usuarios.size()]));
		}
	}

	public void crearBBDD() {
		//Sólo se crea la BBDD si la propiedad initBBDD es true.
		if (properties.get("createBBDD").equals("true")) {
			//La base de datos tiene 6 tablas: Hotel, Valoracion, Reserva, Habitacion, Usuario y Huesped
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
			        + " fecha BIGINT NOT NULL,\n"
			        + " id_usuario TEXT,\n"   
			        + " FOREIGN KEY (id_hotel) REFERENCES Hotel(id) ON DELETE CASCADE\n"
			        + " FOREIGN KEY (id_usuario) REFERENCES Usuario(id) ON DELETE CASCADE\n"
			        + ");";

			String sql3 = "CREATE TABLE IF NOT EXISTS Reserva (\n"
			        + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
			        + " fechaInicio BIGINT NOT NULL,\n"
			        + " fechaFin BIGINT NOT NULL,\n"
			        + " id_habitacion INTEGER NOT NULL,\n"
			        + " id_usuario TEXT,\n"
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
			        + " usuario TEXT NOT NULL UNIQUE,\n"
			        + " nombre TEXT NOT NULL,\n"
			        + " apellido TEXT NOT NULL,\n"
			        + " email TEXT NOT NULL,\n"
			        + " contraseña TEXT NOT NULL,\n"
			        + " fechaNacimiento BIGINT NOT NULL,\n"
			        + " genero INTEGER NOT NULL\n"
			        + ");";
			
			String sql6 = "CREATE TABLE IF NOT EXISTS Huesped (\n"			        
			        + " nombre TEXT NOT NULL,\n"
			        + " apellido TEXT NOT NULL,\n"
			        + " id_reserva INTEGER NOT NULL,\n"
			        + " FOREIGN KEY (id_reserva) REFERENCES Reserva(id) ON DELETE CASCADE\n"
			        + ");";

			
	        //Se abre la conexión y se crea un PreparedStatement para crer cada tabla
			//Al abrir la conexión, si no existía el fichero por defecto, se crea.
			try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt5 = con.prepareStatement(sql5);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
				 PreparedStatement pStmt4 = con.prepareStatement(sql4);
				 PreparedStatement pStmt6 = con.prepareStatement(sql6)) {
				
				//Se ejecutan las sentencias de creación de las tablas
		        if (!pStmt1.execute() && !pStmt5.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute() && !pStmt6.execute()) {
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
			String sql6 = "DROP TABLE IF EXISTS Huesped;";
			
	        //Se abre la conexión y se crea un PreparedStatement para borrar cada tabla
			try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
				 PreparedStatement pStmt4 = con.prepareStatement(sql4);
				 PreparedStatement pStmt5 = con.prepareStatement(sql5);
				 PreparedStatement pStmt6 = con.prepareStatement(sql6)) {
				
				//Se ejecutan las sentencias de borrado de las tablas
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute() && !pStmt5.execute() && !pStmt6.execute()) {
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
			String sql6 = "DELETE FROM Huesped;";
			
	        //Se abre la conexión y se crea un PreparedStatement para borrar los datos de cada tabla
			try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
				 PreparedStatement pStmt4 = con.prepareStatement(sql4);
				 PreparedStatement pStmt5 = con.prepareStatement(sql5);
				 PreparedStatement pStmt6 = con.prepareStatement(sql6)) {
				
				//Se ejecutan las sentencias de borrado de las tablas
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute() && !pStmt5.execute() && !pStmt6.execute()) {
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
					
					h.setId(this.getHotelByNombre(h.getNombre()).getId());					
					logger.info(String.format("Se ha insertado el Hotel: %s", h.getNombre()));
				}
			}
			
			logger.info(String.format("%d Hoteles insertados en la BBDD", hoteles.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar Hotel: %s", ex.getMessage()));
		}			
	}
	
	/**
	 * Inserta Valoracion en la BBDD
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
				pStmt.setString(5, v.getId_Usuario());
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
					logger.warning(String.format("No se ha insertado la Habitacion: %s", h.getNumero()));
				} else {										
					
					logger.info(String.format("Se ha insertado la Habitacion: %s", h.getNumero()));
				}
			}
			
			logger.info(String.format("%d Habitaciones insertadas en la BBDD", habitaciones.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar Habitacion: %s", ex.getMessage()));
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
				pStmt.setLong(6, u.getFechaNacimiento());
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
	
	
	public void insertarHuesped(Huesped... huespedes) {
		//Se define la plantilla de la sentencia SQL
		String sql = "INSERT INTO Huesped (nombre, apellido, id_reserva) VALUES (?, ?, ?);";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
									
			//Se recorren los clientes y se insertan uno a uno
			for (Huesped h : huespedes) {
				//Se añaden los parámetros al PreparedStatement
				pStmt.setString(1, h.getNombre());
				pStmt.setString(2, h.getApellido());
				pStmt.setInt(3, h.getId_reserva());
				
				if (pStmt.executeUpdate() != 1) {					
					logger.warning(String.format("No se ha insertado el Huesped: %s", h.getNombre()));
				} else {
					
					logger.info(String.format("Se ha insertado el Huesped: %s", h.getNombre()));
				}
			}
			
			logger.info(String.format("%d Huespedes insertados en la BBDD", huespedes.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar Huesped: %s", ex.getMessage()));
		}			
	}
	
	
	public void insertarReserva(Reserva... reservas) {
		//Se define la plantilla de la sentencia SQL
		String sql = "INSERT INTO Reserva (fechaInicio, fechaFin, id_habitacion, id_usuario) VALUES (?, ?, ?, ?);";

		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
									
			//Se recorren los clientes y se insertan uno a uno
			for (Reserva r : reservas) {
				//Se añaden los parámetros al PreparedStatement
				pStmt.setLong(1, r.getFechaInicio());
				pStmt.setLong(2, r.getFechaFin());
				pStmt.setInt(3, r.getId_habitacion());
				pStmt.setString(4, r.getId_Usuario());

				
				if (pStmt.executeUpdate() != 1) {					
					logger.warning(String.format("No se ha insertado la reserva de : %s", r.getId_Usuario()));
				} else {
					
					logger.info(String.format("Se ha insertado la reserva de: %s", r.getId_Usuario()));
				}
			}
			
			logger.info(String.format("%d Reservas insertados en la BBDD", reservas.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar Reserva: %s", ex.getMessage()));
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

	
	private void enlazarHotelesHabitaciones(List<Hotel> hoteles, List<Habitacion> habitaciones) {		
		for (Hotel hotel : hoteles) {
			ArrayList<Habitacion> habitacionesActualizadas = new ArrayList<Habitacion>();
			for (Habitacion habitacion : habitaciones) {
				if (habitacion.getIdHotel() == hotel.getId()) {
					habitacionesActualizadas.add(habitacion);
				}
			}
			hotel.setHabitaciones(habitacionesActualizadas);
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
	
	
	public ArrayList<Usuario> getUsuarios() {
		ArrayList<Usuario> usuarios = new ArrayList<>();
		String sql = "SELECT * FROM Usuario";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se ejecuta la sentencia y se obtiene el ResultSet
			ResultSet rs = pStmt.executeQuery();			
			Usuario usuario;
			
			//Se recorre el ResultSet y se crean objetos
			while (rs.next()) {
				usuario = new Usuario(rs.getString("usuario"), rs.getString("nombre"), rs.getString("apellido"), rs.getLong("fechaNacimiento"), rs.getInt("genero"), rs.getString("email"), rs.getString("contraseña"), new ArrayList<Reserva>(), new ArrayList<Valoracion>());
				
				ArrayList<Reserva> reservas = this.getReservaByUsuario(usuario);
				ArrayList<Valoracion> valoraciones = this.getValoracionByUsuario(usuario);

				usuario.setReservas(reservas);
				usuario.setValoraciones(valoraciones);
				
				//Se inserta cada nuevo usuario en la lista de clientes
				usuarios.add(usuario);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			logger.info(String.format("Se han recuperado %d personajes.", usuarios.size()));			
		} catch (Exception ex) {
			logger.warning(String.format("Error recuperar los personajes: %s", ex.getMessage()));						
		}		
		
		return usuarios;
	}
	
	
	public ArrayList<Habitacion> getHabitaciones() {
		ArrayList<Habitacion> habitaciones = new ArrayList<>();
		String sql = "SELECT * FROM Habitacion";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se ejecuta la sentencia y se obtiene el ResultSet
			ResultSet rs = pStmt.executeQuery();			
			Habitacion habitacion;
			
			//Se recorre el ResultSet y se crean objetos
			while (rs.next()) {
				habitacion = new Habitacion(rs.getInt("planta"), rs.getInt("numero"), rs.getInt("capacidad"), TipoHabitacion.valueOf(rs.getString("tipoHabitacion")), rs.getDouble("precio"), rs.getInt("id_hotel"));
				habitacion.setId(rs.getInt("id"));
				ArrayList<Reserva> reservas = this.getReservaByHabitacion(habitacion);
				habitacion.setReservas(reservas);

				
				//Se inserta cada nuevo usuario en la lista de clientes
				habitaciones.add(habitacion);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			logger.info(String.format("Se han recuperado %d personajes.", habitaciones.size()));			
		} catch (Exception ex) {
			logger.warning(String.format("Error recuperar los personajes: %s", ex.getMessage()));						
		}		
		
		return habitaciones;
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
			Valoracion valoracion = new Valoracion(rs.getString("id_usuario"), rs.getLong("fecha"), rs.getString("comentario"), rs.getInt("puntuacion"), rs.getString("autor"), rs.getInt("id_hotel"));
			
			
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
	
	
	public ArrayList<Valoracion> getValoracionByUsuario(Usuario usuario) {
		ArrayList<Valoracion> valoraciones = new ArrayList<Valoracion>();
		String sql = "SELECT * FROM Valoracion WHERE id_usuario = ?";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se definen los parámetros de la sentencia SQL
			pStmt.setString(1, usuario.getUsuario());
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();			

			while (rs.next()) {
			Valoracion valoracion = new Valoracion(rs.getString("id_usuario"), rs.getLong("fecha"), rs.getString("comentario"), rs.getInt("puntuacion"), rs.getString("autor"), rs.getInt("id_hotel"));
			
			
			valoraciones.add(valoracion);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			logger.info(String.format("Se ha recuperado el hotel %s", usuario.getNombre()));			
		} catch (Exception ex) {
			logger.warning(String.format("Error recuperar el comic con nombre %s: %s", usuario.getNombre(), ex.getMessage()));						
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
			habitacion.setId(rs.getInt("id"));
			
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
	
	
	public ArrayList<Reserva> getReservaByUsuario(Usuario usuario) {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		String sql = "SELECT * FROM Reserva WHERE id_usuario = ?";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se definen los parámetros de la sentencia SQL
			pStmt.setString(1, usuario.getUsuario());
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();			

			while (rs.next()) {
			Reserva reserva = new Reserva(rs.getInt("id"),rs.getString("id_usuario"), new ArrayList<Huesped>(), rs.getLong("fechaInicio"), rs.getLong("fechaFin"), rs.getInt("id_habitacion"));	
			ArrayList<Huesped> huespedes = getHuespedesByReserva(reserva);
			reserva.setHuespedes(huespedes);
			
			reservas.add(reserva);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			logger.info(String.format("Se ha recuperado la reserva de %s", usuario.getNombre()));			
		} catch (Exception ex) {
			logger.warning(String.format("Error recuperar la reserva de %s: %s", usuario.getNombre(), ex.getMessage()));						
		}		
		
		return reservas;
	}
	
	
	public ArrayList<Huesped> getHuespedesByReserva(Reserva reserva) {
		ArrayList<Huesped> huespedes = new ArrayList<Huesped>();
		String sql = "SELECT * FROM Huesped WHERE id_reserva = ?";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se definen los parámetros de la sentencia SQL
			pStmt.setInt(1, reserva.getId());
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();			

			while (rs.next()) {
			Huesped huesped = new Huesped(rs.getString("nombre"), rs.getString("apellido"), rs.getInt("id_reserva"));	
			huespedes.add(huesped);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			logger.info(String.format("Se ha recuperado los huespedes de la reserva %s", reserva.getId()));			
		} catch (Exception ex) {
			logger.warning(String.format("Error recuperar los huespedes de la reserva %s: %s", reserva.getId(), ex.getMessage()));						
		}		
		
		return huespedes;
	}

	
	public ArrayList<Reserva> getReservaByHabitacion(Habitacion habitacion) {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		String sql = "SELECT * FROM Reserva WHERE id_habitacion = ?";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se definen los parámetros de la sentencia SQL
			pStmt.setInt(1, habitacion.getId());
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();			

			while (rs.next()) {
			Reserva reserva = new Reserva(rs.getInt("id"),rs.getString("id_usuario"), new ArrayList<Huesped>(), rs.getLong("fechaInicio"), rs.getLong("fechaFin"), rs.getInt("id_habitacion"));	
			ArrayList<Huesped> huespedes = getHuespedesByReserva(reserva);
			reserva.setHuespedes(huespedes);
			
			reservas.add(reserva);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			logger.info(String.format("Se ha recuperado la reserva de %s", habitacion.getNumero()));			
		} catch (Exception ex) {
			logger.warning(String.format("Error recuperar la reserva de %s: %s", habitacion.getNumero(), ex.getMessage()));						
		}		
		
		return reservas;
	}
	
	
	public Reserva getReservaByUsuarioHabitacionFechaInicio(Reserva reserva) {
		String sql = "SELECT * FROM Reserva WHERE id_habitacion = ? and id_usuario = ? and fechaInicio = ?";
		Reserva reservaRecuperada = new Reserva(-1, "error", null, 0, 0, 0);
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se definen los parámetros de la sentencia SQL
			pStmt.setInt(1, reserva.getId_habitacion());
			pStmt.setString(2, reserva.getId_Usuario());
			pStmt.setLong(3, reserva.getFechaInicio());

			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();			
			
			reservaRecuperada = new Reserva(rs.getInt("id"),rs.getString("id_usuario"), new ArrayList<Huesped>(), rs.getLong("fechaInicio"), rs.getLong("fechaFin"), rs.getInt("id_habitacion"));	
			ArrayList<Huesped> huespedes = getHuespedesByReserva(reservaRecuperada);
			reservaRecuperada.setHuespedes(huespedes);
			
			//Se cierra el ResultSet
			rs.close();
			
			logger.info(String.format("Se ha recuperado la reserva de %s", reserva.getId_Usuario()));			
		} catch (Exception ex) {
			logger.warning(String.format("Error recuperar la reserva de %s: %s", reserva.getId_Usuario(), ex.getMessage()));						
		}		
		
		return reservaRecuperada;
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
                Valoracion valoracion = new Valoracion("", fecha, comentario, puntuacion, autor, -1);
                valoraciones.add(valoracion);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato de datos: " + e.getMessage());
        }

        return valoraciones;
    }
	
	public ArrayList<Usuario> cargarUsuarios(String archivoCSV) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String linea;

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            
            // Leer cada línea del archivo
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                // Asignar valores de cada columna a las variables correspondientes
                String nombre_usuario = datos[0].trim();
                String nombre = datos[1].trim();
                String apellido = datos[2].trim();
                long fecha = Long.parseLong(datos[3].trim());
                int genero = Integer.parseInt(datos[4].trim());
                String email = datos[5].trim();
                String contrasena = datos[6].trim();

                // Crear una instancia de Usuario y agregarla a la lista
                Usuario usuario = new Usuario(nombre_usuario, nombre, apellido, fecha, genero, email, contrasena, new ArrayList<Reserva>(), new ArrayList<Valoracion>());
                usuarios.add(usuario);
                
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato de datos: " + e.getMessage());
        }

        return usuarios;
    }
	
	
	public ArrayList<Habitacion> crearHabitaciones(List<Hotel> hoteles) {
		ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
		for (Hotel hotel : hoteles) {
			int numeroPlantas = (int)(Math.random() * (6 - 3 + 1)) + 3; //el numero de planta sera un numero aleatorio entre 1-9
			int numeroHabitaciones = (int)(Math.random() * (9 - 6 + 1)) + 6; //el numero de habitaciones que habra en cada planta ser un numero aleatorio entre 5-20 (todas las plantas mismo numero de habitaciones
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
					habitaciones.add(habitacion);
				}
			}
		}
		return habitaciones;
	}
	
}
