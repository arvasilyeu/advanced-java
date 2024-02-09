package com.avas.db.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

	@Override
	public void save(User user) {
		var conn = Database.getInstance().getConnection();
		try {
			var stmt = conn.prepareStatement("insert into user (name) values (?)");
			stmt.setString(1, user.getName());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new DaoException(e); 
		}
	}

	@Override
	public Optional<User> findById(int id) {

		var conn = Database.getInstance().getConnection();
		try {
			var stmt = conn.prepareStatement("select * from user where id=?");
			stmt.setInt(1, id);

			var rs = stmt.executeQuery();
			
			if (rs.next()) {
				return Optional.of(new User(id, rs.getString("name"))); 
			}
			
			stmt.close();
		} catch (SQLException e) {
			throw new DaoException(e); 
		}
		
		return Optional.empty();
	}
	
	@Override
	public List<User> getAll() {
		List<User> users = new ArrayList<User>();
		
		var conn = Database.getInstance().getConnection();
		try {
			var stmt = conn.createStatement();
			var rs = stmt.executeQuery("select * from user");
			
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				users.add(new User(id, name));
			}
			
			stmt.close();
		} catch (SQLException e) {
			throw new DaoException(e); 
		}
		return users;
	}

	@Override
	public void update(User user) {
		var conn = Database.getInstance().getConnection();
		try {
			var stmt = conn.prepareStatement("update user set name=? where id=?");
			stmt.setString(1, user.getName());
			stmt.setInt(2, user.getId());

			stmt.executeUpdate();
			
			stmt.close();
		} catch (SQLException e) {
			throw new DaoException(e); 
		}
	}

	@Override
	public void delete(int id) {
		var conn = Database.getInstance().getConnection();
		try {
			var stmt = conn.prepareStatement("delete from user where id=?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new DaoException(e); 
		}
	}

}
