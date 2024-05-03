import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();

        // Iniciar threads de hóspedes
        for (int i = 0; i < 10; i++) {
            Hospede hospede = new Hospede(hotel, "Hóspede " + (i + 1), new Random().nextInt(6) + 1);
            hospede.start();
        }

        // Iniciar threads de camareiras
        for (Camareira camareira : hotel.camareiras) {
            camareira.start();
        }

        // Iniciar threads de recepcionistas
        for (Recepcionista recepcionista : hotel.recepcionistas) {
            recepcionista.start();
        }
    }
}
