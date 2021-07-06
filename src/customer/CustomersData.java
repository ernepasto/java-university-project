package customer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import product.Product;
import product.ProductsData;

/**
 * @version 1.0.0
 * @author Ernesto Pastori
 */
public final class CustomersData {

    private static HashSet<Customer> customers;
    private static final String SEPARATOR = "/-/";
    private static final String FILEPATH = "config/customers_data.txt";
    
    public CustomersData() {
        this.customers = new HashSet<Customer>();
        if (!this.readDataFromDB()){
           this.createDBFile();
        }
    }

    /**
     *
     * @param customer
     * @return If this set already contains the element, the call leaves the set
     * unchanged and returns false
     */
    public boolean addCustomer(Customer customer, boolean save) {
        boolean res = this.customers.add(customer);
        if (save && res) this.saveData();
        return res;
    }

    /**
     *
     * @param name
     * @param surname
     * @param email
     * @param password
     * @return If this set already contains the element, the call leaves the set
     * unchanged and returns null, else return the customer added
     */
    public Customer addCustomer(String name, String surname, String email, String password, boolean save) {
        Customer customer = new Customer(name, surname, email, password);
        if (this.customers.add(customer)) {
            if (save) this.saveData();
            return customer;
        } else {
            return null;
        }
    }
    
    /**
     *
     * @param name
     * @param surname
     * @param email
     * @param password
     * @param balance
     * @return If this set already contains the element, the call leaves the set
     * unchanged and returns null, else return the customer added
     */
    public Customer addCustomer(String name, String surname, String email, String password, float balance, boolean save) {
        Customer customer = new Customer(name, surname, email, password, balance);
        if (this.customers.add(customer)) {
            if (save) this.saveData();
            return customer;
        } else {
            return null;
        }
    }

    /**
     *
     * @param customer
     * @return Removes the specified element from this set if it is present, returns true if this set contained the element
     */
    public boolean removeCustomer(Customer customer) {
        boolean res = this.customers.remove(customer);
        if (res) this.saveData();
        return res;
    }

    /**
     *
     * @param email
     * @return Removes the specified element from this set if it is present returns true if this set contained the element
     */
    public boolean removeCustomer(String email) {
        Iterator iterator = this.customers.iterator();
        Customer customer = this.selectCustomer(email);
        if (customer == null) return false;
        boolean res = this.customers.remove(customer);
        if (res) this.saveData();
        return res;
    }

    /**
     *
     * @param email
     * @param password
     * @return Returns true if this set contains the specified element
     */
    public boolean verifyCustomer(String email, String password) {
        Iterator iterator = this.customers.iterator();
        Customer customer = null;
        while (iterator.hasNext()) {
            customer = (Customer) iterator.next();
            if (customer.getEmail().equalsIgnoreCase(email)) {
                if (customer.getPassword().equals(password)) {
                    return true;
                } else {
                    System.err.println("La password non è corretta.");
                    return false;
                }
            }
        }
        System.err.println("Utente non registrato.");
        return false;
    }
    
    /**
     * 
     * @param email
     * @return Returns the customer with the same email in input esle it returns null
     */
    public Customer selectCustomer(String email){
        Iterator iterator = this.customers.iterator();
        Customer customer = null;
        while (iterator.hasNext()) {
            customer = (Customer) iterator.next();
            if (customer.getEmail().equalsIgnoreCase(email)) {
                return customer;
            }
        }
        return null;
    }
    
    /**
     * 
     * @param customer
     * @param key
     * @return Return true if the costumer has been bought the product
     */
    public boolean buyProduct(Customer customer, Integer key) {
        ProductsData productsDB = new ProductsData();
        Iterator iterator = this.customers.iterator();
        Customer c = null;
        while (iterator.hasNext()) {
            c = (Customer) iterator.next();
            if (c.getEmail().equalsIgnoreCase(customer.getEmail())) {
                Product product = productsDB.getAProduct(key);
                if (!c.buy(product.getPrice())) return false;
                productsDB.removeProduct(key);
                this.saveData();
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @param email
     * @param money
     * @return Returns true if the customer has been updated
     */
    public boolean depositMoney(String email, float money) {
        Iterator iterator = this.customers.iterator();
        Customer customer = null;
        while (iterator.hasNext()) {
            customer = (Customer) iterator.next();
            if (customer.getEmail().equalsIgnoreCase(email)) {
                customer.deposit(money);
                this.saveData();
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @param email
     * @param name
     * @param surname
     * @param password
     * @return Return true if the customer has been updated
     */
    public boolean updateCustomer(String email, String name, String surname, String password) {
        Iterator iterator = this.customers.iterator();
        Customer customer = null;
        while (iterator.hasNext()) {
            customer = (Customer) iterator.next();
            if (customer.getEmail().equalsIgnoreCase(email)) {
                customer.setName(name);
                customer.setSurname(surname);
                customer.setPassword(password);
                this.saveData();
                return true;
            }
        }
        return false;
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
            Iterator iterator = this.customers.iterator();
            Customer customer = null;
            while (iterator.hasNext()) {
                customer = (Customer) iterator.next();
                fw.write(customer.toString() + "\n");
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
                        String[] data = line.split(this.SEPARATOR);
                        this.addCustomer(data[0], data[1], data[2], data[3], Float.parseFloat(data[4]), false);
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
