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
import java.util.ArrayList;
import java.util.List;

public class PanelJuego extends JPanel implements ActionListener {

    private Main ventanaPrincipal;
    private final Timer loopTimer = new Timer(1000 / 60, this); // 60 FPS
    private Timer relojTimer;
    private final TileMap mapa = new TileMap();
    private final Jugador jugador;
    private final List<Villano> villanos = new ArrayList<>();

    private int tiempoRestante = 180;
    private String dialogoActual = "";
    private boolean mostrandoDialogo = false;
    private boolean juegoTerminado = false;
    private boolean victoria = false;
    private String motivoDerrota = "TIEMPO AGOTADO";
    private int cofresAbiertos = 0;

    public PanelJuego(String nombreJugador, int tipoPersonaje, Main ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.jugador = new Jugador(nombreJugador, tipoPersonaje);

        this.setLayout(null);
        this.setPreferredSize(new Dimension(640, 480));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        inicializarVillanos();

        relojTimer = new Timer(1000, e -> {
            if (!juegoTerminado && tiempoRestante > 0) {
                tiempoRestante--;
                if (tiempoRestante == 0) {
                    terminarDerrota("TIEMPO AGOTADO");
                }
            }
        });

        // Botón Volver al Menú
        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.setFont(new Font("Monospaced", Font.BOLD, 11));
        btnVolver.setBackground(new Color(180, 45, 45));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusable(false);
        btnVolver.setBounds(490, 12, 135, 30);
        btnVolver.addActionListener(e -> {
            loopTimer.stop();
            relojTimer.stop();
            ventanaPrincipal.volverAlMenu();
        });
        this.add(btnVolver);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                teclaPresionada(e.getKeyCode());
            }
        });

        loopTimer.start();
        relojTimer.start();
        Sonido8Bit.iniciarMusicaFondo();
    }

    private void inicializarVillanos() {
        villanos.clear();
        // (Nombre, Tipo [0:Espectro, 1:Caballero], X, Y, HP, Daño)
        villanos.add(new Villano("Espectro Rojo", 0, 7, 3, 30, 15));
        villanos.add(new Villano("Guardia Sombra", 1, 13, 7, 50, 20));
        villanos.add(new Villano("Espectro Oscuro", 0, 19, 13, 40, 15));
        villanos.add(new Villano("Caballero Negro", 1, 9, 21, 70, 25));
        villanos.add(new Villano("Señor del Laberinto", 1, 25, 27, 90, 30));
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

        if (code == KeyEvent.VK_X) {
            atacar();
        }
    }

    private void atacar() {
        if (jugador.armaEquipada.equals("Ninguna")) {
            dialogoActual = "¡No tienes arma equipada! Busca un cofre para armarte.";
            mostrandoDialogo = true;
            Sonido8Bit.reproducirBlip();
            return;
        }

        int[][] alcance = {{0, 0}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (Villano v : villanos) {
            if (!v.vivo) continue;
            for (int[] d : alcance) {
                if (v.tileX == jugador.tileX + d[0] && v.tileY == jugador.tileY + d[1]) {
                    v.recibirDano(jugador.danoAtaque);
                    Sonido8Bit.reproducirBlip();
                    if (!v.vivo) {
                        jugador.enemigosDerrotados++;
                        dialogoActual = "¡Derrotaste a " + v.nombre + " con tu " + jugador.armaEquipada + "!";
                        mostrandoDialogo = true;
                        Sonido8Bit.reproducirCofre();
                    }
                    return;
                }
            }
        }
    }

    private void verificarEventos() {
        // Verificar colisión directa con villanos
        for (Villano v : villanos) {
            if (v.vivo && v.tileX == jugador.tileX && v.tileY == jugador.tileY) {
                jugador.recibirDano(v.dano);
                Sonido8Bit.reproducirPuerta();
                if (jugador.hp <= 0) {
                    terminarDerrota("DERROTADO EN COMBATE");
                    return;
                }
            }
        }

        // Verificar Meta
        int t = mapa.mapa[jugador.tileY][jugador.tileX];
        if (t == 9) {
            juegoTerminado = true;
            victoria = true;
            relojTimer.stop();
            Sonido8Bit.reproducirVictoria();

            int tiempoUsado = 180 - tiempoRestante;
            int puntuacionFinal = (tiempoRestante * 10) + (jugador.llaves * 50) + (jugador.enemigosDerrotados * 150) - jugador.pasos;
            if (puntuacionFinal < 100) puntuacionFinal = 100;

            BaseDatos.guardarPartida(jugador.nombre, jugador.tipoPersonaje, puntuacionFinal, tiempoUsado, jugador.enemigosDerrotados);
        }
    }

    private void terminarDerrota(String motivo) {
        juegoTerminado = true;
        victoria = false;
        motivoDerrota = motivo;
        relojTimer.stop();
        Sonido8Bit.reproducirGameOver();
    }

    private void interactuar() {
        int x = jugador.tileX;
        int y = jugador.tileY;

        int[][] dirs = {{0, 0}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] d : dirs) {
            int cx = x + d[0];
            int cy = y + d[1];
            if (cx >= 0 && cx < TileMap.COLUMNAS && cy >= 0 && cy < TileMap.FILAS) {
                int tile = mapa.mapa[cy][cx];
                if (tile == 2) {
                    dialogoActual = "Anciano: '¡Usa [X] para atacar monstruos cuando consigas un arma!'";
                    mostrandoDialogo = true;
                    Sonido8Bit.reproducirBlip();
                    return;
                } else if (tile == 3) {
                    dialogoActual = "Explorador: 'Los cofres guardan llaves y espadas poderosas.'";
                    mostrandoDialogo = true;
                    Sonido8Bit.reproducirBlip();
                    return;
                } else if (tile == 4) {
                    mapa.mapa[cy][cx] = 0; // Abrir cofre
                    jugador.llaves++;
                    cofresAbiertos++;

                    // Recompensa progresiva de armas en cofres
                    String recompensaArma = "";
                    if (cofresAbiertos == 1) {
                        jugador.equiparArma("Espada Madera", 20);
                        recompensaArma = " + ¡Espada de Madera (20 Daño)!";
                    } else if (cofresAbiertos == 2) {
                        jugador.equiparArma("Espada Hierro", 35);
                        recompensaArma = " + ¡Espada de Hierro (35 Daño)!";
                    } else {
                        jugador.equiparArma("Hoja Dorada", 50);
                        recompensaArma = " + ¡Hoja Dorada (50 Daño)!";
                    }

                    dialogoActual = "¡Llave Dorada" + recompensaArma;
                    mostrandoDialogo = true;
                    Sonido8Bit.reproducirCofre();
                    return;
                } else if (tile == 5) {
                    if (jugador.llaves > 0) {
                        mapa.mapa[cy][cx] = 0;
                        jugador.llaves--;
                        dialogoActual = "¡Usaste una llave y abriste la puerta!";
                        mostrandoDialogo = true;
                        Sonido8Bit.reproducirPuerta();
                    } else {
                        dialogoActual = "Puerta cerrada. Necesitas una llave dorada.";
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
        jugador.hp = jugador.hpMax;
        jugador.armaEquipada = "Ninguna";
        jugador.danoAtaque = 5;
        jugador.enemigosDerrotados = 0;
        cofresAbiertos = 0;

        tiempoRestante = 180;
        juegoTerminado = false;
        victoria = false;
        mostrandoDialogo = false;

        inicializarVillanos();
        relojTimer.start();
        Sonido8Bit.iniciarMusicaFondo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!juegoTerminado && !mostrandoDialogo) {
            for (Villano v : villanos) {
                v.actualizar(mapa, jugador);
            }
            if (jugador.hp <= 0) {
                terminarDerrota("DERROTADO EN COMBATE");
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int camX = (getWidth() / 2) - (jugador.tileX * TileMap.TILE_SIZE);
        int camY = (getHeight() / 2) - (jugador.tileY * TileMap.TILE_SIZE);

        g2.translate(camX, camY);
        mapa.render(g2);
        for (Villano v : villanos) {
            v.render(g2);
        }
        jugador.render(g2);
        g2.translate(-camX, -camY);

        dibujarHUD(g2);

        if (mostrandoDialogo) {
            g2.setColor(new Color(0, 0, 0, 225));
            g2.fillRect(20, getHeight() - 100, getWidth() - 40, 80);
            g2.setColor(Color.WHITE);
            g2.drawRect(22, getHeight() - 98, getWidth() - 44, 76);
            g2.setFont(new Font("Monospaced", Font.BOLD, 13));
            g2.drawString(dialogoActual, 35, getHeight() - 55);
            g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
            g2.drawString("[Presiona Espacio / Z para continuar]", getWidth() - 280, getHeight() - 30);
        }

        if (juegoTerminado) {
            g2.setColor(new Color(0, 0, 0, 235));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setFont(new Font("Monospaced", Font.BOLD, 24));

            if (victoria) {
                g2.setColor(Color.YELLOW);
                g2.drawString("¡FELICIDADES " + jugador.nombre.toUpperCase() + "!", 90, 150);
                g2.drawString("¡ESCAPASTE DEL LABERINTO!", 85, 185);
            } else {
                g2.setColor(Color.RED);
                g2.drawString("GAME OVER - " + motivoDerrota, 80, 170);
            }

            g2.setFont(new Font("Monospaced", Font.PLAIN, 15));
            g2.setColor(Color.WHITE);
            g2.drawString("Pasos: " + jugador.pasos + " | Bajas: " + jugador.enemigosDerrotados, 195, 240);
            g2.drawString("Presiona [ R ] para volver a jugar", 155, 300);
        }
    }

    private void dibujarHUD(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 190));
        g2.fillRect(10, 10, 360, 78);
        g2.setColor(new Color(100, 100, 140));
        g2.drawRect(10, 10, 360, 78);

        g2.setFont(new Font("Monospaced", Font.BOLD, 12));
        g2.setColor(Color.WHITE);
        g2.drawString("Héroe: " + jugador.nombre, 20, 26);

        // Barra de HP del Jugador
        g2.drawString("HP:", 20, 44);
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(48, 34, 100, 11);
        g2.setColor(jugador.hp > 35 ? Color.GREEN : Color.RED);
        g2.fillRect(48, 34, jugador.hp, 11);
        g2.setColor(Color.WHITE);
        g2.drawRect(48, 34, 100, 11);

        // Arma y Combate
        g2.setColor(new Color(255, 215, 0));
        g2.drawString("Arma: " + jugador.armaEquipada + " [X]", 20, 64);
        g2.setColor(Color.CYAN);
        g2.drawString("Bajas: " + jugador.enemigosDerrotados, 20, 80);

        // Tiempo, Pasos y Llaves
        g2.setColor(Color.GREEN);
        g2.drawString("Tiempo: " + tiempoRestante + "s", 220, 26);
        g2.drawString("Pasos: " + jugador.pasos, 220, 44);
        g2.drawString("Llaves: " + jugador.llaves, 220, 64);

        if (CargadorImagenes.imgLlave != null) {
            g2.drawImage(CargadorImagenes.imgLlave, 305, 50, 20, 20, null);
        }
    }
}