/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.Fruit;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import controller.Menu;
import custom.CodeGenLog;

/**
 *
 * @author Duc Ky
 */
enum MyEnum {
	UNDEFINED {
		void foo() {
		}
	}
}



interface test{
	void demo();
}
public class Printer {

    public static void displayFullofFruit(Fruit fruit){
        DecimalFormat format = new DecimalFormat("0.##$");
        System.out.printf("|   %-5s| %-15s|   %-8s|      %-8d| %-11s|\n", fruit.getId(), fruit.getName(), format.format(fruit.getPrice()),fruit.getQuantity(), fruit.getOrigin());
    }
    
    public static void headerFullofFruit(){
        System.out.println("|++ ID ++|++ Fruit Name ++|++ Price ++|++ Quantity ++|++ Origin ++| ");
    }
    
    public static void displayAllOrder(Hashtable<String, ArrayList<Fruit>> orderTable) {
        if (orderTable.isEmpty()){
            System.err.println("There are nothing to view!");
            return;
        }
        double total = 0, amount;
        int i;
        DecimalFormat format = new DecimalFormat("0.##$");
        
        //Display
        Enumeration names = orderTable.keys();
        ArrayList<Fruit> orderList;
        CodeGenLog.log("Start while statement");
        while(names.hasMoreElements()){
            String nameStr = (String) names.nextElement();
            orderList = orderTable.get(nameStr);
            total = 0;
            i = 1;
            System.out.println("Customer: " +  nameStr);
            System.out.println("Product    | Quantity | Price | Amount |");
            System.out.println("----------------------------------------");
            for (Fruit o : orderList){
                amount = o.getPrice() * o.getQuantity();
                System.out.printf("%-11s|    %-6d|  %-5s|   %-5s|\n", i + ". " +  o.getName(), o.getQuantity(), format.format(o.getPrice()), format.format(amount));
                total += amount;
                i++;
            }
            System.out.println("----------------------------------------");
            System.out.printf("%-39s|\n", "Total: " + format.format(total));
            System.out.println("----------------------------------------\n");
        }
        CodeGenLog.log("Out while statement");
    }
    public static void viewOrder(ArrayList<Fruit> order){CodeGenLog.log("IN - 2314312098: { row: 64 }");
        double amount = 0;
        System.out.println("\nYour order: ");
        System.out.println("Product    | Quantity | Price | Amount  |");
        System.out.println("-----------------------------------------");
        DecimalFormat format = new DecimalFormat("0.##$");
        CodeGenLog.log("OUT - 2314312098: { row: 70 }");CodeGenLog.log("IN - 4122420092: { row: 70 }");for (Fruit o : order){
        	
            amount = o.getPrice() * o.getQuantity();
            System.out.printf("%-11s|    %-6d|  %-5s|   %-6s|\n", o.getName(), o.getQuantity(), format.format(o.getPrice()), format.format(amount));
            CodeGenLog.log("OUT - 4122420092: { row: 74 }");}
        CodeGenLog.log("IN - 1305124561: { row: 75}");
        CodeGenLog.log("OUT - 1305124561: { row: 76 }");}   
    public static void displayListFruit(ArrayList<Fruit> fruitList){CodeGenLog.log("IN - 2314312098: { row: 77 }");
        System.out.println("|++ Item ++|++ Fruit Name ++|++ Origin ++|++ Price ++|");
        int index = 1;
        DecimalFormat format = new DecimalFormat("0.##$");
        CodeGenLog.log("OUT - 6317465098: { row: 81 }");CodeGenLog.log("IN - 5777320092: { row: 81 }");CodeGenLog.log("IN - 6305590361: { row: 81 }");for (Fruit f : fruitList){
            System.out.printf("      %-5d| %-15s| %-11s|   %-8s|\n", index, f.getName(), f.getOrigin(), format.format(f.getPrice()));
            index++;
            CodeGenLog.log("OUT - 5777320092: { row: 84 }");}

        System.out.println("");
        switch(index) {
        	case 1:
        }
        CodeGenLog.log("OUT - 6305590361: { row: 87 }");}
    }

