package padroes.state;

public class EstadoAberto implements EstadoChamado {

    @Override
    public String getNome() {
        return "Aberto";
    }

    @Override
    public boolean iniciarAtendimento(Chamado chamado) {
        chamado.alterarEstado(new EstadoEmAtendimento());
        return true;
    }

    @Override
    public boolean resolver(Chamado chamado) {
        return false;
    }

    @Override
    public boolean cancelar(Chamado chamado) {
        chamado.alterarEstado(new EstadoCancelado());
        return true;
    }

    @Override
    public boolean reabrir(Chamado chamado) {
        return false;
    }
}
