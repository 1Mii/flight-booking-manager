package system.utils;

public class Seat {
    private char letter;
    private int rowNumber;
    private int reservationID;
    private Row row;
    private SeatClasses seatClass;

    public Seat(Row row, int rowNumber, char letter, SeatClasses seatClass) {
        this.rowNumber = rowNumber;
        this.row = row;
        this.letter = letter;
        this.reservationID = 0;
        this.seatClass = seatClass;
    }

    public void reserveSeat(int reservationNumber) {
        if (this.isFree()) {
            this.reservationID = reservationNumber;
        } else {
            throw new IllegalArgumentException("This Seat is already reserved. Info:\n\n" + this.toString());
        }
        
    }

    public void releaseSeat() {
        if (!this.isFree())
            this.reservationID = 0;
        else
            throw new IllegalArgumentException("This Seat isn't reserved. Info:\n\n" + this.toString());

    }

    public boolean isFree() {
        return this.reservationID == 0;

    }

    
    @Override
    public String toString() {
        return "Seat [letter=" + letter + ", row=" + row + ", reservationID=" + reservationID + ", seatClass="
                + seatClass + "]";
    }

    public SeatClasses getSeatClass() {
        return this.seatClass;
    }

    public char getLetter() {
        return letter;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public Row getRow() {
        return row;
    }

    public int getReservationID() {
        return reservationID;
    }
}
