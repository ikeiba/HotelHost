package es.ingenieria.prog3.proyecto.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogReservar extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtFechaEntrada;
    private JTextField txtFechaSalida;
    private JButton btnConfirmar;
    private JButton btnCancelar;

    public DialogReservar(JFrame parent) {
        super(parent, "Confirmación de reserva", true);
        setLayout(new GridLayout(3, 2));
        
        // Campo para la fecha de entrada
        add(new JLabel("Fecha de entrada (dd/mm/yyyy):"));
        txtFechaEntrada = new JTextField();
        add(txtFechaEntrada);

        // Campo para la fecha de salida
        add(new JLabel("Fecha de salida (dd/mm/yyyy):"));
        txtFechaSalida = new JTextField();
        add(txtFechaSalida);
        
        // Botones de confirmar y cancelar
        btnConfirmar = new JButton("Confirmar");
        btnCancelar = new JButton("Cancelar");
        add(btnConfirmar);
        add(btnCancelar);

        // Acción para confirmar la reserva
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarReserva();
            }
        });

        // Acción para cancelar
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setSize(300, 150);
        setLocationRelativeTo(parent);
    }

    private void confirmarReserva() {
        String fechaEntrada = txtFechaEntrada.getText();
        String fechaSalida = txtFechaSalida.getText();

        // Lógica para guardar la reserva, si aplica en el proyecto
        JOptionPane.showMessageDialog(this, 
                "Reserva confirmada\nEntrada: " + fechaEntrada + 
                "\nSalida: " + fechaSalida);

        // Cierra el diálogo tras confirmar la reserva
        dispose();
    }
}
