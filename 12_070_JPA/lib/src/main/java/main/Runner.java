package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import entities.User;
import repositories.UserDao;

@Component
public class Runner implements CommandLineRunner {
	
	@Autowired
	private UserDao userDao;

	@Override
	public void run(String... args) throws Exception {
		
//		System.out.println("=====================================");
//		var user1 = new User("Neo", "exapmple@email.com");
////		var user1 = new User("Morpheos", "exapmple@email.com");
//		var user = userDao.save(user1);
//		System.out.println(user);
//		
//		System.out.println("=====================================");
//		System.out.println("findById");
//		var retrievedUser = userDao.findById(user.getId());
//		if (retrievedUser.isPresent()) {
//			System.out.println(retrievedUser.get());
//		}
//		
//		System.out.println("=====================================");
//		System.out.println("findAll");
//		userDao.findAll().forEach(System.out::println);
		
		userDao.findByName("Morpheos").forEach(u -> System.out.println("Find by name: " + u));
		
		
		
		
	}
}