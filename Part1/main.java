/**
 * Main class to run the horse race simulation.
 * This class initializes the Race and Horse objects, sets up the race, and starts it.
 * 
 * @author Habib Ahmed
 * @version 1.0
 */
public class main {
    public static void main(String[] args) {
        // Create a new Race with a length of 100 meters.
        Race race = new Race(30);

        // Create Horse objects
        Horse horse1 = new Horse('♘', "PIPPI LONGSTOCKING", 0.6);
        Horse horse2 = new Horse('♞', "KOKOMO", 0.7);
        Horse horse3 = new Horse('♞', "EL JEFE", 0.5);

        // Add horses to the race
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);

        // Start the race
        System.out.println("Starting the race!");
        race.startRace();
    }
}
