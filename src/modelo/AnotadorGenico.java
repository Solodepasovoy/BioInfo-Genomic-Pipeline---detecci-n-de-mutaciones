package modelo;

import java.util.ArrayList;
import java.util.List;

/** Ubica codones de inicio y de parada y calcula el GC de cada fragmento. */
public class AnotadorGenico extends AnalizadorGenomico {

    private static final String CODON_INICIO = "ATG";
    private static final List<String> CODONES_PARADA = List.of("TAA", "TAG", "TGA");

    public AnotadorGenico(String nombre) {
        super(nombre);
    }

    /** Anota los codones y el contenido GC de la secuencia. */
    @Override
    public String analizar(Secuencia s) {
        iniciarAnalisis(s);
        List<Integer> inicios = new ArrayList<>();
        List<Integer> paradas = new ArrayList<>();
        String bases = s.getBases();
        for (int i = 0; i + 3 <= bases.length(); i++) {
            String codon = bases.substring(i, i + 3);
            if (codon.equals(CODON_INICIO)) {
                inicios.add(i + 1);
            } else if (CODONES_PARADA.contains(codon)) {
                paradas.add(i + 1);
            }
        }

        StringBuilder gcPorFragmento = new StringBuilder();
        for (Fragmento f : s.getFragmentos()) {
            if (gcPorFragmento.length() > 0) {
                gcPorFragmento.append(", ");
            }
            gcPorFragmento.append(String.format("%s GC %.0f%%", f, f.porcentajeGC()));
        }

        return String.format(
                "AnotadorGenico -> %s: ATG en %s, codones de parada en %s; fragmentos: %s",
                s.getId(), describir(inicios), describir(paradas), gcPorFragmento);
    }

    private String describir(List<Integer> posiciones) {
        return posiciones.isEmpty() ? "ninguna posición" : "pos " + posiciones;
    }
}
