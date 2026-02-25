package system.utils;

import java.util.HashMap;
import java.util.Map;

public class FlightRegistry {
    
    private Map<String, Flight> flights;

    public FlightRegistry() {
        this.flights = new HashMap<>();
    }

    public boolean addFlight(Flight f) {
        if (flights.containsKey(f.getId())) {
            System.out.println("Já existe esse voo");
            return false;
        }
        flights.put(f.getId(), f);
        return true;
    }

    public Flight getFlight(String code) {
        return flights.get(code);
    }
}
