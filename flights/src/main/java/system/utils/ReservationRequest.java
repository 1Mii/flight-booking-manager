package system.utils;


public class ReservationRequest {

    private final SeatClasses seatClass;
    private final int numPassengers;

    public ReservationRequest(SeatClasses seatClass, int numPassengers) {
        this.seatClass = seatClass;
        this.numPassengers = numPassengers;
    }

    public SeatClasses getSeatClass() {
        return seatClass;
    }

    public int getNumPassengers() {
        return numPassengers;
    }
}
