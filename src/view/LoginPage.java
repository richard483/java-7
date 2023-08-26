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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPage implements EventHandler<ActionEvent>{
	private Stage stage;
	private Scene scene;
	
	private BorderPane wrapper;
	private GridPane loginForm;
	private FlowPane buttonWrapper;
	private Label titleLabel, usernameLabel, passwordLabel;
	private TextField usernameTF, passwordTF;
	Button loginButton, registerButton;
	DatabaseConnection db;
	HomePage homePage;
	
	public LoginPage (Stage stage, DatabaseConnection db) {
		this.stage = stage;
		this.stage.setTitle("Login page");
		initialize();
		setLayout();
		setAlignment();
		addEventListener();
		this.scene = new Scene(wrapper, 300, 200);
		this.db = db;
	}
	
	private void initialize() {
		wrapper = new BorderPane();
		loginForm = new GridPane();
		buttonWrapper = new FlowPane();
		
		titleLabel = new Label("Login");
		usernameLabel = new Label("Username");
		passwordLabel = new Label("Password");
		usernameTF = new TextField();
		passwordTF = new TextField();
		
		loginButton = new Button("Login");
		registerButton = new Button("Register");
	}
	
	private void setLayout() {
		loginForm.add(usernameLabel, 0, 0);
		loginForm.add(usernameTF, 1, 0);
		loginForm.add(passwordLabel, 0, 1);
		loginForm.add(passwordTF, 1, 1);
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
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	private boolean validateLoginInput() {
		boolean result = true;
		String error = null;
		if(usernameTF.getText() == "") {
			error = error + "- Username must not be null!\n";
		}
		
		if(passwordTF.getText() == "") {
			error = error + "- Password must not be null!\n";
		}
		if(error != null) {
			result = false;
			error = "Found some error(s):\n" + error;
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Username must not be null!");
			alert.show();
		}
		
		return result;
	}

	@Override
	public void handle(ActionEvent arg0) {
		if(arg0.getSource() == loginButton) {
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
		}
		
	}

}
