/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.laberinto.game;

import java.awt.*;
import java.util.Random;

public class Villano {

    public String nombre;
    public int tipo; // 0: Espectro Carmesí, 1: Caballero Oscuro
    public int tileX;
    public int tileY;
    public int hp;
    public int hpMax;
    public int dano;
    public boolean vivo = true;

    private int contadorMovimiento = 0;
    private final Random rand = new Random();

    public Villano(String nombre, int tipo, int tileX, int tileY, int hp, int dano) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.tileX = tileX;
        this.tileY = tileY;
        this.hp = hp;
        this.hpMax = hp;
        this.dano = dano;
    }

    // Patrullaje automático cada cierto número de frames
    public void actualizar(TileMap mapa, Jugador jugador) {
        if (!vivo) return;

        contadorMovimiento++;
        if (contadorMovimiento < 35) return; // Velocidad de patrulla
        contadorMovimiento = 0;

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int[] dir = dirs[rand.nextInt(dirs.length)];
        int nX = tileX + dir[0];
        int nY = tileY + dir[1];

        // Si el siguiente paso toca al jugador, le hace daño
        if (nX == jugador.tileX && nY == jugador.tileY) {
            jugador.recibirDano(this.dano);
            Sonido8Bit.reproducirPuerta(); // Efecto de impacto
            return;
        }

        // Moverse si la casilla está libre y no es la meta
        if (!mapa.esSolido(nX, nY) && mapa.mapa[nY][nX] != 9) {
            tileX = nX;
            tileY = nY;
        }
    }

    public void recibirDano(int cantidad) {
        hp -= cantidad;
        if (hp <= 0) {
            hp = 0;
            vivo = false;
        }
    }

    public void render(Graphics2D g2) {
        if (!vivo) return;

        int x = tileX * TileMap.TILE_SIZE;
        int y = tileY * TileMap.TILE_SIZE;

        // Dibuja el sprite del villano desde SpriteRenderer
        SpriteRenderer.dibujarVillano(g2, x, y, TileMap.TILE_SIZE, tipo);

        // Barra de vida (HP) flotante encima del villano
        int anchoBarra = 26;
        int altoBarra = 4;
        int bx = x + 3;
        int by = y - 6;

        g2.setColor(Color.BLACK);
        g2.fillRect(bx - 1, by - 1, anchoBarra + 2, altoBarra + 2);
        g2.setColor(Color.RED);
        g2.fillRect(bx, by, anchoBarra, altoBarra);

        int vidaActual = (int) ((hp / (double) hpMax) * anchoBarra);
        g2.setColor(Color.GREEN);
        g2.fillRect(bx, by, vidaActual, altoBarra);
    }
}