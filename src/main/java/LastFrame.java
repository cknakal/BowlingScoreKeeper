

public class LastFrame extends Frame{

    private String thirdShot;

    public LastFrame() {
        super(10);
    }

    public String getThirdShot() {
        return this.thirdShot;
    }

    public boolean setFrameShots(String firstShot, String secondShot, String thirdShot) {
        try {
            isValidShot(firstShot);
            isValidShot(secondShot);
            isValidShot(thirdShot);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        try {
            if (!firstShot.equals("X") && !secondShot.equals("X") && !secondShot.equals("/") && thirdShot == null) {
                isValidShotCombination(firstShot, secondShot);
            } else {
                isValidShotCombination(firstShot, secondShot, thirdShot);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        this.firstShot = firstShot;
        this.secondShot = secondShot;
        if (thirdShot == null) {
            setFrameScore(this.firstShot, this.secondShot, "0");

        } else {
            this.thirdShot = thirdShot;
            setFrameScore(this.firstShot, this.secondShot, this.thirdShot);
        }
        checkStrikeOrSpare();
        return true;
    }

    private void setFrameScore(String firstShot, String secondShot, String thirdShot) {
        int first = setIntValue(firstShot);
        int second;
        int third;
        if (secondShot.equals("/")) {
            second = 10 - first;
            third = setIntValue(thirdShot);
        } else if (thirdShot.equals("/")) {
            second = setIntValue(secondShot);
            third = 10 - second;
        } else {
            second = setIntValue(secondShot);
            third = setIntValue(thirdShot);
        }
        this.frameScore = first + second + third;
        this.frameScoreCalculated = true;
    }

    private int setIntValue(String shot) {
        if (shot.equals("X")) {
            return 10;
        } else {
            return Integer.parseInt(shot);
        }
    }

    private boolean isValidShotCombination(String firstShot, String secondShot, String thirdShot) throws IllegalArgumentException {
        if (!firstShot.equals("X") && !secondShot.equals("/") && thirdShot != null) {
            throw new IllegalArgumentException("Can't have a third shot if you haven't scored a spare or strike.");
        }
        if (firstShot.equals("/")) {
            throw new IllegalArgumentException("First shot can't be '/'");
        }
        if (firstShot.equals("X")) {
            if (secondShot.equals("/")) {
                throw new IllegalArgumentException("If first shot is 'X', second shot can't be '/'.");
            } else if (thirdShot == null) {
                throw new IllegalArgumentException("If first shot is 'X', third shot can't be null.");
            } else if (!secondShot.equals("X")) {
                isValidNumericalCombination(secondShot, thirdShot);
            }
        }
        if (secondShot.equals("/")) {
            if (thirdShot == null) {
                throw new IllegalArgumentException("If first shot is '/', third shot can't be null.");
            }
        }

        return true;
    }

    private void checkStrikeOrSpare() {
        if (this.firstShot.equals("X") || this.secondShot.equals("X") || this.thirdShot.equals("X")) {
            this.strike = true;
        }
        if (this.secondShot.equals("/") || this.thirdShot.equals("/")) {
            this.spare = true;
        }
    }

    public int getFrameScore() {
        return this.frameScore;
    }
}
