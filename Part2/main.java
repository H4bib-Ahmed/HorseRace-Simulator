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
        
        Race race = new Race(20);

        // Create Horse objects
        Horse horse1 = new Horse('d', "PIPPI LONGSTOCKING", 0.8);
        Horse horse2 = new Horse('d', "KOKOMO", 0.5);
        Horse horse3 = new Horse('d', "EL JEFE", 0.6);

        // Add horses to the race
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);

        // Start the race
        System.out.println("Starting the race!");
        race.startRace();

    }

}
