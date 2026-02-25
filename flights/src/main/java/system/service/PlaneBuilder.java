package system.service;

import java.util.HashMap;
import java.util.Map;

import system.utils.Plane;
import system.utils.Row;
import system.utils.SeatClasses;

public class PlaneBuilder {

    public static Plane defaultPlaneConfig(String[] layout) {
        Map<SeatClasses, Row[]> map = new HashMap<>();

        int rowNumber = 1;

        // [ "3x2" , "4x2"]
        if (layout.length == 2) { 
            map.put(SeatClasses.EXECUTIVE, createRows(layout[0], SeatClasses.EXECUTIVE, rowNumber));
            rowNumber += parseNumRows(layout[0]);
            map.put(SeatClasses.TURIST, createRows(layout[1], SeatClasses.TURIST, rowNumber));
        // [ "3x2" ]
        } else { 
            map.put(SeatClasses.TURIST, createRows(layout[0], SeatClasses.TURIST, rowNumber));
        }

        return new Plane(map);
    }

    private static Row[] createRows(String config, SeatClasses seatClass, int startRow) {
        String[] parts = config.split("x");
        int numRows = Integer.parseInt(parts[0]);
        int seatsPerRow = Integer.parseInt(parts[1]);

        Row[] rows = new Row[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new Row(seatsPerRow, startRow + i, seatClass);
        }
        return rows;
    }

    private static int parseNumRows(String config) {
        return Integer.parseInt(config.split("x")[0]);
    }
}

