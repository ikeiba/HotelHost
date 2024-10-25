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
		this.setBackground(Color.WHITE); // Set background color
		this.setLayout(new BorderLayout());  
        
		//Panel principal centro
		JPanel panelCentro = new JPanel();
		panelCentro.setLayout(null);
		
		JButton button = new JButton("Button");
		button.setSize(new Dimension(300, 50));
		button.setMaximumSize(new Dimension(300, 50));
        add(button, BorderLayout.CENTER);
		
		add(panelCentro, BorderLayout.CENTER); //Añadimos el panel panelCentro al BorderLayout.CENTER del mainPanel
		
        // Panel principal sur
        add(new JLabel("Hotel Host® 2024"), BorderLayout.SOUTH);

        // Create a vertical line panel with the same background color
        VerticalLineExample linePanel = new VerticalLineExample();
        linePanel.setBackground(getBackground()); // Match background color
        linePanel.setOpaque(true); // Make the background color visible
        linePanel.setBounds(150, 0, 5, 200); // Initial position and height to fit inside the panel

        // Add ComponentListener to resize components within the button panel based on frame size
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();

                // Resize and position the button within the buttonPanel
                button.setBounds((int) (width * 0.1), (int) (height * 0.3), button.getWidth(), button.getHeight());

                // Resize and reposition the vertical line
                linePanel.setBounds((int) (width * 0.5), (int) (height * 0.05), 10, (int) (height * 0.9)); // Fixed width, fills panel height
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