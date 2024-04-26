
/**
 * Write a description of class Horse here.
 * 
 * @author Habib Ahmed 
 * @version (a version number or a date)
 */
public class Horse {
    private String name;
    private char symbol;
    private int distanceTravelled;
    private boolean hasFallen;
    private double confidence;

    public Horse(char horseSymbol, String horseName, double horseConfidence) {
        this.symbol = horseSymbol;
        this.name = horseName;
        this.confidence = horseConfidence;
        this.distanceTravelled = 0;
        this.hasFallen = false;
    }


    public void fall() {
        this.hasFallen = true;
    }

    public double getConfidence() {
        return this.confidence;
    }

    public int getDistanceTravelled() {
        return this.distanceTravelled;
    }

    public String getName() {
        return this.name;
    }

    public char getSymbol() {
        return this.symbol;
    }

    public void goBackToStart() {
        this.distanceTravelled = 0;
        this.hasFallen = false;
    }

    public boolean hasFallen() {
        return this.hasFallen;
    }

    public void moveForward() {
        if (!this.hasFallen) {
            this.distanceTravelled++;
        }
    }

    public void setConfidence(double newConfidence) {
        if (newConfidence >= 0 && newConfidence <= 1) {
            this.confidence = newConfidence;
        }
    }
    
    public void setSymbol(char newSymbol) {
        this.symbol = newSymbol;
    }

    public void setName(String newName) {
        this.name = newName;
    }
}
