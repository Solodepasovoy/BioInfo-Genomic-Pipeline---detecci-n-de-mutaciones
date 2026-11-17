package util;

import modelo.Secuencia;

import java.util.List;

public class GenomaReferenciaConfig {

    private static GenomaReferenciaConfig instancia;

    private final Secuencia referencia;
    private final List<Integer> posicionesPatogenicas;

    private GenomaReferenciaConfig() {
        referencia = new Secuencia("REF", "ACGTTGCA");
        posicionesPatogenicas = List.of(3, 6);
    }

    public static GenomaReferenciaConfig getInstance() {
        if (instancia == null) {
            instancia = new GenomaReferenciaConfig();
        }
        return instancia;
    }

    public Secuencia getReferencia() {
        return referencia;
    }

    public List<Integer> getPosicionesPatogenicas() {
        return posicionesPatogenicas;
    }
}
