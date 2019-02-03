import java.util.Arrays;

public class Frame {
    protected int frame;
    protected String firstShot;
    protected String secondShot;
    protected boolean strike;
    protected boolean spare;
    protected int frameScore;

    public Frame(int frame) {
        this.frame = frame;
    }

    public int getFrame() {
        return this.frame;
    }

    public boolean setFrameShots(String firstShot, String secondShot) {
        if (!checkValidInputs(firstShot, secondShot)) {
            return false;
        }

        this.firstShot = firstShot;
        this.secondShot = secondShot;
        if (firstShot == "X") {
            strike = true;
        } else if (secondShot == "/") {
            spare = true;
        }
        if (!strike && !spare) {
            setOpenFrameScore();
        }
        return true;
    }

    public String getFirstShot() {
        if (strike) {
            return "X";
        } else {
            return firstShot;
        }
    }

    public String getSecondShot() {
        if (spare) {
            return "/";
        } else {
            return secondShot;
        }
    }

    private void setOpenFrameScore() {
        this.frameScore = Integer.parseInt(firstShot) + Integer.parseInt(secondShot);
    }

    public boolean setStrikeFrameScore(String firstShot, String secondShot) {
        if (firstShot == "X") {
            if (secondShot == "/") {
                throw new IllegalArgumentException("Second argument can't be '/' when first is 'X'.");
            } else if (secondShot == "X") {
                this.frameScore = 30;
            } else {
                // TODO: Make sure secondShot is an integer
                this.frameScore = 20 + Integer.parseInt(secondShot);
            }
            return true;
        }
        isValidShotCombination(firstShot, secondShot);
        if (secondShot == "/") {
            this.frameScore = 20;
            return true;
        }
        this.frameScore = 10 + Integer.parseInt(firstShot) + Integer.parseInt(secondShot);
        return true;
    }

    public boolean setSpareFrameScore(String firstShot) throws IllegalArgumentException{
        // TODO: Refactor. Doesn't follow expected flow of application.
        if (firstShot == "/") {
            throw new IllegalArgumentException("First argument can't be '/'");
        }
        if (firstShot == "X") {
            this.frameScore = 20;
        } else {
            this.frameScore = 10 + Integer.parseInt(firstShot);
        }
        return true;
    }

    public int getFrameScore() {
        return frameScore;
    }

    public String toString() {
        String first = getFirstShot() == null ? " " : getFirstShot();
        String second = getSecondShot() == null ? " " : getSecondShot();
        String total = getFrameScore() == 0 ? " " : Integer.toString(getFrameScore());
        return String.format("----%d----", getFrame()) +
               "---------" +
               String.format("| %d | %d |", first, second) +
               String.format("|   %d   |", total) +
               "---------";
    }

    protected boolean isValidShot(String shot) throws Exception {
        if (shot == null) {
            throw new Exception("Shot can't be null");
        }
        String[] validStrings = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "/", "X"};
        if (!Arrays.asList(validStrings).contains(shot)) {
            throw new Exception("Invalid shot value. Please try again.");
        } else {
            return true;
        }
    }

    protected boolean isValidShotCombination(String firstShot, String secondShot) throws IllegalArgumentException, IllegalStateException {
        // Check valid strike and spare combinations
        if (firstShot == "X" && secondShot != null) {
            throw new IllegalArgumentException("Second shot must be null when first is 'X'.");
        }
        if (secondShot == "X") {
            throw new IllegalArgumentException("Second shot can't be 'X'.");
        }
        if (firstShot == "/") {
            throw new IllegalArgumentException("First shot can't be '/'");
        }

        isValidNumericalCombination(firstShot, secondShot);

        return true;
    }

    private boolean checkValidInputs(String firstShot, String secondShot) {
        try {
            isValidShot(firstShot);
            isValidShot(secondShot);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        try {
            isValidShotCombination(firstShot, secondShot);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    protected boolean isValidNumericalCombination(String firstShot, String secondShot) throws IllegalStateException {
        int first = Integer.parseInt(firstShot);
        int second;
        if (secondShot == "/") {
            // Give a default valid value
            second = 0;
        } else {
            second = Integer.parseInt(secondShot);
        }

        // Check valid numerical combinations
        int total = first + second;

        // Redundant checks
        if (first < 0 || first > 9) {
            return false;
        }
        if (second < 0 || second > 9) {
            return false;
        }

        if (total < 0 || total > 9) {
            throw new IllegalStateException("Shot combination total must be between 0 - 9 when using integers.");
        }

        return true;
    }
}
