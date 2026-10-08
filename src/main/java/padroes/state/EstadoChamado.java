package padroes.state;

public interface EstadoChamado {

    String getNome();

    boolean iniciarAtendimento(Chamado chamado);

    boolean resolver(Chamado chamado);

    boolean cancelar(Chamado chamado);

    boolean reabrir(Chamado chamado);
}
