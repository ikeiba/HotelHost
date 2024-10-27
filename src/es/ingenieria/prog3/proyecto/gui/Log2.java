package es.ingenieria.prog3.proyecto.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

@SuppressWarnings("serial")
public class Log2 extends JPanel {

	@SuppressWarnings("unused")
	private CardLayout cardLayout;
    @SuppressWarnings("unused")
	private JPanel mainPanel;

	public Log2(CardLayout cardLayout, JPanel mainPanel) {
		
		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());  
        
		//Panel principal centro
		JPanel panelCentro = new JPanel();
		panelCentro.setLayout(null);
		
		JLabel logo = new JLabel();
		logo.setSize(new Dimension(200, 200));
        ImageIcon icono = new ImageIcon("Hotel Host 200x200.png");
        logo.setIcon(icono);
        add(logo, BorderLayout.CENTER);
        
        JLabel labelDescripcion = new JLabel("TEXTO LARGO Y ABURRIDO");
        labelDescripcion.setBounds(0, 0, 400, 50);
        add(labelDescripcion, BorderLayout.CENTER);
		
        VerticalLineExample linePanel = new VerticalLineExample();
        linePanel.setBackground(panelCentro.getBackground());
        linePanel.setOpaque(true);
        linePanel.setBounds(0, 0, 10, 0);
        add(linePanel, BorderLayout.CENTER);
        
		add(panelCentro, BorderLayout.CENTER); //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel
		
        // Panel principal sur
        add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);
        

        // Add ComponentListener to resize components within the button panel based on frame size
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                
                // Resize and position the button within the buttonPanel
                logo.setBounds((int) (width * 0.25) - (logo.getWidth() / 2), (int) (height * 0.35) - (logo.getHeight() / 2), logo.getWidth(), logo.getHeight());

                // Resize and reposition the vertical line
                linePanel.setBounds((int) (width * 0.5) - (linePanel.getWidth() / 2), (int) (height * 0.05), linePanel.getWidth(), (int) (height * 0.9)); // Fixed width, fills panel height
            
                // Resize and reposition the vertical line
                labelDescripcion.setBounds((int) (width * 0.25) - (labelDescripcion.getWidth() / 2), (int) logo.getX() + (logo.getHeight() / 2) + 30, labelDescripcion.getWidth(), labelDescripcion.getHeight()); // Fixed width, fills panel height 
            }
        });
    }

    // Inner class for the vertical line
    public static class VerticalLineExample extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK); // Set line color

            // Draw a vertical line filling the entire height of the panel
            int x = getWidth() / 2;
            g.drawLine(x, 0, x, getHeight());
        }
    }
}