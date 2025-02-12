package es.ingenieria.prog3.proyecto.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URI;
import java.util.ArrayList;

import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.ingenieria.prog3.proyecto.domain.Hotel;
import es.ingenieria.prog3.proyecto.domain.Valoracion;
import es.ingenieria.prog3.proyecto.gui.util.DataStore;
import es.ingenieria.prog3.proyecto.gui.util.JPanelBordesRedondos;
import es.ingenieria.prog3.proyecto.gui.util.JTextFieldDefaultText;
import es.ingenieria.prog3.proyecto.gui.util.Preferences;
import es.ingenieria.prog3.proyecto.gui.util.RangeSlider;

public class Busqueda extends JPanel {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private CardLayout cardLayout;
    @SuppressWarnings("unused")
	private JPanel mainPanel;
    //Lista de hoteles a renderizar en la tabla
    private ArrayList<Hotel> hoteles;
    //Tabla de hoteles
    private JTable tablaHoteles = new JTable();
    //Modelo de datos de la tabla de hoteles
	private HotelsTableModel hotelsTableModel;
	//Tabla de valoraciones
	private JTable tablaValoraciones = new JTable();
	//Modelo de datos de la tabla de valoraciones
	private ValoracionesTableModel valoracionesTableModel;
	//Panel de scroll de la tabla de valoraciones
	private JScrollPane panelScrollTablaValoraciones;
	//Panel del label valoraciones
	private JPanelBordesRedondos panelLabelValoraciones;
	
	private JButton buttonQuitarAnuncio = new JButton("X");

	
	public Busqueda(CardLayout cardLayout, JPanel mainPanel, ArrayList<Hotel> hoteles) {
		
		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;
		this.hoteles = hoteles;
	

		
		//Ponemos el fondo del panel blanco y le asignamos el border layout como layoutManager
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());  
		
		//Creamos el panel principal centro con el color de fondo predeterminado y el layoutManager a null
		//esto nos permitira colocar los diferentes componentes por coordenadas mas facilmente
		JPanel panelCentro = new JPanel();
		panelCentro.setBackground(Preferences.COLORBACKGROUND);
		panelCentro.setLayout(null);
		
