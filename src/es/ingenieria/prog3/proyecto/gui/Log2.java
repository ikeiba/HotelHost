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
        // Create a panel that will contain the button and the vertical line
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null); // Use absolute positioning to control button and line placement
        buttonPanel.setBackground(Color.WHITE); // Set background color
        
        // Create a button to resize
        JButton button = new JButton("Button");
        button.setBounds(20, 50, 100, 50); // Initial position and size for the button
        buttonPanel.add(button);

        // Create a vertical line panel with the same background color
        VerticalLineExample linePanel = new VerticalLineExample();
        linePanel.setBackground(buttonPanel.getBackground()); // Match background color
        linePanel.setOpaque(true); // Make the background color visible
        linePanel.setBounds(150, 0, 5, 200); // Initial position and height to fit inside the panel

        // Add the line panel to the button panel
        buttonPanel.add(linePanel);

        // Add button panel to the main frame, set to expand with the frame size
        add(buttonPanel, BorderLayout.CENTER);

        // Add ComponentListener to resize components within the button panel based on frame size
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = buttonPanel.getWidth();
                int height = buttonPanel.getHeight();

                // Resize and position the button within the buttonPanel
                button.setBounds(
                        (int) (width * 0.1),   // x-position (10% of panel width)
                        (int) (height * 0.3),  // y-position (30% of panel height)
                        (int) (width * 0.3),   // width (30% of panel width)
                        (int) (height * 0.2)   // height (20% of panel height)
                );

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