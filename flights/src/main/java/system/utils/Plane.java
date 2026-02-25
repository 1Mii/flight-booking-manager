package system.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Plane {
    private Map<SeatClasses, Row[]> seats;

    public Plane(Map<SeatClasses, Row[]> seats) {
        this.seats = seats;
    }

    public Map<SeatClasses, Row[]> getSeats() {
        return seats;
    }

    public int getNumberOfRows() {
        int rowsTurists = seats.get(SeatClasses.TURIST).length;
        int rowsExecutive = 0;
        if (seats.get(SeatClasses.EXECUTIVE) != null) {
            rowsExecutive = seats.get(SeatClasses.EXECUTIVE).length;
        }

        return rowsExecutive + rowsTurists;
    }

    public int getNumberMaxOfSeatsPerRow() {
        if (seats.get(SeatClasses.EXECUTIVE) == null) {
            return seats.get(SeatClasses.TURIST)[0].size();
        } else {
            if (seats.get(SeatClasses.EXECUTIVE)[0].size() > seats.get(SeatClasses.TURIST)[0].size())
                return seats.get(SeatClasses.EXECUTIVE)[0].size();
            else return seats.get(SeatClasses.TURIST)[0].size();
        }
    }

    public Seat[][] getSeatsMatrix() {
        int maxSeatsPerRow = getNumberMaxOfSeatsPerRow();
        int numRows = getNumberOfRows();

        Seat[][] result = new Seat[maxSeatsPerRow][numRows];
    
        List<Row> allRows = new ArrayList<>();
        if (seats.get(SeatClasses.EXECUTIVE) != null) {
            allRows.addAll(Arrays.asList(seats.get(SeatClasses.EXECUTIVE)));
        }
        allRows.addAll(Arrays.asList(seats.get(SeatClasses.TURIST)));
    
        for (int rowIndex = 0; rowIndex < allRows.size() && rowIndex < numRows; rowIndex++) {
            Row row = allRows.get(rowIndex);
            List<Seat> rowSeats = row.getSeats();
    
            for (int seatIndex = 0; seatIndex < rowSeats.size(); seatIndex++) {
                result[seatIndex][rowIndex] = rowSeats.get(seatIndex);
            }
        }
    
        return result;
    }

    public int numberOfAvailableSeats(SeatClasses seatClass) {
        int availableSeats = 0;
        Row[] possibleSeats = seats.get(seatClass);
        if (possibleSeats == null) {
            return -1;
        }
        for (Row seatRow : possibleSeats) {
            for (Seat seat : seatRow.getSeats()) {

                if (seat.isFree()) availableSeats++;
            }
        }
        return availableSeats;
    }
}
