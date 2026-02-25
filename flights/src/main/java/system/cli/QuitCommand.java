package system.cli;

import system.utils.FlightRegistry;

public class QuitCommand implements Command {
    @Override
    public String execute(FlightRegistry registry) {
        return "Saindo do programa...";
    }

    @Override
    public boolean isExit() { return true; }
}
