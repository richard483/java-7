package view;

import dbconnection.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;

public class AuthPage implements EventHandler<ActionEvent>{
	private Stage stage;
	private Scene scene;
	
	private BorderPane wrapper;
	private GridPane loginForm;
	private FlowPane buttonWrapper;
	private Label titleLabel, usernameLabel, passwordLabel, emailLabel, ageLabel, reInsertPasswordLabel;
	private TextField usernameTF, emailTF, ageTF;
	private PasswordField passwordTF, reInsertPasswordTF;
	private boolean isRegister;
	Button loginButton, registerButton;
	DatabaseConnection db;
	HomePage homePage;
	
	public AuthPage (Stage stage, DatabaseConnection db, boolean isRegister) {
		this.isRegister = isRegister;
		this.stage = stage;
		if(isRegister) {
			this.stage.setTitle("Register page");
		}else {
			this.stage.setTitle("Login page");
		}
		
		initialize();
		setLayout();
		setAlignment();
		addEventListener();
		this.scene = new Scene(wrapper, 300, 400);
		this.db = db;
	}
	
	private void initialize() {
		wrapper = new BorderPane();
		loginForm = new GridPane();
		buttonWrapper = new FlowPane();
		
		if(isRegister) {
			titleLabel = new Label("Register");
		}else {
			titleLabel = new Label("Login");
		}
		
		usernameLabel = new Label("Username");
		passwordLabel = new Label("Password");
		usernameTF = new TextField();
		passwordTF = new PasswordField();
		
		if(isRegister) {
			emailTF = new TextField();
			ageTF = new TextField();
			emailLabel = new Label("Email");
			ageLabel = new Label("Age");
			reInsertPasswordLabel = new Label("Reinsert password");
			reInsertPasswordTF = new PasswordField();
		}
		
		
		loginButton = new Button("Login");
		registerButton = new Button("Register");
	}
	
	private void setLayout() {
		loginForm.add(usernameLabel, 0, 0);
		loginForm.add(usernameTF, 1, 0);
		loginForm.add(passwordLabel, 0, 1);
		loginForm.add(passwordTF, 1, 1);
		
		if(isRegister) {
			loginForm.add(reInsertPasswordLabel, 0, 2);
			loginForm.add(reInsertPasswordTF, 1, 2);
			
			loginForm.add(emailLabel, 0, 3);
			loginForm.add(emailTF, 1, 3);
			loginForm.add(ageLabel, 0, 4);
			loginForm.add(ageTF, 1, 4);
		}
		
		loginForm.setVgap(10);
		loginForm.setHgap(10);
		
		buttonWrapper.getChildren().add(loginButton);
		buttonWrapper.getChildren().add(registerButton);
		buttonWrapper.setHgap(10);
		buttonWrapper.setVgap(10);
		
		wrapper.setTop(titleLabel);
		wrapper.setCenter(loginForm);
		wrapper.setBottom(buttonWrapper);
	}
	
	private void setAlignment() {
		BorderPane.setAlignment(titleLabel, Pos.BOTTOM_CENTER);
		
		loginForm.setAlignment(Pos.BASELINE_CENTER);
		BorderPane.setMargin(loginForm, new Insets(20));
		
		buttonWrapper.setAlignment(Pos.BASELINE_CENTER);
		BorderPane.setMargin(buttonWrapper, new Insets(0, 0, 20, 0));
	}
	
	private void addEventListener() {
		loginButton.setOnAction(this);
		registerButton.setOnAction(this);
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	private boolean validateLoginInput() {
		boolean result = true;
		String error = "";
		if(usernameTF.getText() == "") {
			error = error + "- Username must not be null!\n";
		}
		
		if(passwordTF.getText() == "") {
			error = error + "- Password must not be null!\n";
		}
		if(error != "") {
			result = false;
			error = "Found some error(s):\n" + error;
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText(error);
			alert.show();
		}
		
		return result;
	}
	
	private boolean validateRegisterInput() {
		boolean result = true;
		String error = "";
		if(usernameTF.getText() == "") {
			error = error + "- Username must not be null!\n";
		}
		
		if(passwordTF.getText() == "") {
			error = error + "- Password must not be null!\n";
		}
		
		if(reInsertPasswordTF.getText() == "") {
			error = error + "- Re-insert password must not be null!\n";
		}
		
		if(emailTF.getText() == "") {
			error = error + "- Email must not be null!\n";
		}
		
		if(ageTF.getText() == "") {
			error = error + "- Age must not be null!\n";
		}
		
		if(!passwordTF.getText().equals(reInsertPasswordTF.getText())) {
			error = error + "- Password must be same!\n";
		}
		if(error != "") {
			result = false;
			error = "Found some error(s):\n" + error;
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText(error);
			alert.show();
		}
		
		return result;
	}

	@Override
	public void handle(ActionEvent arg0) {
		if(arg0.getSource() == loginButton) {
			if(isRegister) {
				stage.close();
				AuthPage loginPage = new AuthPage(stage, db, false);
				stage.setScene(loginPage.getScene());
				stage.show();
				return;
			}
			System.out.println("Input");
			if(validateLoginInput()) {
				System.out.println(usernameTF.getText() + passwordTF.getText());
				if(db.validateLogin(usernameTF.getText(), passwordTF.getText())) {
					stage.close();
					homePage = new HomePage(stage);
					stage.setScene(homePage.getScene());
					stage.show();
				}else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("Wrong credential!");
					alert.show();
				}
			}
		}else if(arg0.getSource() == registerButton) {
			if(!isRegister) {
				stage.close();
				AuthPage registerLoginPage = new AuthPage(stage, db, true);
				stage.setScene(registerLoginPage.getScene());
				stage.show();
				return;
			}
			if(validateRegisterInput()) {
				User user = new User(usernameTF.getText(),
						emailTF.getText(), passwordTF.getText(), Integer.valueOf(ageTF.getText()));
				db.createUser(user);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Register success!");
				alert.show();
			}
			
		}
		
	}

}
