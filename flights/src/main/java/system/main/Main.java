package system.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import system.cli.Command;
import system.cli.CommandInterpreter;
import system.utils.FlightRegistry;

public class Main {

    public static void main(String[] args) {
        FlightRegistry flightRegistry = new FlightRegistry();
        CommandInterpreter interpreter = new CommandInterpreter();

        if (args != null && args.length > 0) {
            String commandsFile = args[0];
            try {
                List<String> lines = Files.readAllLines(Paths.get("src/main/resources/input-files/", commandsFile));
                for (String line : lines) {
                    processLine(line, interpreter, flightRegistry);
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler ficheiro de comandos: " + e.getMessage());
            }
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Escolha uma opção: (H para ajuda)");
            String input = sc.nextLine();
            if (input == null) continue;
            processLine(input, interpreter, flightRegistry);
        }
    }

    private static void processLine(String input, CommandInterpreter interpreter, FlightRegistry registry) {
        try {
            Command cmd = interpreter.parse(input);
            String output = cmd.execute(registry);
            if (output != null && !output.isEmpty()) System.out.println(output);
            if (cmd.isExit()) {
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
