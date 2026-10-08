package padroes.state;

public class EstadoEmAtendimento implements EstadoChamado {

    @Override
    public String getNome() {
        return "Em atendimento";
    }

    @Override
    public boolean iniciarAtendimento(Chamado chamado) {
        return false;
    }

    @Override
    public boolean resolver(Chamado chamado) {
        chamado.alterarEstado(new EstadoResolvido());
        return true;
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
