/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.laberinto.game;

import java.awt.*;

public class SpriteRenderer {

    // 1. PERSONAJE: NIÑO
    public static void dibujarNino(Graphics2D g, int x, int y, int size, int dir) {
        g.setColor(new Color(0, 0, 0, 60));
        g.fillOval(x + 4, y + size - 8, size - 8, 6);

        g.setColor(new Color(30, 90, 200)); // Polera Azul
        g.fillRect(x + 8, y + 16, size - 16, 10);
        g.setColor(new Color(40, 40, 40)); // Pantalón
        g.fillRect(x + 10, y + 24, size - 20, 6);

        g.setColor(new Color(255, 205, 148)); // Piel
        g.fillRect(x + 8, y + 8, size - 16, 10);

        g.setColor(new Color(220, 40, 40)); // Gorra Roja
        g.fillRect(x + 6, y + 4, size - 12, 6);
        g.setColor(new Color(180, 20, 20)); // Visera
        if (dir == 0) g.fillRect(x + 6, y + 9, size - 12, 2);
        else if (dir == 2) g.fillRect(x + 4, y + 8, 6, 2);
        else if (dir == 3) g.fillRect(x + size - 10, y + 8, 6, 2);

        g.setColor(Color.BLACK);
        if (dir == 0) {
            g.fillRect(x + 10, y + 12, 3, 3);
            g.fillRect(x + 19, y + 12, 3, 3);
        } else if (dir == 2) {
            g.fillRect(x + 8, y + 12, 3, 3);
        } else if (dir == 3) {
            g.fillRect(x + 21, y + 12, 3, 3);
        }
    }

    // 2. PERSONAJE: NIÑA
    public static void dibujarNina(Graphics2D g, int x, int y, int size, int dir) {
        g.setColor(new Color(0, 0, 0, 60));
        g.fillOval(x + 4, y + size - 8, size - 8, 6);

        g.setColor(new Color(210, 140, 40)); // Cabello largo
        g.fillRect(x + 6, y + 6, size - 12, 18);

        g.setColor(new Color(230, 80, 140)); // Vestido Rosa
        g.fillRect(x + 8, y + 16, size - 16, 10);
        g.setColor(new Color(240, 240, 240));
        g.fillRect(x + 10, y + 24, size - 20, 6);

        g.setColor(new Color(255, 210, 160)); // Piel
        g.fillRect(x + 9, y + 8, size - 18, 9);

        g.setColor(new Color(240, 100, 160)); // Cinta
        g.fillRect(x + 7, y + 4, size - 14, 5);

        g.setColor(new Color(40, 30, 20));
        if (dir == 0) {
            g.fillRect(x + 11, y + 11, 3, 3);
            g.fillRect(x + 18, y + 11, 3, 3);
        } else if (dir == 2) {
            g.fillRect(x + 9, y + 11, 3, 3);
        } else if (dir == 3) {
            g.fillRect(x + 20, y + 11, 3, 3);
        }
    }

