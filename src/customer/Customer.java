package customer;

import java.util.Objects;

/**
 * @version 1.0.0
 * @author Ernesto Pastori
 */
public class Customer {
    
    private String name;
    private String surname;
    private final String EMAIL;
    private String password;
    private float balance;
    
    public Customer (String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.EMAIL = email;
        this.password = password;
        this.balance = 0;
    }
    
    public Customer (String name, String surname, String email, String password, float balance) {
        this.name = name;
        this.surname = surname;
        this.EMAIL = email;
        this.password = password;
        this.balance = balance;
    }
    
    /* Get methods ***********************************/
    public String getName() {
        return this.name;
    }
    public String getSurname() {
        return this.surname;
    }
    public String getEmail() {
        return this.EMAIL;
    }
    public String getPassword() {
        return this.password;
    }
    public float getBalance() {
        return this.balance;
    }
    /*************************************************/
    
    /* Set methods ***********************************/
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    /*************************************************/
    
    /* Object methods overriding *********************/
    // Two customers are equal if their email's are equal
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return this.EMAIL.equals(customer.EMAIL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.EMAIL);
    }
    
    @Override
    public String toString(){
        String str = this.name + "/-/" + this.surname + "/-/" + this.EMAIL + "/-/" + this.password + "/-/" + this.balance;
        return str;
    }
    /*************************************************/
    
    /**
     * 
     * @param money 
     * Adds money on customer's balance
     */
    public void deposit(float money){
        this.balance = this.balance + money;
    }
  
    /**
     * 
     * @param money
     * @return Returns true if the operation is possible else returns false
     */
    public boolean buy(float money){
        if ((this.balance - money) < 0) {
            return false;
        } else {
            this.balance = this.balance - money;
            return true;
        }
    }
}
