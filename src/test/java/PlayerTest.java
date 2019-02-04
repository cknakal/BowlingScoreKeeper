import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    String name;
    Player player;

    @Before
    public void setup() {
        name = "Jane Doe";
        player = new Player(name);
    }

    @Test
    public void testPlayerName() {
        name = "Jane Doe";
        assertEquals(name, player.getName());
    }

    @Test
    public void testSingleFrame() {
        String firstShot = "5";
        String secondShot = "4";
        int frameScore = 9;
        int frame = 1;
        player.populateFrame(frame, firstShot, secondShot);
        Frame testFrame = player.getFrame(frame);
        assertEquals(firstShot, testFrame.getFirstShot());
        assertEquals(secondShot, testFrame.getSecondShot());
        assertEquals(frameScore, testFrame.getFrameScore());
    }

    @Test
    public void testLastFrame() {
        String firstShot = "X";
        String secondShot = "5";
        String thirdShot = "4";
        int frameScore = 19;
        int frame = 10;
        player.populateFrame(frame, firstShot, secondShot, thirdShot);
        LastFrame testFrame = (LastFrame) player.getFrame(frame);
        assertEquals(frame, testFrame.getFrame());
        assertEquals(firstShot, testFrame.getFirstShot());
        assertEquals(secondShot, testFrame.getSecondShot());
        assertEquals(thirdShot, testFrame.getThirdShot());
        assertEquals(frameScore, testFrame.getFrameScore());
    }

    @Test
    public void testSpareUpdate() {
        String firstShot = "7";
        String secondShot = "/";
        String thirdShot = "5";
        String fourthShot = "4";
        int firstFrame = 1;
        int secondFrame = 2;
        int firstFrameScore = 15;
        int secondFrameScore = 9;
        player.populateFrame(firstFrame, firstShot, secondShot);
        player.populateFrame(secondFrame, thirdShot, fourthShot);
        Frame frame1 = player.getFrame(firstFrame);
        Frame frame2 = player.getFrame(secondFrame);
        assertEquals(firstFrameScore, frame1.getFrameScore());
        assertEquals(secondFrameScore, frame2.getFrameScore());
    }

    @Test
    public void testFirstThreeFramesStrikes() {
        String firstShot = "X";
        String secondShot = "X";
        String thirdShot = "X";
        int firstFrame = 1;
        int secondFrame = 2;
        int thirdFrame = 3;
        int firstFrameScore = 30;
        player.populateFrame(firstFrame, firstShot, null);
        player.populateFrame(secondFrame, secondShot, null);
        player.populateFrame(thirdFrame, thirdShot, null);
        Frame frame1 = player.getFrame(firstFrame);
        Frame frame2 = player.getFrame(secondFrame);
        Frame frame3 = player.getFrame(thirdFrame);
        assertEquals(firstFrameScore, frame1.getFrameScore());
        assertEquals(false, frame2.isFrameScoreCalculated());
        assertEquals(false, frame3.isFrameScoreCalculated());
    }

    @Test
    public void testNextToLastStrikeFrame() {
        String firstShot = "X";
        String secondShot = "X";
        String thirdShot = "3";
        String fourthShot = "4";
        int ninthFrame = 9;
        int tenthFrame = 10;
        int ninthFrameScore = 23;
        int tenthFrameScore = 17;
        player.populateFrame(ninthFrame, firstShot, null);
        player.populateFrame(tenthFrame, secondShot, thirdShot, fourthShot);
        Frame frame9 = player.getFrame(ninthFrame);
        Frame frame10 = player.getFrame(tenthFrame);
        assertEquals(ninthFrameScore, frame9.getFrameScore());
        assertEquals(tenthFrameScore, frame10.getFrameScore());
    }


}
