/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.laberinto.game;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private CardLayout cardLayout;
    private JPanel containerPanel;
    private PantallaInicio pantallaInicio;
    private PanelJuego panelJuego;

    public Main() {
        setTitle("Laberinto Retro RPG 16-Bit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);

        // Instanciar Pantalla de Inicio
        pantallaInicio = new PantallaInicio(e -> iniciarJuego());
        containerPanel.add(pantallaInicio, "INTRO");

        add(containerPanel);
        pack();
        setSize(648, 510);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void iniciarJuego() {
        String nombre = pantallaInicio.getNombreJugador();
        int tipoPersonaje = pantallaInicio.getTipoPersonaje();

        // Instanciar el mapa con la configuración seleccionada
        panelJuego = new PanelJuego(nombre, tipoPersonaje);
        containerPanel.add(panelJuego, "JUEGO");

        // Cambiar pantalla y dar foco al teclado
        cardLayout.show(containerPanel, "JUEGO");
        panelJuego.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}