/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Darren
 */
public class Manager extends User {
    
    private static Manager manager = null;
    
    private static ArrayList<String> accounts;
    private final static File ALLACCOUNTS = new File("src/main/java/coe528/project/Files/accounts.csv");
    
    private Manager(String username, String password, String role) {
        super(username, password, role);
        accounts = new ArrayList<>();
        loadAccounts();
    }
    
    public static Manager getManager() {
        if (manager == null) {
            manager = new Manager("admin", "admin", "Manager");
        }
        return manager;
    }
    
    public static ArrayList<String> getAccounts() {
        return accounts;
    }
    
    public boolean addCustomer(String username, String password, String role, double balance) {
        if(accounts.contains(username)) {
            return false;
        } else {
            try {
                File userAccount = new File("src/main/java/coe528/project/Files/" + username +".csv");
                if(userAccount.createNewFile()) {
                    String level = checkLevel(balance);
                    Customer customer = new Customer(username, password, role, balance, level);
                    FileWriter writer = new FileWriter(userAccount);
                    writer.write(customer.toString());
                    writer.close();
                    System.out.println("File created.");
                    accounts.add(username);
                    writeAccounts();
                    return true;
                } else {
                    System.out.println("File already exists");
                    return false;
                }
            } catch(IOException ex) {
                System.out.println("Exception encountered: " + ex);
                return false;
            }
        }  
    }
    
    public boolean deleteCustomer(String username) {
        if(!accounts.contains(username)) {
            return false;
        } else {
            File account = new File("src/main/java/coe528/project/Files/" + username +".csv");
            if(account.delete()) {
                System.out.println("File deleted.");
                accounts.remove(username);
                return writeAccounts();
            } else {
                System.out.println("File was not deleted.");
                return false;
            }
        }
    }
    
    private void loadAccounts() {
        try {
            Scanner scanner = new Scanner(ALLACCOUNTS);
            scanner.nextLine();
            while(scanner.hasNext()) {
                String username = scanner.nextLine();
                accounts.add(username);
            }
            scanner.close();    
        } catch(FileNotFoundException e) {
            System.out.println("Accounts not loaded!");
        }
     }
    
    private boolean writeAccounts() {
        try {
            FileWriter writer = new FileWriter(ALLACCOUNTS);
            writer.write("USERNAME");
            for(String s : accounts) {
                writer.write("\n" + s);
            }
            writer.close();
            return true;
        } catch(IOException ex) {
            System.out.println("File was not deleted.");
            return false;
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + "\n";
    }
    
}
