package modelo;

// VistaConsola la implementa y ProyectoInvestigacion avisa a sus observadores con notificar().
/** Objeto que recibe avisos del modelo cuando se cumple la regla de alerta. */
public interface Observador {

    /** Recibe el mensaje de una alerta. */
    void notificar(String mensaje);
}
