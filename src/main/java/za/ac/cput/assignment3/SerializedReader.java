/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.assignment3;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ali Mohamed - 219113505
 */
public class SerializedReader extends Supplier{
    private ObjectInputStream input;
    BufferedWriter customertxt;
    private ArrayList<Customer> customer = new ArrayList<>();
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

    //Add customer to arraylist
    public void addCustomerArray(){
        try {
            while (true) {
                sta = (Stakeholder) input.readObject();
                if (sta instanceof Customer) {
                    customer.add((Customer) sta);
                }
            }
        }
        catch(EOFException ex){
            System.out.println("EOF reached");
        }
        catch(ClassNotFoundException ex){
            System.out.println("Class error reading ser file: " + ex.getMessage());
        }
        catch(IOException ex){
            System.out.println("Error reading from ser file: " + ex.getMessage());
        }
        
    }
    
    //sort by customer ID
    public void sortCustomerID(){
        customer.sort(Comparator.comparing(Stakeholder::getStHolderId));
    }
    
    //Check is display is correct
    public void displayCustomer(){
        System.out.println("======================================================Customer====================================================");
        System.out.println("ID\t\tName\t\tSurname\t\tArea\t\tDate of birth\tCredit\t\tRent");
        System.out.println("==================================================================================================================");
        for (int i = 0; i < customer.size(); i++) {
                System.out.println(customer.get(i));
        }
    }
    
    //create customer txt file
    public void openCustomerText(){
        try{
            customertxt = new BufferedWriter(new FileWriter("customerOutFile.txt"));
        }
        catch(IOException ex){
            System.out.println("Error creating file" + ex.getMessage());
        }
    }
    
    //write in custoemr txt file
    public void writeCustomerText() {
        try {
            customertxt.write("======================================================Customer====================================================");
            customertxt.newLine();
            customertxt.write("ID\t\tName\t\tSurname\t\t\tDate of birth\t\tAge");
            customertxt.newLine();
            customertxt.write("==================================================================================================================");
            customertxt.newLine();

            //Write age
            int ageList[] = new int[customer.size()];
           // LocalDate todayDate;
            Period diff;
            for (int i = 0; i < customer.size(); i++) {
                LocalDate id = LocalDate.parse(customer.get(i).getDateOfBirth());
                diff = Period.between(id, LocalDate.now());
                ageList[i] = diff.getYears();
            }
            
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        
            for (int i = 0; i < customer.size(); i++) {
                try {
                    String arrayDate = customer.get(i).getDateOfBirth();
                    Date list = format1.parse(arrayDate);
                    DateFormat format2 = new SimpleDateFormat("dd MMM yyyy");
                    customer.get(i).setDateOfBirth(format2.format(list));
                    //System.out.println("date:" + format2.format(list));
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                } catch (DateTimeException ex) {
                    System.out.println("Could not parsed" + ex.getMessage());
                }
            }
            
            SerializedReader c = new SerializedReader();
            for (int i = 0; i < customer.size(); i++) {
                if (customer.get(i).getSurName().length() < 8) {

                    customertxt.write(customer.get(i).getStHolderId() + "\t\t" + customer.get(i).getFirstName() + "\t\t" + customer.get(i).getSurName() + "\t\t\t" + customer.get(i).getDateOfBirth() + "\t\t" + ageList[i] + "\t");
                    customertxt.newLine();
                } else {
                    customertxt.write(customer.get(i).getStHolderId() + "\t\t" + customer.get(i).getFirstName() + "\t\t" + customer.get(i).getSurName() + "\t\t" + customer.get(i).getDateOfBirth() + "\t\t" + ageList[i] + "\t");
                    customertxt.newLine();
                }
            }
            
            //rent
            customertxt.write("Number of customer who can rent: " + rentYes());
            customertxt.newLine();
            customertxt.write("Number of customer who cannot rent: " + rentNo());
            customertxt.close();
        } catch (IOException ex) {
            System.out.println("Error writing file" + ex.getMessage());
        }
        System.out.println("Customer file has been written");
    }
    
    public int rentYes(){
        int rentYes = 0;

        for (int i = 0; i < customer.size(); i++) {
            customer.get(i);
            if (customer.get(i).getCanRent() == true) {
                rentYes++;
            }
        }
        return rentYes;
    }
    
    public int rentNo(){
        int rentNo = 0;
        
        for (int i = 0; i < customer.size(); i++) {
            customer.get(i);
             if(customer.get(i).getCanRent() == false){
                rentNo++;
            }
        }
        return rentNo;
    }
    
    /*
    public int age(){
        String inputDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Period diff = null;
        
        for (int i = 0; i < customer.size(); i++) {
            inputDate = customer.get(i).getDateOfBirth();
            LocalDate age = LocalDate.parse(inputDate, formatter);
            diff = Period.between(age, LocalDate.now());
        
            //System.out.println(diff.getYears());
        }
        diff = Integer.parseInt(Period.between(age, LocalDate.now()));
        return age;
    }
    */
    
    public int ageTest(){
        int ageList[] = new int[customer.size()];
        int i;
         for ( i = 0; i < customer.size(); i++) {
            LocalDate id = LocalDate.parse(customer.get(i).getDateOfBirth()); 
            int list = id.getYear();
            ageList[i]= 2021- list;
        }
        return ageList[i];
    }
    
    public void dateFormat(){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        
        for(int i = 0; i < customer.size(); i++) {
            try {
                String arrayDate = customer.get(i).getDateOfBirth();
                Date list = format1.parse(arrayDate);
                DateFormat format2 = new SimpleDateFormat("dd MMM yyyy");
                customer.get(i).setDateOfBirth(format2.format(list));
                //System.out.println("date:" + format2.format(list));
            } 
            catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
            catch (DateTimeException ex){
                System.out.println("Could not parsed" + ex.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        SerializedReader create= new SerializedReader();
        //open and read customer
        create.openFile();
        create.addCustomerArray();
        create.sortCustomerID();
        create.closeFile();
        //Write customer to text file
        create.openCustomerText();
        create.writeCustomerText();
        
        
        //open and read supplier
        SerializedSupplierReader sup = new SerializedSupplierReader();
        sup.openFile();
        sup.addSupplierArray();
        sup.sortSupplierName();sup.closeFile();
        //Write supplier to text file
        sup.openSupplierText();
        sup.writeSupplierText();
        
        System.out.println("-----------------------------------------Test Ground-----------------------------------------------");
        create.displayCustomer();
        sup.displaySupplier();
//        create.age();
//        create.dateFormat();
    }
}
