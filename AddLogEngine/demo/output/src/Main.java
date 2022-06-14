import entity.Fruit;
import java.util.ArrayList; 
import java.util.Hashtable;
import controller.Menu;
import custom.CodeGenLog;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */

/**
 *
 * @author Duc Ky
 */
public class Main {
	static int i = 0;
	static int j = 0;
    public static void main(String[] args) {CG.log("IN1 - 890339732: { row: 20 }");CodeGenLog.log("IN - 1 - 22");
    methodA();
        ArrayList<Fruit> fruitList = new ArrayList<>();
        methodA();
        Hashtable<String, ArrayList<Fruit>> orderTable = new Hashtable<>();
        fruitList.add(new Fruit("1", "Coconut", 4, 20, "Viet Nam"));
        methodB();
        fruitList.add(new Fruit("2", "Orange", 4, 30, "US"));
        methodB();
        fruitList.add(new Fruit("3", "Apple", 4, 10, "Thailand"));
        fruitList.add(new Fruit("4", "Grape", 4, 40, "France"));
        Menu.menu(fruitList, orderTable);
    	int i = 4;
    	int j = 1;
    	methodA();
CG.log("OUT2 - 7239635959: { row: 35 }");CG.log("IN2.1 - 7239635959: ");    	CodeGenLog.log("OUT - 1 - 37"); CodeGenLog.log("IN - 2 - 37");if (i > 3) {CG.log("IN1 - 602972065: { row: 35 }");CodeGenLog.log("IN - 2.1 - 37");
    		System.out.println("a");
CG.log("OUT2 - 1894643934: { row: 37 }");CG.log("IN2.1 - 1894643934: ");    		CodeGenLog.log("OUT - 2.1 - 39");CodeGenLog.log("IN - 3 - 39");if (j > 3) {CG.log("IN1 - 1978476109: { row: 37 }");CodeGenLog.log("IN - 4 - 39");
    			System.out.println("b");
CG.log("IN4 - 9678509188: { row: 39 }");    			CodeGenLog.log("OUT - 4 - 41");CG.log("OUT5 - 1978476109: { row: 39 }");}CG.log("OUT3 - 1894643934: { row: 39 }");
CG.log("IN4 - 7646030220: { row: 40 }");    		CodeGenLog.log("OUT - 3 - 42");CG.log("OUT5 - 9678509188: { row: 40 }");}CG.log("OUT3 - 7239635959: { row: 40 }");
    	methodA();
    	CodeGenLog.log("OUT - 2 - 44");CG.log("OUT5 - 7646030220: { row: 42 }");}
    
    public static void methodA() {CG.log("IN1 - 7273147769: { row: 44 }");CodeGenLog.log("IN - 5 - 46");
    	i++;
CG.log("OUT2 - 6440726358: { row: 46 }");CG.log("IN2.1 - 6440726358: ");    	CodeGenLog.log("OUT - 5 - 48");CodeGenLog.log("IN - 6 - 46");if (j < 5) {CG.log("IN1 - 2862615993: { row: 46 }");CodeGenLog.log("IN - 7 - 48");
    		methodB();
CG.log("IN4 - 8875889846: { row: 48 }");    		CodeGenLog.log("OUT - 7 - 50");CG.log("OUT5 - 2862615993: { row: 48 }");}CG.log("OUT3 - 6440726358: { row: 48 }");
    	System.out.println("a");
    	methodB();
    CodeGenLog.log("OUT - 6 - 53");CG.log("OUT5 - 8875889846: { row: 51 }");}
    
    public static void methodB() {CG.log("IN1 - 9712922959: { row: 53 }");CodeGenLog.log("IN - 7 - 55");
    	j++;
CG.log("OUT2 - 2060827237: { row: 55 }");CG.log("IN2.1 - 2060827237: ");    	CodeGenLog.log("OUT - 7 - 57");CodeGenLog.log("IN - 8 - 57");if (i < 5) {CG.log("IN1 - 4919906489: { row: 55 }");CodeGenLog.log("IN - 9 - 57");
    		methodA();
CG.log("IN4 - 1634233449: { row: 57 }");    		CodeGenLog.log("OUT - 9 - 59");CG.log("OUT5 - 4919906489: { row: 57 }");}CG.log("OUT3 - 2060827237: { row: 57 }");
    	System.out.println("b");
    CodeGenLog.log("OUT - 8 - 61");CG.log("OUT5 - 1634233449: { row: 59 }");}
}   

