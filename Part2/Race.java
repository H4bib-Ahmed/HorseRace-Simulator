import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 1.0
 */

public class Race {
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;

    public Race(int distance) {
        this.raceLength = distance;
        this.lane1Horse = null;
        this.lane2Horse = null;
        this.lane3Horse = null;
    }

    public void addHorse(Horse theHorse, int laneNumber) {
        if (laneNumber == 1) {
            lane1Horse = theHorse;
        } else if (laneNumber == 2) {
            lane2Horse = theHorse;
        } else if (laneNumber == 3) {
            lane3Horse = theHorse;
        } else {
            System.out.println("Invalid lane number: " + laneNumber + ". Please use lanes 1, 2, or 3.");
        }
    }

    

    public void startRace() {
        boolean finished = false;
        lane1Horse.goBackToStart();
        lane2Horse.goBackToStart();
        lane3Horse.goBackToStart();

        while (!finished) {
            moveHorse(lane1Horse);
            moveHorse(lane2Horse);
            moveHorse(lane3Horse);

            printRace();

            
            if (raceWonBy(lane1Horse)) {
                increaseConfidence(lane1Horse); // Increase the confidence immediately
                printRace(); 
                System.out.println("Winner: " + lane1Horse.getName());
                finished = true;
            } else if (raceWonBy(lane2Horse)) {
                increaseConfidence(lane2Horse);
                printRace(); 
                System.out.println("Winner: " + lane2Horse.getName());
                finished = true;
            } else if (raceWonBy(lane3Horse)) {
                increaseConfidence(lane3Horse);
                printRace(); 
                System.out.println("Winner: " + lane3Horse.getName());
                finished = true;
            }else if (allHorsesHaveFallen()) { // Check if all horses have fallen
                finished = true;
                System.out.println("All horses have fallen. Race is over.");
            }

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Race interrupted.");
                Thread.currentThread().interrupt();
            }
        }

    }

    private boolean allHorsesHaveFallen() {
        return (lane1Horse != null && lane1Horse.hasFallen()) &&
               (lane2Horse != null && lane2Horse.hasFallen()) &&
               (lane3Horse != null && lane3Horse.hasFallen());
    }

    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
                decreaseConfidence(theHorse);
            }
        }
    }

    private void increaseConfidence(Horse horse) {
        double newConfidence = horse.getConfidence() + 0.1;
        if (newConfidence > 1.0) {
            newConfidence = 1.0; // Cap the confidence at 1.0
        }
        horse.setConfidence(newConfidence); // Update the horse's confidence
    }

    private void decreaseConfidence(Horse horse) {
        double newConfidence = horse.getConfidence() - 0.1;
        if (newConfidence < 0) {
            newConfidence = 0; // Ensure confidence doesn't go below 0
        }
        horse.setConfidence(newConfidence); // Update the horse's confidence
    }

    private boolean raceWonBy(Horse theHorse) {
        return theHorse != null && theHorse.getDistanceTravelled() >= raceLength;
    }


    private void printHorseDetail(Horse horse) {
        System.out.printf("Horse %s (%s): Confidence Level = %.1f\n",
                          horse.getName(), horse.getSymbol(), horse.getConfidence());
    }

    private void printLaneWithDetails(Horse horse) {
        printLane(horse); // This prints the lane itself.
        printHorseDetail(horse); // This prints the horse's detail beside the lane.
    }
    

    private void printRace()
    {
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();
        
        printLaneWithDetails(lane1Horse);
        printLaneWithDetails(lane2Horse);
        printLaneWithDetails(lane3Horse);
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();
            
    }

    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('\u2322');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');
    }
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }
}
