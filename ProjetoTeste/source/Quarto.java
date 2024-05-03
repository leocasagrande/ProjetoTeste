import java.util.ArrayList;
import java.util.List;

public class Quarto {
    private int numero;
    private boolean vago;
    private List<Hospede> hospedes;
    private boolean chaveNaRecepcao;
    private int ocupacaoAtual;

    public Quarto(int numero) {
        this.numero = numero;
        this.vago = true;
        this.hospedes = new ArrayList<>();
        this.chaveNaRecepcao = true;
        this.ocupacaoAtual = 0;
    }

    public synchronized boolean isVago() {
        return vago;
    }

    public synchronized List<Hospede> getHospedes() {
        return hospedes;
    }

    public synchronized void adicionarHospede(Hospede hospede, int numeroDeMembros) {
        if(ocupacaoAtual + numeroDeMembros <= 4) {
            hospedes.add(hospede);
            ocupacaoAtual += numeroDeMembros;
            vago = false;
        }
    }

    public synchronized void removerHospede(Hospede hospede) {
        hospedes.remove(hospede);
        if (hospedes.isEmpty()) {
            vago = true;
            chaveNaRecepcao = true;
        }
    }

    public synchronized boolean isChaveNaRecepcao() {
        return chaveNaRecepcao;
    }

    public synchronized void setChaveNaRecepcao(boolean chaveNaRecepcao) {
        this.chaveNaRecepcao = chaveNaRecepcao;
    }

    public synchronized boolean isHospedesNoQuarto() {
        return !hospedes.isEmpty();
    }

    public int getNumero() {
        // TODO Auto-generated method stub
        return this.numero;
    }


}
