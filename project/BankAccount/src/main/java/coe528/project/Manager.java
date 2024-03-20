/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Darren
 */
public class Manager extends User {
    
    private static ArrayList<String> accounts;
    private final static File ALLACCOUNTS = new File("src/main/java/coe528/project/Files/accounts.csv");
    
    public Manager(String username, String password, String role) {
        super(username, password, role);
        accounts = new ArrayList<>();
        loadAccounts();
    }
    
    public static void loadAccounts() {
        accounts.clear();
        try {
            Scanner scanner = new Scanner(ALLACCOUNTS);
            scanner.nextLine();
            while(scanner.hasNext()) {
                String username = scanner.nextLine();
                accounts.add(username);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e);
        }
     }
    
    public void addCustomer(String username, String password, String role, double balance) {
        try {
            File userAccount = new File("src/main/java/coe528/project/Files/" + username +".csv");
            if(userAccount.createNewFile()) {
                String level = checkLevel(balance);
                Customer customer = new Customer(username, password, role, balance, level);
                FileWriter writer = new FileWriter(userAccount);
                writer.write(customer.toString());
                writer.close();
               
                FileWriter allWriter = new FileWriter(ALLACCOUNTS, true);
                allWriter.write("\n"+customer.getUsername());
                allWriter.close();
                System.out.println("File created.");
            } else {
                System.out.println("File already exists");
            }
        } catch(Exception e) {
            System.out.println("hi");
        }
    }
    
    public void deleteCustomer(String username) {
        
        try {
            File account = new File("src/main/java/coe528/project/Files/" + username +".csv");
            if(account.delete()) {
                System.out.println("File deleted.");
                
                File tempFile = new File("src/main/java/coe528/project/Files/tempaccounts.csv");

                BufferedReader reader = new BufferedReader(new FileReader(ALLACCOUNTS));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String lineToRemove = username;
                int userLine = lineToRemove.length();
                String currentLine;
                boolean firstLine = true;

                while((currentLine = reader.readLine()) != null) {
                    // trim newline when comparing with lineToRemove
                    String trimmedLine = currentLine.trim();
                    if(!trimmedLine.substring(0, userLine).equals(lineToRemove)) {
                        if(firstLine) {
                            writer.write(currentLine);
                            firstLine = false;
                        } else {
                            writer.write(System.getProperty("line.separator") + currentLine);
                        }
                    }
                }
                writer.close(); 
                reader.close(); 
                ALLACCOUNTS.delete();
                tempFile.renameTo(ALLACCOUNTS);
            } else {
                System.out.println("File was not deleted.");
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public static ArrayList<String> getAccounts() {
        loadAccounts();
        return accounts;
    }
    
    @Override
    public String toString() {
        return super.toString() + "\n";
    }
    
}
