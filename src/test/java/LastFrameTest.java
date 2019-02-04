import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class LastFrameTest {

    LastFrame lastFrame;

    @Before
    public void setup() {
        lastFrame = new LastFrame();
    }

    @Test
    public void testSettingTwoFrameShots() {
        String firstShot = "4";
        String secondShot = "3";
        lastFrame.setFrameShots(firstShot, secondShot);
        assertEquals(firstShot, lastFrame.getFirstShot());
        assertEquals(secondShot, lastFrame.getSecondShot());
        assertEquals(null, lastFrame.getThirdShot());
    }

    @Test
    public void testSpareThreeFrameShots() {
//        String firstShot = "X";
//        String secondShot = "5";
//        String thirdShot = "4";
        String firstShot = "3";
        String secondShot = "/";
        String thirdShot = "5";
        int frameScore = 15;
        lastFrame.setFrameShots(firstShot, secondShot, thirdShot);
        assertEquals(firstShot, lastFrame.getFirstShot());
        assertEquals(secondShot, lastFrame.getSecondShot());
        assertEquals(thirdShot, lastFrame.getThirdShot());
        assertEquals(frameScore, lastFrame.getFrameScore());
    }

    @Test
    public void testStrikeThreeFrameShots() {
        String firstShot = "X";
        String secondShot = "5";
        String thirdShot = "4";
        int frameScore = 19;
        lastFrame.setFrameShots(firstShot, secondShot, thirdShot);
        assertEquals(firstShot, lastFrame.getFirstShot());
        assertEquals(secondShot, lastFrame.getSecondShot());
        assertEquals(thirdShot, lastFrame.getThirdShot());
        assertEquals(frameScore, lastFrame.getFrameScore());
    }

    @Test
    public void testMinLastFrameScore() {
        int minScore = 0;
        lastFrame.setFrameShots("0", "0");
        assertEquals(minScore, lastFrame.getFrameScore());
    }

    @Test
    public void testMaxLastFrameScore() {
        int maxScore = 30;
        lastFrame.setFrameShots("X", "X", "X");
        assertEquals(maxScore, lastFrame.getFrameScore());
    }

    @Test
    public void testValidTwoShotLastFrameScore() {
        int score = 8;
        lastFrame.setFrameShots("4", "4");
        assertEquals(score, lastFrame.getFrameScore());
    }

    @Test
    public void testValidThreeShotLastFrameScore() {
        int score = 19;
        lastFrame.setFrameShots("X", "5", "4");
        assertEquals(score, lastFrame.getFrameScore());
    }

    @Test
    public void testInvalidTwoShotLastFrameScore() {
        assertFalse(lastFrame.setFrameShots(null, null));
        assertFalse(lastFrame.setFrameShots("8", "9"));
        // TODO: Throw exception 'last shot can't be null if first is `X`'.
        assertFalse(lastFrame.setFrameShots("X", "9"));
        assertFalse(lastFrame.setFrameShots("/", "9"));
        assertFalse(lastFrame.setFrameShots(null, "9"));
        assertFalse(lastFrame.setFrameShots("9", null));

    }

    @Test
    public void testInvalidThreeShotLastFrameScore() {
        assertFalse(lastFrame.setFrameShots("X", "X", null));
        assertFalse(lastFrame.setFrameShots("5", "/", null));
        assertFalse(lastFrame.setFrameShots("X", "/", "5"));
        assertFalse(lastFrame.setFrameShots("X", "5", "5"));
        assertFalse(lastFrame.setFrameShots("2", "2", "2"));
    }
}
