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

                // Dibujar Suelo base (Hierba RPG)
                g2.setColor(new Color(40, 140, 50));
                g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                g2.setColor(new Color(30, 120, 40));
                g2.fillRect(x + 4, y + 4, 4, 4);
                g2.fillRect(x + 20, y + 16, 4, 4);

                // Renderizado según el tipo
                if (tile == 1) { // Árbol / Bosque
                    g2.setColor(new Color(15, 70, 25));
                    g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                    g2.setColor(new Color(25, 100, 35));
                    g2.fillOval(x + 2, y + 2, TILE_SIZE - 4, TILE_SIZE - 4);
                } else if (tile == 2) { // NPC Anciano
                    g2.setColor(new Color(150, 80, 200));
                    g2.fillOval(x + 6, y + 6, 20, 20);
                } else if (tile == 3) { // Cartel de Madera
                    g2.setColor(new Color(139, 69, 19));
                    g2.fillRect(x + 8, y + 12, 16, 12);
                    g2.fillRect(x + 14, y + 24, 4, 8);
                } else if (tile == 4) { // Cofre con Llave
                    g2.setColor(new Color(218, 165, 32));
                    g2.fillRect(x + 6, y + 8, 20, 16);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(x + 6, y + 8, 20, 16);
                } else if (tile == 5) { // Puerta con Candado
                    g2.setColor(new Color(100, 100, 100));
                    g2.fillRect(x + 4, y + 2, 24, 28);
                    g2.setColor(Color.YELLOW);
                    g2.fillOval(x + 12, y + 12, 8, 8);
                } else if (tile == 9) { // Meta Resplandeciente
                    g2.setColor(new Color(255, 215, 0));
                    g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                    g2.setColor(Color.WHITE);
                    g2.fillOval(x + 8, y + 8, 16, 16);
                }
            }
        }
    }
}