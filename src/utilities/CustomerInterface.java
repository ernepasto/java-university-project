package utilities;

import customer.Customer;
import customer.CustomersData;
import java.util.Scanner;
import product.Category;
import product.Color;
import product.Product;
import product.ProductsData;

/**
 * @version 1.0.0
 * @author Ernesto Pastori
 */
public final class CustomerInterface {

    private final ProductsData productsDB = new ProductsData();
    private final CustomersData customersDB = new CustomersData();
    private final String adminPassword = "Admin";

    public CustomerInterface() {
        this.selection();
    }

    public void selection() {
        boolean loop = true;
        String choice = null;
        Customer customer = null;
        Scanner scanner = new Scanner(System.in);
        while(loop) {
            printContext("Select an action (write a letter)");
            System.out.println("- LOGIN [L]");
            System.out.println("- REGISTER [R]");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();
            switch (choice) {
                case "L":
                    customer = this.login();
                    if (customer != null) {
                        this.actions(customer);
                    }
                    loop = false;
                    break;
                case "R":
                    customer = this.register();
                    if (customer != null) {
                        this.actions(customer);
                    }
                    loop = false;
                    break;
                default:
                    System.out.println("You have to enter L or R");
            }
        }
    }

    /* Action that a customer can do */
    public void actions(Customer customer) {
        String choice = null;
        Integer key = null;
        Scanner scanner = new Scanner(System.in);
        boolean end = false;
        do {
            printContext("Select an action (write a letter)");
            System.out.println("- SEE PRODUCTS [S]"
                    + "- SEE A PRODUCT INFO [A]"
                    + "- BUY A PRODUCT [B]"
                    + "- GET ACCOUNT INFO [I]"
                    + "- UPDATE ACCOUNT [U]"
                    + "- ADD MONEY [M]"
                    + "- REMOVE ACCOUNT [R]"
                    + "- PRESS X TO EXIT [X]");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();
            switch (choice) {
                case "S":
                    /* If > 0 products are in the shop, show to the customer */
                    this.printContext("Products in the shop");
                    if (this.productsDB.emptyCheck()) {
                        System.out.println("The shop now is empty");
                    } else {
                        System.out.println("There are " + this.productsDB.getSize() + " products in the shop.");
                        this.productsDB.printProducts();
                    }
                    break;
                case "A":
                    /* See infomation about a product */
                    try {
                        System.out.println("Insert the id of the product: ");
                        System.out.print("Id: ");
                        key = scanner.nextInt();
                        Product product = this.productsDB.getAProduct(key);
                        if (product == null) {
                            System.err.println("Product not found.");
                        } else {
                            System.out.println("Name: " + product.getName()
                                    + " Size: " + product.getSize()
                                    + " Price: " + product.getPrice()
                                    + " Color: " + product.getColor()
                                    + " Category: " + product.getCategory());
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                    break;
                case "B":
                    /* Buy a product with a customer */
                    try {
                        System.out.println("Insert the id of the product: ");
                        System.out.print("Id: ");
                        key = scanner.nextInt();
                        if (this.customersDB.buyProduct(customer, key)) {
                            System.out.println("You have been bought the product!");
                        } else {
                            System.err.println("Error: you can't buy this product or the id is wrong!");
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                    break;
                case "I":
                    /* Print info about a customer */
                    this.printContext("Customer information");
                    System.out.println("NAME: " + customer.getName());
                    System.out.println("SURNAME: " + customer.getSurname());
                    System.out.println("EMAIL: " + customer.getEmail());
                    System.out.println("BALANCE: " + customer.getBalance());
                    break;
                case "U":
                    /* Update a customer information */
                    String email = null,
                     name = null,
                     surname = null,
                     password = null;
                    System.out.println("Insert the information required:");
                    System.out.print("name: ");
                    name = scanner.nextLine();
                    System.out.print("surname: ");
                    surname = scanner.nextLine();
                    System.out.print("password: ");
                    password = scanner.nextLine();
                    if (this.customersDB.updateCustomer(customer.getEmail(), name, surname, password)) {
                        System.out.println("Your account has been updated.");
                    } else {
                        System.err.println("Error 309");
                    }
                    break;
                case "M":
                    /* Add money to a customer */
                    float money = 0;
                    try {
                        System.out.print("Insert the amount of money to add: ");
                        money = scanner.nextFloat();
                        if (this.customersDB.depositMoney(customer.getEmail(), money)) {
                            System.out.println("Your account has been updated.");
                            /* Print info about a customer */
                            this.printContext("Customer information");
                            System.out.println("BALANCE: " + customer.getBalance());
                        } else {
                            System.err.println("Error 310");
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                    break;
                case "R":
                    /* Remove the current account */
                    if (this.customersDB.removeCustomer(customer.getEmail())) {
                        System.err.println("Your account has been removed.");
                        System.err.println("Exit form the shop program...");
                        end = true;
                    } else {
                        System.err.println("Error 311");
                    }
                    break;
                case "X":
                    end = true;
                    System.err.println("Exit form the menu.");
                    break;
                default:
                    System.err.println("You have to enter a valid letter.");
            }
        } while (!end);
    }

    /* The admin's actions */
    private void adminActions() {
        String choice = null,
                name = null,
                size = null,
                color = null,
                category = null;
        float price = 0;
        Integer key = null;
        Scanner scanner = new Scanner(System.in);
        boolean end = false;
        do {
            printContext("Select an action (write a letter)");
            System.out.println("- SEE PRODUCTS [S]"
                    + "- SEE A PRODUCT INFO [I]"
                    + "- ADD PRODUCT [A]"
                    + "- REMOVE A PRODUCT [R]"
                    + "- SALE A PRODUCT [D]"
                    + "- REPLACE A PRODUCT [P]"
                    + "- PRESS X TO EXIT [X]");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();
            switch (choice) {
                case "S":
                    /* If > 0 products are in the shop, show to the admin */
                    this.printContext("Products in the shop");
                    if (this.productsDB.emptyCheck()) {
                        System.out.println("The shop now is empty");
                    } else {
                        System.out.println("There are " + this.productsDB.getSize() + " products in the shop.");
                        this.productsDB.printProducts();
                    }
                    break;
                case "I":
                    /* See infomation about a product */
                    try {
                        System.out.println("Insert the id of the product: ");
                        System.out.print("Id: ");
                        key = scanner.nextInt();
                        Product product = this.productsDB.getAProduct(key);
                        if (product == null) {
                            System.err.println("Product not found.");
                        } else {
                            System.out.println("Name: " + product.getName()
                                    + " Size: " + product.getSize()
                                    + " Price: " + product.getPrice()
                                    + " Color: " + product.getColor()
                                    + " Category: " + product.getCategory());
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                    break;
                case "A":
                    /* Add new product in the DB */
                    try {
                        System.out.println("Insert the information required: ");
                        System.out.print("name: ");
                        name = scanner.nextLine();
                        System.out.print("size: ");
                        size = scanner.nextLine();
                        System.out.print("price: ");
                        price = scanner.nextFloat();
                        Color.getColors();
                        System.out.print("color: ");
                        scanner.nextLine(); //Workaround for scanner bug @see https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo
                        color = scanner.nextLine();
                        Category.getCategories();
                        System.out.print("category: ");
                        category = scanner.nextLine();
                        if (this.productsDB.addProduct(name, size, price, Color.valueOf(color.toUpperCase()), Category.valueOf(category.toUpperCase()), true)) {
                            System.out.println("The product has been added in the shop.");
                        } else {
                            System.err.println("Error 409");
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                    break;
                case "R":
                    /* Remove e product form db */
                    try {
                        System.out.println("Insert the id of the product to remove");
                        System.out.print("Id: ");
                        key = scanner.nextInt();
                        if (this.productsDB.removeProduct(key)) {
                            System.out.println("The product has been removed.");
                            this.printContext("Products in the shop");
                            if (this.productsDB.emptyCheck()) {
                                System.out.println("The shop now is empty");
                            } else {
                                System.out.println("There are " + this.productsDB.getSize() + " products in the shop.");
                                this.productsDB.printProducts();
                            }
                        } else {
                            System.err.println("Error: the id of the product is wrong!");
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                    break;
                case "D":
                    /* Make a product on sale */
                    int discount = 0;
                    try {
                        System.out.println("Insert the id of the product to select");
                        System.out.print("Id: ");
                        key = scanner.nextInt();
                        System.out.println("Insert the disocunt to apply [1 = 10%; 2 = 20%, 3 = 30% ...]");
                        discount = scanner.nextInt();
                        if (discount <10 || discount > 1) {
                            System.err.println("The value of the siscount is not correct.");
                            if (this.productsDB.applyDiscount(key, discount)) {
                                System.out.println("The product has been removed.");
                                this.printContext("Products in the shop");
                                if (this.productsDB.emptyCheck()) {
                                    System.out.println("The shop now is empty");
                                } else {
                                    System.out.println("There are " + this.productsDB.getSize() + " products in the shop.");
                                    this.productsDB.printProducts();
                                }
                            } else {
                                System.err.println("Error: the id of the product is wrong!");
                            }
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                    break;
                case "P":
                    /* Replace a product in the db */
                    try {
                        System.out.println("Insert the id of the product to replace");
                        System.out.print("Id: ");
                        key = scanner.nextInt();
                        System.out.println("Insert the information required: ");
                        System.out.print("name: ");
                        scanner.nextLine(); //Workaround for scanner bug @see https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo
                        name = scanner.nextLine();
                        System.out.print("size: ");
                        size = scanner.nextLine();
                        System.out.print("price: ");
                        price = scanner.nextFloat();
                        Color.getColors();
                        System.out.print("color: ");
                        scanner.nextLine(); //Workaround for scanner bug @see https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo
                        color = scanner.nextLine();
                        Category.getCategories();
                        System.out.print("category: ");
                        category = scanner.nextLine();
                        Product product = new Product(name, size, price, Color.valueOf(color.toUpperCase()), Category.valueOf(category.toUpperCase()));
                        if (this.productsDB.replaceProduct(key, product)) {
                            System.out.println("The product has been added in the shop.");
                        } else {
                            System.err.println("Error: the id of the product is wrong!");
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                    break;
                case "X":
                    end = true;
                    System.err.println("Exit form the menu.");
                    break;
                default:
                    System.err.println("You have to enter a valid letter.");
            }
        } while (!end);
    }

    /* Make the login for a customer */
    private Customer login() {
        printContext("Login form");
        Customer customer = null;
        Scanner scanner = new Scanner(System.in);
        boolean logged = false;
        String email = null, password = null;
        do {
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            System.out.print("Enter your password: ");
            password = scanner.nextLine();
            if (email.equals("Admin") && password.equals("Admin")) {
                this.adminActions();
                return null;
            } else {
                logged = this.customersDB.verifyCustomer(email, password);
                if (!logged) {
                    if (this.printExit()) {
                        return null;
                    }
                }
            }
        } while (!logged);
        System.err.println("You are logged in.");
        customer = this.customersDB.selectCustomer(email);
        return customer;
    }

    /* Register a customer */
    private Customer register() {
        printContext("Register form");
        Customer customer = null;
        Scanner scanner = new Scanner(System.in);
        String name = null, surname = null, email = null, password = null;
        do {
            System.out.print("Enter your name: ");
            name = scanner.nextLine();
            System.out.print("Enter your surname: ");
            surname = scanner.nextLine();
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            System.out.print("Enter your password: ");
            password = scanner.nextLine();
            customer = this.customersDB.addCustomer(name, surname, email, password, true);
            if (customer == null) {
                System.err.println("This email is already used.");
                if (this.printExit()) {
                    return null;
                }
            }
        } while (customer == null);
        System.err.println("Account registered and now you are logged in.");
        customer = this.customersDB.selectCustomer(email);
        return customer;
    }

    /* To print information for the customer */
    private void printContext(String context) {
        System.out.print("\n\n");
        System.out.println("--------------------------------------");
        System.out.println(context);
        System.out.println("--------------------------------------");
    }

    /* To exit from the current menu  */
    private boolean printExit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press X to exit: ");
        String exit = scanner.nextLine();
        if (exit.equals("X")) {
            System.err.println("Exit form the shop program...");
            return true;
        } else {
            return false;
        }
    }
}
