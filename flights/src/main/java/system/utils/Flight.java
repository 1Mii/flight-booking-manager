package system.utils;

import system.service.ReservationManager;

public class Flight {

    private String id;
    private Plane plane;
    private ReservationManager manager;

    public Flight(String id, Plane plane, ReservationManager manager) {
        this.id = id;
        this.plane = plane;
        this.manager = manager;
    }

    public Plane getPlane() {
        return plane;
    }

    public String getId() {
        return id;
    }

    public ReservationManager getReservationManager() {
        return manager;
    }

    public void showReserveMap() {
        Seat[][] seatsMatrix = plane.getSeatsMatrix();
    
        System.out.printf(" "); 
        for (int col = 1; col <= seatsMatrix[0].length; col++) {
            System.out.printf("%3d", col);
        }
        System.out.println();
    
        for (int lin = 0; lin < seatsMatrix.length; lin++) {
            System.out.printf("%s", SeatCode.ABC[lin]); 
            for (int col = 0; col < seatsMatrix[0].length; col++) {
                Seat seat = seatsMatrix[lin][col];
                if (seat != null) {
                    System.out.printf("%3d", seat.getReservationID());
                } else {
                    System.out.printf("%3s", " "); 
                }
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        if (plane.numberOfAvailableSeats(SeatClasses.EXECUTIVE) != -1){
            return "Código de voo " + this.id + ". Lugares disponíveis:" + "\n\t" +
            plane.numberOfAvailableSeats(SeatClasses.EXECUTIVE) + " lugares em classe Executiva;" + "\n\t" +
            plane.numberOfAvailableSeats(SeatClasses.TURIST) + " lugares em classe Turística.";
        } else {
            return "Código de voo " + this.id + ". Lugares disponíveis:" + "\n\t" +
            plane.numberOfAvailableSeats(SeatClasses.TURIST) + " lugares em classe Turística." + "\n\t" +
            "Classe executiva não disponível neste voo";
        }
    }
}
