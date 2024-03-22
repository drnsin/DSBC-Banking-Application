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
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Darren
 */
public class Customer extends User {
    
    private double balance;
    private String level;
    private ArrayList<String> transactionList;
    
    public Customer(String username, String password, String role, double balance, String level) {
        super(username, password, role);
        this.balance = balance;
        this.level = level;
        transactionList = new ArrayList();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    
    public ArrayList<String> gettransactionList() {
        loadTransactions();
        return transactionList;
    }
    
    public double makeTransaction(double transaction, String type) {
        
         DecimalFormat df = new DecimalFormat("0.00");
         df.setRoundingMode(RoundingMode.FLOOR);
         String twoDecimal;
         
         double deduction;

        if(type.equals("Withdrawal") || type.equals("Deposit") || level.equals("Platinum")) {
            deduction = 0.0;
        } else {
            if(level.equals("Gold")) {
                deduction = -10.0;
            } else {
                deduction = -20.0;
            }
        }
        
        if(type.equals("Online Purchase(s)") && transaction < 50.00 || transaction - deduction > balance && !type.equals("Deposit")) {
            return 0;
        } else {

            if(!type.equals("Deposit")) {
                transaction *= -1;
            }
            
            twoDecimal = df.format(transaction + deduction);
            double total = Double.parseDouble(twoDecimal);

            balance += total;
            balance = Double.parseDouble(df.format(balance));

            setLevel(checkLevel(balance));
            
            transactionList.add(type + ": $" + twoDecimal);
            
            try {
                File userAccount = new File("src/main/java/coe528/project/Files/" + getUsername() +".csv");
                FileWriter writer = new FileWriter(userAccount, true);
                writer.write("\n" + type + ": $" + twoDecimal);
                writer.close();

                updateBalance();
            } catch(IOException e) {
                return 0;
            }
            
            return transaction + deduction;
            
        }
        
    }
    
    private void loadTransactions() {
        try {
            File userAccount = new File("src/main/java/coe528/project/Files/" + getUsername() +".csv");
            Scanner scanner = new Scanner(userAccount);
            scanner.nextLine();
            while(scanner.hasNextLine()) {
                transactionList.add(scanner.nextLine());
            }
            scanner.close();
        } catch(IOException e) {
            
        }
    }
    
    private void updateBalance() {
        try {
            File inputFile = new File("src/main/java/coe528/project/Files/" + getUsername() + ".csv");
            File tempFile = new File("src/main/java/coe528/project/Files/temp.csv");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToUpdate = getUsername();
            int userLine = lineToUpdate.length();
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if(trimmedLine.substring(0, userLine).equals(lineToUpdate)) {
                    writer.write(getUsername() + "," + getPassword() + "," + getRole() + "," + balance + "," + level);
                } else {
                    writer.write(System.getProperty("line.separator") + currentLine);
                }
            }
            writer.close(); 
            reader.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } catch(Exception e) {
            System.out.println("bro");
        }
    }
    
    public void logout() {
    }
    
    @Override
    public String toString() {
        return super.toString() + "," + balance + "," + level;
    }
    
}
