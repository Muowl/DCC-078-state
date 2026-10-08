package padroes.state;

public class Chamado {

    private EstadoChamado estado;

    public Chamado() {
        estado = new EstadoAberto();
    }

    void alterarEstado(EstadoChamado estado) {
        this.estado = estado;
    }

    public String getNomeEstado() {
        return estado.getNome();
    }

    public boolean iniciarAtendimento() {
        return estado.iniciarAtendimento(this);
    }

    public boolean resolver() {
        return estado.resolver(this);
    }

    public boolean cancelar() {
        return estado.cancelar(this);
    }

    public boolean reabrir() {
        return estado.reabrir(this);
    }
}
