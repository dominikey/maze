/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.laberinto.game;

import javax.sound.sampled.*;

public class Sonido8Bit {

    private static Thread hiloMusica;
    private static volatile boolean musicaSonando = false;

    // --- MÚSICA DE FONDO CHIPTUNE (BGM) ---
    public static void iniciarMusicaFondo() {
        if (musicaSonando) return;
        musicaSonando = true;

        hiloMusica = new Thread(() -> {
            // Frecuencias de notas musicales (Notas de estilo RPG / Pokémon 8-bit)
            int C4 = 261, D4 = 293, E4 = 329, F4 = 349, G4 = 392, A4 = 440, B4 = 493, C5 = 523;
            int[] melodiaBGM = {
                C4, E4, G4, C5, B4, G4, E4, G4,
                A4, C5, A4, F4, G4, B4, G4, E4,
                D4, F4, A4, D4, E4, G4, C5, G4,
                F4, A4, C5, B4, G4, E4, C4, G4
            };

            while (musicaSonando) {
                for (int nota : melodiaBGM) {
                    if (!musicaSonando) break;
                    // Genera cada nota con onda cuadrada y duración de 160ms por pulso
                    generarTonoDirecto(nota, 160, 0.08f, "CUADRADA");
                    try {
                        Thread.sleep(20); // Silencio corto entre notas
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
        hiloMusica.setDaemon(true); // Se detiene al cerrar la aplicación
        hiloMusica.start();
    }

    public static void detenerMusicaFondo() {
        musicaSonando = false;
        if (hiloMusica != null) {
            hiloMusica.interrupt();
        }
    }

    // --- EFECTOS DE SONIDO (SFX) ---
    public static void reproducirPaso() {
        generarTono(120, 30, 0.12f, "TRIANGULO");
    }

    public static void reproducirBlip() {
        generarTono(440, 50, 0.18f, "CUADRADA");
    }

    public static void reproducirCofre() {
        new Thread(() -> {
            int[] notas = {523, 659, 784, 1046}; // Do, Mi, Sol, Do alto
            for (int f : notas) {
                generarTonoDirecto(f, 60, 0.22f, "CUADRADA");
            }
        }).start();
    }

    public static void reproducirPuerta() {
        generarTono(200, 140, 0.25f, "RUIDO");
    }

    public static void reproducirVictoria() {
        detenerMusicaFondo();
        new Thread(() -> {
            int[] melodia = {523, 659, 784, 1046, 880, 1046};
            int[] duracion = {120, 120, 120, 200, 150, 300};
            for (int i = 0; i < melodia.length; i++) {
                generarTonoDirecto(melodia[i], duracion[i], 0.28f, "CUADRADA");
            }
        }).start();
    }

    public static void reproducirGameOver() {
        detenerMusicaFondo();
        new Thread(() -> {
            int[] notas = {400, 350, 300, 250};
            for (int f : notas) {
                generarTonoDirecto(f, 150, 0.28f, "CUADRADA");
            }
        }).start();
    }

    private static void generarTono(int hz, int msec, float vol, String tipo) {
        new Thread(() -> generarTonoDirecto(hz, msec, vol, tipo)).start();
    }

    private static void generarTonoDirecto(int hz, int msec, float vol, String tipo) {
        try {
            float sampleRate = 22050f;
            byte[] buf = new byte[(int) (sampleRate * msec / 1000)];
            for (int i = 0; i < buf.length; i++) {
                double angle = i / (sampleRate / hz) * 2.0 * Math.PI;
                double val = 0;
                if (tipo.equals("CUADRADA")) {
                    val = Math.sin(angle) > 0 ? 1.0 : -1.0;
                } else if (tipo.equals("TRIANGULO")) {
                    val = Math.asin(Math.sin(angle)) * (2.0 / Math.PI);
                } else if (tipo.equals("RUIDO")) {
                    val = (Math.random() * 2.0) - 1.0;
                }
                buf[i] = (byte) (val * 127.0 * vol);
            }
            AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
            SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af);
            sdl.start();
            sdl.write(buf, 0, buf.length);
            sdl.drain();
            sdl.close();
        } catch (Exception ignored) {}
    }
}