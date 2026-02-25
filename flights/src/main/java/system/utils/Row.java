package system.utils;

import java.util.ArrayList;
import java.util.List;

public class Row {

    private static final int MAX_SEATS_PER_ROW = 26; // 26 letras no alfabeto

    private List<Seat> seats;
    private RowState state;
    private int rowNumber;

    public Row(int num_seats, int rowNumber, SeatClasses seatClass) {
        if (num_seats > MAX_SEATS_PER_ROW)
            throw new IllegalArgumentException("Assentos a mais");

        this.seats = new ArrayList<>();
        this.rowNumber = rowNumber;
        this.state = RowState.AVAILABLE;
        for (int seat = 0; seat < num_seats; seat++) {
            seats.add(new Seat(this, rowNumber, SeatCode.ABC[seat], seatClass));
        }
    }

    public Seat getSeat(char seatLetter) {
        if (seatLetterBelongsToRow(seatLetter)) {
            return seats.get(Character.toUpperCase(seatLetter) - 'A');
        } else {
            throw new IllegalArgumentException("Seat " + seatLetter +
            " out of borders for row " + rowNumber + " (of size: " + seats.size() + ")");
        }
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public RowState getState() {
        return state;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void changeStateTo(RowState state) {
        this.state = state;
    }

    public int size() {
        return this.seats.size();
    }

    public int countFreeSeats() {
        int freeSeats = 0;
        for (Seat seat : seats) {
            if (seat.isFree()) freeSeats++;
        }
        return freeSeats;
    }

    private boolean seatLetterBelongsToRow(char seatLetter) {
        char upper = Character.toUpperCase(seatLetter);
        return upper >= 'A' && upper < (char)('A' + seats.size());
    }

    public List<Seat> reserveRow(int seatsNeeded, int id) {
        List<Seat> reservedSeats = new ArrayList<>();

        for (Seat seat : seats) {
            if (reservedSeats.size() >= seatsNeeded) break;
            if (seat.isFree()) {
                seat.reserveSeat(id);
                reservedSeats.add(seat);
            }
        }
    
        updateState();
        return reservedSeats;
    }

    public List<Seat> reserveRemainingSeats(int seatsNeeded, int id) {
        List<Seat> reservedSeats = new ArrayList<>();

        for (Seat seat : seats) {
            if (reservedSeats.size() >= seatsNeeded) break; 
            if (seat.isFree()) {
                seat.reserveSeat(id);
                reservedSeats.add(seat);
            }
        }

        updateState();

        return reservedSeats;
    }

    public void updateState() {
        int freeSeats = countFreeSeats();
        if (freeSeats == 0) {
            this.state = RowState.FULL;
        } else if (freeSeats < seats.size()) {
            this.state = RowState.SEMI;
        } else {
            this.state = RowState.AVAILABLE;
        }
    }
}
