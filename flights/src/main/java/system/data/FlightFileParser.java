package system.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import system.service.PlaneBuilder;
import system.service.ReservationManager;
import system.utils.Flight;
import system.utils.Plane;

public class FlightFileParser {

    public static boolean validate(String filename) throws IOException {
        List<String> linhas = Files.readAllLines(Paths.get(filename));
        if (linhas.isEmpty()) return false;

        String primeiraLinha = linhas.get(0).trim();
        if (!primeiraLinha.startsWith(">")) return false;

        String[] partes = primeiraLinha.substring(1).trim().split(" ");
        if (partes.length < 2) return false;

        
        String codigoVoo = partes[0];
        if (!codigoVoo.matches("[A-Z]{2}\\d{3,4}")) return false;

        
        for (int i = 1; i < partes.length; i++) {
            if (!partes[i].matches("\\d+x\\d+")) return false;
        }

        return true;
    }

    public static Flight parse(String filename) throws IOException {
        List<String> linhas = Files.readAllLines(Paths.get(filename));
        String primeiraLinha = linhas.get(0).trim();
        String[] partes = primeiraLinha.substring(1).trim().split(" ");

        String flightCode = partes[0];

        
        String[] seatsConfig = new String[partes.length - 1];
        System.arraycopy(partes, 1, seatsConfig, 0, seatsConfig.length);

        Plane plane = PlaneBuilder.defaultPlaneConfig(seatsConfig);
        ReservationManager manager = new ReservationManager(plane);
        Flight flight = new Flight(flightCode, plane, manager);

        return flight;
    }
}
