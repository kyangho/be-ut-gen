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
    
    public FruitController(){CG.log("IN1 - 4687053766: { row: 24 }");
        fruitList = new ArrayList<>();
    CG.log("OUT5 - 4687053766: { row: 26 }");}
    
    public FruitController(ArrayList<Fruit> fruitList){CG.log("IN1 - 2503566176: { row: 28 }");
        this.fruitList = fruitList;
    CG.log("OUT5 - 2503566176: { row: 30 }");}
    
    private boolean isExisted(String fruitId){CG.log("IN1 - 1613849545: { row: 32 }");
CG.log("OUT2 - 710558274: { row: 33 }");CG.log("IN2.1 - 710558274: ");        for(Fruit f : fruitList){
            if (f.getId().compareToIgnoreCase(fruitId) == 0){
                return true;
            }
CG.log("IN4 - 4899052708: { row: 37 }");        }CG.log("OUT3 - 710558274: { row: 37 }");
        return false;
    CG.log("OUT5 - 4899052708: { row: 39 }");}
    private Fruit getFruitById(String id){CG.log("IN1 - 2149968559: { row: 40 }");
CG.log("OUT2 - 1825241320: { row: 41 }");CG.log("IN2.1 - 1825241320: ");        for (Fruit f : fruitList){
            if (f.getId().compareTo(id) == 0){
                return f;
            }
CG.log("IN4 - 681604188: { row: 45 }");        }CG.log("OUT3 - 1825241320: { row: 45 }");
        return null;
    CG.log("OUT5 - 681604188: { row: 47 }");}
    private ArrayList<Fruit> updateFruit(String id, Fruit inputFruit){CG.log("IN1 - 2753171295: { row: 48 }");
        Fruit tmp = null;
        int index = 0;
CG.log("OUT2 - 2948759927: { row: 51 }");CG.log("IN2.1 - 2948759927: ");        for (Fruit f : fruitList){
            if (f.getId().compareTo(id) == 0){
                break;
            }
            index++;
CG.log("IN4 - 8395645450: { row: 56 }");        }CG.log("OUT3 - 2948759927: { row: 56 }");
        fruitList.set(index, inputFruit);
        return fruitList;
    CG.log("OUT5 - 8395645450: { row: 59 }");}
    private boolean nameExisted(String name){CG.log("IN1 - 563355181: { row: 60 }");
CG.log("OUT2 - 793083668: { row: 61 }");CG.log("IN2.1 - 793083668: ");        for (Fruit f : fruitList){
            if (f.getName().compareTo(name) == 0){
                return true;
            }
CG.log("IN4 - 9369513942: { row: 65 }");        }CG.log("OUT3 - 793083668: { row: 65 }");
        return false;
    CG.log("OUT5 - 9369513942: { row: 67 }");}
    private void inputFruit(){CG.log("IN1 - 1794550225: { row: 68 }");
        String tmp;
        Pattern pattern;
        Matcher matcher;
        boolean isSkip = false;
        boolean wantUpdate = false;
        //input ID
        String id;
        
CG.log("OUT2 - 1295140847: { row: 77 }");CG.log("IN2.1 - 1295140847: ");        while(true){
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
CG.log("IN4 - 1807544392: { row: 96 }");        }CG.log("OUT3 - 1295140847: { row: 96 }");
        
        //input name
        String name;
CG.log("OUT2 - 5326202545: { row: 100 }");CG.log("IN2.1 - 5326202545: ");        while (true){
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
CG.log("IN4 - 901392331: { row: 121 }");        }CG.log("OUT3 - 5326202545: { row: 121 }");
        
        //input price
        Double price;
CG.log("OUT2 - 1799256435: { row: 125 }");CG.log("IN2.1 - 1799256435: ");        while(true){
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
CG.log("IN4 - 7161046929: { row: 136 }");        }CG.log("OUT3 - 1799256435: { row: 136 }");
        
        //input quantity
        Integer quantity = 0;
CG.log("OUT2 - 8863851408: { row: 140 }");CG.log("IN2.1 - 8863851408: ");        while(true){
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
CG.log("IN4 - 8401262177: { row: 151 }");        }CG.log("OUT3 - 8863851408: { row: 151 }");
        //input origin
        String origin;
CG.log("OUT2 - 9225404085: { row: 154 }");CG.log("IN2.1 - 9225404085: ");        while(true){
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
CG.log("IN4 - 4017330846: { row: 168 }");        }CG.log("OUT3 - 9225404085: { row: 168 }");
        //create fruit and add to list
        //check if user want to add or not
CG.log("OUT2 - 4860858873: { row: 171 }");CG.log("IN2.1 - 4860858873: ");        if (isSkip && !wantUpdate){CG.log("IN1 - 7221856873: { row: 171 }");
            System.err.println("You just skipped input Fruit's infomation!");
             return;
CG.log("IN4 - 7249065028: { row: 174 }");        CG.log("OUT5 - 7221856873: { row: 174 }");}CG.log("OUT3 - 4860858873: { row: 174 }");
        Fruit fruit;
        System.out.println(wantUpdate);
CG.log("OUT2 - 5911117299: { row: 177 }");CG.log("IN2.1 - 5911117299: ");        if (!wantUpdate){CG.log("IN1 - 5965932265: { row: 177 }");
            fruit = new Fruit(id, name, price, quantity, origin);
            fruitList.add(fruit);
        CG.log("OUT5 - 5965932265: { row: 180 }");}else{
            if (name  == null) name = getFruitById(id).getName();
            if (price  == null) price = getFruitById(id).getPrice();
            if (origin  == null) origin = getFruitById(id).getOrigin();
            if (quantity == null) quantity = getFruitById(id).getQuantity();
            fruit = new Fruit(id, name, price, quantity, origin);
            updateFruit(id, fruit);
CG.log("IN4 - 1675499850: { row: 187 }");        }CG.log("OUT3 - 5911117299: { row: 187 }");
        //display
        Printer.headerFullofFruit();
        Printer.displayFullofFruit(fruit);
    CG.log("OUT5 - 1675499850: { row: 191 }");}
    public void create(){CG.log("IN1 - 1492417550: { row: 192 }");
CG.log("OUT2 - 8028655953: { row: 193 }");CG.log("IN2.1 - 8028655953: ");        while (true){
            inputFruit();
            if (!Validation.checkYesNo("Do you want to add new fruit?(Y/N): ")) {
            	break;
            }
CG.log("IN4 - 8764882143: { row: 198 }");        }CG.log("OUT3 - 8028655953: { row: 198 }");
    CG.log("OUT5 - 8764882143: { row: 199 }");}

    public ArrayList<Fruit> getFruitList() {CG.log("IN1 - 8544934947: { row: 201 }");
        return fruitList;
    CG.log("OUT5 - 8544934947: { row: 203 }");}

    public void setFruitList(ArrayList<Fruit> fruitList) {CG.log("IN1 - 1683410489: { row: 205 }");
        this.fruitList = fruitList;
    CG.log("OUT5 - 1683410489: { row: 207 }");}
    
}

