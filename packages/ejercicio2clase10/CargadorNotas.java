package ejercicio2clase10;

import java.util.List;

/**
 * Interfaz para la carga de notas.
 * Define el contrato para cualquier clase que sea responsable de obtener una lista de notas.
 */
public interface CargadorNotas {
    /**
     * Carga y retorna una lista de notas.
     * La implementación de este método puede variar (ej. leer de consola, de un archivo, etc.).
     * @return Una lista de objetos {@link Nota}.
     */
    List<Nota> cargarNotas();
}