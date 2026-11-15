/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.laberinto.game;

import java.awt.Color;
import java.awt.Graphics2D;

public class TileMap {

    public static final int TILE_SIZE = 32; // Tamaño de cada casilla en píxeles
    public static final int FILAS = 31;
    public static final int COLUMNAS = 31;

    // 0: Suelo, 1: Pared/Arbol, 2: NPC, 3: Cartel, 4: Cofre, 5: Puerta, 9: Meta
    public int[][] mapa = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
        {1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,1,1,0,1,0,1,1,1,1,1,1,1,0,1},
        {1,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1,4,1,0,1,0,0,0,0,0,0,0,1,0,1},
        {1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,0,1,0,1},
        {1,0,0,0,2,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,1,0,1},
        {1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1},
        {1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,1},
        {1,0,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,1,1,0,1},
        {1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,1,0,1},
        {1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,5,1,1,1,1,1,0,1,0,1,1,1,0,1,0,1},
        {1,0,3,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,1,0,1,0,0,0,1,0,1,0,1},
        {1,1,1,1,1,1,1,0,1,1,1,1,1,0,1,0,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1},
        {1,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,1,0,1,0,0,0,1,0,1,0,0,0,1},
        {1,0,1,1,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,0,1,0,1,1,1,1,1},
        {1,0,1,4,1,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,0,0,1,0,1,0,0,0,0,0,1},
        {1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,0,1,0,1,1,1,1,1,0,1},
        {1,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,1},
        {1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,0,1,1,1,1,1,0,1,0,1,0,1},
        {1,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,1,0,1},
        {1,0,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,0,1},
        {1,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
        {1,0,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1},
        {1,0,1,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
        {1,0,1,0,1,0,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1},
        {1,0,0,0,1,0,1,4,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1},
        {1,1,1,1,1,0,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1},
        {1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,1},
        {1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,1,1,5,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,9,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    public boolean esSolido(int x, int y) {
        if (x < 0 || x >= COLUMNAS || y < 0 || y >= FILAS) return true;
        int tile = mapa[y][x];
        return tile == 1 || tile == 5; // Pared o puerta cerrada
    }

    public void render(Graphics2D g2) {
        for (int r = 0; r < FILAS; r++) {
            for (int c = 0; c < COLUMNAS; c++) {
                int x = c * TILE_SIZE;
                int y = r * TILE_SIZE;
                int tile = mapa[r][c];

                // 1. Suelo base (Césped RPG)
                g2.setColor(new Color(50, 155, 60));
                g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                g2.setColor(new Color(40, 135, 50));
                g2.fillRect(x + 5, y + 6, 3, 3);
                g2.fillRect(x + 21, y + 18, 3, 3);

                // 2. Elementos con tus imágenes personalizadas (con respaldo automático)
                if (tile == 1) { // Árbol
                    if (CargadorImagenes.imgArbol != null) {
                        g2.drawImage(CargadorImagenes.imgArbol, x, y, null);
                    } else {
                        SpriteRenderer.dibujarArbolRespaldo(g2, x, y, TILE_SIZE);
                    }
                } else if (tile == 2) { // Aldeano Anciano
                    SpriteRenderer.dibujarAldeano(g2, x, y, TILE_SIZE, 0);
                } else if (tile == 3) { // Aldeano Explorador (antes cartel)
                    SpriteRenderer.dibujarAldeano(g2, x, y, TILE_SIZE, 1);
                } else if (tile == 4) { // Cofre
                    if (CargadorImagenes.imgCofre != null) {
                        g2.drawImage(CargadorImagenes.imgCofre, x, y, null);
                    } else {
                        SpriteRenderer.dibujarCofreRespaldo(g2, x, y, TILE_SIZE);
                    }
                } else if (tile == 5) { // Puerta
                    if (CargadorImagenes.imgPuerta != null) {
                        g2.drawImage(CargadorImagenes.imgPuerta, x, y, null);
                    } else {
                        SpriteRenderer.dibujarPuertaRespaldo(g2, x, y, TILE_SIZE);
                    }
                } else if (tile == 9) { // Meta Resplandeciente
                    g2.setColor(new Color(255, 215, 0));
                    g2.fillRect(x + 3, y + 3, TILE_SIZE - 6, TILE_SIZE - 6);
                    g2.setColor(Color.WHITE);
                    g2.drawRect(x + 6, y + 6, TILE_SIZE - 12, TILE_SIZE - 12);
                }
            }
        }
    }
}