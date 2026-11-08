/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.laberinto.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PantallaInicio extends JPanel {

    private JTextField txtNombre;
    private JRadioButton rbdNino, rbdNina;
    private ButtonGroup grupoSexo;
    private JButton btnIniciar;
    private int selectorPersonaje = 0; // 0: Niño, 1: Niña

    public PantallaInicio(ActionListener alIniciar) {
        setLayout(null);
        setBackground(new Color(15, 15, 30));

        // Título Retro
        JLabel lblTitulo = new JLabel("LABERINTO RPG 8-BIT", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(255, 215, 0));
        lblTitulo.setBounds(20, 30, 600, 40);
        add(lblTitulo);

        // Campo de Nombre
        JLabel lblNombre = new JLabel("Nombre del Héroe:");
        lblNombre.setFont(new Font("Monospaced", Font.BOLD, 14));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(150, 110, 200, 25);
        add(lblNombre);

        txtNombre = new JTextField("Trainer");
        txtNombre.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtNombre.setBounds(310, 110, 180, 25);
        add(txtNombre);

        // Selector de Personaje
        rbdNino = new JRadioButton("Niño", true);
        rbdNina = new JRadioButton("Niña", false);

        rbdNino.setFont(new Font("Monospaced", Font.BOLD, 14));
        rbdNino.setForeground(Color.WHITE);
        rbdNino.setOpaque(false);
        rbdNino.setBounds(200, 160, 80, 25);

        rbdNina.setFont(new Font("Monospaced", Font.BOLD, 14));
        rbdNina.setForeground(Color.WHITE);
        rbdNina.setOpaque(false);
        rbdNina.setBounds(360, 160, 80, 25);

        grupoSexo = new ButtonGroup();
        grupoSexo.add(rbdNino);
        grupoSexo.add(rbdNina);
        add(rbdNino);
        add(rbdNina);

        rbdNino.addActionListener(e -> { selectorPersonaje = 0; repaint(); });
        rbdNina.addActionListener(e -> { selectorPersonaje = 1; repaint(); });

        // Botón de Inicio
        btnIniciar = new JButton("INICIAR AVENTURA");
        btnIniciar.setFont(new Font("Monospaced", Font.BOLD, 16));
        btnIniciar.setBackground(new Color(40, 180, 70));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setBounds(210, 380, 220, 45);
        btnIniciar.addActionListener(alIniciar);
        add(btnIniciar);
    }

    public String getNombreJugador() {
        return txtNombre.getText().trim().isEmpty() ? "Héroe" : txtNombre.getText().trim();
    }

    public int getTipoPersonaje() {
        return selectorPersonaje;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Marco retro de vista previa
        g2.setColor(new Color(40, 40, 70));
        g2.fillRect(260, 210, 120, 140);
        g2.setColor(Color.WHITE);
        g2.drawRect(260, 210, 120, 140);

        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g2.drawString("VISTA PREVIA", 278, 230);

        // Renderizado del sprite seleccionado escala 2X
        if (selectorPersonaje == 0) {
            SpriteRenderer.dibujarNino(g2, 304, 260, 32, 0);
        } else {
            SpriteRenderer.dibujarNina(g2, 304, 260, 32, 0);
        }
    }
}
