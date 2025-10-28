package ejercicio2clase10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GestorNotasTest {

    private GestorNotas gestorNotas;
    private List<Nota> notasDePrueba;

    @BeforeEach
    void setUp() {
        notasDePrueba = new ArrayList<>();
        // GestorNotas ahora se inicializa con una lista de notas
        // Para cada test, crearemos una nueva instancia de GestorNotas con la lista que necesitemos
    }

    @Test
    void testCalcularPromedioConNotas() {
        notasDePrueba.add(new NotaImpl(5, "Matematicas"));
        notasDePrueba.add(new NotaImpl(8, "Literatura"));
        notasDePrueba.add(new NotaImpl(10, "Historia"));
        gestorNotas = new GestorNotas(notasDePrueba);
        assertEquals(7.66, gestorNotas.calcularPromedio(), 0.01);
    }

    @Test
    void testCalcularPromedioSinNotas() {
        gestorNotas = new GestorNotas(notasDePrueba); // Lista vacía
        assertEquals(0.0, gestorNotas.calcularPromedio(), 0.01);
    }

    @Test
    void testCalcularPromedioConUnaSolaNota() {
        notasDePrueba.add(new NotaImpl(7, "Programacion"));
        gestorNotas = new GestorNotas(notasDePrueba);
        assertEquals(7.0, gestorNotas.calcularPromedio(), 0.01);
    }

    @Test
    void testCalcularPromedioConNotasDecimales() {
        notasDePrueba.add(new NotaImpl(7, "Fisica"));
        notasDePrueba.add(new NotaImpl(8, "Quimica"));
        gestorNotas = new GestorNotas(notasDePrueba);
        assertEquals(7.5, gestorNotas.calcularPromedio(), 0.01);
    }

    @Test
    void testCalcularPromedioConNotasCero() {
        notasDePrueba.add(new NotaImpl(0, "Arte"));
        notasDePrueba.add(new NotaImpl(0, "Musica"));
        gestorNotas = new GestorNotas(notasDePrueba);
        assertEquals(0.0, gestorNotas.calcularPromedio(), 0.01);
    }

    @Test
    void testCalcularPromedioConNotasDiez() {
        notasDePrueba.add(new NotaImpl(10, "Deportes"));
        notasDePrueba.add(new NotaImpl(10, "Geografia"));
        gestorNotas = new GestorNotas(notasDePrueba);
        assertEquals(10.0, gestorNotas.calcularPromedio(), 0.01);
    }

    @Test
    void testConstructorConListaNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GestorNotas(null);
        }, "El constructor no debería aceptar una lista nula.");
    }

    @Test
    void testImprimirNotas() {
        notasDePrueba.add(new NotaImpl(7, "Matematicas"));
        notasDePrueba.add(new NotaImpl(8, "Literatura"));
        gestorNotas = new GestorNotas(notasDePrueba);

        // No podemos verificar la salida de System.out directamente sin librerías adicionales
        // Pero podemos asegurarnos de que no lanza excepciones.
        assertDoesNotThrow(() -> gestorNotas.imprimirNotas());
    }
}

