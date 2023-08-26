package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HomePage{
	private Stage stage;
	private Scene scene;
	
	private BorderPane wrapper;
	private GridPane loginForm;
	private FlowPane buttonWrapper;
	private Label titleLabel, usernameLabel, passwordLabel;
	private TextField usernameTF, passwordTF;
//	private TableView<User> tableUser;
	Button loginButton, registerButton;
	
	public HomePage (Stage stage) {
		this.stage = stage;
		this.stage.setTitle("Home page");
		initialize();
		setLayout();
		setAlignment();
		this.scene = new Scene(wrapper, 600, 400);
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
	
	public Scene getScene() {
		return this.scene;
	}

}
