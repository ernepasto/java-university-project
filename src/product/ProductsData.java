package product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * @version 1.0.0
 * @author Ernesto Pastori
 */
public class ProductsData {
    
    private static HashMap<Integer, Product> products;
    private static final String SEPARATOR = "/-/";
    private static final String FILEPATH = "config/products_data.txt";
    
    public ProductsData() {
        this.products = new HashMap<Integer, Product>();
        if (!this.readDataFromDB()){
           this.createDBFile();
        }
    }
    
    /**
     * 
     * @param key
     * @return Returns a product search by a key 
     */
    public Product getAProduct(Integer key){
        return this.products.get(key);
    }
    
    /**
     * 
     * @param product
     * @return Returns true if product has been added
     */
    public boolean addProduct(Product product, boolean save) {
        Integer key;
        boolean check = false, res = false;
        do {
            key = (int) (Math.random()* 10001);
            check = this.products.containsKey(key);
        } while(check);
        this.products.put(key, product);
        res = this.products.containsKey(key);
        if (res && save) this.saveData();
        return res;
    }
    
    /**
     * 
     * @param name
     * @param size
     * @param price
     * @param color
     * @param category
     * @return Returns true if product has been added
     */
    public boolean addProduct(String name, String size, float price, Color color, Category category, boolean save) {
        Product product = new Product(name, size, price, color, category);
        Integer key;
        boolean check = false, res = false;
        do {
            key = (int) (Math.random()* 10001);
            check = this.products.containsKey(key);
        } while(check);
        this.products.put(key, product);
        res = this.products.containsKey(key);
        if (res && save) this.saveData();
        return res;
    }
    
    /**
     * 
     * @param name
     * @param size
     * @param price
     * @param color
     * @param category
     * @return Returns true if product has been added
     */
    public boolean addProduct(String name, String size, float price, Color color, Category category, Integer key, boolean save) {
        Product product = new Product(name, size, price, color, category);
        this.products.put(key, product);
        boolean res = this.products.containsKey(key);
        if (res && save) this.saveData();
        return res;
    }
    
    /**
     * 
     * @param key
     * @return Returns true if the product has been removed
     */
    public boolean removeProduct(Integer key) {
        boolean res = false;
        Product product = this.products.get(key);
        res = this.products.remove(key, product);
        if (res) this.saveData();
        return res;
    }
    
    /**
     * 
     * @param key
     * @param product
     * @return Returns true if the product has been removed
     */
    public boolean removeProduct(Integer key, Product product) {
        boolean res = this.products.remove(key, product);
        if (res) this.saveData();
        return res;
    }
    
    /**
     * 
     * @param key
     * @param product
     * @return Returns true if the product has been replaced
     */
    public boolean replaceProduct(Integer key, Product product) {
        boolean res = false;
        if (this.products.containsKey(key)) res = true;
        this.products.replace(key, product);
        if (res) this.saveData();
        return res;
    }
    
    /**
     * 
     * @param key
     * @param discount
     * @return Returns true if the product is now on sale
     */
    public boolean applyDiscount(Integer key, int discount){
        boolean res = false;
        res = this.products.containsKey(key);
        Product product = null;
        product = this.products.get(key);
        product.applyDiscount(discount);
        if (!res) return false;
        Iterator<Map.Entry<Integer, Product>> iterator = this.products.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Product> entry = iterator.next();
            if (entry.getKey() == key) entry.setValue(product);
        }
        if (res) this.saveData();
        return true;
    }
    
    /**
     * 
     * @return Returns the size of the products actually in the shop in the shop
     */
    public int getSize() {
        return this.products.size();
    }
    
    /**
     * 
     * @return Returns true if the shop is empty
     */
    public boolean emptyCheck() {
        return this.products.isEmpty();
    }
    
    /* To print all products avaible actually in the shop */
    public void printProducts() {
        Iterator<Map.Entry<Integer, Product>> iterator = this.products.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Product> entry = iterator.next();
            System.out.println(entry.getKey() + ":" + entry.getValue().getName());
        }
    }
    
    /**
     * 
     * @return Returns true if the file exists
     */
    private boolean saveData() {
        File file = new File(this.FILEPATH);
        if (!file.exists()) return false;
        if (!file.canWrite()) return false;
        try {
            FileWriter fw = new FileWriter(file);
            Iterator<Map.Entry<Integer, Product>> iterator = this.products.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Product> entry = iterator.next();
                fw.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
            fw.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
        return true;
    }
    
    /*
    * Creates a new file if it doesn't exist
    */
    private void createDBFile(){
        File folder = new File("config");
        File file = new File(this.FILEPATH);
        try {
            if (!folder.exists()) folder.mkdirs();
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            fw.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    /**
     * 
     * @return Returns true if the data has been read
     */
    private boolean readDataFromDB () {
        File file = new File(this.FILEPATH);
        if (!file.exists()) return false;
        if (!file.canRead()) return false;
        Scanner scanner;
        try {
            scanner = new Scanner(file);
                if (scanner.hasNext()) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] data = line.split(":");
                        Integer key = Integer.parseInt(data[0]);
                        String[] info = data[1].split(this.SEPARATOR);
                        this.addProduct(info[0], info[1], Float.parseFloat(info[2]), Color.valueOf(info[3]), Category.valueOf(info[4]), key, false);
                    }
                }
            scanner.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
            return false;
        }
    }
    
}
