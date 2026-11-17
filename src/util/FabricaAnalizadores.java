package util;

import modelo.Alineador;
import modelo.AnalizadorGenomico;
import modelo.AnotadorGenico;
import modelo.DetectorVariantes;

public class FabricaAnalizadores {

    private FabricaAnalizadores() {
    }

    public static AnalizadorGenomico crear(String tipo, String nombre) {
        if (tipo == null) {
            throw new IllegalArgumentException("Debe indicar el tipo de analizador");
        }
        GenomaReferenciaConfig config = GenomaReferenciaConfig.getInstance();
        switch (tipo.trim().toLowerCase()) {
            case "alineador":
                return new Alineador(nombre, config.getReferencia());
            case "detector":
                return new DetectorVariantes(nombre, config.getReferencia(),
                        config.getPosicionesPatogenicas());
            case "anotador":
                return new AnotadorGenico(nombre);
            default:
                throw new IllegalArgumentException("Tipo de analizador desconocido: " + tipo
                        + " (use alineador, detector o anotador)");
        }
    }
}
