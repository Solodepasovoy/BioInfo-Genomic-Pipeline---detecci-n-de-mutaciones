package controlador;

import modelo.AnalizadorGenomico;
import modelo.Investigador;
import modelo.ProyectoInvestigacion;
import modelo.Secuencia;
import util.FabricaAnalizadores;
import util.GenomaReferenciaConfig;
import vista.VistaConsola;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class ControladorPipeline {

    private static final String BASES = "ACGT";
    private static final int LONGITUD_MAXIMA = 40;

    private final VistaConsola vista;
    private final ProyectoInvestigacion proyecto;
    private final Random random = new Random();
    private int contadorAleatorias;

    public ControladorPipeline(VistaConsola vista, ProyectoInvestigacion proyecto) {
        this.vista = vista;
        this.proyecto = proyecto;
        proyecto.agregarObservador(vista);
    }

    public void cargarDatosIniciales() {
        proyecto.agregarAnalizador(FabricaAnalizadores.crear("alineador", "Alineador base"));
        proyecto.agregarAnalizador(FabricaAnalizadores.crear("detector", "Detector SNV"));
        proyecto.agregarAnalizador(FabricaAnalizadores.crear("anotador", "Anotador ORF"));
        proyecto.agregarInvestigador(new Investigador("Ana Torres", "Bioinformática"));
        proyecto.agregarInvestigador(new Investigador("Luis Pérez", "Genética clínica"));
        proyecto.registrarSecuencia(new Secuencia("S-001", "ACGTTGCA"));
        proyecto.registrarSecuencia(new Secuencia("S-002", "ACTTTGCA"));
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            vista.mostrarMenu();
            try {
                int opcion = vista.leerEntero("Opción");
                salir = procesarOpcion(opcion);
            } catch (NumberFormatException e) {
                vista.mostrarError("Debe escribir un número entero.");
            } catch (IllegalArgumentException e) {
                vista.mostrarError(e.getMessage());
            } catch (NoSuchElementException e) {
                salir = true;
            }
        }
        vista.mostrarMensaje("Fin del programa.");
    }

    private boolean procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarSecuencia();
                break;
            case 2:
                generarSecuenciaAleatoria();
                break;
            case 3:
                listarSecuencias();
                break;
            case 4:
                agregarAnalizador();
                break;
            case 5:
                agregarInvestigador();
                break;
            case 6:
                analizarUnaSecuencia();
                break;
            case 7:
                analizarTodas();
                break;
            case 8:
                mostrarReporte();
                break;
            case 0:
                return true;
            default:
                vista.mostrarError("Opción no válida: " + opcion);
        }
        return false;
    }

    private void registrarSecuencia() {
        String id = vista.leerTexto("Id de la secuencia (ej. S-010)");
        String bases = vista.leerTexto("Bases (solo A, C, G, T)");
        Secuencia secuencia = new Secuencia(id, bases);
        proyecto.registrarSecuencia(secuencia);
        vista.mostrarMensaje("Registrada: " + secuencia.generarReporte());
    }

    private void generarSecuenciaAleatoria() {
        int longitud = vista.leerEntero("Longitud (1 a " + LONGITUD_MAXIMA + ")");
        if (longitud < 1 || longitud > LONGITUD_MAXIMA) {
            throw new IllegalArgumentException(
                    "La longitud debe estar entre 1 y " + LONGITUD_MAXIMA);
        }
        StringBuilder bases = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            bases.append(BASES.charAt(random.nextInt(BASES.length())));
        }
        String id;
        do {
            contadorAleatorias++;
            id = String.format("R-%03d", contadorAleatorias);
        } while (proyecto.buscarSecuencia(id) != null);
        Secuencia secuencia = new Secuencia(id, bases.toString());
        proyecto.registrarSecuencia(secuencia);
        vista.mostrarMensaje("Generada: " + secuencia.generarReporte());
    }

    private void listarSecuencias() {
        List<String> lineas = new ArrayList<>();
        for (Secuencia s : proyecto.getSecuencias()) {
            lineas.add(s.generarReporte());
        }
        vista.mostrarLista("Secuencias registradas", lineas);
    }

    private void agregarAnalizador() {
        String tipo = vista.leerTexto("Tipo (alineador, detector, anotador)");
        String nombre = vista.leerTexto("Nombre del analizador");
        AnalizadorGenomico analizador = FabricaAnalizadores.crear(tipo, nombre);
        proyecto.agregarAnalizador(analizador);
        vista.mostrarMensaje("Agregado: " + analizador.generarReporte());
    }

    private void agregarInvestigador() {
        String nombre = vista.leerTexto("Nombre del investigador");
        String especialidad = vista.leerTexto("Especialidad");
        Investigador investigador = new Investigador(nombre, especialidad);
        proyecto.agregarInvestigador(investigador);
        vista.mostrarMensaje("Agregado: " + investigador);
    }

    private void analizarUnaSecuencia() {
        String id = vista.leerTexto("Id de la secuencia a analizar");
        Secuencia secuencia = proyecto.buscarSecuencia(id);
        if (secuencia == null) {
            vista.mostrarError("No existe la secuencia " + id);
            return;
        }
        vista.mostrarLista("Resultados de " + secuencia.getId(),
                proyecto.analizarSecuencia(secuencia));
    }

    private void analizarTodas() {
        List<Secuencia> secuencias = proyecto.getSecuencias();
        if (secuencias.isEmpty()) {
            vista.mostrarError("No hay secuencias registradas");
            return;
        }
        for (Secuencia secuencia : secuencias) {
            vista.mostrarLista("Resultados de " + secuencia.getId(),
                    proyecto.analizarSecuencia(secuencia));
        }
    }

    private void mostrarReporte() {
        GenomaReferenciaConfig config = GenomaReferenciaConfig.getInstance();
        vista.mostrarMensaje(proyecto.generarReporte());
        vista.mostrarMensaje("Configuración: referencia " + config.getReferencia().getBases()
                + ", posiciones patogénicas " + config.getPosicionesPatogenicas());
    }
}
