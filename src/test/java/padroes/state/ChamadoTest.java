package padroes.state;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ChamadoTest {

    @Test
    void deveCriarChamadoAberto() {
        assertEquals("Aberto", new Chamado().getNomeEstado());
    }

    @ParameterizedTest(name = "{0}: {1} -> {3} (permitida: {2})")
    @MethodSource("transicoes")
    void deveRespeitarOperacoesDeCadaEstado(String estadoInicial, String operacao,
                                            boolean permitido, String estadoEsperado) {
        Chamado chamado = criarChamadoNoEstado(estadoInicial);

        boolean resultado = obterOperacao(operacao).apply(chamado);

        assertAll(
                () -> assertEquals(permitido, resultado),
                () -> assertEquals(estadoEsperado, chamado.getNomeEstado())
        );
    }

    static Stream<Arguments> transicoes() {
        return Stream.of(
                Arguments.of("Aberto", "iniciarAtendimento", true, "Em atendimento"),
                Arguments.of("Aberto", "resolver", false, "Aberto"),
                Arguments.of("Aberto", "cancelar", true, "Cancelado"),
                Arguments.of("Aberto", "reabrir", false, "Aberto"),
                Arguments.of("Em atendimento", "iniciarAtendimento", false, "Em atendimento"),
                Arguments.of("Em atendimento", "resolver", true, "Resolvido"),
                Arguments.of("Em atendimento", "cancelar", true, "Cancelado"),
                Arguments.of("Em atendimento", "reabrir", false, "Em atendimento"),
                Arguments.of("Resolvido", "iniciarAtendimento", false, "Resolvido"),
                Arguments.of("Resolvido", "resolver", false, "Resolvido"),
                Arguments.of("Resolvido", "cancelar", false, "Resolvido"),
                Arguments.of("Resolvido", "reabrir", true, "Aberto"),
                Arguments.of("Cancelado", "iniciarAtendimento", false, "Cancelado"),
                Arguments.of("Cancelado", "resolver", false, "Cancelado"),
                Arguments.of("Cancelado", "cancelar", false, "Cancelado"),
                Arguments.of("Cancelado", "reabrir", false, "Cancelado")
        );
    }

    @Test
    void devePermitirNovoAtendimentoDepoisDeReabrir() {
        Chamado chamado = new Chamado();

        assertTrue(chamado.iniciarAtendimento());
        assertTrue(chamado.resolver());
        assertTrue(chamado.reabrir());
        assertEquals("Aberto", chamado.getNomeEstado());
        assertTrue(chamado.iniciarAtendimento());
        assertTrue(chamado.resolver());
        assertEquals("Resolvido", chamado.getNomeEstado());
    }

    @Test
    void devePreservarEstadoCanceladoAposTodasAsTentativas() {
        Chamado chamado = new Chamado();

        assertTrue(chamado.iniciarAtendimento());
        assertTrue(chamado.cancelar());
        assertFalse(chamado.iniciarAtendimento());
        assertFalse(chamado.resolver());
        assertFalse(chamado.cancelar());
        assertFalse(chamado.reabrir());
        assertEquals("Cancelado", chamado.getNomeEstado());
    }

    @Test
    void deveManterEstadosIndependentesEntreChamados() {
        Chamado primeiro = new Chamado();
        Chamado segundo = new Chamado();

        assertTrue(primeiro.iniciarAtendimento());
        assertEquals("Aberto", segundo.getNomeEstado());
        assertTrue(segundo.cancelar());
        assertEquals("Em atendimento", primeiro.getNomeEstado());
        assertTrue(primeiro.resolver());
        assertEquals("Cancelado", segundo.getNomeEstado());
    }

    private Chamado criarChamadoNoEstado(String estado) {
        Chamado chamado = new Chamado();
        switch (estado) {
            case "Aberto":
                break;
            case "Em atendimento":
                assertTrue(chamado.iniciarAtendimento());
                break;
            case "Resolvido":
                assertTrue(chamado.iniciarAtendimento());
                assertTrue(chamado.resolver());
                break;
            case "Cancelado":
                assertTrue(chamado.cancelar());
                break;
            default:
                throw new IllegalArgumentException("Estado de teste desconhecido: " + estado);
        }
        assertEquals(estado, chamado.getNomeEstado());
        return chamado;
    }

    private Function<Chamado, Boolean> obterOperacao(String operacao) {
        switch (operacao) {
            case "iniciarAtendimento":
                return Chamado::iniciarAtendimento;
            case "resolver":
                return Chamado::resolver;
            case "cancelar":
                return Chamado::cancelar;
            case "reabrir":
                return Chamado::reabrir;
            default:
                throw new IllegalArgumentException("Operação de teste desconhecida: " + operacao);
        }
    }
}
