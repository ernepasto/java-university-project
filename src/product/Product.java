package product;

/**
 * @version 1.0.0
 * @author Ernesto Pastori
 */
public class Product {
   
    private final String NAME;
    private final String SIZE;
    private float price;
    private final Color COLOR;
    private final Category CATEGORY;
    
    public Product(String name, String size, float price, Color color, Category category){
        this.NAME = name;
        this.SIZE = size;
        this.price = price;
        this.COLOR = color;
        this.CATEGORY = category;
    }
    
    /* Get methods ***********************************/
    public String getName(){
        return this.NAME;
    }
    public String getSize(){
        return this.SIZE;
    }
    public float getPrice(){
        return this.price;
    }
    public Color getColor(){
        return this.COLOR;
    }
    public Category getCategory(){
        return this.CATEGORY;
    }
    /*************************************************/
    
    /* Set methods ***********************************/
    public void setPrice(float price){
        this.price = price;
    }
    /*************************************************/
    
    /* Object methods overriding *********************/
    @Override
    public String toString(){
        String str = this.NAME + "/-/" + this.SIZE + "/-/" + this.price + "/-/" + this.COLOR + "/-/" + this.CATEGORY;
        return str;
    }
    /*************************************************/
    
    /* Make a product's stack on sale */
    public void applyDiscount(int discount){
        discount *= 10;
        this.price = this.price - (float) ((this.price * discount ) / 100);  
    }
    
}
