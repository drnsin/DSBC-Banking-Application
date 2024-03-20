/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coe528.project;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Darren
 */
public class CustomerScreenController implements Initializable {
    
    @FXML
    private Label usernameLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private Label levelFeeLabel;
    @FXML
    private TextField amountField;
    @FXML
    private ComboBox transactionTypeBox;
    @FXML
    private ListView transactionList;
    @FXML
    private Button submitButton;
    @FXML
    private Button logoutButton;
    
    private Customer customer;
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
        usernameLabel.setText(customer.getUsername());
        roleLabel.setText(customer.getRole());
        balanceLabel.setText("$" + customer.getBalance());
        levelLabel.setText(customer.getLevel());
        for(String s : customer.gettransactionList()) {
            transactionList.getItems().add(0, s);
        }
        levelFeeCheck();
    }
    
    @FXML
    private void logoutButtonClicked(ActionEvent event) {
        try {
            App.setRoot("LoginScreen");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void addTransaction(ActionEvent event) {
        double balanceValue = 0.0;
        
        boolean balanceCheck = false;
        
        try{
            balanceValue = Double.parseDouble(amountField.getText());
            int balInt = (int) balanceValue;
            int twoDecimals = (int) ((balanceValue - balInt) * 100);
            balanceValue = (double) balInt + ((double) twoDecimals / 100);
            amountField.setText(String.valueOf(balanceValue));
            balanceCheck = true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        if(balanceCheck && transactionTypeBox.getSelectionModel().getSelectedItem() != null) {
            String category = transactionTypeBox.getSelectionModel().getSelectedItem().toString();
            if (customer.makeTransaction(balanceValue, category)) { 
                if(!category.equals("Deposit")) {
                    balanceValue *= -1;
                }   
                transactionList.getItems().add(0, category + ": $" + balanceValue);
                balanceLabel.setText("$" + customer.getBalance());
                levelLabel.setText(customer.getLevel());
                levelFeeCheck();
            }
        }
        
    }
    
    @FXML
    private void levelFeeCheck() {
        if(customer.getLevel().equals("Platinum")) {
            levelFeeLabel.setText("Platinum: $0.00 Fee");
        } else if(customer.getLevel().equals("Gold")) {
            levelFeeLabel.setText("Gold: $10.00 Fee");
        } else {
            levelFeeLabel.setText("Silver: $20.00 Fee");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("Deposit", "Withdrawal", "Dining Out", "Groceries", "Entertainment", "Shopping",
                                                                        "Transportation", "Home", "Travel", "Education", "Miscellaneous");
        transactionTypeBox.setItems(list);
        
    }
  
}
