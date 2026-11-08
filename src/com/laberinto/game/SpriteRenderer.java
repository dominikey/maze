/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.laberinto.game;

import java.awt.*;

public class SpriteRenderer {

    // 1. PERSONAJE: NIÑO
    public static void dibujarNino(Graphics2D g, int x, int y, int size, int dir) {
        // Sombra base
        g.setColor(new Color(0, 0, 0, 60));
        g.fillOval(x + 4, y + size - 8, size - 8, 6);

        // Cuerpo / Ropa deportiva
        g.setColor(new Color(30, 90, 200)); // Polera Azul
        g.fillRect(x + 8, y + 16, size - 16, 10);
        g.setColor(new Color(40, 40, 40)); // Pantalón
        g.fillRect(x + 10, y + 24, size - 20, 6);

        // Cabeza / Piel
        g.setColor(new Color(255, 205, 148));
        g.fillRect(x + 8, y + 8, size - 16, 10);

        // Gorra & Cabello corto
        g.setColor(new Color(220, 40, 40)); // Gorra Roja
        g.fillRect(x + 6, y + 4, size - 12, 6);
        g.setColor(new Color(180, 20, 20)); // Visera
        if (dir == 0) g.fillRect(x + 6, y + 9, size - 12, 2);
        else if (dir == 2) g.fillRect(x + 4, y + 8, 6, 2);
        else if (dir == 3) g.fillRect(x + size - 10, y + 8, 6, 2);

        // Ojos según la dirección
        g.setColor(Color.BLACK);
        if (dir == 0) { // Abajo
            g.fillRect(x + 10, y + 12, 3, 3);
            g.fillRect(x + 19, y + 12, 3, 3);
        } else if (dir == 2) { // Izquierda
            g.fillRect(x + 8, y + 12, 3, 3);
        } else if (dir == 3) { // Derecha
            g.fillRect(x + 21, y + 12, 3, 3);
        }
    }

    // 2. PERSONAJE: NIÑA
    public static void dibujarNina(Graphics2D g, int x, int y, int size, int dir) {
        // Sombra base
        g.setColor(new Color(0, 0, 0, 60));
        g.fillOval(x + 4, y + size - 8, size - 8, 6);

        // Cabello largo posterior (Castaño/Rubio)
        g.setColor(new Color(210, 140, 40));
        g.fillRect(x + 6, y + 6, size - 12, 18);

        // Vestido / Atuendo
        g.setColor(new Color(230, 80, 140)); // Vestido Rosa
        g.fillRect(x + 8, y + 16, size - 16, 10);
        g.setColor(new Color(240, 240, 240)); // Calcetas
        g.fillRect(x + 10, y + 24, size - 20, 6);

        // Cabeza / Piel
        g.setColor(new Color(255, 210, 160));
        g.fillRect(x + 9, y + 8, size - 18, 9);

        // Gorra / Cinta Rosa
        g.setColor(new Color(240, 100, 160));
        g.fillRect(x + 7, y + 4, size - 14, 5);

        // Ojos
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

    // 3. ALDEANOS / NPCs VARIADOS
    public static void dibujarAldeano(Graphics2D g, int x, int y, int size, int variante) {
        Color ropa = Color.BLUE;
        Color pelo = Color.BLACK;

        switch (variante) {
            case 0: ropa = new Color(40, 120, 210); pelo = new Color(100, 50, 10); break; // Ropa Azul
            case 1: ropa = new Color(40, 160, 70);  pelo = new Color(200, 170, 40); break; // Ropa Verde
            case 2: ropa = new Color(200, 60, 50);  pelo = new Color(80, 80, 80); break;   // Ropa Roja
        }

        // Sombra
        g.setColor(new Color(0, 0, 0, 50));
        g.fillOval(x + 4, y + size - 6, size - 8, 5);

        // Cuerpo y Piel
        g.setColor(ropa);
        g.fillRect(x + 8, y + 14, size - 16, 12);
        g.setColor(new Color(255, 205, 148));
        g.fillRect(x + 9, y + 6, size - 18, 10);

        // Pelo
        g.setColor(pelo);
        g.fillRect(x + 8, y + 4, size - 16, 5);

        // Ojos
        g.setColor(Color.BLACK);
        g.fillRect(x + 11, y + 10, 2, 3);
        g.fillRect(x + 19, y + 10, 2, 3);
    }

    // 4. ENTORNO: ÁRBOL / PINO RETRO
    public static void dibujarArbol(Graphics2D g, int x, int y, int size) {
        // Tronco
        g.setColor(new Color(110, 60, 25));
        g.fillRect(x + 12, y + 20, 8, 12);

        // Copas (Triángulos superpuestos estilo 16-bit)
        g.setColor(new Color(20, 90, 35));
        g.fillPolygon(new int[]{x + 4, x + 16, x + 28}, new int[]{y + 22, y + 10, y + 22}, 3);
        g.setColor(new Color(30, 120, 45));
        g.fillPolygon(new int[]{x + 6, x + 16, x + 26}, new int[]{y + 15, y + 5, y + 15}, 3);
        g.setColor(new Color(40, 150, 60));
        g.fillPolygon(new int[]{x + 8, x + 16, x + 24}, new int[]{y + 9, y + 2, y + 9}, 3);
    }

    // 5. ÍTEMS: COFRE (Cerrado y Abierto)
    public static void dibujarCofre(Graphics2D g, int x, int y, int size, boolean abierto) {
        g.setColor(new Color(160, 90, 30));
        g.fillRect(x + 4, y + 10, size - 8, size - 14);

        if (abierto) {
            g.setColor(new Color(60, 30, 10)); // Interior oscuro
            g.fillRect(x + 6, y + 8, size - 12, 6);
            g.setColor(new Color(220, 180, 40)); // Tapa levantada
            g.fillRect(x + 4, y + 2, size - 8, 6);
        } else {
            g.setColor(new Color(220, 180, 40)); // Detalles dorados
            g.fillRect(x + 4, y + 10, size - 8, 3);
            g.fillRect(x + 14, y + 14, 4, 5); // Cerrojo
        }
    }

    // 6. ÍTEMS: LLAVE BRILLANTE
    public static void dibujarLlave(Graphics2D g, int x, int y, int size, Color colorLlave) {
        g.setColor(colorLlave);
        g.drawOval(x + 8, y + 8, 8, 8);
        g.fillRect(x + 14, y + 11, 10, 3);
        g.fillRect(x + 20, y + 14, 2, 4);
    }

    // 7. PUERTA (Cerrada y Abierta)
    public static void dibujarPuerta(Graphics2D g, int x, int y, int size, boolean abierta, Color colorMadera) {
        if (abierta) {
            g.setColor(new Color(40, 40, 40)); // Marco abierto transparente/oscuro
            g.fillRect(x, y, size, size);
            g.setColor(colorMadera);
            g.fillRect(x, y, 4, size); // Borde
        } else {
            g.setColor(colorMadera);
            g.fillRect(x + 2, y + 2, size - 4, size - 4);
            g.setColor(Color.BLACK);
            g.drawRect(x + 2, y + 2, size - 4, size - 4);
            g.setColor(Color.YELLOW);
            g.fillOval(x + size - 10, y + (size / 2) - 2, 4, 4); // Candado/Manija
        }
    }
}