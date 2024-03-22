package coe528.project;

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ManagerScreenController {
    
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label invalidUsername;
    @FXML
    private Label invalidPassword;
    @FXML
    private Label blankField;
    @FXML
    private Label addedLabel;
    @FXML
    private Button registerButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button logoutButton;
    @FXML
    private ComboBox<String> allAccountsBox;
    
    private Manager manager;
    
    public void setManager(Manager manager) {
        this.manager = manager;
    }
    
    @FXML
    private void registerButtonClicked(ActionEvent event) {
        
        addedLabel.setVisible(false);
        
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            blankField.setVisible(true);
        } else {
            blankField.setVisible(false);
            
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            double balanceValue = 0.0;
            
            boolean usernameCheck = false;
            boolean passwordCheck = false;
            
            if(Manager.getAccounts().contains(username)) {
                invalidUsername.setText("Username already exists!");
                invalidUsername.setVisible(true);
            } else if(!username.matches("^[a-zA-Z0-9]*$")) {
                invalidUsername.setText("Password must be at least 8 charactes long!");
                invalidUsername.setVisible(true);
            } else {
                invalidUsername.setVisible(false);
                usernameCheck = true;
            }
            
            if(password.length() <= 7) {
                invalidPassword.setVisible(true);
            } else {
                invalidPassword.setVisible(false);
                passwordCheck = true;
            }
            
            if(usernameCheck && passwordCheck) {
                System.out.println(username);
                System.out.println(password);
                manager.addCustomer(username, password, "Customer", 100.00);
                addedLabel.setVisible(true);
            }
    
        }
        
    }
    
    @FXML
    private void logoutButtonClicked(ActionEvent event) throws IOException {
        App.setRoot("LoginScreen");

    }
    
    @FXML
    private void displayAccounts() {
        allAccountsBox.getItems().clear();
        
        for(String username:Manager.getAccounts()) {
            allAccountsBox.getItems().add(username);
        }
    }
    
    @FXML
    private void deleteAccount(ActionEvent event) {
        if(manager.deleteCustomer(allAccountsBox.getSelectionModel().getSelectedItem())) {
            displayAccounts();
            allAccountsBox.setValue("Customer deleted!");
        } else {
            allAccountsBox.setValue("Customer not deleted!");
        }
    }
    
}