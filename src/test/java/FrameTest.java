import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(value = BlockJUnit4ClassRunner.class)
public class FrameTest {

    Frame frame;

    @Before
    public void setup() {
        frame = new Frame(1);
    }

    @Test
    public void testMinFrameScore() {
        int minScore = 0;
        frame.setFrameShots("0", "0");
        assertEquals(minScore, frame.getFrameScore());
    }

    @Test
    public void testMaxFrameScore() {
        int maxScore = 30;
        frame.setStrikeFrameScore("X", "X");
        assertEquals(maxScore, frame.getFrameScore());
    }

    @Test
    public void testValidFrameScore() {
        int score = 9;
        frame.setFrameShots("3", "6");
        assertEquals(score, frame.getFrameScore());

    }

    //@Test(expected = IllegalStateException.class)
    @Test
    public void testInvalidFrameScore() {
        //TODO: Check that the correct exception message is shown
        int score = 0;
        frame.setFrameShots("3", "7");
        assertEquals(score, frame.getFrameScore());
    }

    @Test
    public void testMinSpare() {
        int minScore = 10;
        frame.setSpareFrameScore("0");
        assertEquals(minScore, frame.getFrameScore());
    }

    @Test
    public void testMaxSpare() {
        int maxScore = 20;
        frame.setSpareFrameScore("X");
        assertEquals(maxScore, frame.getFrameScore());
    }

    @Test
    public void testValidSpare() {
        int score = 15;
        frame.setSpareFrameScore("5");
        assertEquals(score, frame.getFrameScore());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSpare() {
        int score = 0;
        frame.setSpareFrameScore("/");
        assertEquals(score, frame.getFrameScore());
    }

    @Test
    public void testMinStrike() {
        int minScore = 10;
        frame.setStrikeFrameScore("0", "0");
        assertEquals(minScore, frame.getFrameScore());
    }

    @Test
    public void testMaxStrike() {
        int score = 30;
        frame.setStrikeFrameScore("X", "X");
        assertEquals(score, frame.getFrameScore());

    }

    @Test
    public void testValidStrike() {
        int score = 17;
        frame.setStrikeFrameScore("3", "4");
        assertEquals(score, frame.getFrameScore());
    }

    @Test
    public void testValidStrikeWithSpare() {
        int score = 20;
        frame.setStrikeFrameScore("9", "/");
        assertEquals(score, frame.getFrameScore());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidStrike() {
        int score = 0;
        frame.setStrikeFrameScore("X", "/");
        assertEquals(score, frame.getFrameScore());
    }
}
