/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.laberinto.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelJuego extends JPanel implements ActionListener {

    private final Timer loopTimer = new Timer(1000 / 60, this); // 60 FPS
    private final Timer relojTimer;
    private final TileMap mapa = new TileMap();
    private final Jugador jugador;

    private int tiempoRestante = 180; // 3 minutos
    private String dialogoActual = "";
    private boolean mostrandoDialogo = false;
    private boolean juegoTerminado = false;
    private boolean victoria = false;

    // CONSTRUCTOR: Recibe el nombre y el tipo de personaje seleccionados en la pantalla de inicio
    public PanelJuego(String nombreJugador, int tipoPersonaje) {
        this.jugador = new Jugador(nombreJugador, tipoPersonaje);

        this.setPreferredSize(new Dimension(640, 480));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                teclaPresionada(e.getKeyCode());
            }
        });

        relojTimer = new Timer(1000, e -> {
            if (!juegoTerminado && tiempoRestante > 0) {
                tiempoRestante--;
                if (tiempoRestante == 0) {
                    juegoTerminado = true;
                    victoria = false;
                    Sonido8Bit.reproducirGameOver();
                }
            }
        });

        loopTimer.start();
        relojTimer.start();

        // 🎵 Iniciar la música de fondo 8-bit
        Sonido8Bit.iniciarMusicaFondo();
    }

    private void teclaPresionada(int code) {
        if (juegoTerminado) {
            if (code == KeyEvent.VK_R) reiniciar();
            return;
        }

        if (mostrandoDialogo) {
            if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_Z) mostrandoDialogo = false;
            return;
        }

        int dx = 0, dy = 0;
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) dy = -1;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) dy = 1;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) dx = -1;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) dx = 1;

        if (dx != 0 || dy != 0) {
            jugador.mover(dx, dy, mapa);
            verificarEventos();
        }

        if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_Z) {
            interactuar();
        }
    }

    private void verificarEventos() {
        int t = mapa.mapa[jugador.tileY][jugador.tileX];
        if (t == 9) { // Meta
            juegoTerminado = true;
            victoria = true;
            Sonido8Bit.reproducirVictoria();
        }
    }

    private void interactuar() {
        int x = jugador.tileX;
        int y = jugador.tileY;

        int[][] dirs = {{0,0}, {0,1}, {0,-1}, {1,0}, {-1,0}};
        for (int[] d : dirs) {
            int cx = x + d[0];
            int cy = y + d[1];
            if (cx >= 0 && cx < TileMap.COLUMNAS && cy >= 0 && cy < TileMap.FILAS) {
                int tile = mapa.mapa[cy][cx];
                if (tile == 2) {
                    dialogoActual = "Anciano: '¡Hola " + jugador.nombre + "! Encuentra las llaves en los cofres.'";
                    mostrandoDialogo = true;
                    Sonido8Bit.reproducirBlip();
                    return;
                } else if (tile == 3) {
                    dialogoActual = "Cartel: 'Ruta Este -> Salida del Bosque Oscuro.'";
                    mostrandoDialogo = true;
                    Sonido8Bit.reproducirBlip();
                    return;
                } else if (tile == 4) {
                    mapa.mapa[cy][cx] = 0; // Recoger cofre
                    jugador.llaves++;
                    dialogoActual = "¡Encontraste una Llave Dorada!";
                    mostrandoDialogo = true;
                    Sonido8Bit.reproducirCofre();
                    return;
                } else if (tile == 5) {
                    if (jugador.llaves > 0) {
                        mapa.mapa[cy][cx] = 0; // Abrir puerta
                        jugador.llaves--;
                        dialogoActual = "¡Usaste una llave y abriste la puerta!";
                        mostrandoDialogo = true;
                        Sonido8Bit.reproducirPuerta();
                    } else {
                        dialogoActual = "La puerta está cerrada. Necesitas una llave.";
                        mostrandoDialogo = true;
                        Sonido8Bit.reproducirBlip();
                    }
                    return;
                }
            }
        }
    }

    private void reiniciar() {
        jugador.tileX = 1;
        jugador.tileY = 1;
        jugador.pasos = 0;
        jugador.llaves = 0;
        tiempoRestante = 180;
        juegoTerminado = false;
        victoria = false;
        mostrandoDialogo = false;

        // 🎵 Reanudar la música ambiental al reiniciar
        Sonido8Bit.iniciarMusicaFondo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Cámara centrada en el jugador
        int camX = (getWidth() / 2) - (jugador.tileX * TileMap.TILE_SIZE);
        int camY = (getHeight() / 2) - (jugador.tileY * TileMap.TILE_SIZE);

        g2.translate(camX, camY);
        mapa.render(g2);
        jugador.render(g2);
        g2.translate(-camX, -camY);

        dibujarHUD(g2);

        // Diálogo
        if (mostrandoDialogo) {
            g2.setColor(new Color(0, 0, 0, 220));
            g2.fillRect(20, getHeight() - 100, getWidth() - 40, 80);
            g2.setColor(Color.WHITE);
            g2.drawRect(22, getHeight() - 98, getWidth() - 44, 76);
            g2.setFont(new Font("Monospaced", Font.BOLD, 14));
            g2.drawString(dialogoActual, 35, getHeight() - 55);
            g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
            g2.drawString("[Presiona Espacio / Z para continuar]", getWidth() - 280, getHeight() - 30);
        }

        // Fin del Juego
        if (juegoTerminado) {
            g2.setColor(new Color(0, 0, 0, 230));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setFont(new Font("Monospaced", Font.BOLD, 26));

            if (victoria) {
                g2.setColor(Color.YELLOW);
                g2.drawString("¡FELICIDADES " + jugador.nombre.toUpperCase() + "!", 80, 160);
                g2.drawString("¡ESCAPASTE DEL LABERINTO!", 70, 200);
            } else {
                g2.setColor(Color.RED);
                g2.drawString("GAME OVER - TIEMPO AGOTADO", 80, 180);
            }

            g2.setFont(new Font("Monospaced", Font.PLAIN, 16));
            g2.setColor(Color.WHITE);
            g2.drawString("Pasos Totales: " + jugador.pasos, 230, 250);
            g2.drawString("Presiona [ R ] para volver a jugar", 150, 310);
        }
    }

    private void dibujarHUD(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(10, 10, 300, 60);
        g2.setColor(Color.GREEN);
        g2.setFont(new Font("Monospaced", Font.BOLD, 12));
        g2.drawString("Héroe: " + jugador.nombre, 20, 28);
        g2.drawString("Tiempo: " + tiempoRestante + "s | Pasos: " + jugador.pasos, 20, 44);
        g2.drawString("Llaves: " + jugador.llaves, 200, 28);
    }
}