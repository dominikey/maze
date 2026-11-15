/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.laberinto.game;

import java.awt.Graphics2D;

public class Jugador {

    public String nombre = "Trainer";
    public int tipoPersonaje = 0; // 0: Niño, 1: Niña
    public int tileX = 1;
    public int tileY = 1;
    public int direccion = 0; // 0: Abajo, 1: Arriba, 2: Izquierda, 3: Derecha
    public int pasos = 0;
    public int llaves = 0;

    // Estadísticas de Combate
    public int hp = 100;
    public int hpMax = 100;
    public String armaEquipada = "Ninguna";
    public int danoAtaque = 5;
    public int enemigosDerrotados = 0;

    public Jugador() {
    }

    public Jugador(String nombre, int tipoPersonaje) {
        this.nombre = nombre;
        this.tipoPersonaje = tipoPersonaje;
    }

    public void mover(int dx, int dy, TileMap mapa) {
        int nX = tileX + dx;
        int nY = tileY + dy;

        if (dx == 0 && dy > 0) direccion = 0;
        if (dx == 0 && dy < 0) direccion = 1;
        if (dx < 0 && dy == 0) direccion = 2;
        if (dx > 0 && dy == 0) direccion = 3;

        if (!mapa.esSolido(nX, nY)) {
            tileX = nX;
            tileY = nY;
            pasos++;
            Sonido8Bit.reproducirPaso();
        }
    }

    public void equiparArma(String nombreArma, int nuevoDano) {
        if (nuevoDano > this.danoAtaque) {
            this.armaEquipada = nombreArma;
            this.danoAtaque = nuevoDano;
        }
    }

    public void recibirDano(int cantidad) {
        hp -= cantidad;
        if (hp < 0) hp = 0;
    }

    public void render(Graphics2D g2) {
        int x = tileX * TileMap.TILE_SIZE;
        int y = tileY * TileMap.TILE_SIZE;

        if (tipoPersonaje == 0) {
            SpriteRenderer.dibujarNino(g2, x, y, TileMap.TILE_SIZE, direccion);
        } else {
            SpriteRenderer.dibujarNina(g2, x, y, TileMap.TILE_SIZE, direccion);
        }
    }
}