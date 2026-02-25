package system.cli;

import system.utils.Flight;
import system.utils.FlightRegistry;

public class CancelCommand implements Command {
    private final String reservationCode;

    public CancelCommand(String reservationCode) {
        this.reservationCode = reservationCode;
    }

    @Override
    public String execute(FlightRegistry registry) throws Exception {
        String[] parts = reservationCode.split(":");
        if (parts.length != 2) {
            return "Código de reserva inválido: " + reservationCode;
        }
        String flightCode = parts[0];
        int id;
        try {
            id = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return "Número de reserva inválido: " + parts[1];
        }

        Flight flight = registry.getFlight(flightCode);
        if (flight == null) return "Voo não encontrado: " + flightCode;

        boolean ok = flight.getReservationManager().cancelReservation(id);
        if (ok) return "Cancelar reserva: " + reservationCode;
        else return "Não foi possivel cancelar porque não existe reserva";
    }
}
