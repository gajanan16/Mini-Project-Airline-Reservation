
// Name : Gajanan Bhokare 
// Enrollment no. : EBEON1222719298
// Program for Airline Reservation System



package sqldemo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MiniProject {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/practice_database";
    private static final String USER = "root";
    private static final String PASSWORD = "Satara@123";

    public static void main(String[] args) {
    	Scanner ab = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // Connect to the database
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // Create a statement object
            stmt = conn.createStatement();

            // Display available flights
            System.out.println("Available flights:");
            String query = "SELECT * FROM flights";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("id") + " " + rs.getString("destination"));
            }

            // Prompt user for flight number and operation
            System.out.println("\nEnter flight number to book or cancel:");
            String flightNumber = ab.nextLine().toUpperCase();
            System.out.println("\nEnter operation (book or cancel):");
            String operation = ab.nextLine().toLowerCase();

            // Check if flight exists
            query = "SELECT * FROM flights WHERE id = " + flightNumber + "";
            rs = stmt.executeQuery(query);
            if (!rs.next()) {
                System.out.println("\nInvalid flight number.");
                return;
            }

            // Check if flight is already booked or cancelled
            boolean isBooked = rs.getBoolean("is_booked");
            boolean isCancelled = rs.getBoolean("is_cancelled");
            if (isCancelled) {
                System.out.println("\nThis flight has already been cancelled.");
                return;
            }

            // Book or cancel flight
            if (operation.equals("book")) {
                if (isBooked) {
                    System.out.println("\nThis flight has already been booked.");
                    return;
                }
                String update = "UPDATE flights SET is_booked = true WHERE id = '" + flightNumber + "'";
                stmt.executeUpdate(update);
                System.out.println("\nFlight " + flightNumber + " has been booked.");
            } else if (operation.equals("cancel")) {
                if (!isBooked) {
                    System.out.println("\nThis flight has not been booked yet.");
                    return;
                }
                String update = "UPDATE flights SET is_booked = false WHERE id = '" + flightNumber + "'";
                stmt.executeUpdate(update);
                System.out.println("\nFlight " + flightNumber + " has been cancelled.");
            } else {
                System.out.println("\nInvalid operation.");
            }

            // Display booking details
            query = "SELECT * FROM flights WHERE id = '" + flightNumber + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("\nBooking details:");
                System.out.println("Flight number: " + rs.getString("id"));
                System.out.println("Destination: " + rs.getString("destination"));
                System.out.println("Departure time: " + rs.getString("departure_time"));
                System.out.println("Arrival time: " + rs.getString("arrival_time"));
                System.out.println("Price: " + rs.getDouble("price"));
                System.out.println("Booked: " + rs.getBoolean("is_booked"));
                System.out.println("Cancelled: " + rs.getBoolean("is_cancelled"));
            }

            // Close the result set, statement, and connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

   
