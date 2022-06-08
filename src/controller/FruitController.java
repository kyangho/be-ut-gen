/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Fruit;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import custom.CodeGenLog;
import util.Printer;
import util.Validation;

/**
 *
 * @author Duc Ky
 */
public class FruitController {
    private ArrayList<Fruit> fruitList;
    
    public FruitController(){
        fruitList = new ArrayList<>();
    }
    
    public FruitController(ArrayList<Fruit> fruitList){
        this.fruitList = fruitList;
    }
    
    private boolean isExisted(String fruitId){
        for(Fruit f : fruitList){
            if (f.getId().compareToIgnoreCase(fruitId) == 0){
                return true;
            }
        }
        return false;
    }
    private Fruit getFruitById(String id){
        for (Fruit f : fruitList){
            if (f.getId().compareTo(id) == 0){
                return f;
            }
        }
        return null;
    }
    private ArrayList<Fruit> updateFruit(String id, Fruit inputFruit){
        Fruit tmp = null;
        int index = 0;
        for (Fruit f : fruitList){
            if (f.getId().compareTo(id) == 0){
                break;
            }
            index++;
        }
        fruitList.set(index, inputFruit);
        return fruitList;
    }
    private boolean nameExisted(String name){
        for (Fruit f : fruitList){
            if (f.getName().compareTo(name) == 0){
                return true;
            }
        }
        return false;
    }
    private void inputFruit(){
        String tmp;
        Pattern pattern;
        Matcher matcher;
        boolean isSkip = false;
        boolean wantUpdate = false;
        //input ID
        String id;
        
        while(true){
                id = Validation.inputString("Fruit ID: ", true);
                // check if id is null or not
                if (id == null){ 
                    isSkip = true;
                    break;
                }
                id = id.trim().replaceAll("\\s+", " ");
            if (isExisted(id)){
                System.err.println("This ID is existed!");
//                System.err.println("");
                wantUpdate = Validation.checkYesNo("Do you want to update?(Y/N): ");
                break;
            }else if(!Validation.isWord(id)){
                System.err.println("Invalid ID! Enter again!");
            }
            else{
                break;
            }
        }
        
        //input name
        String name;
        while (true){
            pattern = Pattern.compile("[\\w\\s]+");
            name = Validation.inputString("Fruit name: ", true);
            // check if name is null or not
            if (name == null){
                isSkip = true;
                break;
            }

            matcher = pattern.matcher(name);
            if (!matcher.matches()){
                System.err.println("Invalid Fruit name! Enter again!");
                continue;
            }
            if (nameExisted(name)){
                if(!Validation.checkYesNo("This name already existed, do you want to keep this name?(Y/N): ")){
                    continue;
                }
            }
            name = Validation.beautyName(name);
            break;
        }
        
        //input price
        Double price;
        while(true){
            tmp = Validation.inputString("Enter price: ", true);
            if (tmp == null){
                isSkip = true;
                price = null;
                break;
            }
            if (Validation.isPositiveNumber(tmp) >= 1){
                price = Double.parseDouble(tmp);
                break;
            }
        }
        
        //input quantity
        Integer quantity = 0;
        while(true){
            tmp = Validation.inputString("Enter quantity: ", true);
            if (tmp == null){
                isSkip = true;
                quantity = null;
                break;
            }
            if (Validation.isPositiveNumber(tmp) == 1){
                quantity = Integer.parseInt(tmp);
                break;
            }
        }
        //input origin
        String origin;
        while(true){
            pattern = Pattern.compile("[a-zA-Z\\s]+");
            origin = Validation.inputString("Origin: ", true);
            if (origin == null){
                isSkip = true;
                break;
            }
            matcher = pattern.matcher(origin);
            if (!matcher.matches()){
                System.err.println("Invalid origin, enter again!");
                continue;
            }
            origin = origin.trim().replaceAll("\\s+", " ");
            break;
        }
        //create fruit and add to list
        //check if user want to add or not
        if (isSkip && !wantUpdate){
            System.err.println("You just skipped input Fruit's infomation!");
             return;
        }
        Fruit fruit;
        System.out.println(wantUpdate);
        if (!wantUpdate){
            fruit = new Fruit(id, name, price, quantity, origin);
            fruitList.add(fruit);
        }else{
            if (name  == null) name = getFruitById(id).getName();
            if (price  == null) price = getFruitById(id).getPrice();
            if (origin  == null) origin = getFruitById(id).getOrigin();
            if (quantity == null) quantity = getFruitById(id).getQuantity();
            fruit = new Fruit(id, name, price, quantity, origin);
            updateFruit(id, fruit);
        }
        //display
        Printer.headerFullofFruit();
        Printer.displayFullofFruit(fruit);
    }
    public void create(){
        while (true){
            inputFruit();
            if (!Validation.checkYesNo("Do you want to add new fruit?(Y/N): ")) {
            	break;
            }
        }
    }

    public ArrayList<Fruit> getFruitList() {
        return fruitList;
    }

    public void setFruitList(ArrayList<Fruit> fruitList) {
        this.fruitList = fruitList;
    }
    
}
