/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Fruit;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import custom.CodeGenLog;
import util.Printer;
import util.Validation;

/**
 *
 * @author Duc Ky
 */
public class Manage {
//    ArrayList<ListOrder> listOrder; 
    private ArrayList<Fruit> fruitList;
    private Hashtable<String, ArrayList<Fruit>> orderTable;
    
    public Manage() {
        
    }
    public Manage(ArrayList<Fruit> fruitList, Hashtable<String, ArrayList<Fruit>> orderTable) {CG.log("IN1 - 4991183943: { row: 31 }");
        this.orderTable = orderTable;
        this.fruitList = fruitList;
    CG.log("OUT5 - 4991183943: { row: 34 }");}
    public void shopping(){CG.log("IN1 - 8431084766: { row: 35 }");CodeGenLog.log("IN - 1: { row: 35 }");
CG.log("OUT2 - 863703182: { row: 36 }");CG.log("IN2.1 - 863703182: ");    CodeGenLog.log("OUT - 1: { row: 36 }");CodeGenLog.log("IN - 2: { row: 36 }");if (fruitList.isEmpty()){CG.log("IN1 - 7462292308: { row: 36 }");CodeGenLog.log("IN - 3: { row: 36 }");
            System.err.println("Sorry! Our store is out of stock! ");
            CodeGenLog.log("OUT - 3: { row: 38 }");return;
CG.log("IN4 - 1866045626: { row: 39 }");        CG.log("OUT5 - 7462292308: { row: 39 }");}CG.log("OUT3 - 863703182: { row: 39 }");
        
        //init
        ArrayList<Fruit> orderList = new ArrayList<>();
        Integer quantity = 0;
        Integer item = 0;
        
        //process
CG.log("OUT2 - 7994101478: { row: 47 }");CG.log("IN2.1 - 7994101478: ");        CodeGenLog.log("OUT - 2: { row: 47 }");CodeGenLog.log("IN - 4: { row: 47 }");while(true){CodeGenLog.log("IN - 5: { row: 47 }");
        //display fruit
            System.out.println("List of Fruit: ");
            Printer.displayListFruit(fruitList);
            
        //Input item
            System.out.print("Enter item's number you want to order: ");
            item = Validation.inputIntLimit(1, fruitList.size());
            CodeGenLog.log("OUT - 5: { row: 55 }");if (item == null){CodeGenLog.log("IN - 6: { row: 55 }");
                System.err.println("You just skipped order!");
                CodeGenLog.log("OUT - 4: { row: 57 }");CodeGenLog.log("OUT - 6: { row: 57 }"); break;
            }
        //Find fruit by index in arraylist
            Fruit tmpFruit = fruitList.get(item - 1);
            
        //Enter quantity
            while(true){
                quantity = Validation.inputInt("Enter quantity: ");
                if (quantity == null) break;
                if (isEnoughFruit(fruitList.get(item - 1).getId(), quantity)){
                    break;
                }
            }
            if (quantity == null){
                System.err.println("You just skipped order!");
                if (Validation.checkYesNo("Do you want to order now?(Y/N): ")){
                    break;
                }else if(fruitList.isEmpty()){
                    System.err.println("Sorry! Our store is out of stock!");
                    break;
                }
                else{
                    System.err.println("Order new!");
                    continue;
                }
            }
        //use updateOrder(); to add or change duplicate order
            orderList = updateOrder(new Fruit(tmpFruit.getId(), tmpFruit.getName(), quantity, tmpFruit.getPrice()), orderList);
            
        //Ask to continue order
            if (Validation.checkYesNo("Do you want to order now?(Y/N): ")){
                break;
            }else if(fruitList.isEmpty()){
                System.err.println("Sorry! Our store is out of stock!");
                break;
            }
            else{
                System.err.println("Order new!");
            }
CG.log("IN4 - 8079133247: { row: 96 }");        }CG.log("OUT3 - 7994101478: { row: 96 }");
CG.log("OUT2 - 5101649170: { row: 97 }");CG.log("IN2.1 - 5101649170: ");        if (orderList.isEmpty()){CG.log("IN1 - 7720602201: { row: 97 }");
            System.out.println("You didn't order anything");
            return;
CG.log("IN4 - 5537998429: { row: 100 }");        CG.log("OUT5 - 7720602201: { row: 100 }");}CG.log("OUT3 - 5101649170: { row: 100 }");
        //Display order
        Printer.viewOrder(orderList);
        
        //Enter customer's name
        Pattern pattern = Pattern.compile("[a-zA-z\\s]+");
        Matcher matcher;
        String name;
CG.log("OUT2 - 6511561791: { row: 108 }");CG.log("IN2.1 - 6511561791: ");        while(true){
            name = Validation.inputString("Enter your name: ", false);
            matcher = pattern.matcher(name);
            if(!matcher.matches()){
                System.err.println("Invalid name, enter again!");
                continue;
            }
            name = Validation.beautyName(name);
            break;
CG.log("IN4 - 5397239483: { row: 117 }");        }CG.log("OUT3 - 6511561791: { row: 117 }");
        orderTable.put(name, orderList);
    CG.log("OUT5 - 5397239483: { row: 119 }");}
    
