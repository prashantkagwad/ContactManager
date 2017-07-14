package com.cm.techservices;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.StringTokenizer;

import com.cm.model.User;

/**
 * @author prashant kagwad
 */
public class ReadRecords {

	private ArrayList<User> userList = new ArrayList<User>();

	public ReadRecords() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<User> getUser() {
		return userList;
	}

	public void setUser(ArrayList<User> userList) {
		this.userList = userList;
	}

	public void readFromFile(String fileName) {

		FileReader file = null;
		BufferedReader reader = null;
		String line = "";
		String delimiter = "|";

		try {

			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {

				User user = new User();
				StringTokenizer st = new StringTokenizer(line, delimiter);

				// st.countTokens(); // should always be equal to 10

				// Populate the single User data
				user.setFirstName(checkString(st.nextToken()));
				user.setLastName(checkString(st.nextToken()));
				user.setMiddleInitial(checkChar(st.nextToken().charAt(0)));
				user.setAddressLine1(checkString(st.nextToken()));
				user.setAddressLine2(checkString(st.nextToken()));
				user.setCity(checkString(st.nextToken()));
				user.setState(checkString(st.nextToken()));
				user.setZipCode(new BigInteger(st.nextToken()));
				user.setPhoneNumber(new BigInteger(st.nextToken()));
				user.setGender(st.nextToken().charAt(0));

				userList.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (file != null) {
				try {
					reader.close();
					file.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public char checkChar(char token) {

		char checkText = ' ';
		if (token != '*')
			checkText = token;

		return checkText;
	}

	public String checkString(String token) {

		String checkText = " ";
		if (!token.equalsIgnoreCase("NULL"))
			checkText = token;

		return checkText;
	}

	public void printData() {

		System.out.println("Printing records...");
		ListIterator<User> x = userList.listIterator();

		int counter = 0;
		while (x.hasNext()) {

			counter++;
			System.out.println("User " + counter + " Information : ");

			User user = x.next();
			System.out.println("FirstName      : " + user.getFirstName());
			System.out.println("LastName       : " + user.getLastName());
			System.out.println("MiddleInitial  : " + user.getMiddleInitial());
			System.out.println("Address Line 1 : " + user.getAddressLine1());
			System.out.println("Address Line 2 : " + user.getAddressLine2());
			System.out.println("City           : " + user.getCity());
			System.out.println("State          : " + user.getState());
			System.out.println("Zip Code       : " + user.getZipCode());
			System.out.println("Phone Number   : " + user.getPhoneNumber());
			System.out.println("Gender         : " + user.getGender());
			System.out.println();
		}
	}
}
