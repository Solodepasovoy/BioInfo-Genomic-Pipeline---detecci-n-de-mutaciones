package modelo;

/** Superclase abstracta de los analizadores del pipeline genómico. */
public abstract class AnalizadorGenomico implements Reportable {

    private final String nombre;
    private int analisisRealizados;

    protected AnalizadorGenomico(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del analizador no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }

    /** Analiza una secuencia; cada subclase lo hace a su manera. */
    public abstract String analizar(Secuencia s);

    /** Indica si el último análisis cumple la regla de alerta; por defecto no. */
    public boolean detectoAlerta() {
        return false;
    }

    protected void iniciarAnalisis(Secuencia s) {
        if (s == null) {
            throw new IllegalArgumentException("No se puede analizar una secuencia nula");
        }
        analisisRealizados++;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAnalisisRealizados() {
        return analisisRealizados;
    }

    public String getTipo() {
        return getClass().getSimpleName();
    }

    @Override
    public String generarReporte() {
        return String.format("%s [%s] - análisis realizados: %d",
                nombre, getTipo(), analisisRealizados);
    }
}
