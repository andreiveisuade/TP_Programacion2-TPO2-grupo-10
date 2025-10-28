package ejercicio2clase10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

class CargadorNotasImplTest {

    private final InputStream originalSystemIn = System.in;

    @AfterEach
    void restoreSystemIn() {
        System.setIn(originalSystemIn);
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void testCargarNotasConEntradaValida() {
        provideInput("7\nMateria1\n8\nMateria2\n9\nMateria3\n-1\n");
        CargadorNotasImpl cargadorNotas = new CargadorNotasImpl();
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(3, notas.size());
        assertEquals(7, notas.get(0).getValor());
        assertEquals("Materia1", notas.get(0).getMateria());
        assertEquals(8, notas.get(1).getValor());
        assertEquals("Materia2", notas.get(1).getMateria());
        assertEquals(9, notas.get(2).getValor());
        assertEquals("Materia3", notas.get(2).getMateria());
    }

    @Test
    void testCargarNotasConEntradaVacia() {
        provideInput("-1\n");
        CargadorNotasImpl cargadorNotas = new CargadorNotasImpl();
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertTrue(notas.isEmpty());
    }

    @Test
    void testCargarNotasConEntradaInvalidaNoNumerica() {
        provideInput("abc\n7\nMateria1\n-1\n");
        CargadorNotasImpl cargadorNotas = new CargadorNotasImpl();
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(1, notas.size());
        assertEquals(7, notas.get(0).getValor());
        assertEquals("Materia1", notas.get(0).getMateria());
    }

    @Test
    void testCargarNotasConEntradaFueraDeRango() {
        provideInput("11\n-5\n5\nMateria1\n-1\n");
        CargadorNotasImpl cargadorNotas = new CargadorNotasImpl();
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(1, notas.size());
        assertEquals(5, notas.get(0).getValor());
        assertEquals("Materia1", notas.get(0).getMateria());
    }

    @Test
    void testCargarNotasConSoloValorDeSalida() {
        provideInput("-1\n");
        CargadorNotasImpl cargadorNotas = new CargadorNotasImpl();
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertTrue(notas.isEmpty());
    }

    @Test
    void testCargarNotasConMultiplesValoresDeSalida() {
        provideInput("7\nMateria1\n-1\n-1\n"); // Only the first -1 should act as exit
        CargadorNotasImpl cargadorNotas = new CargadorNotasImpl();
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(1, notas.size());
        assertEquals(7, notas.get(0).getValor());
        assertEquals("Materia1", notas.get(0).getMateria());
    }

    @Test
    void testCargarNotasConCeroYDiez() {
        provideInput("0\nMateria1\n10\nMateria2\n-1\n");
        CargadorNotasImpl cargadorNotas = new CargadorNotasImpl();
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(2, notas.size());
        assertEquals(0, notas.get(0).getValor());
        assertEquals("Materia1", notas.get(0).getMateria());
        assertEquals(10, notas.get(1).getValor());
        assertEquals("Materia2", notas.get(1).getMateria());
    }

    @Test
    void testCargarNotasConEspaciosEnBlanco() {
        provideInput(" 7 \nMateria1\n 8 \nMateria2\n-1\n");
        CargadorNotasImpl cargadorNotas = new CargadorNotasImpl();
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(2, notas.size());
        assertEquals(7, notas.get(0).getValor());
        assertEquals("Materia1", notas.get(0).getMateria());
        assertEquals(8, notas.get(1).getValor());
        assertEquals("Materia2", notas.get(1).getMateria());
    }

    @Test
    void testCargarNotasConEntradaNoNumericaYLuegoValida() {
        provideInput("dos\n5\nMateria1\n-1\n");
        CargadorNotasImpl cargadorNotas = new CargadorNotasImpl();
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(1, notas.size());
        assertEquals(5, notas.get(0).getValor());
        assertEquals("Materia1", notas.get(0).getMateria());
    }

    @Test
    void testCargarNotasConEntradaNumericaPeroNoEntera() {
        provideInput("7.5\n8\nMateria1\n-1\n");
        CargadorNotasImpl cargadorNotas = new CargadorNotasImpl();
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(1, notas.size());
        assertEquals(8, notas.get(0).getValor());
        assertEquals("Materia1", notas.get(0).getMateria());
    }

    @Test
    void testCargarNotasConEntradaMuyLarga() {
        provideInput("12345678901234567890\n5\nMateria1\n-1\n");
        CargadorNotasImpl cargadorNotas = new CargadorNotasImpl();
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(1, notas.size());
        assertEquals(5, notas.get(0).getValor());
        assertEquals("Materia1", notas.get(0).getMateria());
    }

    @Test
    void testCargarNotasConMateriaEnBlanco() {
        provideInput("7\n   \n8\nMateria2\n-1\n");
        CargadorNotasImpl cargadorNotas = new CargadorNotasImpl();
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(1, notas.size());
        assertEquals(8, notas.get(0).getValor());
        assertEquals("Materia2", notas.get(0).getMateria());
    }
}
