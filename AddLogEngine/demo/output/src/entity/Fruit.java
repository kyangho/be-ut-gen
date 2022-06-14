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

    public Fruit(String id, String name, double price, int quantity, String origin) {CG.log("IN1 - 7824127414: { row: 22 }");
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.origin = origin;
    CG.log("OUT5 - 7824127414: { row: 28 }");}
    public Fruit(String id, String name,int quantity, double price) {CG.log("IN1 - 4220924598: { row: 29 }");
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    CG.log("OUT5 - 4220924598: { row: 34 }");}
    public String getId() {CG.log("IN1 - 9813856331: { row: 35 }");
        return id;
    CG.log("OUT5 - 9813856331: { row: 37 }");}


    public String getName() {CG.log("IN1 - 5849338597: { row: 40 }");
        return name;
    CG.log("OUT5 - 5849338597: { row: 42 }");}


    public double getPrice() {CG.log("IN1 - 7189906825: { row: 45 }");
        return price;
    CG.log("OUT5 - 7189906825: { row: 47 }");}


    public int getQuantity() {CG.log("IN1 - 4340477300: { row: 50 }");
        return quantity;
    CG.log("OUT5 - 4340477300: { row: 52 }");}



    public String getOrigin() {CG.log("IN1 - 3768293519: { row: 56 }");
        return origin;
    CG.log("OUT5 - 3768293519: { row: 58 }");}


    
}

