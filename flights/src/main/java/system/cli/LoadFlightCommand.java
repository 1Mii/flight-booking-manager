package system.cli;

import java.util.List;

import system.data.FlightFileParser;
import system.data.ReservationFileParser;
import system.utils.Flight;
import system.utils.FlightRegistry;
import system.utils.ReservationRequest;

public class LoadFlightCommand implements Command {
    private final String filename; 

    public LoadFlightCommand(String filename) {
        this.filename = filename;
    }

    private String pathFor(String filename) {
        
        return "src/main/resources/FlightFiles/" + filename;
    }

    @Override
    public String execute(FlightRegistry registry) throws Exception {
        String path = pathFor(filename);
        
        if (!FlightFileParser.validate(path)) {
            return "Deves dar um ficheiro correto";
        }

        Flight flight = FlightFileParser.parse(path);

        boolean added = registry.addFlight(flight);
        if (!added) {
            return "Já existe esse voo: " + flight.getId();
        }

        if (ReservationFileParser.validate(path)) {
            List<ReservationRequest> requests = ReservationFileParser.parse(path);
            for (ReservationRequest req : requests) {
                var res = flight.getReservationManager().addReservation(req);
                if (res == null) {
                    System.out.println("Não foi possível obter lugares para a reserva: "
                            + (req.getSeatClass() == null ? "?" : req.getSeatClass().toString()) + " " + req.getNumPassengers());
                }
            }
        }

        return flight.toString();

    }
}
