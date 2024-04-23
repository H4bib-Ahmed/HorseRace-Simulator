public class horsetest {
    public static void main(String[] args) {
        Horse testHorse = new Horse('d', "Test Horse", 0.75);
        System.out.println("\nTesting Invalid Confidence...");
        testHorse.setConfidence(1.5);
        System.out.println("Expected Confidence (unchanged): 0.75, Actual Confidence: " + testHorse.getConfidence());
    }
}
