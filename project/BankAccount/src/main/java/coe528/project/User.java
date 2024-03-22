/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Darren
 */
public class User {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    } 
    
    public User login() {
        try {
            File file = new File("src/main/java/coe528/project/Files/" + username +".csv");
            Scanner scanner = new Scanner(file);
            String[] creds = scanner.next().split(",");
            String username = creds[0];
            String password = creds[1];
            String role = creds[2];
            
            scanner.close();
            
            if(creds.length > 3 && username.equalsIgnoreCase(this.username) && password.equals(this.password) && role.equals("Customer")) {
                double balance = Double.parseDouble(creds[3]);
                String level = creds[4];
                return new Customer(username, password, role, balance, level);
            } else if(username.equals("admin") && password.equals("admin") && role.equals("Manager")){
                return Manager.getManager();
            } else {
                return null;
            }
        } catch(FileNotFoundException ex) {
            System.out.println("Exception encountered: " + ex);
            return null;
        }
    }
    
    public String checkLevel(double balance) {
        if(balance < 10000) {
            return "Silver";
        } else if(balance >= 10000 && balance < 20000) {
            return "Gold";
        } else {
            return "Platinum";
        }
    }
    
    @Override
    public String toString() {
        return username + "," + password + "," + role;
    }
    
}
