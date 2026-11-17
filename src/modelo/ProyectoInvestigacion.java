package modelo;

import java.util.ArrayList;
import java.util.List;

/** Proyecto que agrega investigadores, analizadores y secuencias. */
public class ProyectoInvestigacion implements Reportable {

    private final String nombre;
    private final List<Investigador> investigadores = new ArrayList<>();
    private final List<AnalizadorGenomico> analizadores = new ArrayList<>();
    private final List<Secuencia> secuencias = new ArrayList<>();
    private final List<Observador> observadores = new ArrayList<>();

    public ProyectoInvestigacion(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del proyecto no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }

    /** Agrega un investigador creado por fuera del proyecto (agregación). */
    public void agregarInvestigador(Investigador investigador) {
        if (investigador == null) {
            throw new IllegalArgumentException("El investigador no puede ser nulo");
        }
        if (investigadores.contains(investigador)) {
            throw new IllegalArgumentException(
                    investigador.getNombre() + " ya está en el proyecto");
        }
        investigadores.add(investigador);
    }

    /** Agrega un analizador al pipeline. */
    public void agregarAnalizador(AnalizadorGenomico analizador) {
        if (analizador == null) {
            throw new IllegalArgumentException("El analizador no puede ser nulo");
        }
        analizadores.add(analizador);
    }

    /** Registra una secuencia con id único. */
    public void registrarSecuencia(Secuencia secuencia) {
        if (secuencia == null) {
            throw new IllegalArgumentException("La secuencia no puede ser nula");
        }
        if (buscarSecuencia(secuencia.getId()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una secuencia con id " + secuencia.getId());
        }
        secuencias.add(secuencia);
    }

    /** Busca una secuencia por id; devuelve null si no existe. */
    public Secuencia buscarSecuencia(String id) {
        for (Secuencia s : secuencias) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    /** Registra un observador que recibirá las alertas. */
    public void agregarObservador(Observador observador) {
        if (observador == null) {
            throw new IllegalArgumentException("El observador no puede ser nulo");
        }
        observadores.add(observador);
    }

    /** Pasa la secuencia por todos los analizadores (polimorfismo) y avisa si hay alerta. */
    public List<String> analizarSecuencia(Secuencia s) {
        List<String> resultados = new ArrayList<>();
        for (AnalizadorGenomico analizador : analizadores) {
            resultados.add(analizador.analizar(s));
            if (analizador.detectoAlerta()) {
                notificarObservadores("Variante patogénica detectada en " + s.getId()
                        + " (" + analizador.getNombre() + ")");
            }
        }
        return resultados;
    }

    private void notificarObservadores(String mensaje) {
        for (Observador o : observadores) {
            o.notificar(mensaje);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public List<Investigador> getInvestigadores() {
        return List.copyOf(investigadores);
    }

    public List<AnalizadorGenomico> getAnalizadores() {
        return List.copyOf(analizadores);
    }

    public List<Secuencia> getSecuencias() {
        return List.copyOf(secuencias);
    }

    @Override
    public String generarReporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("Proyecto: ").append(nombre).append('\n');
        sb.append("Investigadores (").append(investigadores.size()).append("): ")
                .append(investigadores).append('\n');
        sb.append("Secuencias registradas: ").append(secuencias.size()).append('\n');
        sb.append("Pipeline:");
        for (AnalizadorGenomico a : analizadores) {
            sb.append("\n  - ").append(a.generarReporte());
        }
        return sb.toString();
    }
}
