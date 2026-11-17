package modelo;

/** Compara una secuencia con la referencia y calcula el porcentaje de identidad. */
public class Alineador extends AnalizadorGenomico {

    private final Secuencia referencia;

    public Alineador(String nombre, Secuencia referencia) {
        super(nombre);
        if (referencia == null) {
            throw new IllegalArgumentException("El alineador necesita una secuencia de referencia");
        }
        this.referencia = referencia;
    }

    /** Cuenta las posiciones que coinciden con la referencia. */
    @Override
    public String analizar(Secuencia s) {
        iniciarAnalisis(s);
        int longitudMaxima = Math.max(s.getLongitud(), referencia.getLongitud());
        int longitudComun = Math.min(s.getLongitud(), referencia.getLongitud());
        int coincidencias = 0;
        for (int pos = 1; pos <= longitudComun; pos++) {
            if (s.baseEn(pos) == referencia.baseEn(pos)) {
                coincidencias++;
            }
        }
        double identidad = coincidencias * 100.0 / longitudMaxima;
        return String.format(
                "Alineador -> %s: %d/%d bases coinciden con la referencia (%.1f%% de identidad)",
                s.getId(), coincidencias, longitudMaxima, identidad);
    }

    public Secuencia getReferencia() {
        return referencia;
    }
}
