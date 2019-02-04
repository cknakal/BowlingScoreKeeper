import java.util.ArrayList;
import java.util.Stack;

public class Player {

    private String name;
    private Frame[] frames;
    private int currentScore;
    private ArrayList<Frame> strikeFrames;
    private ArrayList<Frame> spareFrames;

    public Player(String name) {
        this.name = name;
        this.frames = new Frame[10];
        for (int i = 0; i < frames.length - 1; i++) {
            this.frames[i] = new Frame(i + 1);
        }
        // Make the last frame a LastFame object
        this.frames[9] = new LastFrame();
        this.currentScore = 0;
        this.strikeFrames = new ArrayList<Frame>();
        this.spareFrames = new ArrayList<Frame>();
    }

    public String getName() {
        return this.name;
    }

    public Frame getFrame(int frame) {
        return this.frames[frame - 1];
    }

    public int getCurrentScore() {
        calcCurrentScore();
        return this.currentScore;
    }

    public boolean populateFrame(int frame, String firstShot, String secondShot) {
        if (frame == 10) {
            if(!populateFrame(frame, firstShot, secondShot, null)) {
                return false;
            }
        } else {
            Frame currentFrame = frames[frame - 1];
            if (currentFrame.setFrameShots(firstShot, secondShot)) {
                if (currentFrame.isStrike()) {
                    strikeFrames.add(currentFrame);
                }
                if (currentFrame.isSpare()) {
                    spareFrames.add(currentFrame);
                }

            } else {
                return false;
            }
        }
        if (!(frame == 1)) {
            updateSpareFrames(frame);
            updateStrikeFrames(frame);
        }
        return true;
    }

    public boolean populateFrame(int frame, String firstShot, String secondShot, String thirdShot) {
        // This should always be the last frame
        LastFrame lastFrame = (LastFrame) frames[frame - 1];

        boolean successfull = false;
        if (thirdShot == null) {
            successfull = lastFrame.setFrameShots(firstShot, secondShot);
        } else {
            successfull = lastFrame.setFrameShots(firstShot, secondShot, thirdShot);
        }

        if (successfull) {
            updateSpareFrames(frame);
            updateStrikeFrames(frame);
            return true;
        } else {
            return false;
        }
    }


    public void updateStrikeFrames(int currentFrameNumber) {
        // Use a stack so we don't delete from beginning of list while looping
        Stack<Integer> strikeFramesToRemove = new Stack<Integer>();
        Frame currentFrame = getFrame(currentFrameNumber);
        for (int i = 0; i < strikeFrames.size(); i++) {
            Frame strikeFrame = strikeFrames.get(i);
            // Is this the current frame?
            if (strikeFrame.getFrame() == currentFrameNumber) {
                continue;
            }
            // Is this strike frame the previous frame?
            if (strikeFrame.getFrame() == currentFrameNumber - 1) {
                // and the current frame isn't a strike
                if (!currentFrame.isStrike()) {
                    updateStrikeFrame(strikeFrame.getFrame(), currentFrame.getFirstShot(), currentFrame.getSecondShot());
                    strikeFramesToRemove.push(i);
                } else {
                    if (currentFrameNumber == 10) {
                        // Calculate if we're on the last frame
                        updateStrikeFrame(strikeFrame.getFrame(), currentFrame.getFirstShot(), currentFrame.getSecondShot());
                        strikeFramesToRemove.push(i);
                    } else {
                        // We'll calculate the strike frame later
                        continue;
                    }
                }

            } else {
                // Scored two strikes in a row
                if (currentFrameNumber > 2) {
                    Frame previousFrame = getFrame(currentFrameNumber - 1);
                    updateStrikeFrame(strikeFrame.getFrame(), previousFrame.getFirstShot(), currentFrame.getFirstShot());
                    strikeFramesToRemove.push(i);
                } else {
                    continue;
                }
            }
        }
        while (!strikeFramesToRemove.isEmpty()) {
            int frame = strikeFramesToRemove.pop();
            this.strikeFrames.remove(frame);
        }
    }

    public void updateStrikeFrame(int frame, String firstShot, String secondShot) {
        Frame strikeFrame = getFrame(frame);
        strikeFrame.setStrikeFrameScore(firstShot, secondShot);
    }

    public void updateSpareFrames(int currentFrameNumber) {
        Frame currentFrame = getFrame(currentFrameNumber);
        for (int i = 0; i < spareFrames.size(); i++) {
            if (spareFrames.get(i).getFrame() == currentFrameNumber - 1) {
                updateSpareFrame(spareFrames.get(i).getFrame(), currentFrame.getFirstShot());
                spareFrames.remove(i);
            }
        }
    }

    public void updateSpareFrame(int frame, String firstShot) {
        Frame spareFrame = getFrame(frame);
        spareFrame.setSpareFrameScore(firstShot);
    }

    private void calcCurrentScore() {
        this.currentScore = 0;
        for (Frame frame : frames) {
            if (frame.isFrameScoreCalculated()) {
                this.currentScore += frame.getFrameScore();
            }
        }
    }

}
