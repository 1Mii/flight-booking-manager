package system.cli;

import java.util.List;

import system.utils.Flight;
import system.utils.FlightRegistry;
import system.utils.Reservation;
import system.utils.ReservationRequest;
import system.utils.Seat;
import system.utils.SeatClasses;

public class ReserveCommand implements Command {
    private final String flightCode;
    private final SeatClasses seatClass;
    private final int numSeats;

    public ReserveCommand(String flightCode, SeatClasses seatClass, int numSeats) {
        this.flightCode = flightCode;
        this.seatClass = seatClass;
        this.numSeats = numSeats;
    }

    @Override
    public String execute(FlightRegistry registry) throws Exception {
        Flight flight = registry.getFlight(flightCode);
        if (flight == null) {
            return "Voo não encontrado: " + flightCode;
        }
        ReservationRequest req = new ReservationRequest(seatClass, numSeats);
        Reservation res = flight.getReservationManager().addReservation(req);
        if (res == null) {
            
            return "Não foi possível obter lugares para a reserva: " + seatClass.toString() + " " + numSeats;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s:%d = ", flightCode, res.getReservationID()));
        List<Seat> seats = res.getReservedSeats();
        for (int i = 0; i < seats.size(); i++) {
            Seat s = seats.get(i);
            sb.append(String.format("%d%s", s.getRow(), s.getLetter()));
            if (i < seats.size() - 1) sb.append(" | ");
        }
        return sb.toString();
    }
}