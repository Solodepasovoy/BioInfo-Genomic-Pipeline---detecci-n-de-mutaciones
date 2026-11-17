package modelo;

/** Parte de una Secuencia; solo la propia Secuencia la crea (composición). */
public class Fragmento {

    private final int posicionInicial;
    private final String bases;

    Fragmento(int posicionInicial, String bases) {
        this.posicionInicial = posicionInicial;
        this.bases = bases;
    }

    public int getPosicionInicial() {
        return posicionInicial;
    }

    public int getPosicionFinal() {
        return posicionInicial + bases.length() - 1;
    }

    public String getBases() {
        return bases;
    }

    /** Porcentaje de bases G o C dentro del fragmento. */
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
    public String toString() {
        return "[" + posicionInicial + "-" + getPosicionFinal() + "] " + bases;
    }
}
