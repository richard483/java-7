package dbconnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

public class DatabaseConnection {
	
	public Connection connection;
	public Statement statement;
	public ResultSet resultSet;
	public ResultSetMetaData resultSetMetaData;
	public PreparedStatement preparedStatement;
	
	public DatabaseConnection () {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/java_7", "root", "");
			
			statement = connection.createStatement();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet execQuery(String query) {
		try {
			// executeQuery: return resSet
			resultSet = statement.executeQuery(query);
			resultSetMetaData = resultSet.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultSet;
	}
	
	public void exec(String query) {
		try {
			// execute: tidak return resSet
			statement.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean createUser(User user) {
		boolean res = false;
		try {
			preparedStatement = connection.
					prepareStatement("INSERT INTO `users`(`username`, `email`, `password`, `age`) "
							+ "VALUES (?,?,?,?)");
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setInt(4, user.getAge());
			
			res = preparedStatement.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
	
	public boolean validateLogin(String username, String password) {
		boolean res = false;
		try {
			preparedStatement = connection.
					prepareStatement("SELECT * FROM `users` WHERE `username` = ? AND `password` = ? ");

			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			
//			preparedStatement.execute()
			// untuk function execute, dia hanya mengecek apakah query yang dimasukkan bisa me return result set'
			// bukan mengcek apakah terdapat resultset dalam query yang dimasukkan
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				System.out.println("true res");
				res = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}

}
