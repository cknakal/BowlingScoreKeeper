import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.setOut;

public class Game {

    int MAX_FRAMES = 10;
    int currentFrame;
    Player winner;
    ArrayList<Player> players;

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }

    public Game() {
        this.currentFrame = 1;
        this.players = new ArrayList<Player>();
    }

    public void startGame() {
        // Get players;
        while(true) {
            System.out.print("Please enter a player's name (enter 'done' if all players have been entered): ");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine();
            if (name.equals("done")) {
                playGame();
            } else {
                Player player = new Player(name);
                players.add(player);
                System.out.println("Created new player: " + player.getName() + ".");
            }
        }
    }

    public void playGame() {
        for (int frame = 1; frame <= MAX_FRAMES; frame++) {
            for (Player player : players) {
                System.out.println("The current frame is: " + frame + ".");
                while (true) {
                    System.out.print("Please enter what " + player.getName() + " scored for frame " + frame + ": ");
                    Scanner scanner = new Scanner(System.in);
                    String[] shots = tokenizeInput(scanner.nextLine());
                    if ((shots.length > 2 && frame != 10) || (shots.length > 3 && frame == 10)) {
                        System.out.println("You entered too many shots. Please try again.");
                        continue;
                    } else if (frame == 10 && shots.length < 2) {
                        System.out.println("You entered too few shots for the last shot. Please try again.");
                    } else {
                        String firstShot = shots[0];
                        String secondShot = null;
                        String thirdShot = null;
                        if (shots.length == 1) {
                            if (!firstShot.equals("X")) {
                                System.out.println("You can only enter 'X' as a single shot in a frame. Please try again.");
                                continue;
                            }
                        } else if (shots.length == 2) {
                            secondShot = shots[1];
                        } else if (shots.length == 3) {
                            secondShot = shots[1];
                            thirdShot = shots[2];
                        }
                        if (frame == 10) {
                            if (player.populateFrame(frame, firstShot, secondShot, thirdShot)) {

                            } else {
                                System.out.println("You entered invalid shots. Please try again");
                                continue;
                            }
                        } else {
                            if (player.populateFrame(frame, firstShot, secondShot)) {

                            } else {
                                System.out.println("You entered invalid shots. Please try again");
                                continue;
                            }
                        }
                        break;
                    }
                }
                System.out.println(player.getName() + " current score is: " + player.getCurrentScore() + ".\n");
            }
        }
        endGame();
    }

    public void endGame() {
        Player winner = null;
        System.out.println("\nThe game is over!");
        for (Player player : players) {
            System.out.println("Final score for " + player.getName() + " is: " + player.getCurrentScore());
            if (winner == null || winner.getCurrentScore() < player.getCurrentScore()) {
                winner = player;
            }
        }
        System.out.println("\nThe winner of the game is " + winner.getName() + " with a score of " + winner.getCurrentScore() + ".");
        exit(0);
    }

    private String[] tokenizeInput(String input) {
        return input.split(",");
    }
}
