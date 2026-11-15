package com.laberinto.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class CargadorImagenes {

    public static BufferedImage imgArbol;
    public static BufferedImage imgCofre;
    public static BufferedImage imgLlave;
    public static BufferedImage imgPuerta;

    public static void cargarTodo() {
        int size = TileMap.TILE_SIZE;
        imgArbol = cargarYProcesar("arbol", size, size);
        imgCofre = cargarYProcesar("cofre", size, size);
        imgLlave = cargarYProcesar("llave", size, size);
        imgPuerta = cargarYProcesar("puerta", size, size);
    }

    private static BufferedImage cargarYProcesar(String nombreBase, int ancho, int alto) {
        String[] extensiones = {".png", ".jpg", ".jpeg"};
        BufferedImage original = null;

        for (String ext : extensiones) {
            try (InputStream is = CargadorImagenes.class.getResourceAsStream("/imagenes/" + nombreBase + ext)) {
                if (is != null) {
                    original = ImageIO.read(is);
                    break;
                }
            } catch (Exception e) {
                System.err.println("Error leyendo " + nombreBase + ext + ": " + e.getMessage());
            }
        }

        if (original == null) {
            System.err.println("AVISO: No se encontró la imagen /imagenes/" + nombreBase + " (.png/.jpg)");
            return null;
        }

        // 1. Eliminar fondo negro y convertir a imagen con canal Alfa (Transparencia)
        BufferedImage sinFondo = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int rgb = original.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // Si el píxel es negro o muy oscuro (tolerancia por compresión JPEG), lo hacemos transparente
                if (r < 30 && g < 30 && b < 30) {
                    sinFondo.setRGB(x, y, 0x00000000);
                } else {
                    sinFondo.setRGB(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
                }
            }
        }

        // 2. Escalar exactamente a TILE_SIZE (32x32)
        BufferedImage escalada = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = escalada.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(sinFondo, 0, 0, ancho, alto, null);
        g2.dispose();

        return escalada;
    }
}