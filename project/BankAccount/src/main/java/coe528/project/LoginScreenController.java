package coe528.project;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LoginScreenController {
    
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label invalidCreds;
    
    @FXML
    private void onLoginHover() throws IOException {
        loginButton.setStyle("-fx-background-color: DARKGREEN");
    }
    
    @FXML
    private void onLoginExit() throws IOException {
        loginButton.setStyle("-fx-background-color: GREEN");
    }
    
    @FXML
    private void keyPressed(KeyEvent e) {
        if(e.getCode().equals(KeyCode.ENTER)) {
            loginButtonClicked();
        }
    }
    
    @FXML
    private void loginButtonClicked() {
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            invalidCreds.setText("You must fill in all the fields!");
            invalidCreds.setVisible(true);
        } else {
            invalidCreds.setVisible(false);
            
            User tempUser = new User(usernameField.getText(), passwordField.getText(), "temp");
            
            if(tempUser.login() == null) {
                invalidCreds.setText("Invalid username/password!");
                invalidCreds.setVisible(true);
            } else {
                invalidCreds.setVisible(false);
                try {
                    if(tempUser.login() instanceof Manager) {
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("managerScreen.fxml"));     

                        Parent root = (Parent)fxmlLoader.load();          
                        ManagerScreenController controller = fxmlLoader.<ManagerScreenController>getController();
                        controller.setManager((Manager)tempUser.login());
                        App.setRoot(root);
                     
                    } else if(tempUser.login() instanceof Customer) {
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("CustomerScreen.fxml"));
                        System.out.println(App.class.getResource("CustomerScreen.fxml"));
                        
                        Parent root = (Parent)fxmlLoader.load();          
                        CustomerScreenController controller = fxmlLoader.<CustomerScreenController>getController();
                        controller.setCustomer((Customer)tempUser.login());
                        App.setRoot(root);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            
        }
            
    }
    
}
