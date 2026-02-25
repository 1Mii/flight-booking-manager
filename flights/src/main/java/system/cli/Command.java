package system.cli;

import system.utils.FlightRegistry;

public interface Command {
    
    String execute(FlightRegistry registry) throws Exception;
    
    default boolean isExit() { return false; }
}