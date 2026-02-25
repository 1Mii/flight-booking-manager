package system.cli;

import system.service.PlaneBuilder;
import system.service.ReservationManager;
import system.utils.Flight;
import system.utils.FlightRegistry;
import system.utils.Plane;

public class CreateFlightCommand implements Command {

    private final String flightCode;
    private final String execConfig;
    private final String touristConfig;

    public CreateFlightCommand(String flightCode, String execConfig, String touristConfig) {
        this.flightCode = flightCode;
        this.execConfig = execConfig;
        this.touristConfig = touristConfig;
    }

    @Override
    public String execute(FlightRegistry registry) throws Exception {
        try {
            String[] configs;
            if (execConfig != null) {
                configs = new String[] { execConfig, touristConfig };
            } else {
                configs = new String[] { touristConfig };
            }
            Plane plane = PlaneBuilder.defaultPlaneConfig(configs);
            ReservationManager manager = new ReservationManager(plane);
            Flight flight = new Flight(flightCode, plane, manager);

            if (!registry.addFlight(flight)) {
                return "Já existe esse voo: " + flightCode;
            }
            return flight.toString();
        } catch (Exception e) {
            throw new Exception("Erro ao criar voo: " + e.getMessage(), e);
        }
    }
}
