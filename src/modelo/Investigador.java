package modelo;

/** Persona que existe por fuera del proyecto y este solo recibe (agregación). */
public class Investigador {

    private final String nombre;
    private String especialidad;

    public Investigador(String nombre, String especialidad) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del investigador no puede estar vacío");
        }
        this.nombre = nombre.trim();
        this.especialidad = validarEspecialidad(especialidad);
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = validarEspecialidad(especialidad);
    }

    private static String validarEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.isBlank()) {
            throw new IllegalArgumentException("La especialidad no puede estar vacía");
        }
        return especialidad.trim();
    }

    @Override
    public String toString() {
        return nombre + " (" + especialidad + ")";
    }
}
