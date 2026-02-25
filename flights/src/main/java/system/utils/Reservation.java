package system.utils;

import java.util.List;

public class Reservation {

    private int id;
    private SeatClasses resClass;
    private List<Seat> reservedSeats;

    public Reservation(int id, SeatClasses resClass, List<Seat> ReservedSeats) {
        this.resClass = resClass;
        this.reservedSeats = ReservedSeats;
        this.id = id;
    }

    public SeatClasses getReservationClass() {
        return resClass;
    }

    public int getNumberOfPassangers() {
        return reservedSeats.size();
    }

    public List<Seat> getReservedSeats() {
        return reservedSeats;
    }

    public int getReservationID() {
        return id;
    }
}
