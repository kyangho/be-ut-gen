/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Duc Ky
 */
public class Fruit {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String origin;

    public Fruit() {
    }

    public Fruit(String id, String name, double price, int quantity, String origin) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.origin = origin;
    }
    public Fruit(String id, String name,int quantity, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public double getPrice() {
        return price;
    }


    public int getQuantity() {
        return quantity;
    }



    public String getOrigin() {
        return origin;
    }


    
}
