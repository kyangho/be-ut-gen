package util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Duc Ky
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import custom.CodeGenLog;

public class Validation{
    public static Scanner in = new Scanner(System.in);
    /**
     * Check if input is String or not
     * @param message
     * @return 
     */
    public static String inputString(String p, boolean canNull){CG.log("IN1 - 4185676868: { row: 29 }");
        String s = "";
        System.out.print(p);
CG.log("OUT2 - 2642284638: { row: 32 }");CG.log("IN2.1 - 2642284638: ");        do{
            s = in.nextLine();
            if (canNull == true && s.replaceAll("\\s+", "").isEmpty()) return null; 
            if (!s.replaceAll("\\s+", "").isEmpty())
                break;
            else System.err.print("Invalid input, enter again: "); 
CG.log("IN4 - 1633492714: { row: 38 }");        }while(true);CG.log("OUT3 - 2642284638: { row: 38 }");
        return s;
    CG.log("OUT5 - 1633492714: { row: 40 }");}
    /**
     * Check if input is int or not
     * @param message
     * @return 
     */
    public static Integer inputInt(String p){CG.log("IN1 - 2092363089: { row: 46 }");
        String tmp;
        int i = 0;
        System.out.print(p);
CG.log("OUT2 - 6972556926: { row: 50 }");CG.log("IN2.1 - 6972556926: ");        do{
            try{
                tmp = in.nextLine();
                if (tmp.replaceAll(" ", "").trim().isEmpty()){
                    return null;
                }
                if (Integer.parseInt(tmp) == Integer.parseInt(tmp)){
                    i = Integer.parseInt(tmp);
                }
                break;
            }catch(Exception e){
            	CodeGenLog.log(CodeGenLog.getCallerClassName(), e);
                System.err.print("Invalid input, enter again: ");
            }
CG.log("IN4 - 2000525840: { row: 64 }");        }while(true);CG.log("OUT3 - 6972556926: { row: 64 }");
        return i;
    CG.log("OUT5 - 2000525840: { row: 66 }");}
    /**
     * Check if input is double or not
     * @param message
     * @return 
     */
    public static double inputDouble(String p){CG.log("IN1 - 4229987771: { row: 72 }");
        String tmp;
        double d = 0;
        System.out.print(p);
CG.log("OUT2 - 3943516293: { row: 76 }");CG.log("IN2.1 - 3943516293: ");        do{
            try{
                tmp = in.nextLine();
                if (Double.parseDouble(tmp) == Double.parseDouble(tmp)){
                    d = Double.parseDouble(tmp);
                }
                break;
            }catch(Exception e){
            	CodeGenLog.log(CodeGenLog.getCallerClassName(), e);
                System.err.print("Invalid input, enter again: ");
            }
CG.log("IN4 - 844332115: { row: 87 }");        }while(true);CG.log("OUT3 - 3943516293: { row: 87 }");
        return d;
    CG.log("OUT5 - 844332115: { row: 89 }");}
    /**
     * Check if input is boolean or not
     * @param message
     * @return 
     */
    public static boolean inputBoolean(String p){CG.log("IN1 - 3918742040: { row: 95 }");
        String tmp;
        boolean b = true;
        System.out.print(p);
CG.log("OUT2 - 5454452012: { row: 99 }");CG.log("IN2.1 - 5454452012: ");        do{
            try{
                tmp = in.nextLine();
                if (Boolean.parseBoolean(tmp) == Boolean.parseBoolean(tmp)){
                    b = Boolean.parseBoolean(tmp);
                }
                break;
            }catch(Exception e){
            	CodeGenLog.log(CodeGenLog.getCallerClassName(), e);
                System.err.print("Invalid input, enter again: ");
            }
CG.log("IN4 - 95155153: { row: 110 }");        }while(true);CG.log("OUT3 - 5454452012: { row: 110 }");
        return b;
    CG.log("OUT5 - 95155153: { row: 112 }");}
    /**
     * Check if input is char or not
     * @param message
     * @return 
     */
    public static char inputChar(String p){CG.log("IN1 - 181109372: { row: 118 }");
        System.out.print(p);
        String c;
CG.log("OUT2 - 6729164918: { row: 121 }");CG.log("IN2.1 - 6729164918: ");        while(true){
            c = in.nextLine();
            if (c.length() == 1){
                return c.charAt(0);
            }else{
                System.err.print("Invalid input, enter again: ");
            }
CG.log("IN4 - 8210639334: { row: 128 }");        }CG.log("OUT3 - 6729164918: { row: 128 }");
    CG.log("OUT5 - 8210639334: { row: 129 }");}
    /**
     * Check if input date is valid or not
     * @param message
     * @param format date
     * @return 
     */
    public static String inputDate(String p, String pattern){CG.log("IN1 - 1129912597: { row: 136 }");
        System.out.print(p);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = new Date();
        String tmp;
CG.log("OUT2 - 8923120697: { row: 141 }");CG.log("IN2.1 - 8923120697: ");        while(true){
            try{
                tmp = in.nextLine();
                date = sdf.parse(tmp);
                if (tmp.equals(sdf.format(date))){
                    return tmp;
                }else{
                    System.err.print("Invalid input, enter again: ");
                }
            }catch(Exception e){
            	CodeGenLog.log(CodeGenLog.getCallerClassName(), e);
                System.err.print("Invalid input, enter again: ");
            }
CG.log("IN4 - 1986038435: { row: 154 }");        }CG.log("OUT3 - 8923120697: { row: 154 }");
    CG.log("OUT5 - 1986038435: { row: 155 }");} 
    /**
     * return 0 is not a positive number
     * return 1 is integer number
     * return 2 is double number
     * @param num
     * @return 
     */
    public static int isPositiveNumber(String num){CG.log("IN1 - 6443450983: { row: 163 }");
        boolean result = false;
        double numDouble = 0;
        int numInt = 0;
CG.log("OUT2 - 6020542439: { row: 167 }");CG.log("IN2.1 - 6020542439: ");        try{CG.log("IN1 - 1629529108: { row: 167 }");
CG.log("OUT2 - 7923250763: { row: 168 }");CG.log("IN2.1 - 7923250763: ");            if (Integer.parseInt(num) == Integer.parseInt(num)){CG.log("IN1 - 9938334362: { row: 168 }");
                numInt = Integer.parseInt(num);
CG.log("OUT2 - 7027898465: { row: 170 }");CG.log("IN2.1 - 7027898465: ");                if (numInt <= 0){CG.log("IN1 - 2799098539: { row: 170 }");
                    System.err.println("The number must be positive!");
                    return 0;
CG.log("IN4 - 8241243543: { row: 173 }");                CG.log("OUT5 - 2799098539: { row: 173 }");}CG.log("OUT3 - 7027898465: { row: 173 }");
                return 1;
CG.log("IN4 - 576352605: { row: 175 }");            CG.log("OUT5 - 8241243543: { row: 175 }");}CG.log("OUT3 - 7923250763: { row: 175 }");
CG.log("OUT2 - 3860432799: { row: 176 }");CG.log("IN2.1 - 3860432799: ");            if (Double.parseDouble(num) == Double.parseDouble(num)){CG.log("IN1 - 3793668808: { row: 176 }");
                numDouble = Double.parseDouble(num);
CG.log("OUT2 - 2959796526: { row: 178 }");CG.log("IN2.1 - 2959796526: ");                if (numDouble <= 0){CG.log("IN1 - 4573961942: { row: 178 }");
                    System.err.println("The number must be positive!");
                    return 0;
CG.log("IN4 - 9179121052: { row: 181 }");                CG.log("OUT5 - 4573961942: { row: 181 }");}CG.log("OUT3 - 2959796526: { row: 181 }");
                return 2;
CG.log("IN4 - 6409893315: { row: 183 }");            CG.log("OUT5 - 9179121052: { row: 183 }");}CG.log("OUT3 - 3860432799: { row: 183 }");
            
CG.log("OUT2 - 2913122198: { row: 185 }");CG.log("IN2.1 - 2913122198: ");            if (numDouble <= 0 || numInt <= 0){CG.log("IN1 - 5634909266: { row: 185 }");
                System.err.println("The number must be positive!");
                return 0;
CG.log("IN4 - 7119553595: { row: 188 }");            CG.log("OUT5 - 5634909266: { row: 188 }");}CG.log("OUT3 - 2913122198: { row: 188 }");
        CG.log("OUT5 - 7119553595: { row: 189 }");}catch(NumberFormatException e){CG.log("IN1 - 7045219626: { row: 189 }");
        	CodeGenLog.log(CodeGenLog.getCallerClassName(), e);
            System.err.println("Invalid input! Please enter number!");
            return 0;
CG.log("IN4 - 6079328776: { row: 193 }");        CG.log("OUT5 - 7045219626: { row: 193 }");}CG.log("OUT3 - 6020542439: { row: 193 }");
        return 0;
    CG.log("OUT5 - 6079328776: { row: 195 }");}
    
