package modelo;

/** Diferencia puntual entre una secuencia y la referencia. */
public class Variante {

    private final int posicion;
    private final char baseReferencia;
    private final char baseObservada;
    private final boolean patogenica;

    public Variante(int posicion, char baseReferencia, char baseObservada, boolean patogenica) {
        if (posicion < 1) {
            throw new IllegalArgumentException(
                    "La posición de una variante debe ser mayor o igual a 1");
        }
        this.posicion = posicion;
        this.baseReferencia = baseReferencia;
        this.baseObservada = baseObservada;
        this.patogenica = patogenica;
    }

    public int getPosicion() {
        return posicion;
    }

    public char getBaseReferencia() {
        return baseReferencia;
    }

    public char getBaseObservada() {
        return baseObservada;
    }

    public boolean esPatogenica() {
        return patogenica;
    }

    @Override
    public String toString() {
        return "pos " + posicion + " " + baseReferencia + ">" + baseObservada
                + (patogenica ? " (PATOGÉNICA)" : "");
    }
}
