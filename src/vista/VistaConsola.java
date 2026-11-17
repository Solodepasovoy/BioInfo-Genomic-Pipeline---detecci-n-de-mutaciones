package vista;

import modelo.Observador;

import java.util.List;
import java.util.Scanner;

public class VistaConsola implements Observador {

    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println();
        System.out.println("======= BioInfo Genomic Pipeline =======");
        System.out.println("1. Registrar secuencia");
        System.out.println("2. Generar secuencia aleatoria");
        System.out.println("3. Listar secuencias");
        System.out.println("4. Agregar analizador al pipeline");
        System.out.println("5. Agregar investigador");
        System.out.println("6. Analizar una secuencia");
        System.out.println("7. Analizar todas las secuencias");
        System.out.println("8. Ver reporte del proyecto");
        System.out.println("0. Salir");
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje + ": ");
        return scanner.nextLine().trim();
    }

    public int leerEntero(String mensaje) {
        return Integer.parseInt(leerTexto(mensaje));
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje) {
        System.out.println("[Error] " + mensaje);
    }

    public void mostrarLista(String titulo, List<String> elementos) {
        System.out.println("--- " + titulo + " ---");
        if (elementos.isEmpty()) {
            System.out.println("(sin elementos)");
        }
        for (String elemento : elementos) {
            System.out.println("  " + elemento);
        }
    }

    @Override
    public void notificar(String mensaje) {
        System.out.println("*** ALERTA: " + mensaje + " ***");
    }
}
