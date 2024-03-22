/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package coe528.project;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
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
    private Label invalidAmount;
    @FXML
    private TextField amountField;
    @FXML
    private ComboBox transactionTypeBox;
    @FXML
    private ListView transactionListView;
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
            transactionListView.getItems().add(0, s);
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
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        double balanceValue = 0.0;
        
        boolean balanceCheck = false;
        String twoDecimals;
        
        try{
            twoDecimals = df.format(Double.parseDouble(amountField.getText()));
            amountField.setText(twoDecimals);
            balanceValue = Double.parseDouble(twoDecimals);
            balanceCheck = balanceValue > 0.00;
        } catch(NumberFormatException ex) {
            System.out.println("Exception encountered: " + ex);
            balanceCheck = false;
        }
        
        if(!balanceCheck || transactionTypeBox.getSelectionModel().getSelectedItem() == null) {
            invalidAmount.setVisible(true);
        } else {
          String category = transactionTypeBox.getSelectionModel().getSelectedItem().toString();
            twoDecimals = df.format(customer.makeTransaction(balanceValue, category));
            balanceValue = Double.parseDouble(twoDecimals);
            if (balanceValue != 0) {
                invalidAmount.setVisible(false);
                transactionListView.getItems().add(0, category + ": $" + twoDecimals);
                balanceLabel.setText("$" + df.format(customer.getBalance()));
                levelLabel.setText(customer.getLevel());
                levelFeeCheck();
            } else {
                invalidAmount.setVisible(true);
            }
        }
    }
    
    @FXML
    private void levelFeeCheck() {
        if(customer.getLevel().equals("Platinum")) {
            levelFeeLabel.setText("+ Platinum: $0.00 Fee");
        } else if(customer.getLevel().equals("Gold")) {
            levelFeeLabel.setText("+ Gold: $10.00 Fee");
        } else {
            levelFeeLabel.setText("+ Silver: $20.00 Fee");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("Deposit", "Withdrawal", "Online Purchase(s)");
        transactionTypeBox.setItems(list);
    }
  
}
