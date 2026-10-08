package padroes.state;

public class EstadoResolvido implements EstadoChamado {

    @Override
    public String getNome() {
        return "Resolvido";
    }

    @Override
    public boolean iniciarAtendimento(Chamado chamado) {
        return false;
    }

    @Override
    public boolean resolver(Chamado chamado) {
        return false;
    }

    @Override
    public boolean cancelar(Chamado chamado) {
        return false;
    }

    @Override
    public boolean reabrir(Chamado chamado) {
        chamado.alterarEstado(new EstadoAberto());
        return true;
    }
}
