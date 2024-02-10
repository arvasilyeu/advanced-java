package com.avas.db.mysql;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserDaoImplTest {

	private Connection conn;
	List<User> users;
	private static int NUM_TEST_UESERS = 2;

	private List<User> loadUsers() throws IOException {

		return Files.lines(Paths.get("greatexpectations.txt")).map(line -> line.split("[^A-Za-z]")).map(Arrays::asList)
				.flatMap(list -> list.stream()).filter(word -> word.length() > 3).limit(NUM_TEST_UESERS)
				.map(word -> new User(word)).collect(Collectors.toList());
	}

	private int getMaxId() throws SQLException {
		var stmt = conn.createStatement();
		var rs = stmt.executeQuery("select max(id) as id from user");

		rs.next();
		var id = rs.getInt("id");
		stmt.close();

		return id;
	}

	private List<User> getUsersInRange(int minId, int maxId) throws SQLException {
		List<User> users = new ArrayList<User>();

		var stmt = conn.prepareStatement("select * from user where id <= ? or id >= ?");
		stmt.setInt(1, minId);
		stmt.setInt(2, maxId);
		var rs = stmt.executeQuery();

		while (rs.next()) {
			users.add(new User(rs.getInt("id"), rs.getString("name")));

		}

		stmt.close();
		return users;
	}

	@Before
	public void setUp() throws SQLException, IOException {
		users = loadUsers();

		System.setProperty("env", "test");
		var props = Profile.getProperties("db");

		var db = Database.getInstance();
		db.connect(props);
		conn = db.getConnection();
		conn.setAutoCommit(false);
	}

	@After
	public void tearDown() throws SQLException {
		Database.getInstance().disconnect();
	}

	@Test
	public void testSave() throws SQLException {
		User user = new User("John");
		UserDao userDao = new UserDaoImpl();
		userDao.save(user);

		var stmt = conn.createStatement();
		var rs = stmt.executeQuery("select * from user order by id desc");
		var hasResult = rs.next();

		assertTrue("Can not retrieve inserted user.", hasResult);

		assertEquals("The user name doesn't match retrieved", user.getName(), rs.getString("name"));

		stmt.close();
	}

	@Test
	public void testSaveMultiplke() throws SQLException {
		UserDao userDao = new UserDaoImpl();

		for (User user : users) {
			userDao.save(user);
		}

		int maxId = getMaxId();

		for (int i = 0; i < users.size(); i++) {
			int id = (maxId - users.size()) + i + 1;
			users.get(i).setId(id);
		}

		var retrievedUsers = getUsersInRange((maxId - users.size()) + 1, maxId);

		assertEquals("The retrieved users size is wrong.", NUM_TEST_UESERS, retrievedUsers.size());
		assertEquals("Retrieved users don't match saved users", users, retrievedUsers);
	}

	@Test
	public void testFindAndUpdate() throws SQLException {
		UserDao userDao = new UserDaoImpl();
		var user = users.get(0);
		userDao.save(user);
		int maxId = getMaxId();
		user.setId(maxId);
		var retrievedUserOpt = userDao.findById(maxId);

		assertTrue("No user retrieved", retrievedUserOpt.isPresent());
		assertEquals("Retrieved user doesn't match saved user", user, retrievedUserOpt.get());

		user.setName("NewName");
		;
		userDao.update(user);
		retrievedUserOpt = userDao.findById(user.getId());
		assertTrue("No updated user retrieved", retrievedUserOpt.isPresent());
		assertEquals("Retrieved user doesn't match updated user", user, retrievedUserOpt.get());
	}

	@Test
	public void testGetAll() throws SQLException {
		UserDao userDao = new UserDaoImpl();

		for (User user : users) {
			userDao.save(user);
		}

		int maxId = getMaxId();

		for (int i = 0; i < users.size(); i++) {
			int id = (maxId - users.size()) + i + 1;
			users.get(i).setId(id);
		}

		var dbUsers = userDao.getAll();
		dbUsers = dbUsers.subList((dbUsers.size() - users.size()), dbUsers.size());

		assertEquals("The retrieved users size is wrong.", NUM_TEST_UESERS, dbUsers.size());
		assertEquals("Retrieved users don't match saved users", users, dbUsers);
	}

	@Test
	public void testDelete() throws SQLException {
		UserDao userDao = new UserDaoImpl();

		for (User user : users) {
			userDao.save(user);
		}

		int maxId = getMaxId();

		for (int i = 0; i < users.size(); i++) {
			int id = (maxId - users.size()) + i + 1;
			users.get(i).setId(id);
		}
		
		var deleteUserIndex = NUM_TEST_UESERS / 2;
		var deleteUser = users.get(deleteUserIndex);
		
		users.remove(deleteUser);
		userDao.delete(deleteUser.getId());

		var retrievedUsers = getUsersInRange((maxId - NUM_TEST_UESERS) + 1, maxId);

		assertEquals("The retrieved users size is wrong.", retrievedUsers.size(), users.size());
		assertEquals("Retrieved users don't match saved users", users, retrievedUsers);
	}
}