    private boolean isEnoughFruit(String fruitId, int amount){CG.log("IN1 - 496772678: { row: 121 }");
        Fruit tmp;
        int i = 0;
CG.log("OUT2 - 1379917440: { row: 124 }");CG.log("IN2.1 - 1379917440: ");        for (Iterator<Fruit> fruit = fruitList.iterator(); fruit.hasNext();){
            Fruit f = fruit.next();
            if (fruitId.compareTo(f.getId()) == 0){
                if (amount <= f.getQuantity()){
                    tmp = new Fruit(f.getId(), f.getName(), f.getPrice(), f.getQuantity() - amount, f.getOrigin());
                    fruitList.set(i, tmp);
                    
                    //if quantity of fruit == 0 remove
                    if (fruitList.get(i).getQuantity() == 0){
                        fruit.remove();
                    }
                    return true;
                }else{
                    System.err.println("Remaining " + f.getName() + " fruit in store: " + f.getQuantity());
                    System.err.println("There is not enough fruit to order. Enter again!");
                    return false;
                }

            }
            i++;
CG.log("IN4 - 4620646656: { row: 144 }");        }CG.log("OUT3 - 1379917440: { row: 144 }");
        return false;
    CG.log("OUT5 - 4620646656: { row: 146 }");}
    public ArrayList<Fruit> updateOrder(Fruit order, ArrayList<Fruit> orderList){CG.log("IN1 - 6654536721: { row: 147 }");
CG.log("OUT2 - 4956868454: { row: 148 }");CG.log("IN2.1 - 4956868454: ");CG.log("IN4 - 8449704707: { row: 148 }");        if (order.getQuantity()== 0) return orderList;CG.log("OUT3 - 4956868454: { row: 148 }");
        Fruit oTmp;
        int i = 0;
        
        //Process
CG.log("OUT2 - 9383685992: { row: 153 }");CG.log("IN2.1 - 9383685992: ");        for (Fruit o : orderList){
            if(o.getId().compareTo(order.getId()) == 0){    //check if id order equal order in list
                oTmp = new Fruit(o.getId(), o.getName(), o.getQuantity() + order.getQuantity(), o.getPrice());
                orderList.set(i, oTmp);
                return orderList;
            }
            i++;
CG.log("IN4 - 9152533720: { row: 160 }");        }CG.log("OUT3 - 9383685992: { row: 160 }");
        orderList.add(new Fruit(order.getId(), order.getName(), order.getQuantity(), order.getPrice()));
        return orderList;
    CG.log("OUT5 - 9152533720: { row: 163 }");}
}