		//Creamos el panel que contendra el filtro del precio
		JPanelBordesRedondos panelFiltro = new JPanelBordesRedondos(25);
		panelFiltro.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.92), 70);
		panelFiltro.setBounds((int) ((Preferences.WINDOWWIDTH * 0.5) - (panelFiltro.getWidth() / 2)) - 5, 80, panelFiltro.getWidth() - 10, panelFiltro.getHeight());
		panelFiltro.setBackground(Color.WHITE);
		panelFiltro.setLayout(null);
		
        //Creamos el label diciendo que vamos a filtrar
        JLabel labelFiltrar = new JLabel("Filtrar:");
        labelFiltrar.setHorizontalAlignment(SwingConstants.LEFT);
        labelFiltrar.setVerticalAlignment(SwingConstants.TOP);
        labelFiltrar.setBounds(15, 20, panelFiltro.getWidth(), 150);
        labelFiltrar.setFont(Preferences.FONT);
        panelFiltro.add(labelFiltrar);
		
        //Creamos el panel para la tabla de hoteles y añadimos la tabla al panel
        JScrollPane panelScrollTablaHoteles = new JScrollPane(tablaHoteles);
        panelScrollTablaHoteles.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.5), (int) ((Preferences.WINDOWHEIGHT * 0.6)));
        panelScrollTablaHoteles.setBounds((int) ((Preferences.WINDOWWIDTH * 0.7) - (panelScrollTablaHoteles.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.65) - (panelScrollTablaHoteles.getHeight() / 2)) - 25, panelScrollTablaHoteles.getWidth(), panelScrollTablaHoteles.getHeight());
        panelScrollTablaHoteles.setBackground(Color.WHITE);
       
        //TABLA DE HOTELES
        //Cargamos los hoteles
        this.cargarTablaHoteles();
        
        //Filtro para el nombre del hotel
        JTextFieldDefaultText textFieldFiltroHotel = new JTextFieldDefaultText("Nombre Hotel");
        textFieldFiltroHotel.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.5), 40);
        textFieldFiltroHotel.setBounds((int) ((Preferences.WINDOWWIDTH * 0.7) - (panelScrollTablaHoteles.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.65) - (panelScrollTablaHoteles.getHeight() / 2)) - 75, textFieldFiltroHotel.getWidth(), textFieldFiltroHotel.getHeight());
        textFieldFiltroHotel.setBorder(BorderFactory.createEmptyBorder());

        // Queria crear un JSlider con dos "cabezas", y al no encontrar una clase así 
        // pregunte a chatGPT si existia. Me dio la opcion de utilizar una libreria externa como
        // SwingX o aplicar dos sliders. Con su ayuda, implemente la segunda opcion:
        //IAG (herramienta: ChatGPT)
        //ADAPTADO (he tenido que cambiar varios de los componentes graphics ya que no se visualizaban correctamente.
        //Ademas, he cambiado el color de la parte seleccionada y de las "cabezas" del slider (asi como su forma)).
        RangeSlider sliderPrecio = new RangeSlider((int) Hotel.precioMinimoHoteles(hoteles) - 1,(int) Hotel.precioMaximoHoteles(hoteles) + 1);
        sliderPrecio.setBounds(0, 0, (int) (panelFiltro.getWidth()*0.2), 20);
        sliderPrecio.setBounds((int) (panelFiltro.getWidth()*0.25 - (sliderPrecio.getWidth() / 2)), 30, sliderPrecio.getWidth(), sliderPrecio.getHeight());
        sliderPrecio.setBackground(Color.WHITE);
        sliderPrecio.setFocusable(false);

        // Creamos un label para mostrar en que rango de precios se encuentra 
        JLabel labelPrecioActual = new JLabel();
        labelPrecioActual.setHorizontalAlignment(SwingConstants.LEFT);
        labelPrecioActual.setVerticalAlignment(SwingConstants.TOP);
        labelPrecioActual.setBounds(0, 0, panelFiltro.getWidth(), 150);
        labelPrecioActual.setBounds((int) (sliderPrecio.getWidth()*0.75), 50, labelPrecioActual.getWidth(), labelPrecioActual.getHeight());
    	labelPrecioActual.setText("Precio minimo: " + sliderPrecio.getMinRange() + ", Precio maximo: " + sliderPrecio.getMaxRange());
        
        // Listener que actualiza en que rango de precios se encuentra
        sliderPrecio.addChangeListener(e -> {
        	labelPrecioActual.setText("Precio minimo: " + sliderPrecio.getMinRange() + ", Precio maximo: " + sliderPrecio.getMaxRange());
        	filtrarHoteles(textFieldFiltroHotel.getText(), sliderPrecio.getMinRange(), sliderPrecio.getMaxRange());
        });
        
        //Anadimos el label y el slider al panel del filro
        panelFiltro.add(labelPrecioActual);
        panelFiltro.add(sliderPrecio);
        
        //Listener para filtrar
        DocumentListener documentListener = new DocumentListener() {
            
        	@Override
            public void insertUpdate(DocumentEvent e) {
            	filtrarHoteles(textFieldFiltroHotel.getText(), sliderPrecio.getMinRange(), sliderPrecio.getMaxRange());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            	filtrarHoteles(textFieldFiltroHotel.getText(), sliderPrecio.getMinRange(), sliderPrecio.getMaxRange());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            	filtrarHoteles(textFieldFiltroHotel.getText(), sliderPrecio.getMinRange(), sliderPrecio.getMaxRange());
            }

        };
		
		textFieldFiltroHotel.getDocument().addDocumentListener(documentListener);           
		//Fin listener filtrar
				
		//Inicio listener para añadir un toolTipText con la descripcion del hotel
		tablaHoteles.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // Get the row at the mouse position
                int row = tablaHoteles.rowAtPoint(e.getPoint());
                Hotel hotel = (Hotel) tablaHoteles.getValueAt(row, 0);

                tablaHoteles.setToolTipText(hotel.getDescripcion());
                
            }
        });
		
		//Fin listener añadir toolTipText
		
		//Inicio listener mostrar tabla valoraciones
		tablaHoteles.getSelectionModel().addListSelectionListener(e -> {
			// Cuando se selecciona una fila, se actualiza la tabla de valoraciones
			if (tablaHoteles.getSelectedRow() != -1) {
				Hotel hotelSeleccionado = (Hotel) (tablaHoteles.getValueAt(tablaHoteles.getSelectedRow(), 0));
				this.cargarTablaValoraciones(((Hotel) hotelSeleccionado).getValoraciones());
				panelScrollTablaValoraciones.setVisible(true);
		  		panelLabelValoraciones.setVisible(true);

			}else {
				panelScrollTablaValoraciones.setVisible(false); //para que cuando estemos filtrando desaparezca la tabla de valoraciones
		  		panelLabelValoraciones.setVisible(false);

			}
		});
		
		//Fin listener mostrar tabla valoraciones
		//FIN TABLA DE HOTELES
		
		
		//TABLA DE VALORACIONES
        //Creamos el panel para la tabla de valoraciones y añadimos la tabla al panel
        panelScrollTablaValoraciones = new JScrollPane(tablaValoraciones);
        panelScrollTablaValoraciones.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.4), (int) ((Preferences.WINDOWHEIGHT * 0.4)));
        panelScrollTablaValoraciones.setBounds((int) ((Preferences.WINDOWWIDTH * 0.22) - (panelScrollTablaValoraciones.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.75) - (panelScrollTablaValoraciones.getHeight() / 2)) - 25, panelScrollTablaValoraciones.getWidth(), panelScrollTablaValoraciones.getHeight());
        panelScrollTablaValoraciones.getViewport().setBackground(Preferences.COLORBACKGROUND);
        panelScrollTablaValoraciones.setBorder(BorderFactory.createEmptyBorder());
        panelScrollTablaValoraciones.setVisible(false);       
        
        //FIN TABLA VALORACIONES
		
        
        //Creamos el logo
        JLabel logo = new JLabel();
		logo.setBounds(0, 0, 50, 35);
		logo.setBounds(40, 18, logo.getWidth(), logo.getHeight());
		ImageIcon originalIcon = new ImageIcon("resources/images/Hotel Host.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(50, 35, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(scaledImage);
        logo.setIcon(resizedIcon);
        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	cardLayout.show(mainPanel, "Home");
            }
        });
        
        //Creamos el panel que contendra el filtro del precio
  		this.panelLabelValoraciones = new JPanelBordesRedondos(25);
  		panelLabelValoraciones.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.17), 35);
  		panelLabelValoraciones.setBounds((int) ((Preferences.WINDOWWIDTH * 0.22) - (panelScrollTablaValoraciones.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.75) - (panelScrollTablaValoraciones.getHeight() / 2)) - 75, panelLabelValoraciones.getWidth(), panelLabelValoraciones.getHeight());
  		panelLabelValoraciones.setBackground(Color.WHITE);
  		panelLabelValoraciones.setLayout(null);
  		panelLabelValoraciones.setVisible(false);
  		
  		//Creamos el label diciendo que vamos a filtrar
  		JLabel labelValoraciones = new JLabel("Valoraciones:");
		labelValoraciones.setHorizontalAlignment(SwingConstants.LEFT);
		labelValoraciones.setVerticalAlignment(SwingConstants.TOP);
		labelValoraciones.setBounds(15, 5, panelLabelValoraciones.getWidth(), 20);
		labelValoraciones.setFont(Preferences.FONT);
		panelLabelValoraciones.add(labelValoraciones);
       
		//Creamos el evento de teclado para añadir una valoracion y una reserva
		KeyListener keyListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				//Codigo para crear una nueva valoracion
				if (e.getKeyCode() == KeyEvent.VK_V && e.isControlDown()) {
			        
					JTextField textFieldComentario = new JTextField(25);
					JSpinner spinnerPuntuacion = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
					JTextField textFieldAutor = new JTextField(15);
					textFieldAutor.setText(DataStore.getUsuarioActivo().getUsuario());
					//textFieldAutor.setEditable(false);
					textFieldAutor.setEnabled(false);
					
			        JLabel labelComentario = new JLabel("Comentario:");
			        JLabel labelPuntuacion = new JLabel("Puntuacion:");
			        JLabel labelAutor = new JLabel("Nombre:");

			        
					JComponent[] componentes = new JComponent[]{labelAutor, textFieldAutor, labelComentario, textFieldComentario, labelPuntuacion, spinnerPuntuacion};

					int respuesta =  JOptionPane.showConfirmDialog(null, componentes, "Valoracion", JOptionPane.OK_CANCEL_OPTION);
					
					if (respuesta == 0) {
						// Crear nueva valoracion
						// Obtener el hotel seleccionado
						Hotel hotelAnadirValoracion = (Hotel) tablaHoteles.getValueAt(tablaHoteles.getSelectedRow(), 0);
						Valoracion valoracionNueva = new Valoracion(DataStore.getUsuarioActivo().getUsuario(), System.currentTimeMillis(), textFieldComentario.getText(), (int) spinnerPuntuacion.getValue(), textFieldAutor.getText(), hotelAnadirValoracion.getId());
						hotelAnadirValoracion.getValoraciones().add(valoracionNueva);
						((AbstractTableModel) tablaValoraciones.getModel()).fireTableDataChanged();
						
						//Se añade la nueva valoracion a la base de datos
						DataStore.getGestorBD().insertarValoracion(valoracionNueva);
					}
					
				//Codigo para hacer una nueva reserva
				} else if (e.getKeyCode() == KeyEvent.VK_R && e.isControlDown()) {
					if (tablaHoteles.getSelectedRow() != -1) {
						Hotel hotelSeleccionado = (Hotel) (tablaHoteles.getValueAt(tablaHoteles.getSelectedRow(), 0));
						DataStore.setHotelReserva(hotelSeleccionado);
						new DialogReservar(hotelSeleccionado);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		tablaHoteles.addKeyListener(keyListener);
		tablaValoraciones.addKeyListener(keyListener);
		//Fin listener añadir valoracion y reserva
		
		//ANUNCIOS
		//Creamos el panel para los anuncios y añadimos la tabla al panel
		JPanelBordesRedondos panelAnuncios = new JPanelBordesRedondos(25);
		panelAnuncios.setBounds(0, 0, (int) (Preferences.WINDOWWIDTH * 0.4), 125);
		panelAnuncios.setBounds((int) ((Preferences.WINDOWWIDTH * 0.22) - (panelAnuncios.getWidth() / 2)), (int) ((Preferences.WINDOWHEIGHT * 0.435) - (panelAnuncios.getHeight() / 2)) - 75, panelAnuncios.getWidth(), panelAnuncios.getHeight());
		panelAnuncios.setLayout(new BorderLayout());
		
		JLabel labelAnuncio = new JLabel("HotelHost Anuncios");
		
		final boolean[] flagAnuncioQuitado = {false};
		
		Thread hiloAnuncio = new Thread() {
		    @Override
		    public void run() {
		        String[] paths = {
		            "resources/images/Anuncio_Booking.jpg",
		            "resources/images/Anuncio_Emirates.jpg",
		            "resources/images/Anuncio_Mine.png",
		            "resources/images/Anuncio_BambuLab.png",
		            "resources/images/Anuncio_Apple.jpg",
		            "resources/images/Anuncio_Aliexpress.png",
		            "resources/images/Anuncio_Unity.jpg",
		            "resources/images/Anuncio_Godot.jpg",
		            "resources/images/Anuncio_GitHub.png",
		            "resources/images/Anuncio_Nvidia.jpg"
		            
		        };
		        String[] urls = {
		            "https://www.booking.com",
		            "https://www.emirates.com",
		            "https://www.minecraft.net",
		            "https://bambulab.com",
		            "https://www.apple.com",
		            "https://es.aliexpress.com",
		            "https://unity.com",
		            "https://godotengine.org",
		            "https://github.com",
		            "https://www.nvidia.com"
		        };
		        Random random = new Random();
		        int startIndex = random.nextInt(paths.length);
		        int[] currentIndex = {startIndex};

		        labelAnuncio.addMouseListener(new MouseAdapter() {
		            @Override
		            public void mouseClicked(MouseEvent e) {
		                try {
		                    switch (currentIndex[0]) {
		                    case 0:
		                        Desktop.getDesktop().browse(new URI(urls[0]));
		                        break;
		                    case 1:
		                        Desktop.getDesktop().browse(new URI(urls[1]));
		                        break;
		                    case 2:
		                        Desktop.getDesktop().browse(new URI(urls[2]));
		                        break;
		                    case 3:
		                        Desktop.getDesktop().browse(new URI(urls[3]));
		                        break;
		                    case 4:
		                        Desktop.getDesktop().browse(new URI(urls[4]));
		                        break;
		                    case 5:
		                        Desktop.getDesktop().browse(new URI(urls[5]));
		                        break;
		                    case 6:
		                        Desktop.getDesktop().browse(new URI(urls[6]));
		                        break;
		                    case 7:
		                        Desktop.getDesktop().browse(new URI(urls[7]));
		                        break;
		                    case 8:
		                        Desktop.getDesktop().browse(new URI(urls[8]));
		                        break;
		                    case 9:
		                        Desktop.getDesktop().browse(new URI(urls[9]));
		                        break;
		                    }
		                } catch (Exception ex) {
		                    ex.printStackTrace();
		                }
		                if (flagAnuncioQuitado[0] == true) {
		                	try {
								Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
							} catch (Exception ex) {
							}
		                }
		            }
		        });

		        while (true) {
		            String path = paths[currentIndex[0]];

		            // Crear y configurar el icono
		            ImageIcon originalIconAnuncio = new ImageIcon(path);
		            Image scaledImageAnuncio = originalIconAnuncio.getImage().getScaledInstance((int) (Preferences.WINDOWWIDTH * 0.4), 125, Image.SCALE_SMOOTH);
		            ImageIcon resizedIconAnuncio = new ImageIcon(scaledImageAnuncio);

		            // Establecer el icono en el JLabel
		            labelAnuncio.setIcon(resizedIconAnuncio);

		            try {
		                Thread.sleep(10000); // Pausa de 10 segundos
		            } catch (InterruptedException e) {
		            	labelAnuncio.setIcon(null);
		            	 try {
				                Thread.sleep(20000); // Pausa de 10 segundos
				            } catch (InterruptedException e2) {
				            }
		            	 flagAnuncioQuitado[0] = false;
		            	 labelAnuncio.setHorizontalAlignment(SwingConstants.LEFT);
		            	 buttonQuitarAnuncio.setVisible(true);
		            }

		            // Avanzar al siguiente índice en la cola circular (Chat-GPT)
		            currentIndex[0] = (currentIndex[0] + 1) % paths.length;
		        }
		    }
		};

		hiloAnuncio.start();
		
		
        buttonQuitarAnuncio.setBounds((int) (panelAnuncios.getWidth() * 0.95), 175, 45, 40);
        buttonQuitarAnuncio.setHorizontalAlignment(SwingConstants.RIGHT);
        buttonQuitarAnuncio.setBackground(Preferences.COLORBACKGROUND);
        buttonQuitarAnuncio.setBorderPainted(false);

        // Cambia el color del texto o ícono si es necesario (opcional)
        buttonQuitarAnuncio.setForeground(Color.DARK_GRAY);
        buttonQuitarAnuncio.addActionListener(e -> {
        	flagAnuncioQuitado[0] = true;
        	buttonQuitarAnuncio.setVisible(false);
        	labelAnuncio.setHorizontalAlignment(SwingConstants.CENTER);
        	hiloAnuncio.interrupt();
        });
        panelAnuncios.add(buttonQuitarAnuncio, BorderLayout.NORTH);
		panelAnuncios.add(labelAnuncio, BorderLayout.CENTER);
		
		// FIN DE ANUNCIOS

		
		//Creamos el boton que iniciara el proceso de generar un itinerario
		JButton buttonItinerario = new JButton("Generar Itinerario");
		buttonItinerario.setBounds((int) (panelLabelValoraciones.getWidth() * 4.5), 10, (int) (panelLabelValoraciones.getWidth() * 0.8), 50);
		
		buttonItinerario.addActionListener(e -> {
			ItinerarioDialog test = new ItinerarioDialog((String) tablaHoteles.getValueAt(1, 2));
			test.dispose();
		});
			
		panelFiltro.add(buttonItinerario);
		
		
		
        //Añadimos los componentes al panelCentro y el panelCentro al centro del Panel de la clase (this)
        panelCentro.add(textFieldFiltroHotel);
        panelCentro.add(panelFiltro);
        panelCentro.add(panelScrollTablaHoteles);
        panelCentro.add(logo);
        panelCentro.add(panelScrollTablaValoraciones);
        panelCentro.add(panelLabelValoraciones);
        panelCentro.add(panelAnuncios);
        //panelCentro.add(buttonQuitarAnuncio);
        
        
        //Añadimos el panel panelCentro al BorderLayout.CENTER del Panel de la clase (this)
		this.add(panelCentro, BorderLayout.CENTER); 
		
		//Panel principal sur
        this.add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);        
        
        
	}
	
	
	//Metodos tabla hotel
	public void cargarTablaHoteles() {
		this.hotelsTableModel = new HotelsTableModel(hoteles);
		tablaHoteles.setModel(this.hotelsTableModel);	
        
		tablaHoteles.setRowHeight(45);

		//Renderer para los hoteles
		HotelsRenderer rendererHotel = new HotelsRenderer();
		
		tablaHoteles.setDefaultRenderer(Object.class, rendererHotel);
		
		TableColumnModel columnModel = tablaHoteles.getColumnModel();
		
		columnModel.getColumn(0).setPreferredWidth(135);  // Set preferred width for column 0
		
		columnModel.getColumn(1).setPreferredWidth(100);  // Set preferred width for column 1
		
		columnModel.getColumn(2).setPreferredWidth(100);  // Set preferred width for column 2
	
		columnModel.getColumn(3).setPreferredWidth(100); // Set preferred width for column 3
		

		//No dejar que las columnas se muevan
		tablaHoteles.getTableHeader().setReorderingAllowed(false);

	}
	
	//Metodo para filtrar los hoteles segun el nombre
	public void filtrarHoteles(String filtro, int minimo, int maximo) {
	    // Crea una lista filtrada con los hoteles que coinciden con el filtro
	    ArrayList<Hotel> hotelesFiltrados = new ArrayList<>();
	    if (filtro.isEmpty() || filtro.equals("Nombre Hotel")) {
	    	hoteles.forEach(hotel -> {
		        if (hotel.getPrecioMaximo() < maximo 
		        		&& hotel.getPrecioMinimo() > minimo 
		        		&& hotel.getCiudad().equals(DataStore.getSelectedCiudad())) {
		        	
		        	hotelesFiltrados.add(hotel);
		        }
		    });
	    }else {
	    	hoteles.forEach(hotel -> {
		        if (hotel.getNombre().toLowerCase().contains(filtro.toLowerCase()) 
		        		&& hotel.getPrecioMaximo() < maximo 
		        		&& hotel.getPrecioMinimo() > minimo
		        		&& hotel.getCiudad().equals(DataStore.getSelectedCiudad())) {
		        	
		            hotelesFiltrados.add(hotel);
		        }
		    });
	    } 
	    // Actualiza el modelo de datos de la tabla con la lista filtrada
	    hotelsTableModel.setHotels(hotelesFiltrados);
	}
	
	//Fin metodos tabla hotel
	
	
	//Metodos tabla valoracion
	public void cargarTablaValoraciones(ArrayList<Valoracion> valoraciones) {
		this.valoracionesTableModel = new ValoracionesTableModel(valoraciones);
		tablaValoraciones.setModel(this.valoracionesTableModel);	
        
		tablaValoraciones.setRowHeight(45);

		//Renderer para las valoraciones
		ValoracionesRenderer rendererHotel = new ValoracionesRenderer();
		
		tablaValoraciones.setDefaultRenderer(Object.class, rendererHotel);
		
		TableColumnModel columnModel = tablaValoraciones.getColumnModel();
		
		columnModel.getColumn(0).setPreferredWidth(120);  // Set preferred width for column 0
		
		columnModel.getColumn(1).setPreferredWidth(80);  // Set preferred width for column 1
		
		columnModel.getColumn(2).setPreferredWidth(180);  // Set preferred width for column 2
	
		columnModel.getColumn(3).setPreferredWidth(80); // Set preferred width for column 3
		
		//No dejar que las columnas se muevan
		tablaValoraciones.getTableHeader().setReorderingAllowed(false);
	}
	
	//Fin metodos tabla valoracion	
	
}