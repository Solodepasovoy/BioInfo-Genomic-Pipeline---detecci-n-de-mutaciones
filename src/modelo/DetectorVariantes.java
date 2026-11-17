package modelo;

import java.util.ArrayList;
import java.util.List;

/** Detecta diferencias con la referencia y marca las variantes patogénicas. */
public class DetectorVariantes extends AnalizadorGenomico {

    private final Secuencia referencia;
    private final List<Integer> posicionesPatogenicas;
    private final List<Variante> ultimasVariantes = new ArrayList<>();

    public DetectorVariantes(String nombre, Secuencia referencia,
                             List<Integer> posicionesPatogenicas) {
        super(nombre);
        if (referencia == null) {
            throw new IllegalArgumentException("El detector necesita una secuencia de referencia");
        }
        for (int pos : posicionesPatogenicas) {
            if (pos < 1 || pos > referencia.getLongitud()) {
                throw new IllegalArgumentException(
                        "Posición patogénica fuera de la referencia: " + pos);
            }
        }
        this.referencia = referencia;
        this.posicionesPatogenicas = List.copyOf(posicionesPatogenicas);
    }

    /** Compara la secuencia con la referencia posición por posición. */
    @Override
    public String analizar(Secuencia s) {
        iniciarAnalisis(s);
        ultimasVariantes.clear();
        int longitudComun = Math.min(s.getLongitud(), referencia.getLongitud());
        for (int pos = 1; pos <= longitudComun; pos++) {
            char esperada = referencia.baseEn(pos);
            char observada = s.baseEn(pos);
            if (esperada != observada) {
                ultimasVariantes.add(new Variante(pos, esperada, observada,
                        posicionesPatogenicas.contains(pos)));
            }
        }
        if (ultimasVariantes.isEmpty()) {
            return "DetectorVariantes -> " + s.getId() + ": sin variantes respecto a la referencia";
        }
        return "DetectorVariantes -> " + s.getId() + ": " + ultimasVariantes.size()
                + " variante(s): " + ultimasVariantes;
    }

    /** Indica si el último análisis encontró una variante patogénica. */
    public boolean hayVariantePatogenica() {
        for (Variante v : ultimasVariantes) {
            if (v.esPatogenica()) {
                return true;
            }
        }
        return false;
    }

    /** La alerta se cumple cuando hay una variante patogénica. */
    @Override
    public boolean detectoAlerta() {
        return hayVariantePatogenica();
    }

    public List<Variante> getUltimasVariantes() {
        return List.copyOf(ultimasVariantes);
    }

    public List<Integer> getPosicionesPatogenicas() {
        return posicionesPatogenicas;
    }
}
