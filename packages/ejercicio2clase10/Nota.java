package ejercicio2clase10;

/**
 * Interfaz que representa una nota académica.
 * Define los métodos para obtener el valor numérico de la nota y la materia a la que corresponde.
 */
public interface Nota {
    /**
     * Obtiene el valor numérico de la nota.
     * @return El valor de la nota (generalmente entre 0 y 10).
     */
    int getValor();

    /**
     * Obtiene el nombre de la materia a la que corresponde la nota.
     * @return El nombre de la materia.
     */
    String getMateria();
}