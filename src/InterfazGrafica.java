import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class InterfazGrafica {
    private TarjetaDeCredito tarjeta;
    private JFrame frame;
    private JTextField limiteField;
    private JTextField numeroTarjetaField;
    private JTextField fechaVencimientoField;
    private JComboBox<Producto> productosComboBox;
    private JTextField valorField;
    private JTextField pagarField;
    private JLabel saldoLabel;
    private JTable comprasTable;
    private DefaultTableModel tableModel;
    private List<Producto> productos;

    public InterfazGrafica() {
        // Inicializar la ventana principal
        frame = new JFrame("Simulador de Compras");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(245, 245, 245)); // Fondo claro

        // Lista de productos predefinidos
        productos = new ArrayList<>();
        productos.add(new Producto("Laptop", 1200.00));
        productos.add(new Producto("Teléfono", 800.00));
        productos.add(new Producto("Ropa", 50.00));
        productos.add(new Producto("Zapatos", 80.00));
        productos.add(new Producto("Libro", 15.00));
        productos.add(new Producto("Auriculares", 40.00));
        productos.add(new Producto("Televisor", 500.00));
        productos.add(new Producto("Juguete", 25.00));

        // Panel superior para los datos de la tarjeta
        JPanel tarjetaPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        tarjetaPanel.setBackground(new Color(245, 245, 245));
        tarjetaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel limiteLabel = new JLabel("Límite de la tarjeta:");
        limiteField = new JTextField(10);
        JLabel numeroTarjetaLabel = new JLabel("Número de la tarjeta (16 dígitos):");
        numeroTarjetaField = new JTextField(16);
        JLabel fechaVencimientoLabel = new JLabel("Fecha de vencimiento (MM/YYYY):");
        fechaVencimientoField = new JTextField(10);
        JButton iniciarButton = new JButton("Iniciar Tarjeta");
        iniciarButton.setBackground(new Color(100, 181, 246)); // Azul claro
        iniciarButton.setForeground(Color.WHITE);
        tarjetaPanel.add(limiteLabel);
        tarjetaPanel.add(limiteField);
        tarjetaPanel.add(numeroTarjetaLabel);
        tarjetaPanel.add(numeroTarjetaField);
        tarjetaPanel.add(fechaVencimientoLabel);
        tarjetaPanel.add(fechaVencimientoField);
        tarjetaPanel.add(new JLabel()); // Espacio vacío
        tarjetaPanel.add(iniciarButton);

        // Panel central combinado para compras y tabla
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(245, 245, 245));

        // Subpanel para ingresar compras
        JPanel comprasPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        comprasPanel.setBackground(new Color(245, 245, 245));
        comprasPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel productoLabel = new JLabel("Seleccionar producto:");
        productosComboBox = new JComboBox<>(productos.toArray(new Producto[0]));
        JLabel valorLabel = new JLabel("Valor de la compra:");
        valorField = new JTextField();
        JButton comprarButton = new JButton("Añadir Compra");
        comprarButton.setBackground(new Color(100, 181, 246));
        comprarButton.setForeground(Color.WHITE);
        comprasPanel.add(productoLabel);
        comprasPanel.add(productosComboBox);
        comprasPanel.add(valorLabel);
        comprasPanel.add(valorField);
        comprasPanel.add(new JLabel()); // Espacio vacío
        comprasPanel.add(comprarButton);

        // Actualizar el valor cuando se seleccione un producto
        productosComboBox.addActionListener(e -> {
            Producto productoSeleccionado = (Producto) productosComboBox.getSelectedItem();
            if (productoSeleccionado != null) {
                valorField.setText(String.format("%.2f", productoSeleccionado.getPrecio()));
            }
        });
        // Establecer el valor inicial
        if (!productos.isEmpty()) {
            Producto primerProducto = productos.get(0);
            valorField.setText(String.format("%.2f", primerProducto.getPrecio()));
        }

        // Tabla para mostrar las compras
        String[] columnas = {"Fecha", "Descripción", "Valor"};
        tableModel = new DefaultTableModel(columnas, 0);
        comprasTable = new JTable(tableModel);
        comprasTable.setRowHeight(25);
        comprasTable.setGridColor(new Color(200, 200, 200));
        JScrollPane tableScrollPane = new JScrollPane(comprasTable);

        // Añadir subpaneles al panel central
        centerPanel.add(comprasPanel, BorderLayout.NORTH);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel inferior para el saldo, pagos y botones adicionales
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(new Color(245, 245, 245));
        saldoLabel = new JLabel("Saldo restante: $0.00");
        JButton resumenButton = new JButton("Mostrar Resumen");
        resumenButton.setBackground(new Color(255, 183, 77)); // Naranja claro
        resumenButton.setForeground(Color.WHITE);
        JButton limpiarButton = new JButton("Limpiar");
        limpiarButton.setBackground(new Color(239, 83, 80)); // Rojo claro
        limpiarButton.setForeground(Color.WHITE);
        JButton interesesButton = new JButton("Aplicar Intereses");
        interesesButton.setBackground(new Color(149, 117, 205)); // Morado claro
        interesesButton.setForeground(Color.WHITE);
        JLabel pagarLabel = new JLabel("Monto a pagar:");
        pagarField = new JTextField(10);
        JButton pagarButton = new JButton("Pagar Tarjeta");
        pagarButton.setBackground(new Color(76, 175, 80)); // Verde claro
        pagarButton.setForeground(Color.WHITE);
        bottomPanel.add(saldoLabel);
        bottomPanel.add(resumenButton);
        bottomPanel.add(limpiarButton);
        bottomPanel.add(interesesButton);
        bottomPanel.add(pagarLabel);
        bottomPanel.add(pagarField);
        bottomPanel.add(pagarButton);

        // Añadir componentes al frame
        frame.add(tarjetaPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Acciones de los botones
        iniciarButton.addActionListener(e -> iniciarTarjeta());
        comprarButton.addActionListener(e -> realizarCompra());
        resumenButton.addActionListener(e -> mostrarResumen());
        limpiarButton.addActionListener(e -> limpiarCampos());
        interesesButton.addActionListener(e -> aplicarIntereses());
        pagarButton.addActionListener(e -> pagarTarjeta());

        // Centrar la ventana y hacerla visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void iniciarTarjeta() {
        try {
            // Validar límite
            double limite = Double.parseDouble(limiteField.getText());
            if (limite <= 0) {
                JOptionPane.showMessageDialog(frame, "El límite debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar número de tarjeta (16 dígitos)
            String numeroTarjeta = numeroTarjetaField.getText().trim();
            if (!numeroTarjeta.matches("\\d{16}")) {
                JOptionPane.showMessageDialog(frame, "El número de tarjeta debe tener 16 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar fecha de vencimiento (formato MM/YYYY)
            LocalDate fechaVencimiento;
            try {
                String[] fechaPartes = fechaVencimientoField.getText().trim().split("/");
                if (fechaPartes.length != 2) throw new DateTimeParseException("Formato inválido", "", 0);
                int mes = Integer.parseInt(fechaPartes[0]);
                int anio = Integer.parseInt(fechaPartes[1]);
                if (mes < 1 || mes > 12) throw new DateTimeParseException("Mes inválido", "", 0);
                fechaVencimiento = LocalDate.of(anio, mes, 1);
                if (fechaVencimiento.isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(frame, "La tarjeta está vencida.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Fecha de vencimiento inválida. Use formato MM/YYYY.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear la tarjeta
            tarjeta = new TarjetaDeCredito(limite, numeroTarjeta, fechaVencimiento);
            saldoLabel.setText(String.format("Saldo restante: $%.2f", tarjeta.getSaldo()));
            tableModel.setRowCount(0); // Limpiar tabla
            limiteField.setEditable(false);
            numeroTarjetaField.setEditable(false);
            fechaVencimientoField.setEditable(false);
            JOptionPane.showMessageDialog(frame, "Tarjeta iniciada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Ingrese un límite válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarCompra() {
        if (tarjeta == null) {
            JOptionPane.showMessageDialog(frame, "Primero inicie la tarjeta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Producto productoSeleccionado = (Producto) productosComboBox.getSelectedItem();
            if (productoSeleccionado == null) {
                JOptionPane.showMessageDialog(frame, "Seleccione un producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String descripcion = productoSeleccionado.getNombre();
            double valor = Double.parseDouble(valorField.getText());
            if (valor <= 0) {
                JOptionPane.showMessageDialog(frame, "El valor debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Compra compra = new Compra(valor, descripcion);
            if (tarjeta.lanzarCompra(compra)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                tableModel.addRow(new Object[]{compra.getFechaHora().format(formatter), compra.getDescripcion(), String.format("%.2f", compra.getValor())});
                saldoLabel.setText(String.format("Saldo restante: $%.2f", tarjeta.getSaldo()));
                JOptionPane.showMessageDialog(frame, "✅ Compra realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                productosComboBox.setSelectedIndex(0); // Resetear selección
                valorField.setText(String.format("%.2f", productos.get(0).getPrecio())); // Resetear valor
            } else {
                JOptionPane.showMessageDialog(frame, "❌ Saldo insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Ingrese un valor numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarResumen() {
        if (tarjeta == null || tarjeta.getListaDeCompras().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No hay compras para mostrar.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        tableModel.setRowCount(0); // Limpiar tabla
        List<Compra> comprasOrdenadas = new ArrayList<>(tarjeta.getListaDeCompras());
        Collections.sort(comprasOrdenadas);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        for (Compra compra : comprasOrdenadas) {
            tableModel.addRow(new Object[]{compra.getFechaHora().format(formatter), compra.getDescripcion(), String.format("%.2f", compra.getValor())});
        }
    }

    private void limpiarCampos() {
        productosComboBox.setSelectedIndex(0);
        valorField.setText(String.format("%.2f", productos.get(0).getPrecio()));
        pagarField.setText("");
    }

    private void aplicarIntereses() {
        if (tarjeta == null) {
            JOptionPane.showMessageDialog(frame, "Primero inicie la tarjeta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double saldoAntes = tarjeta.getSaldo();
        tarjeta.aplicarIntereses();
        saldoLabel.setText(String.format("Saldo restante: $%.2f", tarjeta.getSaldo()));
        if (saldoAntes != tarjeta.getSaldo()) {
            JOptionPane.showMessageDialog(frame, "Intereses aplicados. Su saldo ha disminuido.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "No hay saldo utilizado para aplicar intereses.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void pagarTarjeta() {
        if (tarjeta == null) {
            JOptionPane.showMessageDialog(frame, "Primero inicie la tarjeta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            double monto = Double.parseDouble(pagarField.getText());
            if (monto <= 0) {
                JOptionPane.showMessageDialog(frame, "El monto a pagar debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            tarjeta.pagar(monto);
            saldoLabel.setText(String.format("Saldo restante: $%.2f", tarjeta.getSaldo()));
            pagarField.setText("");
            JOptionPane.showMessageDialog(frame, "✅ Pago realizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Ingrese un monto válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfazGrafica::new);
    }
}