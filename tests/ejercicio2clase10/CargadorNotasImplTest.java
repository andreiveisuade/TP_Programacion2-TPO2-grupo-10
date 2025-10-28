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

    private CargadorNotasImpl cargadorNotas;
    private final InputStream originalSystemIn = System.in;

    @BeforeEach
    void setUp() {
        cargadorNotas = new CargadorNotasImpl();
    }

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
        provideInput("7\n8\n9\n-1\n");
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(3, notas.size());
        assertEquals(7, notas.get(0).getValor());
        assertEquals(8, notas.get(1).getValor());
        assertEquals(9, notas.get(2).getValor());
    }

    @Test
    void testCargarNotasConEntradaVacia() {
        provideInput("-1\n");
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertTrue(notas.isEmpty());
    }

    @Test
    void testCargarNotasConEntradaInvalidaNoNumerica() {
        provideInput("abc\n7\n-1\n");
        // Expecting an exception or a graceful handling that skips invalid input
        // Current implementation will throw InputMismatchException if not handled internally
        // For this test, we expect it to skip 'abc' and continue with '7'
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(1, notas.size());
        assertEquals(7, notas.get(0).getValor());
    }

    @Test
    void testCargarNotasConEntradaFueraDeRango() {
        provideInput("11\n-5\n5\n-1\n");
        // Expecting invalid grades to be skipped or handled
        // Current implementation of NotaImpl throws IllegalArgumentException
        // CargadorNotasImpl should catch this and prompt again or skip
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(1, notas.size());
        assertEquals(5, notas.get(0).getValor());
    }

    @Test
    void testCargarNotasConSoloValorDeSalida() {
        provideInput("-1\n");
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertTrue(notas.isEmpty());
    }

    @Test
    void testCargarNotasConMultiplesValoresDeSalida() {
        provideInput("7\n-1\n-1\n"); // Only the first -1 should act as exit
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(1, notas.size());
        assertEquals(7, notas.get(0).getValor());
    }

    @Test
    void testCargarNotasConCeroYDiez() {
        provideInput("0\n10\n-1\n");
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(2, notas.size());
        assertEquals(0, notas.get(0).getValor());
        assertEquals(10, notas.get(1).getValor());
    }

    @Test
    void testCargarNotasConEspaciosEnBlanco() {
        provideInput(" 7 \n 8 \n-1\n");
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(2, notas.size());
        assertEquals(7, notas.get(0).getValor());
        assertEquals(8, notas.get(1).getValor());
    }

    @Test
    void testCargarNotasConEntradaNoNumericaYLuegoValida() {
        provideInput("dos\n5\n-1\n");
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(1, notas.size());
        assertEquals(5, notas.get(0).getValor());
    }

    @Test
    void testCargarNotasConEntradaNumericaPeroNoEntera() {
        provideInput("7.5\n8\n-1\n");
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(1, notas.size());
        assertEquals(8, notas.get(0).getValor());
    }

    @Test
    void testCargarNotasConEntradaMuyLarga() {
        provideInput("12345678901234567890\n5\n-1\n");
        List<Nota> notas = cargadorNotas.cargarNotas();
        assertNotNull(notas);
        assertEquals(1, notas.size());
        assertEquals(5, notas.get(0).getValor());
    }
}
