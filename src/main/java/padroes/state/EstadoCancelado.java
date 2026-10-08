package padroes.state;

public class EstadoCancelado implements EstadoChamado {

    @Override
    public String getNome() {
        return "Cancelado";
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
        return false;
    }
}
