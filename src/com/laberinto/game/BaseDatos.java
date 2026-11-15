package com.laberinto.game;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class BaseDatos {

    private static final String ARCHIVO_DB = "ranking_laberinto.txt";

    public static void inicializar() {
        File archivo = new File(ARCHIVO_DB);
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error al crear archivo de ranking: " + e.getMessage());
        }
    }

    public static void guardarPartida(String nombre, int tipoPersonaje, int puntuacion, int tiempoRecord, int enemigos) {
        String avatar = (tipoPersonaje == 0) ? "Niño" : "Niña";
        String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

        // Guardamos separado por punto y coma (;)
        String linea = nombre + ";" + avatar + ";" + puntuacion + ";" + tiempoRecord + ";" + enemigos + ";" + fecha;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_DB, true))) {
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar partida: " + e.getMessage());
        }
    }

    public static List<Object[]> obtenerTopJugadores() {
        List<String[]> registrosCrudos = new ArrayList<>();
        File archivo = new File(ARCHIVO_DB);
        if (!archivo.exists()) return new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 6) {
                    registrosCrudos.add(partes);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer ranking: " + e.getMessage());
        }

        // Ordenar: primero mayor puntuación, luego menor tiempo
        registrosCrudos.sort((a, b) -> {
            int puntosA = Integer.parseInt(a[2]);
            int puntosB = Integer.parseInt(b[2]);
            if (puntosB != puntosA) return Integer.compare(puntosB, puntosA);
            return Integer.compare(Integer.parseInt(a[3]), Integer.parseInt(b[3]));
        });

        List<Object[]> listaFinal = new ArrayList<>();
        int puesto = 1;
        for (String[] r : registrosCrudos) {
            if (puesto > 15) break;
            listaFinal.add(new Object[]{
                "#" + puesto++,
                r[0],           // Nombre
                r[1],           // Avatar
                r[2] + " pts",  // Puntos
                r[3] + "s",     // Tiempo
                r[4],           // Enemigos
                r[5]            // Fecha
            });
        }
        return listaFinal;
    }
}