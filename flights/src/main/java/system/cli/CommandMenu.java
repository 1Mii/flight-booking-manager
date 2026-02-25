package system.cli;

public class CommandMenu {

    public static void showMenu() {
        System.out.println("===== MENU DE OPÇÕES =====");
        System.out.println("H                       : Exibe este menu de ajuda");
        System.out.println("I <filename.txt>        : Lê um ficheiro de voo");
        System.out.println("M <flight_code>         : Mostra o mapa de reservas de um voo");
        System.out.println("F <flight_code> [Ex] <Tur> : Acrescenta um novo voo (Exec opcional, Tur obrigatória)");
        System.out.println("R <flight_code> <T/E> <num_lugares> : Acrescenta uma nova reserva");
        System.out.println("C <flight_code>:<res_num> : Cancela uma reserva");
        System.out.println("Q                       : Termina o programa");
        System.out.println("==========================");
    }
}
