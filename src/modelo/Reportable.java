package modelo;

// La implementan AnalizadorGenomico (y sus 3 subclases), Secuencia y ProyectoInvestigacion.
/** Objetos que pueden describirse a sí mismos en un reporte de texto. */
public interface Reportable {

    /** Devuelve un reporte legible del estado del objeto. */
    String generarReporte();
}
