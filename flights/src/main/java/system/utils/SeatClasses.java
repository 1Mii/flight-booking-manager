package system.utils;

import java.util.Arrays;
import java.util.List;

public enum SeatClasses {
        EXECUTIVE {
        @Override
        public String toString() {
            return "E";
        }
    },
    TURIST {
        @Override
        public String toString() {
            return "T";
        }
    };   

    public static List<SeatClasses> listOfSeatClasses() {
        return Arrays.asList(SeatClasses.values());
    }

    public static SeatClasses convertFromChar(char resClassChar) {
        char c = Character.toUpperCase(resClassChar);
        if (c == 'E') return EXECUTIVE;
        else if (c == 'T') return TURIST;
        else throw new IllegalArgumentException("Invalid seat class char: " + resClassChar);
    }
}
