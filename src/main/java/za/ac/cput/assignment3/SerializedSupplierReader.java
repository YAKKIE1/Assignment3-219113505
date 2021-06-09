/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.assignment3;

import java.io.*;
import java.util.*;

/**
 *
 * @author Ali
 */
public class SerializedSupplierReader {
    private ObjectInputStream input;
    BufferedWriter suppliertxt;
    private ArrayList<Supplier> supplier = new ArrayList<>();
    Stakeholder sta;
    
    
    //Open ser file
    public void openFile(){
        try{
            input = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
        }
        catch(IOException ex){
            System.out.println("Opening File Error: " + ex.getMessage());
        }
    }
    
    //Close ser file
    public void closeFile(){
        try{
            input.close();
            System.out.println("***.ser File has been closed!***");
        }
        catch(IOException ex){
            System.out.println("Error clsing ser file: " + ex.getMessage());
        }
    }
    
    //add supplier to arraylist
    public void addSupplierArray(){
        try {
            while (true) {
                sta = (Stakeholder) input.readObject();
                if (sta instanceof Supplier) {
                    supplier.add((Supplier) sta);
                }
            }
        }
        catch(EOFException ex){
            System.out.println("EOF reached");
        }
        catch(ClassCastException ex){
            System.out.println("Cast Error" + ex.getMessage());
        }
        catch(ClassNotFoundException ex){
            System.out.println("Class error reading ser file: " + ex.getMessage());
        }
        catch(IOException ex){
            System.out.println("Error reading from ser file: " + ex.getMessage());
        }
    }
    
    //Sort by supplier name
    public void sortSupplierName(){
        supplier.sort(Comparator.comparing(Supplier::getName));
    }
    
    //check if supplier display is correct
    public void displaySupplier(){
        System.out.println("===========================Supplier=============================");
        System.out.println("ID\tName\t\t\tProd Type\tDescription");
        System.out.println("================================================================");
        
        for (int i = 0; i < supplier.size(); i++) {
                System.out.println(supplier.get(i));
        }
    }
    
    //create supplier txt file
    public void openSupplierText(){
        try{
            suppliertxt = new BufferedWriter(new FileWriter("supplierOutFile.txt"));
        }
        catch(IOException ex){
            System.out.println("Error creating file" + ex.getMessage());
        }
    }
    
    //write in supplier txt file
    public void writeSupplierText(){
        try{
            suppliertxt.write("======================================================Supplier====================================================");
            suppliertxt.newLine();
            suppliertxt.write("ID\tName\t\t\tProd Type\tDescription");
            suppliertxt.newLine();
            suppliertxt.write("==================================================================================================================");
            suppliertxt.newLine();
            
            for (int i = 0; i < supplier.size(); i++) {
                suppliertxt.write(supplier.get(i).toString());
                suppliertxt.newLine();
            }
            suppliertxt.close();
        }
        catch(IOException ex){
            System.out.println("Error writing file" + ex.getMessage());
        }
        System.out.println("Supplier file has been written");
    }
}
