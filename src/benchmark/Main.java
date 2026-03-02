package benchmark;

import utils.GeneradorDatos;
import algorithms.Fibonacci;
import algorithms.BusquedaLineal;
import algorithms.Factorial;
import algorithms.OrdenamientoBurbuja;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ============================================================
 * PUNTO DE ENTRADA — BENCHMARK FIBONACCI
 * ============================================================
 * Ejecuta ambas versiones de Fibonacci para distintos valores de n,
 * mide sus tiempos y exporta los resultados a un archivo CSV.
 *
 * CÓMO COMPILAR (desde la carpeta raíz del proyecto):
 *   javac -d out src/algorithms/Fibonacci.java src/benchmark/Medidor.java src/benchmark/Main.java
 *
 * CÓMO EJECUTAR:
 *   java -cp out benchmark.Main
 * ============================================================
 */
public class Main {

    // ----------------------------------------------------------------
    // TAMAÑOS DE PRUEBA
    // ----------------------------------------------------------------
    /**
     * Valores de n para los que se ejecutará el benchmark.
     *
     * ITERATIVO: puede manejar n grandes (hasta ~92 con long)
     * RECURSIVO: limitado a n ≤ 30 por la complejidad O(2^n)
     *   fib(30) → ~2.7 millones de llamadas
     *   fib(40) → ~2.7 mil millones de llamadas (tarda minutos)
     */
    
    private static final int[] TAMANOS = {5, 10, 15, 20, 25, 30};
    private static final int[] tamanosPequenos = {5, 10, 15, 20, 25, 30}; // Para Factorial y Fibonacci
    private static final int[] tamanosGrandes = {100, 500, 1000, 5000, 10000}; 

    /** Ruta del archivo de resultados */
    private static final String CSV_PATH = "resultados/tiempos.csv";