    public static Integer inputIntLimit(int first, int end){CG.log("IN1 - 4378340691: { row: 197 }");
        Integer input;
        
CG.log("OUT2 - 5660861113: { row: 200 }");CG.log("IN2.1 - 5660861113: ");        while(true){
            input = inputInt("");
            if (input == null){
                    System.err.print("Invalid input! Enter again: ");
                    continue;
            }
            if (first <= input && input <= end){
                return input;
            }else{
                if (first != end) System.err.printf("Enter a integer number from %d to %d: ", first, end);
                else System.err.printf("A integer valiable is %d! Enter again: ", first);
            }
CG.log("IN4 - 119228232: { row: 212 }");        }CG.log("OUT3 - 5660861113: { row: 212 }");
    CG.log("OUT5 - 119228232: { row: 213 }");}
    public static boolean checkYesNo(String tmp){CG.log("IN1 - 9075497965: { row: 214 }");
        System.out.print(tmp);

        boolean check = false;
CG.log("OUT2 - 4598805452: { row: 218 }");CG.log("IN2.1 - 4598805452: ");        while(true){
            String s = in.nextLine();
            s.replaceAll(" ", "");
            if (s.compareToIgnoreCase("yes") == 0 || s.compareToIgnoreCase("y") == 0){
                return true;
            }
            if (s.compareToIgnoreCase("no") == 0 || s.compareToIgnoreCase("n") == 0){
                return false;
            }
            System.err.print("Invalid input! Enter again: ");
CG.log("IN4 - 4276013303: { row: 228 }");        }CG.log("OUT3 - 4598805452: { row: 228 }");
    CG.log("OUT5 - 4276013303: { row: 229 }");}
        
