package com.avas.reflection_api;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

class User {
	private int userPrivateField;
	public int userPublicField;
	public int getUserPrivateField() {
		return userPrivateField;
	}
	private void setUserPrivateField(int userPrivateField) {
		this.userPrivateField = userPrivateField;
	}
	public int getUserPublicField() {
		return userPublicField;
	}
	private void setUserPublicField(int userPublicField) {
		this.userPublicField = userPublicField;
	}
}

class Emploee extends User {
	private int emploeePrivateField;
	public int emploeePublicField;
	public int getEmploeePrivateField() {
		return emploeePrivateField;
	}
	private void setEmploeePrivateField(int emploeePrivateField) {
		System.out.println("Setting emploeePrivateField by: " + emploeePrivateField);
		this.emploeePrivateField = emploeePrivateField;
	}
	public int getEmploeePublicField() {
		return emploeePublicField;
	}
	public void setEmploeePublicField(int emploeePublicField) {
		System.out.println("Setting emploeePublicField by: " + emploeePublicField);
		this.emploeePublicField = emploeePublicField;
	}
	@Override
	public String toString() {
		return "Emploee [emploeePrivateField=" + emploeePrivateField + ", emploeePublicField=" + emploeePublicField
				+ "]";
	}
}

public class App {
	public static void main(String[] args) 
			throws ClassNotFoundException, NoSuchFieldException, SecurityException, 
				NoSuchMethodException, IllegalAccessException, IllegalArgumentException, 
				InvocationTargetException {

		Class<Emploee> clazz1 = Emploee.class;
		System.out.println(clazz1);
		
		Class<?> clazz2 = Class.forName("com.avas.reflection_api.Emploee");
		System.out.println(clazz2);
		
		User u = new Emploee();
		Class<? extends User> clazz3 = u.getClass();
		System.out.println(clazz3);
		
		System.out.println("-----------------------------------------------------------------");
		
		Arrays.asList(clazz1.getFields()).forEach(System.out::println);
		Arrays.asList(clazz1.getDeclaredFields()).forEach(System.out::println);
		
		System.out.println("-----------------------------------------------------------------");
		
		Arrays.asList(clazz1.getMethods()).forEach(System.out::println);
		System.out.println();
		Arrays.asList(clazz1.getDeclaredMethods()).forEach(System.out::println);
		
		System.out.println("-----------------------------------------------------------------");
		
		var emploeePublicField = clazz1.getField("emploeePublicField");
		System.out.println(emploeePublicField);
		
		var user = new Emploee();
		emploeePublicField.set(user, 3);
		System.out.println(user);
		
		var emploeePrivateField = clazz1.getDeclaredField("emploeePrivateField");
		emploeePrivateField.setAccessible(true);
		emploeePrivateField.set(user, 4);
		System.out.println(user);
		
		System.out.println("-----------------------------------------------------------------");
		
		var method = clazz1.getDeclaredMethod("setEmploeePublicField", int.class); 
		System.out.println(method);
		
		System.out.println("-----------------------------------------------------------------");
		
		method.invoke(new Emploee(), 1);
		
		var setEmploeePrivateField = clazz1.getDeclaredMethod("setEmploeePrivateField", int.class); 
		setEmploeePrivateField.setAccessible(true);
		setEmploeePrivateField.invoke(new Emploee(), 2);
		
		System.out.println("-----------------------------------------------------------------");
		
		var methodExists = Arrays.stream(clazz1.getDeclaredMethods()).anyMatch(m -> m.getName().equals("setEmploeePrivateField"));
		System.out.println(methodExists);
		
		System.out.println("-----------------------------------------------------------------");
		
		
		
		
		
	}
}
