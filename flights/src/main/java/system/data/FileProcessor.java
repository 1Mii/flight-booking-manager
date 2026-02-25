package system.data;

import java.io.IOException;
import java.util.List;

import system.utils.Flight;
import system.utils.ReservationRequest;

public class FileProcessor {
    public static Flight loadFlight(String filename) throws IOException {
        
        filename = "src/main/resources/FlightFiles/" + filename;

        if (!FlightFileParser.validate(filename)) {
            throw new IOException("Ficheiro de voo inválido");
        }
        Flight flight = FlightFileParser.parse(filename);

        if (ReservationFileParser.validate(filename)) {
            List<ReservationRequest> requests = ReservationFileParser.parse(filename);
            for (ReservationRequest req : requests) {
                flight.getReservationManager().addReservation(req);
            }
        }

        return flight;
    }
}
