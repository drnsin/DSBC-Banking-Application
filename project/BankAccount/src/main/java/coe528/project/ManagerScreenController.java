package coe528.project;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class ManagerScreenController {
    
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField balanceField;
    @FXML
    private Label invalidUsername;
    @FXML
    private Label invalidPassword;
    @FXML
    private Label invalidBalance;
    @FXML
    private Label blankField;
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
        
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank() || balanceField.getText().isBlank()) {
            blankField.setVisible(true);
        } else {
            blankField.setVisible(false);
            
            String username = usernameField.getText();
            String password = passwordField.getText();
            String balance = balanceField.getText();
            
            double balanceValue = 0.0;
            
            boolean usernameCheck = false;
            boolean passwordCheck = false;
            boolean balanceCheck = false;
            
            if(checkExistingUsername(username)) {
                invalidUsername.setText("Username already exists!");
                invalidUsername.setVisible(true);
            } else if(!username.matches("^[a-zA-Z0-9]*$")) {
                invalidUsername.setText("Username can only contain alphanumeric characters!");
                invalidUsername.setVisible(true);
            } else {
                invalidUsername.setVisible(false);
                usernameCheck = true;
            }
            
            if(password.length() < 7) {
                invalidPassword.setText("Pasword must be at least 8 charactes long!");
                invalidPassword.setVisible(true);
            } else {
                invalidPassword.setVisible(false);
                passwordCheck = true;
            }
            
            try{
                balanceValue = Double.parseDouble(balance);
                int balInt = (int) balanceValue;
                int twoDecimals = (int) ((balanceValue - balInt) * 100);
                balanceValue = (double) balInt + ((double) twoDecimals / 100);
                balanceField.setText(String.valueOf(balanceValue));
                balanceCheck = true;
                invalidBalance.setVisible(false);
            } catch(Exception e) {
                invalidBalance.setText("Enter valid balance!");
                invalidBalance.setVisible(true);
            }
            
            if(usernameCheck && passwordCheck && balanceCheck) {
                System.out.println(username);
                System.out.println(password);
                System.out.println(balanceValue);
                manager.addCustomer(username, password, "Customer", balanceValue);
            }
    
        }
        
    }
    
    @FXML
    private void logoutButtonClicked(ActionEvent event) {
        try {
            App.setRoot("LoginScreen");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private boolean checkExistingUsername(String username) {
        File file = new File("src/main/java/coe528/project/Files/" + username +".csv");
        return file.exists();
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
        manager.deleteCustomer(allAccountsBox.getSelectionModel().getSelectedItem());
        displayAccounts();
        allAccountsBox.setValue("Customer deleted!");
    }
    
}