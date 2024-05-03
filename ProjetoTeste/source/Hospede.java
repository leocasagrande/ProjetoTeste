import java.util.Random;

public class Hospede extends Thread{
    private Hotel hotel;
    private String nome;
    private int membrosFamilia;

    public Hospede(Hotel hotel, String nome, int membrosFamilia) {
        this.hotel = hotel;
        this.nome = nome;
        this.membrosFamilia = membrosFamilia;
    }

    @Override
    	public void run() {
    	    Random random = new Random();
    	    // Adiciona o hóspede à fila de espera assim que ele chega
    	    if (!hotel.adicionarFilaEspera(this)) {
    	        System.out.println(nome + " deixou uma reclamação e foi embora.");
    	        return;
    	    }
    	    System.out.println(nome + " está esperando por um quarto.");

    	    boolean checkedIn = false;
    	    while (!checkedIn) {
    	        if (hotel.checkIn(this)) {
    	            checkedIn = true;
    	            System.out.println(nome + " fez check-in.");
    	        } else {
    	            try {
    	                Thread.sleep(random.nextInt(5000)); // Tempo aleatório para passear
    	            } catch (InterruptedException e) {
    	                e.printStackTrace();
    	            }
    	        }
    	    }
    	    try {
    	        Thread.sleep(random.nextInt(10000)); // Tempo aleatório para permanecer no quarto
    	    } catch (InterruptedException e) {
    	        e.printStackTrace();
    	    }
    	    hotel.checkOut(this);
    	    System.out.println(nome + " fez check-out.");
    	}

    public int getMembrosFamilia() {
        return membrosFamilia;
    }

    public String getNome() {
        return nome;
    }
}
