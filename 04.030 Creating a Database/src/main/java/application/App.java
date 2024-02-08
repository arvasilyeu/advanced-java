package application;

import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
//		Class.forName("org.sqlite.JDBC");
//		String dbUrl = "jdbc:sqlite:people.db";
//		var conn = DriverManager.getConnection(dbUrl);
		
//		Class.forName("com.mysql.cj.jdbc.Driver");
		String dbUrl = "jdbc:mysql://localhost:3306/people";
		var conn = DriverManager.getConnection(dbUrl, "root", "1234567890");
		
		conn.setAutoCommit(false);
		
		var stmt = conn.createStatement();
		
//		var createTable = "create table if not exists user (id integer primary key, name not null)";
//		var createTable = "create table user (id integer primary key, name not null)";
//		stmt.execute(createTable);
		
//======================================================================================================		
//		var insert = """
//				insert into user (id, name)
//				values
//					(0, 'Bob'),
//					(1, 'Mary');
//				""";
//		stmt.execute(insert);
//======================================================================================================
		int[] ids = {0, 1, 2};
		String[] names = {"Sue", "Bob", "Charley"};
		
		var insertQuery = "insert into user (id, name) values (?, ?)";
		var insertStmt = conn.prepareStatement(insertQuery);
		for(int i=0; i<ids.length; i++) {
			insertStmt.setInt(1, ids[i]);
			insertStmt.setString(2, names[i]);
			insertStmt.executeUpdate();
		}
		conn.commit();
		insertStmt.close();
//======================================================================================================		
		var rs = stmt.executeQuery("select * from user");
		
		while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			System.out.println(id + ": " + name);
		}
		
//		stmt.execute("drop table user");
		
		stmt.close();
		conn.close();
	}

}
