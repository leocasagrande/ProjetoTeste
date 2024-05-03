import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Hotel {
    List<Quarto> quartos;
    private BlockingQueue<Hospede> filaEspera;
    private AtomicInteger hospedesAtivos = new AtomicInteger(0);
    List<Camareira> camareiras;
    List<Recepcionista> recepcionistas;
    private List<Hospede> todosHospedes;

    public Hotel() {
        quartos = new ArrayList<>();
        filaEspera = new LinkedBlockingQueue<>();
        camareiras = new ArrayList<>();
        todosHospedes = new ArrayList<>();
        recepcionistas = new ArrayList<>();

        // Inicializar os quartos
        for (int i = 0; i < 8; i++) {
            quartos.add(new Quarto(i + 1));
        }

        // Inicializar as camareiras
        for (int i = 0; i < 10; i++) {
            camareiras.add(new Camareira(this));
        }

        // Inicializar os recepcionistas
        for (int i = 0; i < 5; i++) {
            recepcionistas.add(new Recepcionista(this));
        }
    }

    public synchronized Hospede proximoHospede() {
        if (!todosHospedes.isEmpty()) {
            return todosHospedes.remove(0); // Retorna e remove o próximo hóspede da lista
        }
        return null; // Retorna null se não houver hóspedes
    }
    
//    public synchronized void novoHospede(Hospede hospede) {
//        todosHospedes.add(hospede); // Adiciona o novo hóspede à lista
//        notifyAll(); // Notifica as recepcionistas que um novo hóspede chegou
//    }
    
    public synchronized boolean checkInRecepcionista(Hospede hospede) {
        for (Quarto quarto : quartos) {
            if (quarto.isVago()) {
                quarto.adicionarHospede(hospede, 1); // A recepcionista aloca o hóspede no quarto
                return true;
            }
        }
        return false; // Retorna false se não há quartos vagos
    }
    
    public synchronized boolean checkIn(Hospede hospede) {
        int membrosRestantes = hospede.getMembrosFamilia();
        while(membrosRestantes > 0) {
            for (Quarto quarto : quartos) {
                if (quarto.isVago()) {
//                    Se o quarto estiver vago, aloca um número máximo de 4 membros
//                    (ou menos, se houver menos membros restantes) para esse quarto.
                    int membrosAlocados = Math.min(membrosRestantes, 4);
                    System.out.println("Membros alocados: " + membrosAlocados);
                    quarto.adicionarHospede(hospede, membrosAlocados);

                    // System.out.println(quarto.getNumero());
                    quarto.adicionarHospede(hospede, membrosAlocados);
                    membrosRestantes -= membrosAlocados;
                    if(membrosRestantes <= 0) {
                        hospedesAtivos.incrementAndGet(); // Incrementar o contador de hóspedes ativos
                        return true;
                    }
                }
            }
            if (membrosRestantes > 0) {
                return false; // Há membros restantes, mas não há quartos suficientes disponíveis
            }
        }
        return true;
    }

    public synchronized void checkOut(Hospede hospede) {
        for (Quarto quarto : quartos) {
            if (quarto.getHospedes().contains(hospede)) {
                quarto.removerHospede(hospede);
                quarto.setChaveNaRecepcao(true); // Marcar a chave como na recepção para limpeza
                notifyAll(); // Notificar camareiras para limpeza do quarto
                break;
            }
        }
        if(hospedesAtivos.decrementAndGet() == 0) {
            System.out.println("Não há mais hospedes no hotel, portanto o sistema será encerrado");
            System.exit(0); // Método encerra a aplicação se não houver hóspedes ativos!
        }
    }

    public synchronized boolean adicionarFilaEspera(Hospede hospede) {
        return filaEspera.offer(hospede);
    }

    public synchronized Hospede proximoFilaEspera() {
        return filaEspera.poll();
    }

    // Método para encontrar o quarto de um hospede específico
//    public Quarto encontrarQuartoPorHospede(String nomeHospede) {
//        for (Quarto quarto : quartos) {
//            for (Hospede hospede : quarto.getHospedes()) {
//                if (hospede.getNome().equals(nomeHospede)) {
//                    return quarto;
//                }
//            }
//        }
//        return null; // Não encontrado
//    }
}
