package ejercicio2clase10;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotaImplTest {

    @Test
    void testConstructorAndGetters() {
        NotaImpl nota = new NotaImpl(7, "Matematicas");
        assertEquals(7, nota.getValor());
        assertEquals("Matematicas", nota.getMateria());
    }

    @Test
    void testInvalidGradeBelowZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new NotaImpl(-1, "Matematicas");
        }, "La nota no puede ser menor que 0.");
    }

    @Test
    void testInvalidGradeAboveTen() {
        assertThrows(IllegalArgumentException.class, () -> {
            new NotaImpl(11, "Matematicas");
        }, "La nota no puede ser mayor que 10.");
    }

    @Test
    void testValidGradeZero() {
        NotaImpl nota = new NotaImpl(0, "Matematicas");
        assertEquals(0, nota.getValor());
    }

    @Test
    void testValidGradeTen() {
        NotaImpl nota = new NotaImpl(10, "Matematicas");
        assertEquals(10, nota.getValor());
    }

    @Test
    void testToString() {
        NotaImpl nota = new NotaImpl(7, "Matematicas");
        assertEquals("7", nota.toString());
    }

    @Test
    void testNullMateria() {
        assertThrows(IllegalArgumentException.class, () -> {
            new NotaImpl(7, null);
        }, "La materia no puede ser nula.");
    }

    @Test
    void testEmptyMateria() {
        assertThrows(IllegalArgumentException.class, () -> {
            new NotaImpl(7, "");
        }, "La materia no puede estar vacía.");
    }
}

