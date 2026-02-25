package system.service;

import java.util.ArrayList;
import java.util.List;

import system.utils.Plane;
import system.utils.Reservation;
import system.utils.ReservationRequest;
import system.utils.Row;
import system.utils.RowState;
import system.utils.Seat;
import system.utils.SeatClasses;

public class ReservationManager {
    
    private int ID_counter = 1;
    private List<Reservation> reservations;
    private Plane managedPlane;

    public ReservationManager(Plane managedPlane) {
        this.reservations = new ArrayList<>();
        this.managedPlane = managedPlane;
    }

    public Reservation addReservation(ReservationRequest request) {
        SeatClasses seatClass = request.getSeatClass();
        int numPassengers = request.getNumPassengers();

        if (managedPlane.getSeats().get(seatClass) == null) throw new IllegalArgumentException("Assentos nessa classe nao existem");

        if (managedPlane.numberOfAvailableSeats(seatClass) < numPassengers) {
            return null;
        }

        List<Seat> assignedSeats = assignSeats(seatClass, numPassengers);

        Reservation reservation = new Reservation(ID_counter++, seatClass, assignedSeats);
        reservations.add(reservation);
        return reservation;
    }

    private List<Seat> assignSeats(SeatClasses seatClass, int numPassengers) {
        List<Seat> assignedSeats = new ArrayList<>();
        Row[] rows = managedPlane.getSeats().get(seatClass);
        int seatsAssigned = 0;

        for (Row row : rows) {
            if (seatsAssigned >= numPassengers) break;
            if (row.getState() == RowState.FULL) continue;
            
            if (seatsAssigned != 0 && row.getState() == RowState.SEMI) { 
                List<Seat> seats = row.reserveRemainingSeats(numPassengers - seatsAssigned, ID_counter);
                assignedSeats.addAll(seats);
                seatsAssigned += seats.size();
                continue;
            }
            
            if (row.getState() == RowState.AVAILABLE) {
                List<Seat> seats = row.reserveRow(numPassengers - seatsAssigned, ID_counter);
                assignedSeats.addAll(seats);
                seatsAssigned += seats.size();
                continue;
            }
        }

        for (Row row : rows) {
            if (seatsAssigned >= numPassengers) break;
            if (row.getState() == RowState.FULL) continue;

            List<Seat> seats = row.reserveRemainingSeats(numPassengers - seatsAssigned, ID_counter);
            assignedSeats.addAll(seats);
            seatsAssigned += seats.size();
        }

        return assignedSeats;
    }

    public Reservation getReservation(int id) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationID() == id) {
                return reservation;
            }
        }
        return null;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public boolean cancelReservation(int reservationNum) {
        Reservation reservation = getReservation(reservationNum);
        if (reservation == null) {
            return false;
        }

        for (Seat seat : reservation.getReservedSeats()) {
            seat.releaseSeat();
            seat.getRow().updateState();
        }

        reservations.remove(reservations.indexOf(reservation));
        return true;

    }

}
