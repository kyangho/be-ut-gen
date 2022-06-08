/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.FruitController;
import controller.Manage;
import custom.CodeGenLog;
import entity.Fruit;
import java.util.ArrayList;
import java.util.Hashtable;
import util.Printer;
import util.Validation;

/**
 *
 * @author Duc Ky
 */
public class Menu {
    public static void menu(ArrayList<Fruit> fruitList, Hashtable<String, ArrayList<Fruit>> orderTable){
        int choice;
        FruitController fc = new FruitController(fruitList);
        Manage m = new Manage(fruitList, orderTable);
        //Menu
        System.out.println("FRUIT SHOP SYSTEM");
        //Process
        while(true){
            System.out.println("================ Menu ================");
            System.out.println("1. Create Fruit");
            System.out.println("2. View orders");
            System.out.println("3. Shopping (for buyer)");
            System.out.println("4. Exit");
            System.out.println("(Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).");
            System.out.print("Enter choice: ");
            choice = Validation.inputIntLimit(1, 4);
            System.out.println("");
            switch(choice){
                case 1:
                    fc.create();
                    break;
                case 2:
                    Printer.displayAllOrder(orderTable);
                    break;
                case 3:
                    m.shopping();
                    break;
            }
            if (choice == 4) break;
            
        }
    } 
}
