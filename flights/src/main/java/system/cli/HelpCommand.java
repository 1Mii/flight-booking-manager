package system.cli;

import system.utils.FlightRegistry;

public class HelpCommand implements Command {
    @Override
    public String execute(FlightRegistry registry) {
        CommandMenu.showMenu();
        return null;
    }
}
