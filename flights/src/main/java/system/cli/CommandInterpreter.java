package system.cli;

import system.utils.SeatClasses;

public class CommandInterpreter {

    public Command parse(String input) throws Exception {
        if (input == null) throw new Exception("Comando vazio");
        String trimmed = input.trim();
        if (trimmed.isEmpty()) throw new Exception("Comando vazio");

        String[] tokens = trimmed.split("\\s+");
        String cmd = tokens[0].toUpperCase();

        try {
            switch (cmd) {
                case "H":
                    return new HelpCommand();

                case "Q":
                    return new QuitCommand();

                case "I":
                    if (tokens.length != 2) throw new Exception("Sintaxe: I <filename>");
                    return new LoadFlightCommand(tokens[1]);

                case "M":
                    if (tokens.length != 2) throw new Exception("Sintaxe: M <flight_code>");
                    return new ShowMapCommand(tokens[1]);

                case "F":
                    if (tokens.length != 3 && tokens.length != 4) throw new Exception("Sintaxe: F <flight_code> [Ex] <Tur>");
                    String code = tokens[1];
                    if (tokens.length == 4) {
                        return new CreateFlightCommand(code, tokens[2], tokens[3]);
                    } else {
                        return new CreateFlightCommand(code, null, tokens[2]);
                    }

                case "R":
                    if (tokens.length != 4) throw new Exception("Sintaxe: R <flight_code> <T/E> <num>");
                    String flight = tokens[1];
                    char cls = tokens[2].charAt(0);
                    SeatClasses sc = SeatClasses.convertFromChar(cls);
                    if (sc == null) throw new Exception("Classe inválida: " + tokens[2]);
                    int n = Integer.parseInt(tokens[3]);
                    return new ReserveCommand(flight, sc, n);

                case "C":
                    if (tokens.length != 2) throw new Exception("Sintaxe: C <flight_code>:<res_num>");
                    return new CancelCommand(tokens[1]);

                default:
                    throw new Exception("Comando inválido: " + cmd);
            }
        } catch (NumberFormatException e) {
            throw new Exception("Número inválido: " + e.getMessage());
        }
    }
}
