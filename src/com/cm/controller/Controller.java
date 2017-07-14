package com.cm.controller;

import java.math.BigInteger;
import java.util.ArrayList;

import com.cm.model.User;
import com.cm.techservices.ReadRecords;
import com.cm.techservices.WriteRecords;

/**
 * @author prashant kagwad
 */
public class Controller {

	public ArrayList<User> readRecords() {

		String fileName = System.getProperty("user.dir")
				+ "\\records\\userrecords.txt";

		ReadRecords readRecords = new ReadRecords();
		readRecords.readFromFile(fileName);
		// readRecords.printData();

		ArrayList<User> users = readRecords.getUser();
		return users;
	}

	public void writeRecords(ArrayList<User> userList) {

		String fileName = System.getProperty("user.dir")
				+ "\\records\\userrecords.txt";

		WriteRecords w = new WriteRecords();
		w.setUser(userList);
		w.writeToFile(fileName);
	}

	public void writeTestRecords() {

		String fileName = System.getProperty("user.dir")
				+ "\\records\\userrecords.txt";

		System.out.println(fileName);

		ArrayList<User> userList = new ArrayList<User>();

		User user = new User();
		// Populate the single User data
		user.setFirstName("Prashant");
		user.setLastName("Kagwad");
		user.setMiddleInitial('D');
		user.setAddressLine1("7825 Mccallum Blvd.,");
		user.setAddressLine2("Apt. 1701");
		user.setCity("Dallas");
		user.setState("Texas");
		BigInteger zipCode = new BigInteger("123456789");
		user.setZipCode(zipCode);
		BigInteger phoneNumber = new BigInteger("12345678901234568901");
		user.setPhoneNumber(phoneNumber);
		user.setGender('M');
		userList.add(user);

		User user1 = new User();
		// Populate the single User data
		user1.setFirstName("Prashant");
		user1.setLastName("Kagwad");
		user1.setMiddleInitial('*');
		user1.setAddressLine1("7825 Mccallum Blvd.,");
		user1.setAddressLine2("NULL");
		user1.setCity("Dallas");
		user1.setState("Texas");
		BigInteger zipCode1 = new BigInteger("123456789");
		user1.setZipCode(zipCode1);
		BigInteger phoneNumber1 = new BigInteger("12345678901234568901");
		user1.setPhoneNumber(phoneNumber1);
		user1.setGender('M');
		userList.add(user1);

		WriteRecords w = new WriteRecords();
		w.setUser(userList);
		w.writeToFile(fileName);

	}
}
