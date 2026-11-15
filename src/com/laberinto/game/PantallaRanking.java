/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.laberinto.game;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PantallaRanking extends JPanel {

    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public PantallaRanking(ActionListener alVolver) {
        setLayout(null);
        setBackground(new Color(15, 15, 30));

        // Título
        JLabel lblTitulo = new JLabel("SALÓN DE LA FAMA - TOP HÉROES", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(255, 215, 0));
        lblTitulo.setBounds(20, 20, 600, 35);
        add(lblTitulo);

        // Configuración de la Tabla
        String[] columnas = {"Pos", "Jugador", "Avatar", "Puntos", "Tiempo", "Bajas", "Fecha"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setBackground(new Color(25, 25, 45));
        tabla.setForeground(Color.WHITE);
        tabla.setFont(new Font("Monospaced", Font.PLAIN, 12));
        tabla.setRowHeight(26);
        tabla.getTableHeader().setBackground(new Color(45, 45, 80));
        tabla.getTableHeader().setForeground(new Color(255, 215, 0));
        tabla.getTableHeader().setFont(new Font("Monospaced", Font.BOLD, 12));

        // Centrar texto de celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < columnas.length; i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Ajustar anchos de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(60);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(130);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(30, 70, 580, 310);
        scrollPane.getViewport().setBackground(new Color(25, 25, 45));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(scrollPane);

        // Botón Volver
        JButton btnVolver = new JButton("VOLVER AL MENÚ");
        btnVolver.setFont(new Font("Monospaced", Font.BOLD, 15));
        btnVolver.setBackground(new Color(200, 60, 60));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setBounds(210, 400, 220, 42);
        btnVolver.addActionListener(alVolver);
        add(btnVolver);
    }

    // Método para refrescar datos cada vez que se abre esta pantalla
    public void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Object[]> registros = BaseDatos.obtenerTopJugadores();
        for (Object[] fila : registros) {
            modeloTabla.addRow(fila);
        }
    }
}