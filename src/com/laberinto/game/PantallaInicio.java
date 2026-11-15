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
    private int selectorPersonaje = 0; // 0: Niño, 1: Niña

    public PantallaInicio(ActionListener alIniciar, ActionListener alVerRanking) {
        setLayout(null);
        setBackground(new Color(15, 15, 30));

        // Título Retro
        JLabel lblTitulo = new JLabel("LABERINTO RPG 16-BIT", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(255, 215, 0));
        lblTitulo.setBounds(20, 25, 600, 40);
        add(lblTitulo);

        // Campo de Nombre
        JLabel lblNombre = new JLabel("Nombre del Héroe:");
        lblNombre.setFont(new Font("Monospaced", Font.BOLD, 14));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(150, 95, 200, 25);
        add(lblNombre);

        txtNombre = new JTextField("Trainer");
        txtNombre.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtNombre.setBounds(310, 95, 180, 25);
        add(txtNombre);

        // Selector de Personaje
        rbdNino = new JRadioButton("Niño", true);
        rbdNina = new JRadioButton("Niña", false);

        rbdNino.setFont(new Font("Monospaced", Font.BOLD, 14));
        rbdNino.setForeground(Color.WHITE);
        rbdNino.setOpaque(false);
        rbdNino.setBounds(200, 140, 80, 25);

        rbdNina.setFont(new Font("Monospaced", Font.BOLD, 14));
        rbdNina.setForeground(Color.WHITE);
        rbdNina.setOpaque(false);
        rbdNina.setBounds(360, 140, 80, 25);

        ButtonGroup grupoSexo = new ButtonGroup();
        grupoSexo.add(rbdNino);
        grupoSexo.add(rbdNina);
        add(rbdNino);
        add(rbdNina);

        rbdNino.addActionListener(e -> { selectorPersonaje = 0; repaint(); });
        rbdNina.addActionListener(e -> { selectorPersonaje = 1; repaint(); });

        // Botón: Nueva Partida
        JButton btnIniciar = new JButton("NUEVA PARTIDA");
        btnIniciar.setFont(new Font("Monospaced", Font.BOLD, 15));
        btnIniciar.setBackground(new Color(40, 180, 70));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setBounds(100, 385, 200, 45);
        btnIniciar.addActionListener(alIniciar);
        add(btnIniciar);

        // Botón: Ranking
        JButton btnRanking = new JButton("RANKING / TOP");
        btnRanking.setFont(new Font("Monospaced", Font.BOLD, 15));
        btnRanking.setBackground(new Color(40, 110, 210));
        btnRanking.setForeground(Color.WHITE);
        btnRanking.setFocusPainted(false);
        btnRanking.setBounds(340, 385, 200, 45);
        btnRanking.addActionListener(alVerRanking);
        add(btnRanking);
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
        g2.setColor(new Color(35, 35, 65));
        g2.fillRect(260, 190, 120, 145);
        g2.setColor(Color.WHITE);
        g2.drawRect(260, 190, 120, 145);

        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g2.drawString("VISTA PREVIA", 278, 212);

        if (selectorPersonaje == 0) {
            SpriteRenderer.dibujarNino(g2, 304, 245, 32, 0);
        } else {
            SpriteRenderer.dibujarNina(g2, 304, 245, 32, 0);
        }
    }
}