package com.cm.techservices;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.cm.model.User;

/**
 * @author prashant kagwad
 */
public class WriteRecords {

	private ArrayList<User> userList = new ArrayList<User>();

	public WriteRecords() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<User> getUser() {
		return userList;
	}

	public void setUser(ArrayList<User> userList) {
		this.userList = userList;
	}

	public void writeToFile(String fileName) {

		File file = new File(fileName);
		FileWriter fileWriter = null;
		BufferedWriter writer = null;
		String delimiter = "|";

		try {
			fileWriter = new FileWriter(fileName);
			writer = new BufferedWriter(fileWriter);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// return if the user list is empty
			if (userList.isEmpty() || userList.size() <= 0) {
				return;
			}

			Iterator<User> x = userList.iterator();

			while (x.hasNext()) {

				String tempUserText = "";
				User tempUser = x.next();
				tempUserText = tempUserText + tempUser.getFirstName()
						+ delimiter;
				tempUserText = tempUserText + tempUser.getLastName()
						+ delimiter;

				// Insert dummmy char ('*') if middle initial is empty.
				if (tempUser.getMiddleInitial() == ' ') {
					tempUserText = tempUserText + '*' + delimiter;
				} else {
					tempUserText = tempUserText + tempUser.getMiddleInitial()
							+ delimiter;
				}

				tempUserText = tempUserText + tempUser.getAddressLine1()
						+ delimiter;

				// Insert dummmy char ("NULL") if addressline2 is empty.
				if (tempUser.getAddressLine2().isEmpty()
						|| tempUser.getAddressLine2().equalsIgnoreCase("")) {
					tempUserText = tempUserText + "NULL" + delimiter;
				} else {
					tempUserText = tempUserText + tempUser.getAddressLine2()
							+ delimiter;
				}

				tempUserText = tempUserText + tempUser.getCity() + delimiter;
				tempUserText = tempUserText + tempUser.getState() + delimiter;
				tempUserText = tempUserText + tempUser.getZipCode() + delimiter;
				tempUserText = tempUserText + tempUser.getPhoneNumber()
						+ delimiter;
				tempUserText = tempUserText + tempUser.getGender() + "\n";
				writer.write(tempUserText);
			}

			writer.close();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (fileWriter != null) {
				try {
					writer.close();
					fileWriter.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
