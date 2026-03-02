package algorithms;

public class Factorial {

    //Calcula el factorial de un numero n multiplicando todos los numeros enteros positivos desde 1 hasta n

    public static long iterativo(int n) {
        if (n < 0) throw new IllegalArgumentException("n no puede ser negativo");
        long resultado = 1;
        for (int i = 2; i <= n; i++) {
            resultado *= i;
            }
        return resultado;
    }

    public static long recursivo(int n) {
        if (n < 0) throw new IllegalArgumentException("n no puede ser negativo");
        if (n == 0 || n == 1) return 1;
        return n * recursivo(n - 1);
    }



}