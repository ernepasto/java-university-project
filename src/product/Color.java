package product;

/**
 * @version 1.0.0
 * @author Ernesto Pastori
 */
public enum Color {
    RED, GREEN, YELLOW, WHITE, BLACK;

    public static void getColors() {
        System.out.println("You have to choose a color: " + RED.toString() + " " + GREEN.toString() + " " + YELLOW.toString() + " " + WHITE.toString() + " " + BLACK.toString());
    }
}
