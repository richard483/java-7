import java.sql.SQLException;

import dbconnection.DatabaseConnection;
import javafx.application.Application;
import javafx.stage.Stage;
import model.User;
import view.AuthPage;

public class Main extends Application{
	
	DatabaseConnection db = new DatabaseConnection();
	
	
	public Main() {
//		printUserData();
//		db.exec("INSERT INTO `users`(`username`, `email`, `password`, `age`) "
//				+ "VALUES ('Nabila','nabila@email.com','passwordNabila123','20')");
		
//		User user = new User("NotHadi", "Nothadi@hadi.com", "passwordhadi123", 25);
		
		// ? System.out.println("Success create user!") : System.out.println("Error creating user");
//		db.createUser(user);
		printUserData();
	}
	
	public void printUserData() {
//		db.resultSet = db.execQuery("SELECT * FROM `users`");
		db.resultSet = db.getUser();
		User user;
		
		try {
//			while (db.resultSet.next()) {
//				user = new User(
//						Integer.valueOf(db.resultSet.getObject("id").toString()),
//						db.resultSet.getObject(2).toString(),
//						db.resultSet.getObject(3).toString(),
//						db.resultSet.getObject(4).toString(),
//						Integer.valueOf(db.resultSet.getObject(5).toString()));
//				System.out.println(user.toString());
//			}
			
			while (db.resultSet.next()) {
				user = new User(
						Integer.valueOf(db.resultSet.getObject("id").toString()),
						db.resultSet.getObject(2).toString(),
						db.resultSet.getObject(3).toString(),
						db.resultSet.getObject(4).toString(),
						Integer.valueOf(db.resultSet.getObject(5).toString()));
				System.out.println(user.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Main();
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		AuthPage loginPage = new AuthPage(stage, db, false);
		stage.setScene(loginPage.getScene());
		stage.show();
	}

}
