

public class LastFrame extends Frame{

    private String thirdShot;

    public LastFrame() {
        super(10);
    }

    public String getThirdShot() {
        if (strike) {
            return "X";
        } else if (spare) {
            return "/";
        } else {
            return this.thirdShot;
        }
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
            if (thirdShot == null) {
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
        this.thirdShot = thirdShot;
        setFrameScore(this.firstShot, this.secondShot, this.thirdShot);
        return true;
    }

    private void setFrameScore(String firstShot, String secondShot, String thirdShot) {
        int first = setIntValue(firstShot);
        int second;
        int third;
        if (secondShot == "/" || thirdShot == "/") {
            // total will equal 10, so set one variable to 10 and the other to 0
            second = 10;
            third = 0;
        } else {
            second = setIntValue(secondShot);
            third = setIntValue(thirdShot);
        }
        this.frameScore = first + second + third;
    }

    private int setIntValue(String shot) {
        if (shot == "X") {
            return 10;
        } else {
            return Integer.parseInt(shot);
        }
    }

    private boolean isValidShotCombination(String firstShot, String secondShot, String thirdShot) throws IllegalArgumentException {
        if (firstShot != "X" && secondShot != "/" && thirdShot != null) {
            throw new IllegalArgumentException("Can't have a third shot if you haven't scored a spare or strike.");
        }
        if (firstShot == "/") {
            throw new IllegalArgumentException("First shot can't be '/'");
        }
        if (firstShot == "X") {
            if (secondShot == "/") {
                throw new IllegalArgumentException("If first shot is 'X', second shot can't be '/'.");
            } else if (secondShot != "X") {
                isValidNumericalCombination(secondShot, thirdShot);
            }
        }

        return true;
    }
}