    // ----------------------------------------------------------------
    // MAIN
    // ----------------------------------------------------------------
    public static void main(String[] args) {
        imprimirBanner();

        StringBuilder csv = new StringBuilder();
        csv.append("Algoritmo,Version,n,Resultado,Tiempo_ms\n");
        System.out.println("\n  FACTORIAL ITERATIVO  [O(n)]");
        Medidor.imprimirEncabezado();
        // --- PROBAR FACTORIAL (A1) ---
        for (int n : TAMANOS) {
            // Factorial Iterativo
            
            double tiempoMs = Medidor.medir(() -> Factorial.iterativo(n));
            long resultado = Factorial.iterativo(n);
            csv.append(String.format("Factorial,Recursivo,%d,%d,%.6f%n", n, resultado, tiempoMs));
            Medidor.imprimirFila("Factorial", "Iterativo", n, tiempoMs);
        }

        System.out.println("\n  FACTORIAL RECURSIVO  [O(n)]");
        Medidor.imprimirEncabezado();
        for (int n : TAMANOS) {
            // Factorial Recursivo
            double tiempoRec =0; //Medidor.medir(() -> Factorial.recursivo(n));
            long resultado = Factorial.recursivo(n);
            csv.append(String.format("Factorial,Recursivo,%d,%d,%.6f%n", n, resultado, tiempoRec));
            Medidor.imprimirFila("Factorial", "Recursivo", n, tiempoRec);
        }

        


        // ---- FIBONACCI ITERATIVO (A2) ----
        System.out.println("\n  FIBONACCI ITERATIVO  [O(n)]");
        Medidor.imprimirEncabezado();

        for (int n : TAMANOS) {
            final int fn = n;

            // Calcular resultado una vez (solo para mostrarlo)
            long resultado = Fibonacci.iterativo(fn);

            // Medir solo el algoritmo puro (sin I/O ni inicialización)
            double tiempoMs = Medidor.medir(() -> Fibonacci.iterativo(fn));

            Medidor.imprimirFila("Fibonacci", "Iterativo", n, tiempoMs);
            csv.append(String.format("Fibonacci,Iterativo,%d,%d,%.6f%n", n, resultado, tiempoMs));
        }

        // ---- FIBONACCI RECURSIVO ----
        System.out.println("\n  FIBONACCI RECURSIVO  [O(2^n)]");
        Medidor.imprimirEncabezado();

        for (int n : TAMANOS) {
            final int fn = n;

            long resultado = Fibonacci.recursivo(fn);
            double tiempoMs = Medidor.medir(() -> Fibonacci.recursivo(fn));

            Medidor.imprimirFila("Fibonacci", "Recursivo", n, tiempoMs);
            csv.append(String.format("Fibonacci,Recursivo,%d,%d,%.6f%n", n, resultado, tiempoMs));
        }

         // --- PROBAR BUSQUEDA LINEAL (A3) ---
        System.out.println("\n  Busqueda lineal Iterativa [O(n)]");
        Medidor.imprimirEncabezado();
            for (int n : tamanosGrandes) {
                int[] arreglo = GeneradorDatos.generarArregloOrdenado(n); // Generamos datos fijos para la prueba
                int valorBuscado = n-1; // Buscamos el último elemento (peor caso)

                // Búsqueda Iterativa
                long resultado = BusquedaLineal.iterativo(arreglo, valorBuscado);
                double tiempoBusqIter = Medidor.medir(() -> BusquedaLineal.iterativo(arreglo, valorBuscado));
                Medidor.imprimirFila("Busqueda Lineal", "Iterativo", n, tiempoBusqIter);
                csv.append(String.format("Busqueda Lineal,Iterativo,%d,%d,%.6f%n", n, resultado, tiempoBusqIter));

            }
            System.out.println("\n  Busqueda lineal Recursiva [O(n)]");
            Medidor.imprimirEncabezado();
            for (int n : tamanosGrandes) {
                int[] arreglo = GeneradorDatos.generarArregloOrdenado(n); // Generamos datos fijos para la prueba
                int valorBuscado = n-1; // Buscamos el último elemento (peor caso)
               // Búsqueda Recursiva
                double tiempoBusqRec = Medidor.medir(() -> BusquedaLineal.recursivo(arreglo, valorBuscado, 0));
                Medidor.imprimirFila("Busqueda Lineal", "Recursiva", n, tiempoBusqRec);
                csv.append(String.format("Busqueda Lineal,Recursiva,%d,%d,%.6f%n", n, 0, tiempoBusqRec));
            }

            // --- PROBAR ORDENAMIENTO BURBUJA (A4) ---
            System.out.println("\n  Ordenamiento Burbuja Iterativo  [O(n^2)]");
            Medidor.imprimirEncabezado();
            for (int n : tamanosGrandes) {
                // IMPORTANTE: Para cada prueba, creamos una copia del arreglo original para que no esté ordenado.
                int[] arregloOriginal = GeneradorDatos.generarArregloAleatorio(n, 10000);
                
                // Burbuja Iterativo (pasamos una copia)
                int[] copiaIter = arregloOriginal.clone();
                long resultado = 0;
                double tiempoBurbIter = Medidor.medir(() -> OrdenamientoBurbuja.iterativo(copiaIter));
                Medidor.imprimirFila("Busqueda Burbuja", "Iterativo", n, tiempoBurbIter);
                csv.append(String.format("Burbuja,Iterativo,%d,%d,%.6f%n", n, resultado, tiempoBurbIter));
            }
            System.out.println("\n  Ordenamiento Burbuja Recursivo  [O(n^2)]");
            Medidor.imprimirEncabezado();
             for (int n : tamanosGrandes) {
                int[] arregloOriginal = GeneradorDatos.generarArregloAleatorio(n, 10000);
                // Burbuja Recursivo (pasamos una copia)
                int[] copiaRec = arregloOriginal.clone();
                long resultado = 0;
                double tiempoBurbRec = Medidor.medir(() -> OrdenamientoBurbuja.recursivo(copiaRec, copiaRec.length));
                Medidor.imprimirFila("Busqueda Burbuja", "Recursivo", n, tiempoBurbRec);
                csv.append(String.format("Burbuja,Iterativo,%d,%d,%.6f%n", n, resultado, tiempoBurbRec));
            }

        // ---- ANÁLISIS DE DIFERENCIA ----
        System.out.println("\n  COMPARACIÓN ITERATIVO vs RECURSIVO");
        System.out.println("-".repeat(60));
        System.out.printf("%-8s | %-14s | %-14s | %s%n",
                "n", "Iterativo (ms)", "Recursivo (ms)", "Recursivo / Iterativo");
        System.out.println("-".repeat(60));

        for (int n : TAMANOS) {
            final int fn = n;
            double tIter = Medidor.medir(() -> Fibonacci.iterativo(fn));
            double tRec  = Medidor.medir(() -> Fibonacci.recursivo(fn));
            double factor = (tIter > 0) ? tRec / tIter : 0;

            System.out.printf("n=%-6d | %-14.6f | %-14.6f | %.1fx más lento%n",
                    n, tIter, tRec, factor);
        }

        // ---- EXPORTAR CSV ----
        exportarCSV(csv.toString());

        System.out.println("\n============================================================");
        System.out.println("  Resultados exportados → " + CSV_PATH);
        System.out.println("============================================================");
         
    }

    // ----------------------------------------------------------------
    // AUXILIARES
    // ----------------------------------------------------------------
    private static void imprimirBanner() {
        System.out.println("============================================================");
        System.out.println("  ESTRUCTURA DE DATOS — BENCHMARK FIBONACCI");
        System.out.println("  Universidad Da Vinci de Guatemala");
        System.out.println("  Ing. Brandon Chitay");
        System.out.println("============================================================");
    }

    private static void exportarCSV(String contenido) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_PATH))) {
            pw.print(contenido);
            System.out.println("\n  ✓ CSV generado exitosamente en: " + CSV_PATH);
        } catch (IOException e) {
            System.err.println("  ✗ Error al escribir CSV: " + e.getMessage());
        }
    }
}