    public static String beautyName(String stringInput){CG.log("IN1 - 6931087569: { row: 231 }");
CG.log("OUT2 - 9053788260: { row: 232 }");CG.log("IN2.1 - 9053788260: ");CG.log("IN4 - 1360578441: { row: 232 }");        if (stringInput.isEmpty()) return null;CG.log("OUT3 - 9053788260: { row: 232 }");
        String [] strA = stringInput.replaceAll("\\s+", " ").trim().split(" ");
CG.log("OUT2 - 7384741866: { row: 234 }");CG.log("IN2.1 - 7384741866: ");CG.log("IN4 - 4611496822: { row: 234 }");        if (stringInput.replaceAll(" ", "").isEmpty()) return null;CG.log("OUT3 - 7384741866: { row: 234 }");
        String tmp = "";
        String result = "";
CG.log("OUT2 - 3791822987: { row: 237 }");CG.log("IN2.1 - 3791822987: ");        for (String s : strA){
            s = s.toLowerCase();
            if (s.length() == 1){
                result = result + s.toUpperCase() + " ";
                continue;
            }
            tmp = s.substring(0, 1).toUpperCase() + s.substring(1) + " ";
            result += tmp;
CG.log("IN4 - 5099896889: { row: 245 }");        }CG.log("OUT3 - 3791822987: { row: 245 }");
        return result.trim();
    CG.log("OUT5 - 5099896889: { row: 247 }");}
    
    public static boolean isLetter(String stringInput){CG.log("IN1 - 366419456: { row: 249 }");
        Pattern pattern = Pattern.compile("^[a-zA-Z]+");
        Matcher matcher = pattern.matcher(stringInput);
CG.log("OUT2 - 5302790460: { row: 252 }");CG.log("IN2.1 - 5302790460: ");CG.log("IN4 - 159776857: { row: 252 }");        if (matcher.matches()) return true;CG.log("OUT3 - 5302790460: { row: 252 }");
        return false;
    CG.log("OUT5 - 159776857: { row: 254 }");}
    public static boolean hasNumeric(String stringInput){CG.log("IN1 - 5427155538: { row: 255 }");
        Pattern pattern = Pattern.compile("^[0-9]+");
        Matcher matcher = pattern.matcher(stringInput);
CG.log("OUT2 - 7304226402: { row: 258 }");CG.log("IN2.1 - 7304226402: ");CG.log("IN4 - 4775410045: { row: 258 }");        if (matcher.matches()) return true;CG.log("OUT3 - 7304226402: { row: 258 }");
        return false;
    CG.log("OUT5 - 4775410045: { row: 260 }");}
    public static boolean isWord(String stringInput){CG.log("IN1 - 4052269044: { row: 261 }");
        Pattern pattern = Pattern.compile("[\\w]+");
        Matcher matcher = pattern.matcher(stringInput);
CG.log("OUT2 - 5571431608: { row: 264 }");CG.log("IN2.1 - 5571431608: ");CG.log("IN4 - 1623382273: { row: 264 }");        if (matcher.matches()) return true;CG.log("OUT3 - 5571431608: { row: 264 }");
        return false;
    CG.log("OUT5 - 1623382273: { row: 266 }");}
    public static boolean isWords(String stringInput){CG.log("IN1 - 2126688442: { row: 267 }");
        Pattern pattern = Pattern.compile("[\\w\\s]+");
        Matcher matcher = pattern.matcher(stringInput);
CG.log("OUT2 - 3264178394: { row: 270 }");CG.log("IN2.1 - 3264178394: ");CG.log("IN4 - 580426954: { row: 270 }");        if (matcher.matches()) return true;CG.log("OUT3 - 3264178394: { row: 270 }");
        return false;
    CG.log("OUT5 - 580426954: { row: 272 }");}
}

