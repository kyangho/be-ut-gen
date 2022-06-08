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
    public static void main(String[] args) {CodeGenLog.log("IN - 1 - 22");
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
    	CodeGenLog.log("OUT - 1 - 37"); CodeGenLog.log("IN - 2 - 37");if (i > 3) {CodeGenLog.log("IN - 2.1 - 37");
    		System.out.println("a");
    		CodeGenLog.log("OUT - 2.1 - 39");CodeGenLog.log("IN - 3 - 39");if (j > 3) {CodeGenLog.log("IN - 4 - 39");
    			System.out.println("b");
    			CodeGenLog.log("OUT - 4 - 41");}
    		CodeGenLog.log("OUT - 3 - 42");}
    	methodA();
    	CodeGenLog.log("OUT - 2 - 44");}
    
    public static void methodA() {CodeGenLog.log("IN - 5 - 46");
    	i++;
    	CodeGenLog.log("OUT - 5 - 48");CodeGenLog.log("IN - 6 - 46");if (j < 5) {CodeGenLog.log("IN - 7 - 48");
    		methodB();
    		CodeGenLog.log("OUT - 7 - 50");}
    	System.out.println("a");
    	methodB();
    CodeGenLog.log("OUT - 6 - 53");}
    
    public static void methodB() {CodeGenLog.log("IN - 7 - 55");
    	j++;
    	CodeGenLog.log("OUT - 7 - 57");CodeGenLog.log("IN - 8 - 57");if (i < 5) {CodeGenLog.log("IN - 9 - 57");
    		methodA();
    		CodeGenLog.log("OUT - 9 - 59");}
    	System.out.println("b");
    CodeGenLog.log("OUT - 8 - 61");}
}   

