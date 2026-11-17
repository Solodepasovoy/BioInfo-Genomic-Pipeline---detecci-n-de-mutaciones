package vista;

import controlador.ControladorPipeline;
import modelo.ProyectoInvestigacion;


public class Main {

    public static void main(String[] args) {
        ProyectoInvestigacion proyecto = new ProyectoInvestigacion("BioInfo Genomic Pipeline");
        VistaConsola vista = new VistaConsola();
        ControladorPipeline controlador = new ControladorPipeline(vista, proyecto);
        controlador.cargarDatosIniciales();
        controlador.iniciar();
    }
}
