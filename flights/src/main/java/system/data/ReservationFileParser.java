package system.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import system.utils.ReservationRequest;
import system.utils.SeatClasses;

public class ReservationFileParser {

    public static boolean validate(String filename) throws IOException {
        List<String> linhas = Files.readAllLines(Paths.get(filename));
        if (linhas.size() <= 1) return false;

        for (int i = 1; i < linhas.size(); i++) {
            String linha = linhas.get(i).trim();
            if (!linha.matches("[ET] \\d+")) {
                return false;
            }
        }

        return true;
    }

    public static List<ReservationRequest> parse(String filename) throws IOException {
        List<String> linhas = Files.readAllLines(Paths.get(filename));
        List<ReservationRequest> requests = new ArrayList<>();

        for (int i = 1; i < linhas.size(); i++) {
            String linha = linhas.get(i).trim();
            String[] parts = linha.split(" ");
            char seatClassChar = parts[0].charAt(0);
            int numPassengers = Integer.parseInt(parts[1]);

            SeatClasses seatClass = SeatClasses.convertFromChar(seatClassChar);
            if (seatClass != null) {
                requests.add(new ReservationRequest(seatClass, numPassengers));
            }
        }

        return requests;
    }
}
