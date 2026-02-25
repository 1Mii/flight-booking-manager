package system.cli;

import system.utils.Flight;
import system.utils.FlightRegistry;

public class ShowMapCommand implements Command {

    private final String flightCode;

    public ShowMapCommand(String flightCode) {
        this.flightCode = flightCode;
    }

    @Override
    public String execute(FlightRegistry registry) throws Exception {
        Flight flight = registry.getFlight(flightCode);
        if (flight == null) {
            return "Voo não encontrado: " + flightCode;
        }
        flight.showReserveMap(); 
        return null;
    }
}
