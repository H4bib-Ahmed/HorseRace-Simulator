import java.util.*;

public class BettingSystem {
    private Map<String, Double> odds; // Betting odds for each horse
    private List<Bet> bettingHistory; // Track user betting history
    private int virtualCurrency; // User's virtual currency

    public BettingSystem() {
        odds = new HashMap<>();
        bettingHistory = new ArrayList<>();
        virtualCurrency = 1000; // Starting balance
    }

    public Map<String, Double> getOdds() {
        return odds;
    }

    public boolean placeBet(String horseName, int amount) {
        if (virtualCurrency >= amount) {
            virtualCurrency -= amount; // Deduct from user balance
            bettingHistory.add(new Bet(horseName, amount));
            return true;
        }
        return false; // Insufficient virtual currency
    }

    public int getVirtualCurrency() {
        return virtualCurrency;
    }

    public void processBetOutcome(String horseName, boolean won, double odds) {
        for (Bet bet : bettingHistory) {
            if (!bet.isProcessed() && bet.getHorseName().equals(horseName)) {
                bet.setProcessed(true); // Mark the bet as processed
                if (won) {
                    int winnings = (int) (bet.getAmount() * odds);
                    virtualCurrency += winnings; // Add winnings to user balance
                }
            }
        }
    }
}

// Representing a single bet
class Bet {
    private String horseName;
    private int amount; // Bet amount
    private boolean processed; // Whether the bet has been processed

    public Bet(String horseName, int amount) {
        this.horseName = horseName;
        this.amount = amount;
        this.processed = false; // Initially not processed
    }

    public String getHorseName() {
        return horseName;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}