    // 3. ALDEANOS / NPCs DETALLADOS (Estilo RPG)
    public static void dibujarAldeano(Graphics2D g, int x, int y, int size, int variante) {
        // Sombra
        g.setColor(new Color(0, 0, 0, 70));
        g.fillOval(x + 5, y + size - 7, size - 10, 6);

        if (variante == 0) { // Anciano Sabio (Túnica Morada y Barba Blanca)
            g.setColor(new Color(110, 50, 160));
            g.fillRect(x + 7, y + 14, size - 14, 14);
            g.setColor(new Color(255, 215, 0)); // Cinturón dorado
            g.fillRect(x + 7, y + 20, size - 14, 2);

            g.setColor(new Color(255, 205, 160)); // Rostro
            g.fillRect(x + 9, y + 6, size - 18, 9);
            g.setColor(Color.WHITE); // Barba y cabello
            g.fillRect(x + 9, y + 12, size - 18, 5);
            g.fillRect(x + 8, y + 4, size - 16, 3);
        } else { // Explorador / Guía (Túnica Verde y Sombrero)
            g.setColor(new Color(35, 130, 65));
            g.fillRect(x + 7, y + 14, size - 14, 14);
            g.setColor(new Color(120, 70, 30)); // Cinturón cuero
            g.fillRect(x + 7, y + 20, size - 14, 2);

            g.setColor(new Color(255, 205, 160)); // Rostro
            g.fillRect(x + 9, y + 7, size - 18, 8);
            g.setColor(new Color(160, 90, 30)); // Sombrero explorador
            g.fillRect(x + 5, y + 5, size - 10, 3);
            g.fillRect(x + 8, y + 2, size - 16, 4);
        }

        // Ojos
        g.setColor(Color.BLACK);
        g.fillRect(x + 11, y + 9, 2, 3);
        g.fillRect(x + 19, y + 9, 2, 3);

        // Mini globo indicador de diálogo encima de la cabeza
        g.setColor(Color.WHITE);
        g.fillRoundRect(x + 10, y - 6, 12, 8, 4, 4);
        g.setColor(Color.BLACK);
        g.drawRoundRect(x + 10, y - 6, 12, 8, 4, 4);
        g.fillRect(x + 13, y - 3, 2, 2);
        g.fillRect(x + 17, y - 3, 2, 2);
    }

    // 4. VILLANOS / MONSTRUOS DEL LABERINTO
    public static void dibujarVillano(Graphics2D g, int x, int y, int size, int tipo) {
        // Sombra
        g.setColor(new Color(0, 0, 0, 80));
        g.fillOval(x + 4, y + size - 7, size - 8, 6);

        if (tipo == 0) { // Espectro / Slime Carmesí
            g.setColor(new Color(190, 25, 45));
            g.fillRoundRect(x + 5, y + 8, size - 10, size - 12, 12, 12);
            g.setColor(new Color(240, 70, 90)); // Brillo superior
            g.fillOval(x + 9, y + 10, 6, 4);

            // Cuernos
            g.setColor(new Color(60, 20, 20));
            g.fillPolygon(new int[]{x + 7, x + 10, x + 12}, new int[]{y + 9, y + 3, y + 9}, 3);
            g.fillPolygon(new int[]{x + 20, x + 22, x + 25}, new int[]{y + 9, y + 3, y + 9}, 3);

            // Ojos amarillos amenazantes
            g.setColor(Color.YELLOW);
            g.fillRect(x + 10, y + 15, 4, 3);
            g.fillRect(x + 18, y + 15, 4, 3);
        } else { // Caballero Oscuro
            g.setColor(new Color(45, 45, 60)); // Armadura oscura
            g.fillRect(x + 7, y + 13, size - 14, 15);
            g.setColor(new Color(70, 70, 90)); // Yelmo
            g.fillRect(x + 8, y + 4, size - 16, 10);
            g.setColor(Color.RED); // Visor brillante
            g.fillRect(x + 10, y + 8, size - 20, 3);
            // Espada oscura en mano
            g.setColor(new Color(180, 180, 200));
            g.fillRect(x + 25, y + 6, 3, 16);
        }
    }

    // 5. RESPALDO POR CÓDIGO (Por si alguna imagen no carga)
    public static void dibujarArbolRespaldo(Graphics2D g, int x, int y, int size) {
        g.setColor(new Color(110, 60, 25));
        g.fillRect(x + 12, y + 20, 8, 12);
        g.setColor(new Color(30, 120, 45));
        g.fillOval(x + 4, y + 4, size - 8, size - 10);
    }

    public static void dibujarCofreRespaldo(Graphics2D g, int x, int y, int size) {
        g.setColor(new Color(218, 165, 32));
        g.fillRect(x + 5, y + 8, size - 10, size - 12);
    }

    public static void dibujarPuertaRespaldo(Graphics2D g, int x, int y, int size) {
        g.setColor(new Color(30, 70, 180));
        g.fillRect(x + 3, y + 2, size - 6, size - 4);
        g.setColor(Color.YELLOW);
        g.drawRect(x + 3, y + 2, size - 6, size - 4);
    }
}