package utils;

import java.util.Random;

public class GeneradorDatos {

    /**
     * Genera un arreglo de enteros aleatorios.
     * @param tamano El tamaño del arreglo.
     * @param limiteSuperior El valor máximo que puede tener un elemento.
     * @return Un arreglo de enteros aleatorios.
     */
    public static int[] generarArregloAleatorio(int tamano, int limiteSuperior) {
        int[] arreglo = new int[tamano];
        Random rand = new Random();
        for (int i = 0; i < tamano; i++) {
            arreglo[i] = rand.nextInt(limiteSuperior);
        }
        return arreglo;
    }

    /**
     * Genera un arreglo de enteros en orden ascendente (útil para búsqueda).
     * @param tamano El tamaño del arreglo.
     * @return Un arreglo ordenado [0, 1, 2, ...].
     */
    public static int[] generarArregloOrdenado(int tamano) {
        int[] arreglo = new int[tamano];
        for (int i = 0; i < tamano; i++) {
            arreglo[i] = i;
        }
        return arreglo;
    }
}