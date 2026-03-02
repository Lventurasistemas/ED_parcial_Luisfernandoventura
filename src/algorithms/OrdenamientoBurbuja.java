package algorithms;

public class OrdenamientoBurbuja {

    //Ejecuta una busqueda de burbuja de un valor n en un arreglo de cualquier tamaño

    public static void iterativo(int[] arreglo) {
    int n = arreglo.length;
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (arreglo[j] > arreglo[j + 1]) {
                int temp = arreglo[j];
                arreglo[j] = arreglo[j + 1];
                arreglo[j + 1] = temp;
                }
            }
        }
    }

    public static void recursivo(int[] arreglo, int n) {
    if (n == 1) return;
    
    for (int i = 0; i < n - 1; i++) {
        if (arreglo[i] > arreglo[i + 1]) {
            int temp = arreglo[i];
            arreglo[i] = arreglo[i + 1];
            arreglo[i + 1] = temp;
        }
    }
    
    recursivo(arreglo, n - 1);
}



}