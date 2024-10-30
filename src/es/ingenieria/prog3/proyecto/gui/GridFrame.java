package es.ingenieria.prog3.proyecto.gui;
import javax.swing.*;
import java.awt.*;

//Esta clase ha sido generada por CHATGPT
public class GridFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public GridFrame() {
        // Configura el tamaño y el comportamiento del JFrame
        setTitle("Cuadrícula de Coordenadas");
        setSize(1152, 720); // el 60% de la pantalla
        setResizable(false);
        //setExtendedState(JFrasme.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Agrega un panel personalizado que dibuja la cuadrícula
        add(new GridPanel());
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GridFrame());
    }

    // Clase personalizada para dibujar la cuadrícula
    class GridPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawGrid(g);
        }

        private void drawGrid(Graphics g) {
            int width = getWidth();
            int height = getHeight();
            int gridSpacing = 20;  // Espacio entre las líneas de la cuadrícula

            g.setColor(Color.LIGHT_GRAY);  // Color de las líneas de la cuadrícula

            // Dibujar líneas verticales
            for (int x = 0; x <= width; x += gridSpacing) {
                g.drawLine(x, 0, x, height);
            }

            // Dibujar líneas horizontales
            for (int y = 0; y <= height; y += gridSpacing) {
                g.drawLine(0, y, width, y);
            }

            // Opción: dibujar coordenadas en las líneas (puedes ajustarlo según tus preferencias)
            g.setColor(Color.BLACK);
            for (int x = 0; x <= width; x += gridSpacing) {
                g.drawString(String.valueOf(x), x, 10);  // Coordenadas x
            }
            for (int y = 0; y <= height; y += gridSpacing) {
                g.drawString(String.valueOf(y), 0, y);  // Coordenadas y
            }
        }
    }
}