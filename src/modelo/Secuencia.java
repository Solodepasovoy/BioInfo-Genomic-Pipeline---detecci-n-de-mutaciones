package modelo;

import java.util.ArrayList;
import java.util.List;

/** Secuencia genética (A, C, G, T) que se compone de sus fragmentos. */
public class Secuencia implements Reportable {

    public static final int TAMANO_FRAGMENTO = 4;

    private final String id;
    private final String bases;
    private final List<Fragmento> fragmentos = new ArrayList<>();

    /** Valida la cadena y crea sus fragmentos (composición). */
    public Secuencia(String id, String bases) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El id de la secuencia no puede estar vacío");
        }
        if (bases == null || bases.isBlank()) {
            throw new IllegalArgumentException("La secuencia " + id + " no tiene bases");
        }
        String normalizada = bases.trim().toUpperCase();
        if (!normalizada.matches("[ACGT]+")) {
            throw new IllegalArgumentException(
                    "La secuencia " + id + " solo puede contener A, C, G y T: " + bases);
        }
        this.id = id.trim();
        this.bases = normalizada;
        crearFragmentos();
    }

    private void crearFragmentos() {
        for (int i = 0; i < bases.length(); i += TAMANO_FRAGMENTO) {
            int fin = Math.min(i + TAMANO_FRAGMENTO, bases.length());
            fragmentos.add(new Fragmento(i + 1, bases.substring(i, fin)));
        }
    }

    public String getId() {
        return id;
    }

    public String getBases() {
        return bases;
    }

    public int getLongitud() {
        return bases.length();
    }

    public char baseEn(int posicion) {
        return bases.charAt(posicion - 1);
    }

    public List<Fragmento> getFragmentos() {
        return List.copyOf(fragmentos);
    }

    /** Porcentaje de bases G o C en toda la secuencia. */
    public double porcentajeGC() {
        int gc = 0;
        for (char base : bases.toCharArray()) {
            if (base == 'G' || base == 'C') {
                gc++;
            }
        }
        return gc * 100.0 / bases.length();
    }

    @Override
    public String generarReporte() {
        return String.format("Secuencia %s: %s (%d pb, %d fragmentos, GC %.1f%%)",
                id, bases, getLongitud(), fragmentos.size(), porcentajeGC());
    }
}
