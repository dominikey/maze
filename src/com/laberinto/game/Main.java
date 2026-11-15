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
    private PantallaRanking pantallaRanking;
    private PanelJuego panelJuego;

    public Main() {
        // 1. Inicializar SQLite al arrancar
        BaseDatos.inicializar();
        CargadorImagenes.cargarTodo();

        setTitle("Laberinto Retro RPG 16-Bit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);

        // 2. Instanciar Pantallas
        pantallaInicio = new PantallaInicio(e -> iniciarJuego(), e -> abrirRanking());
        pantallaRanking = new PantallaRanking(e -> volverAlMenu());

        containerPanel.add(pantallaInicio, "INTRO");
        containerPanel.add(pantallaRanking, "RANKING");

        add(containerPanel);
        setSize(648, 510);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void iniciarJuego() {
        String nombre = pantallaInicio.getNombreJugador();
        int tipoPersonaje = pantallaInicio.getTipoPersonaje();

        // Pasamos 'this' (la referencia de Main) para que PanelJuego pueda regresar al menú
        panelJuego = new PanelJuego(nombre, tipoPersonaje, this);
        containerPanel.add(panelJuego, "JUEGO");

        cardLayout.show(containerPanel, "JUEGO");
        panelJuego.requestFocusInWindow();
    }

    private void abrirRanking() {
        pantallaRanking.cargarDatos(); // Consulta los últimos registros de SQLite
        cardLayout.show(containerPanel, "RANKING");
    }

    public void volverAlMenu() {
        Sonido8Bit.detenerMusicaFondo();
        cardLayout.show(containerPanel, "INTRO");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}