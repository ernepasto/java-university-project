package product;

/**
 * @version 1.0.0
 * @author Ernesto Pastori
 */
public enum Category {
    SWEATSHIRT, TSHIRT, HAT, TROUSERS;

    public static void getCategories() {
        System.out.println("You have to choose a category: " + SWEATSHIRT.toString() + " " + TSHIRT.toString() + " " + HAT.toString() + " " + TROUSERS.toString());
    }
}
