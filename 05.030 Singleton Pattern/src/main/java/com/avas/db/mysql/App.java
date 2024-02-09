package com.avas.db.mysql;

import java.sql.SQLException;

public class App {
	public static void main(String[] args) {

		var props = Profile.getProperties("db");

		var db = Database.getInstance();

		try {
			db.connect(props);
		} catch (SQLException e) {
			System.out.println("Can not connect to database!");
			return;
		}
		System.out.println("Connected!");

		UserDaoImpl userDao = new UserDaoImpl();
//		userDao.save(new User("Neo"));
//		userDao.save(new User("John"));

//		var users = userDao.getAll();
//		for (User user : users) {
//			System.out.println(user);
//		}

		userDao.getAll().forEach(System.out::println);

		var userOpt = userDao.findById(3);
		if (userOpt.isPresent()) {
			System.out.println(userOpt.get());
		} else {
			System.out.println("No user retrieved.");
		}

		userDao.delete(1);
		userDao.getAll().forEach(System.out::println);

		userDao.update(new User(5, "John Week"));

		userOpt = userDao.findById(5);
		if (userOpt.isPresent()) {
			System.out.println(userOpt.get());
		} else {
			System.out.println("No user retrieved.");
		}

		try {
			db.disconnect();
		} catch (SQLException e) {
			System.out.println("Can not close database connection!");
		}
	}
}
