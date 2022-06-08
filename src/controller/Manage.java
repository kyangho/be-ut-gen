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
    public Manage(ArrayList<Fruit> fruitList, Hashtable<String, ArrayList<Fruit>> orderTable) {
        this.orderTable = orderTable;
        this.fruitList = fruitList;
    }
    public void shopping(){CodeGenLog.log("IN - 1: { row: 35 }");
    CodeGenLog.log("OUT - 1: { row: 36 }");CodeGenLog.log("IN - 2: { row: 36 }");if (fruitList.isEmpty()){CodeGenLog.log("IN - 3: { row: 36 }");
            System.err.println("Sorry! Our store is out of stock! ");
            CodeGenLog.log("OUT - 3: { row: 38 }");return;
        }
        
        //init
        ArrayList<Fruit> orderList = new ArrayList<>();
        Integer quantity = 0;
        Integer item = 0;
        
        //process
        CodeGenLog.log("OUT - 2: { row: 47 }");CodeGenLog.log("IN - 4: { row: 47 }");while(true){CodeGenLog.log("IN - 5: { row: 47 }");
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
        }
        if (orderList.isEmpty()){
            System.out.println("You didn't order anything");
            return;
        }
        //Display order
        Printer.viewOrder(orderList);
        
        //Enter customer's name
        Pattern pattern = Pattern.compile("[a-zA-z\\s]+");
        Matcher matcher;
        String name;
        while(true){
            name = Validation.inputString("Enter your name: ", false);
            matcher = pattern.matcher(name);
            if(!matcher.matches()){
                System.err.println("Invalid name, enter again!");
                continue;
            }
            name = Validation.beautyName(name);
            break;
        }
        orderTable.put(name, orderList);
    }
    
    private boolean isEnoughFruit(String fruitId, int amount){
        Fruit tmp;
        int i = 0;
        for (Iterator<Fruit> fruit = fruitList.iterator(); fruit.hasNext();){
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
        }
        return false;
    }
    public ArrayList<Fruit> updateOrder(Fruit order, ArrayList<Fruit> orderList){
        if (order.getQuantity()== 0) return orderList;
        Fruit oTmp;
        int i = 0;
        
        //Process
        for (Fruit o : orderList){
            if(o.getId().compareTo(order.getId()) == 0){    //check if id order equal order in list
                oTmp = new Fruit(o.getId(), o.getName(), o.getQuantity() + order.getQuantity(), o.getPrice());
                orderList.set(i, oTmp);
                return orderList;
            }
            i++;
        }
        orderList.add(new Fruit(order.getId(), order.getName(), order.getQuantity(), order.getPrice()));
        return orderList;
    }
}
