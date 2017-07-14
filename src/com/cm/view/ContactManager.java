package com.cm.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cm.controller.Controller;
import com.cm.model.User;

/**
 * @author prashant kagwad
 *
 *         ContactManager class - helps in creating the references for all the
 *         components, initializing the components with settings, does the
 *         validation on the entered data. Provides with functionality like
 *         searching, adding, updating and deleting records from the text file.
 */
@SuppressWarnings("unused")
public class ContactManager {

	private JFrame mainFrame;
	private JTextPane txtTitle;
	private JDesktopPane desktopPane;
	private JSeparator separatorTop;
	private JSeparator separatorBottom;
	private JButton btnSearchContact;
	private JButton btnAddContact;
	private JButton btnEditContact;
	private JButton btnDeleteContact;
	private JScrollPane scrollPane;
	private JList<Object> list;
	private JTextPane txtInfo;
	private JTextField txtSearch;

	// Main display panes
	private JDesktopPane desktopOuterPane;
	private JDesktopPane desktopDisplayPane;
	private JDesktopPane desktopAddPane;

	// Second frame display components
	private JLabel lblFirstName;
	private JTextField txtFirstName;
	private JLabel lblMiddleInitial;
	private JTextField txtMiddleInitial;
	private JLabel lblLastName;
	private JTextField txtLastName;
	private JLabel lblAddressLine1;
	private JTextField txtAddressLine1;
	private JLabel lblAddressLine2;
	private JTextField txtAddressLine2;
	private JLabel lblCity;
	private JTextField txtCity;
	private JLabel lblState;
	private JTextField txtState;
	private JLabel lblZipCode;
	private JTextField txtZipCode;
	private JLabel lblPhoneNumber;
	private JTextField txtPhoneNumber;
	private JLabel lblGender;
	private JComboBox<String> cmbGender;

	private ArrayList<User> userList;

	private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					ContactManager window = new ContactManager();
					window.mainFrame.setVisible(true);

				} catch (Exception exp) {
					exp.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the User Interface.
	 */
	public ContactManager() {

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setCompnents();
		displayContacts();
	}

	/**
	 * setCompnents() - sets all the components with the initial content
	 * settings and data setting where ever required.
	 */
	private void setCompnents() {

		try {

			// Get the screen width and height - in order to display the frame
			// in the center of the screen
			int width = 605, height = 511;
			mainFrame = new JFrame();

			int xAxis = (int) ((dimension.getWidth() - width) / 2);
			int yAxis = (int) ((dimension.getHeight() - height) / 2);

			mainFrame.setResizable(false);
			mainFrame.setTitle("Contacts Manager");
			mainFrame.setBounds(330, 200, width, height);
			mainFrame.setBounds(xAxis, yAxis, width, height);
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			// Set the title.
			txtTitle = new JTextPane();
			txtTitle.setEditable(false);
			txtTitle.setFont(new Font("Calibri Light", Font.ITALIC, 41));
			txtTitle.setText("                 Contact Manager");
			mainFrame.getContentPane().add(txtTitle, BorderLayout.NORTH);

			desktopPane = new JDesktopPane();
			mainFrame.getContentPane().add(desktopPane, BorderLayout.CENTER);
			desktopPane.setBackground(Color.WHITE);

			scrollPane = new JScrollPane();
			scrollPane.setBounds(342, 78, 229, 294);
			desktopPane.add(scrollPane);

			// Search Contact
			btnSearchContact = new JButton("Search");
			btnSearchContact
					.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			btnSearchContact.setEnabled(true);
			btnSearchContact.setBounds(422, 13, 122, 23);
			desktopPane.add(btnSearchContact);

			btnSearchContact.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {

					searchContacts();
				}
			});

			// Add Contact
			btnAddContact = new JButton("Add Contact");
			btnAddContact.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			btnAddContact.setEnabled(true);
			btnAddContact.setBounds(449, 383, 122, 23);
			desktopPane.add(btnAddContact);

			btnAddContact.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {

					addContact();
				}
			});

			// Info text holder for all operations
			txtInfo = new JTextPane();
			txtInfo.setText("Click on one of the contacts to view their complete details.");
			txtInfo.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			txtInfo.setEditable(false);
			txtInfo.setBounds(20, 47, 551, 23);
			desktopPane.add(txtInfo);

			txtSearch = new JTextField();
			txtSearch.setBounds(52, 13, 345, 23);
			desktopPane.add(txtSearch);
			txtSearch.setColumns(10);

			desktopOuterPane = new JDesktopPane();
			desktopOuterPane.setBorder(new LineBorder(Color.LIGHT_GRAY));
			desktopOuterPane.setBackground(Color.WHITE);
			desktopOuterPane.setBounds(20, 78, 288, 324);

			desktopPane.add(desktopOuterPane);

		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	/**
	 * searchContacts() - function to search a single contact.
	 */
	private void searchContacts() {
		// TODO Auto-generated method stub

		String searchText = txtSearch.getText();

		if (!(searchText.isEmpty() || searchText.length() == 0)) {
			final String users[] = new String[userList.size()];
			Iterator<User> iterator = userList.iterator();
			int counter = 0;

			while (iterator.hasNext()) {

				User user = iterator.next();

				if (user.getFirstName().equalsIgnoreCase(searchText)) {

					char middleInitial;
					if (user.getMiddleInitial() != ' ')
						middleInitial = user.getMiddleInitial();
					else
						middleInitial = '_';

					users[counter] = " " + user.getFirstName() + " "
							+ middleInitial + " " + user.getLastName() + " - "
							+ user.getPhoneNumber();
					counter++;
				}
			}

			list = new JList<Object>(users);
			list.setBounds(10, 78, 254, 304);

			list.addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent event) {
					displayDetails();
				}
			});

			scrollPane.setViewportView(list);
		} else {
			displayContacts();
		}
	}

	/**
	 * displayContacts() - function to displays all the contacts stored in
	 * contacts.txt
	 */
	private void displayContacts() {
		// TODO Auto-generated method stub

		try {
			// Get the user list from test file.
			desktopOuterPane.setVisible(true);
			Controller controller = new Controller();
			userList = controller.readRecords();

			final String users[] = new String[userList.size()];
			Iterator<User> iterator = userList.iterator();
			int counter = 0;

			while (iterator.hasNext()) {

				User user = iterator.next();

				char middleInitial;
				if (user.getMiddleInitial() != ' ')
					middleInitial = user.getMiddleInitial();
				else
					middleInitial = '_';
				users[counter] = " " + user.getFirstName() + " "
						+ middleInitial + " " + user.getLastName() + " - "
						+ user.getPhoneNumber();
				counter++;
			}

			list = new JList<Object>(users);
			list.setBounds(10, 78, 254, 304);

			list.addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent event) {
					displayDetails();
				}
			});

			scrollPane.setViewportView(list);

		} catch (Exception exp) {

			exp.printStackTrace();
		}
	}

	/**
	 * displayDetails() - sets all the components with the user content settings
	 * to the side panel displaying single user details.
	 */
	private void displayDetails() {
		// TODO Auto-generated method stub

		try {
			// set text on right panel
			String selectedUser = (String) list.getSelectedValue();
			StringTokenizer tokenizer = new StringTokenizer(selectedUser, " ");

			String firstName = tokenizer.nextToken();
			char middleInitial = tokenizer.nextToken().charAt(0);
			String lastName = tokenizer.nextToken();

			Iterator<User> iterator = userList.iterator();

			while (iterator.hasNext()) {
				User user = iterator.next();

				if (user.getFirstName().equalsIgnoreCase(firstName)) {
					if (user.getMiddleInitial() == middleInitial
							|| user.getMiddleInitial() == '_'
							|| user.getMiddleInitial() == ' ') {
						if (user.getLastName().equalsIgnoreCase(lastName)) {
							displayUser(user);
						}
					}
				}
			}

		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	/**
	 *
	 * displayUser(User) - function to display the details of the user passed as
	 * the parameter.
	 *
	 * @param user
	 *            - User object sent whos details have to be displayed.
	 */
	private void displayUser(final User user) {
		// TODO Auto-generated method stub

		try {

			// Make the left pane visible which holds all the elements below in
			// order to display user details.
			desktopOuterPane.setVisible(true);

			// Remove any inner pane and buttons previously added by other
			// function.
			if (desktopOuterPane.getComponentCount() > 0)
				desktopOuterPane.removeAll();

			txtInfo.setText("Click on one of the contacts to view their complete details.");

			// Create a new pane to display user details on left pane.
			desktopDisplayPane = new JDesktopPane();
			desktopDisplayPane.setBackground(Color.WHITE);
			desktopDisplayPane.setBounds(10, 11, 267, 275);
			GridBagLayout gbl_desktopInnerPane = new GridBagLayout();
			gbl_desktopInnerPane.columnWidths = new int[] { 86, 86, 86, 0, 0,
					0, 0 };
			gbl_desktopInnerPane.rowHeights = new int[] { 27, 27, 27, 27, 27,
					27, 27, 27, 27, 27, 0 };
			gbl_desktopInnerPane.columnWeights = new double[] { 0.0, 0.0, 0.0,
					0.0, 0.0, 0.0, Double.MIN_VALUE };
			gbl_desktopInnerPane.rowWeights = new double[] { 0.0, 0.0, 0.0,
					0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
			desktopDisplayPane.setLayout(gbl_desktopInnerPane);

			// Create all the labels and textfields to display user details on
			// the left pane.
			lblFirstName = new JLabel();
			lblFirstName.setText("First Name - ");
			lblFirstName.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
			gbc_lblFirstName.fill = GridBagConstraints.BOTH;
			gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
			gbc_lblFirstName.gridx = 0;
			gbc_lblFirstName.gridy = 0;
			desktopDisplayPane.add(lblFirstName, gbc_lblFirstName);

			txtFirstName = new JTextField(20);
			txtFirstName.setText(user.getFirstName());
			txtFirstName.setEditable(false);
			txtFirstName.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textFirstName = new GridBagConstraints();
			gbc_textFirstName.gridwidth = 4;
			gbc_textFirstName.fill = GridBagConstraints.BOTH;
			gbc_textFirstName.insets = new Insets(0, 0, 5, 5);
			gbc_textFirstName.gridx = 1;
			gbc_textFirstName.gridy = 0;
			desktopDisplayPane.add(txtFirstName, gbc_textFirstName);

			lblMiddleInitial = new JLabel();
			lblMiddleInitial.setText("Middle Initial -");
			lblMiddleInitial
					.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblMiddleInitial = new GridBagConstraints();
			gbc_lblMiddleInitial.fill = GridBagConstraints.BOTH;
			gbc_lblMiddleInitial.insets = new Insets(0, 0, 5, 5);
			gbc_lblMiddleInitial.gridx = 0;
			gbc_lblMiddleInitial.gridy = 1;
			desktopDisplayPane.add(lblMiddleInitial, gbc_lblMiddleInitial);

			txtMiddleInitial = new JTextField(1);
			txtMiddleInitial
					.setText(Character.toString(user.getMiddleInitial()));
			txtMiddleInitial.setEditable(false);
			txtMiddleInitial
					.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textMiddleInitial = new GridBagConstraints();
			gbc_textMiddleInitial.gridwidth = 4;
			gbc_textMiddleInitial.fill = GridBagConstraints.BOTH;
			gbc_textMiddleInitial.insets = new Insets(0, 0, 5, 5);
			gbc_textMiddleInitial.gridx = 1;
			gbc_textMiddleInitial.gridy = 1;
			desktopDisplayPane.add(txtMiddleInitial, gbc_textMiddleInitial);

			lblLastName = new JLabel();
			lblLastName.setText("Last Name -");
			lblLastName.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblLastName = new GridBagConstraints();
			gbc_lblLastName.fill = GridBagConstraints.BOTH;
			gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
			gbc_lblLastName.gridx = 0;
			gbc_lblLastName.gridy = 2;
			desktopDisplayPane.add(lblLastName, gbc_lblLastName);

			txtLastName = new JTextField(20);
			txtLastName.setText(user.getLastName());
			txtLastName.setEditable(false);
			txtLastName.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textLastName = new GridBagConstraints();
			gbc_textLastName.gridwidth = 4;
			gbc_textLastName.fill = GridBagConstraints.BOTH;
			gbc_textLastName.insets = new Insets(0, 0, 5, 5);
			gbc_textLastName.gridx = 1;
			gbc_textLastName.gridy = 2;
			desktopDisplayPane.add(txtLastName, gbc_textLastName);

			lblAddressLine1 = new JLabel();
			lblAddressLine1.setText("Address Line 1 -");
			lblAddressLine1.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblAddressLine1 = new GridBagConstraints();
			gbc_lblAddressLine1.fill = GridBagConstraints.BOTH;
			gbc_lblAddressLine1.insets = new Insets(0, 0, 5, 5);
			gbc_lblAddressLine1.gridx = 0;
			gbc_lblAddressLine1.gridy = 3;
			desktopDisplayPane.add(lblAddressLine1, gbc_lblAddressLine1);

			txtAddressLine1 = new JTextField(35);
			txtAddressLine1.setText(user.getAddressLine1());
			txtAddressLine1.setEditable(false);
			txtAddressLine1.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textAddressLine1 = new GridBagConstraints();
			gbc_textAddressLine1.gridwidth = 4;
			gbc_textAddressLine1.fill = GridBagConstraints.BOTH;
			gbc_textAddressLine1.insets = new Insets(0, 0, 5, 5);
			gbc_textAddressLine1.gridx = 1;
			gbc_textAddressLine1.gridy = 3;
			desktopDisplayPane.add(txtAddressLine1, gbc_textAddressLine1);

			lblAddressLine2 = new JLabel();
			lblAddressLine2.setText("Address Line 2 -");
			lblAddressLine2.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblAddressLine2 = new GridBagConstraints();
			gbc_lblAddressLine2.fill = GridBagConstraints.BOTH;
			gbc_lblAddressLine2.insets = new Insets(0, 0, 5, 5);
			gbc_lblAddressLine2.gridx = 0;
			gbc_lblAddressLine2.gridy = 4;
			desktopDisplayPane.add(lblAddressLine2, gbc_lblAddressLine2);

			txtAddressLine2 = new JTextField(35);
			txtAddressLine2.setText(user.getAddressLine2());
			txtAddressLine2.setEditable(false);
			txtAddressLine2.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textAddressLine2 = new GridBagConstraints();
			gbc_textAddressLine2.gridwidth = 4;
			gbc_textAddressLine2.fill = GridBagConstraints.BOTH;
			gbc_textAddressLine2.insets = new Insets(0, 0, 5, 5);
			gbc_textAddressLine2.gridx = 1;
			gbc_textAddressLine2.gridy = 4;
			desktopDisplayPane.add(txtAddressLine2, gbc_textAddressLine2);

			lblCity = new JLabel();
			lblCity.setText("City -");
			lblCity.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblCity = new GridBagConstraints();
			gbc_lblCity.fill = GridBagConstraints.BOTH;
			gbc_lblCity.insets = new Insets(0, 0, 5, 5);
			gbc_lblCity.gridx = 0;
			gbc_lblCity.gridy = 5;
			desktopDisplayPane.add(lblCity, gbc_lblCity);

			txtCity = new JTextField(25);
			txtCity.setText(user.getCity());
			txtCity.setEditable(false);
			txtCity.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textCity = new GridBagConstraints();
			gbc_textCity.gridwidth = 4;
			gbc_textCity.fill = GridBagConstraints.BOTH;
			gbc_textCity.insets = new Insets(0, 0, 5, 5);
			gbc_textCity.gridx = 1;
			gbc_textCity.gridy = 5;
			desktopDisplayPane.add(txtCity, gbc_textCity);

			lblState = new JLabel();
			lblState.setText("State -");
			lblState.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblState = new GridBagConstraints();
			gbc_lblState.fill = GridBagConstraints.BOTH;
			gbc_lblState.insets = new Insets(0, 0, 5, 5);
			gbc_lblState.gridx = 0;
			gbc_lblState.gridy = 6;
			desktopDisplayPane.add(lblState, gbc_lblState);

			txtState = new JTextField(2);
			txtState.setText(user.getState());
			txtState.setEditable(false);
			txtState.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textState = new GridBagConstraints();
			gbc_textState.gridwidth = 4;
			gbc_textState.fill = GridBagConstraints.BOTH;
			gbc_textState.insets = new Insets(0, 0, 5, 5);
			gbc_textState.gridx = 1;
			gbc_textState.gridy = 6;
			desktopDisplayPane.add(txtState, gbc_textState);

			lblZipCode = new JLabel();
			lblZipCode.setText("Zip Code -");
			lblZipCode.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblZipCode = new GridBagConstraints();
			gbc_lblZipCode.fill = GridBagConstraints.BOTH;
			gbc_lblZipCode.insets = new Insets(0, 0, 5, 5);
			gbc_lblZipCode.gridx = 0;
			gbc_lblZipCode.gridy = 7;
			desktopDisplayPane.add(lblZipCode, gbc_lblZipCode);

			txtZipCode = new JTextField(9);
			txtZipCode.setText(user.getZipCode().toString());
			txtZipCode.setEditable(false);
			txtZipCode.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textZipCode = new GridBagConstraints();
			gbc_textZipCode.gridwidth = 4;
			gbc_textZipCode.fill = GridBagConstraints.BOTH;
			gbc_textZipCode.insets = new Insets(0, 0, 5, 5);
			gbc_textZipCode.gridx = 1;
			gbc_textZipCode.gridy = 7;
			desktopDisplayPane.add(txtZipCode, gbc_textZipCode);

			lblPhoneNumber = new JLabel();
			lblPhoneNumber.setText("Phone No. -");
			lblPhoneNumber.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblPhoneNumber = new GridBagConstraints();
			gbc_lblPhoneNumber.fill = GridBagConstraints.BOTH;
			gbc_lblPhoneNumber.insets = new Insets(0, 0, 5, 5);
			gbc_lblPhoneNumber.gridx = 0;
			gbc_lblPhoneNumber.gridy = 8;
			desktopDisplayPane.add(lblPhoneNumber, gbc_lblPhoneNumber);

			txtPhoneNumber = new JTextField(21);
			txtPhoneNumber.setText(user.getPhoneNumber().toString());
			txtPhoneNumber.setEditable(false);
			txtPhoneNumber.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textPhoneNumber = new GridBagConstraints();
			gbc_textPhoneNumber.gridwidth = 4;
			gbc_textPhoneNumber.fill = GridBagConstraints.BOTH;
			gbc_textPhoneNumber.insets = new Insets(0, 0, 5, 5);
			gbc_textPhoneNumber.gridx = 1;
			gbc_textPhoneNumber.gridy = 8;
			desktopDisplayPane.add(txtPhoneNumber, gbc_textPhoneNumber);

			lblGender = new JLabel();
			lblGender.setText("Gender -");
			lblGender.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblGender = new GridBagConstraints();
			gbc_lblGender.fill = GridBagConstraints.BOTH;
			gbc_lblGender.insets = new Insets(0, 0, 0, 5);
			gbc_lblGender.gridx = 0;
			gbc_lblGender.gridy = 9;
			desktopDisplayPane.add(lblGender, gbc_lblGender);

			cmbGender = new JComboBox<String>();
			cmbGender.addItem("Male");
			cmbGender.addItem("Female");

			if (user.getGender() == 'F')
				cmbGender.setSelectedIndex(1);
			else
				cmbGender.setSelectedIndex(0);

			cmbGender.setEnabled(false);
			cmbGender.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textGender = new GridBagConstraints();
			gbc_textGender.gridwidth = 2;
			gbc_textGender.insets = new Insets(0, 0, 0, 5);
			gbc_textGender.fill = GridBagConstraints.BOTH;
			gbc_textGender.gridx = 1;
			gbc_textGender.gridy = 9;
			desktopDisplayPane.add(cmbGender, gbc_textGender);

			// Add this new left pane to the outer desktop pane.
			desktopOuterPane.add(desktopDisplayPane);

			btnEditContact = new JButton("Edit");
			btnEditContact.setBounds(20, 291, 115, 25);
			btnEditContact.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			btnEditContact.setEnabled(true);
			desktopOuterPane.add(btnEditContact);

			// Listener for the edit button.
			btnEditContact.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					editContact(user);
				}
			});

			btnDeleteContact = new JButton("Delete");
			btnDeleteContact.setBounds(145, 291, 115, 25);
			btnDeleteContact
					.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			btnDeleteContact.setEnabled(true);
			desktopOuterPane.add(btnDeleteContact);

			// Listener for the delete button. Pre-processing is done in order
			// to display the contents.
			btnDeleteContact.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {

					desktopOuterPane.setVisible(true);
					txtInfo.setText(" ► Are you sure you want to delete this user? If yes then press \"Delete\" again, else press \"Cancel\".");

					desktopOuterPane.remove(btnEditContact);
					desktopOuterPane.remove(btnDeleteContact);

					JButton btnCancel = new JButton("Cancel");
					btnCancel.setBounds(20, 291, 115, 25);
					btnCancel
							.setFont(new Font("Calibri Light", Font.ITALIC, 13));
					btnCancel.setEnabled(true);
					desktopOuterPane.add(btnCancel);

					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							txtInfo.setText("Click on one of the contacts to view their complete details.");
							displayUser(user);
						}
					});

					JButton btnOK = new JButton("OK");
					btnOK.setEnabled(false);
					btnOK.setBounds(145, 291, 115, 25);
					btnOK.setFont(new Font("Calibri Light", Font.ITALIC, 13));
					btnOK.setEnabled(true);
					desktopOuterPane.add(btnOK);

					btnOK.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							deleteContact(user);
						}
					});
				}

			});
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	/**
	 * addContacts() - function to add a single user to contacts.txt
	 */
	private void addContact() {
		// TODO Auto-generated method stub

		try {

			// Make the left pane visible which holds all the elements below in
			// order to display user details.
			desktopOuterPane.setVisible(true);

			// Remove any inner pane and buttons previously added by other
			// function.
			if (desktopOuterPane.getComponentCount() > 0)
				desktopOuterPane.removeAll();

			// Set the info text.
			txtInfo.setText("Enter all the details below. Required fields are marked with '*'.");

			// Custom border. Default
			final Border borderGrey = BorderFactory
					.createLineBorder(Color.lightGray);

			// Create a new pane to display user details on left pane.
			desktopAddPane = new JDesktopPane();
			desktopAddPane.setBackground(Color.WHITE);
			desktopAddPane.setBounds(10, 11, 267, 275);
			GridBagLayout gbl_desktopInnerPane = new GridBagLayout();
			gbl_desktopInnerPane.columnWidths = new int[] { 86, 86, 86, 0, 0,
					0, 0 };
			gbl_desktopInnerPane.rowHeights = new int[] { 27, 27, 27, 27, 27,
					27, 27, 27, 27, 27, 0 };
			gbl_desktopInnerPane.columnWeights = new double[] { 0.0, 0.0, 0.0,
					0.0, 0.0, 0.0, Double.MIN_VALUE };
			gbl_desktopInnerPane.rowWeights = new double[] { 0.0, 0.0, 0.0,
					0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
			desktopAddPane.setLayout(gbl_desktopInnerPane);

			// Create all the labels and textfields to display user details on
			// the left pane.
			lblFirstName = new JLabel();
			lblFirstName.setText("First Name - *");
			lblFirstName.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
			gbc_lblFirstName.fill = GridBagConstraints.BOTH;
			gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
			gbc_lblFirstName.gridx = 0;
			gbc_lblFirstName.gridy = 0;
			desktopAddPane.add(lblFirstName, gbc_lblFirstName);

			txtFirstName = new JTextField(20);
			txtFirstName.setBorder(borderGrey);
			txtFirstName.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textFirstName = new GridBagConstraints();
			gbc_textFirstName.gridwidth = 4;
			gbc_textFirstName.fill = GridBagConstraints.BOTH;
			gbc_textFirstName.insets = new Insets(0, 0, 5, 5);
			gbc_textFirstName.gridx = 1;
			gbc_textFirstName.gridy = 0;
			desktopAddPane.add(txtFirstName, gbc_textFirstName);

			lblMiddleInitial = new JLabel();
			lblMiddleInitial.setText("Middle Initial -");
			lblMiddleInitial
					.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblMiddleInitial = new GridBagConstraints();
			gbc_lblMiddleInitial.fill = GridBagConstraints.BOTH;
			gbc_lblMiddleInitial.insets = new Insets(0, 0, 5, 5);
			gbc_lblMiddleInitial.gridx = 0;
			gbc_lblMiddleInitial.gridy = 1;
			desktopAddPane.add(lblMiddleInitial, gbc_lblMiddleInitial);

			txtMiddleInitial = new JTextField(1);
			txtMiddleInitial.setBorder(borderGrey);
			txtMiddleInitial
					.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textMiddleInitial = new GridBagConstraints();
			gbc_textMiddleInitial.gridwidth = 4;
			gbc_textMiddleInitial.fill = GridBagConstraints.BOTH;
			gbc_textMiddleInitial.insets = new Insets(0, 0, 5, 5);
			gbc_textMiddleInitial.gridx = 1;
			gbc_textMiddleInitial.gridy = 1;
			desktopAddPane.add(txtMiddleInitial, gbc_textMiddleInitial);

			lblLastName = new JLabel();
			lblLastName.setText("Last Name - *");
			lblLastName.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblLastName = new GridBagConstraints();
			gbc_lblLastName.fill = GridBagConstraints.BOTH;
			gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
			gbc_lblLastName.gridx = 0;
			gbc_lblLastName.gridy = 2;
			desktopAddPane.add(lblLastName, gbc_lblLastName);

			txtLastName = new JTextField(20);
			txtLastName.setBorder(borderGrey);
			txtLastName.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textLastName = new GridBagConstraints();
			gbc_textLastName.gridwidth = 4;
			gbc_textLastName.fill = GridBagConstraints.BOTH;
			gbc_textLastName.insets = new Insets(0, 0, 5, 5);
			gbc_textLastName.gridx = 1;
			gbc_textLastName.gridy = 2;
			desktopAddPane.add(txtLastName, gbc_textLastName);

			lblAddressLine1 = new JLabel();
			lblAddressLine1.setText("Address Line 1 - *");
			lblAddressLine1.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblAddressLine1 = new GridBagConstraints();
			gbc_lblAddressLine1.fill = GridBagConstraints.BOTH;
			gbc_lblAddressLine1.insets = new Insets(0, 0, 5, 5);
			gbc_lblAddressLine1.gridx = 0;
			gbc_lblAddressLine1.gridy = 3;
			desktopAddPane.add(lblAddressLine1, gbc_lblAddressLine1);

			txtAddressLine1 = new JTextField(35);
			txtAddressLine1.setBorder(borderGrey);
			txtAddressLine1.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textAddressLine1 = new GridBagConstraints();
			gbc_textAddressLine1.gridwidth = 4;
			gbc_textAddressLine1.fill = GridBagConstraints.BOTH;
			gbc_textAddressLine1.insets = new Insets(0, 0, 5, 5);
			gbc_textAddressLine1.gridx = 1;
			gbc_textAddressLine1.gridy = 3;
			desktopAddPane.add(txtAddressLine1, gbc_textAddressLine1);

			lblAddressLine2 = new JLabel();
			lblAddressLine2.setText("Address Line 2 -");
			lblAddressLine2.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblAddressLine2 = new GridBagConstraints();
			gbc_lblAddressLine2.fill = GridBagConstraints.BOTH;
			gbc_lblAddressLine2.insets = new Insets(0, 0, 5, 5);
			gbc_lblAddressLine2.gridx = 0;
			gbc_lblAddressLine2.gridy = 4;
			desktopAddPane.add(lblAddressLine2, gbc_lblAddressLine2);

			txtAddressLine2 = new JTextField(35);
			txtAddressLine2.setBorder(borderGrey);
			txtAddressLine2.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textAddressLine2 = new GridBagConstraints();
			gbc_textAddressLine2.gridwidth = 4;
			gbc_textAddressLine2.fill = GridBagConstraints.BOTH;
			gbc_textAddressLine2.insets = new Insets(0, 0, 5, 5);
			gbc_textAddressLine2.gridx = 1;
			gbc_textAddressLine2.gridy = 4;
			desktopAddPane.add(txtAddressLine2, gbc_textAddressLine2);

			lblCity = new JLabel();
			lblCity.setText("City - *");
			lblCity.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblCity = new GridBagConstraints();
			gbc_lblCity.fill = GridBagConstraints.BOTH;
			gbc_lblCity.insets = new Insets(0, 0, 5, 5);
			gbc_lblCity.gridx = 0;
			gbc_lblCity.gridy = 5;
			desktopAddPane.add(lblCity, gbc_lblCity);

			txtCity = new JTextField(25);
			txtCity.setBorder(borderGrey);
			txtCity.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textCity = new GridBagConstraints();
			gbc_textCity.gridwidth = 4;
			gbc_textCity.fill = GridBagConstraints.BOTH;
			gbc_textCity.insets = new Insets(0, 0, 5, 5);
			gbc_textCity.gridx = 1;
			gbc_textCity.gridy = 5;
			desktopAddPane.add(txtCity, gbc_textCity);

			lblState = new JLabel();
			lblState.setText("State - *");
			lblState.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblState = new GridBagConstraints();
			gbc_lblState.fill = GridBagConstraints.BOTH;
			gbc_lblState.insets = new Insets(0, 0, 5, 5);
			gbc_lblState.gridx = 0;
			gbc_lblState.gridy = 6;
			desktopAddPane.add(lblState, gbc_lblState);

			txtState = new JTextField(2);
			txtState.setBorder(borderGrey);
			txtState.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textState = new GridBagConstraints();
			gbc_textState.gridwidth = 4;
			gbc_textState.fill = GridBagConstraints.BOTH;
			gbc_textState.insets = new Insets(0, 0, 5, 5);
			gbc_textState.gridx = 1;
			gbc_textState.gridy = 6;
			desktopAddPane.add(txtState, gbc_textState);

			lblZipCode = new JLabel();
			lblZipCode.setText("Zip Code - *");
			lblZipCode.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblZipCode = new GridBagConstraints();
			gbc_lblZipCode.fill = GridBagConstraints.BOTH;
			gbc_lblZipCode.insets = new Insets(0, 0, 5, 5);
			gbc_lblZipCode.gridx = 0;
			gbc_lblZipCode.gridy = 7;
			desktopAddPane.add(lblZipCode, gbc_lblZipCode);

			txtZipCode = new JTextField();
			txtZipCode.setBorder(borderGrey);
			txtZipCode.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textZipCode = new GridBagConstraints();
			gbc_textZipCode.gridwidth = 4;
			gbc_textZipCode.fill = GridBagConstraints.BOTH;
			gbc_textZipCode.insets = new Insets(0, 0, 5, 5);
			gbc_textZipCode.gridx = 1;
			gbc_textZipCode.gridy = 7;
			desktopAddPane.add(txtZipCode, gbc_textZipCode);

			lblPhoneNumber = new JLabel();
			lblPhoneNumber.setText("Phone No. - *");
			lblPhoneNumber.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblPhoneNumber = new GridBagConstraints();
			gbc_lblPhoneNumber.fill = GridBagConstraints.BOTH;
			gbc_lblPhoneNumber.insets = new Insets(0, 0, 5, 5);
			gbc_lblPhoneNumber.gridx = 0;
			gbc_lblPhoneNumber.gridy = 8;
			desktopAddPane.add(lblPhoneNumber, gbc_lblPhoneNumber);

			txtPhoneNumber = new JTextField(21);
			txtPhoneNumber.setBorder(borderGrey);
			txtPhoneNumber.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textPhoneNumber = new GridBagConstraints();
			gbc_textPhoneNumber.gridwidth = 4;
			gbc_textPhoneNumber.fill = GridBagConstraints.BOTH;
			gbc_textPhoneNumber.insets = new Insets(0, 0, 5, 5);
			gbc_textPhoneNumber.gridx = 1;
			gbc_textPhoneNumber.gridy = 8;
			desktopAddPane.add(txtPhoneNumber, gbc_textPhoneNumber);

			lblGender = new JLabel();
			lblGender.setText("Gender - *");
			lblGender.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_lblGender = new GridBagConstraints();
			gbc_lblGender.fill = GridBagConstraints.BOTH;
			gbc_lblGender.insets = new Insets(0, 0, 0, 5);
			gbc_lblGender.gridx = 0;
			gbc_lblGender.gridy = 9;
			desktopAddPane.add(lblGender, gbc_lblGender);

			cmbGender = new JComboBox<String>();
			cmbGender.setBorder(borderGrey);
			cmbGender.addItem("Male");
			cmbGender.addItem("Female");
			cmbGender.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			GridBagConstraints gbc_textGender = new GridBagConstraints();
			gbc_textGender.gridwidth = 2;
			gbc_textGender.insets = new Insets(0, 0, 0, 5);
			gbc_textGender.fill = GridBagConstraints.BOTH;
			gbc_textGender.gridx = 1;
			gbc_textGender.gridy = 9;
			desktopAddPane.add(cmbGender, gbc_textGender);

			// Add this new left pane to the outer desktop pane.
			desktopOuterPane.add(desktopAddPane);

			JButton btnCancel = new JButton("Cancel");
			btnCancel.setBounds(20, 291, 115, 25);
			btnCancel.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			btnCancel.setEnabled(true);
			desktopOuterPane.add(btnCancel);

			// Listener for the cancel button. This just takes back to previous
			// screen look.
			btnCancel.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {

					txtInfo.setText("Click on one of the contacts to view their complete details.");
					desktopOuterPane.setVisible(false);
					// displayContacts();
				}
			});

			JButton btnSave = new JButton("Save");
			btnSave.setEnabled(false);
			btnSave.setBounds(145, 291, 115, 25);
			btnSave.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			btnSave.setEnabled(true);
			desktopOuterPane.add(btnSave);

			// Listener for the save button. This performs several validations
			// on input fields. If all validations are clear then checks where
			// the user already exists. If not it simply adds the user to
			// contacts.txt
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {

					// Custom border. To display error fields.
					Border borderRed = BorderFactory
							.createLineBorder(Color.red);

					if (!(txtFirstName.getText().isEmpty() || txtFirstName
							.getText().length() == 0)) {

						if (!(txtFirstName.getText().length() > 20)) {

							txtFirstName.setBorder(borderGrey);

							if (!(txtMiddleInitial.getText().length() > 1)) {

								txtMiddleInitial.setBorder(borderGrey);

								if (!(txtLastName.getText().isEmpty() || txtLastName
										.getText().length() == 0)) {

									if (!(txtLastName.getText().length() > 20)) {

										txtLastName.setBorder(borderGrey);

										if (!(txtAddressLine1.getText()
												.isEmpty() || txtAddressLine1
												.getText().length() == 0)) {

											if (!(txtAddressLine1.getText()
													.length() > 35)) {

												txtAddressLine1
														.setBorder(borderGrey);

												if (!(txtAddressLine2.getText()
														.length() > 35)) {

													txtAddressLine2
															.setBorder(borderGrey);

													if (!(txtCity.getText()
															.isEmpty() || txtCity
															.getText().length() == 0)) {

														if (!(txtCity.getText()
																.length() > 25)) {

															txtCity.setBorder(borderGrey);

															if (!(txtState
																	.getText()
																	.isEmpty() || txtState
																	.getText()
																	.length() == 0)) {

																if (!(txtState
																		.getText()
																		.length() > 2)) {

																	txtState.setBorder(borderGrey);

																	if (!(txtZipCode
																			.getText()
																			.isEmpty() || txtZipCode
																			.getText()
																			.length() == 0)) {

																		if (!(txtZipCode
																				.getText()
																				.length() > 9)) {

																			if (testForInteger(txtZipCode
																					.getText())) {

																				txtZipCode
																						.setBorder(borderGrey);

																				if (!(txtPhoneNumber
																						.getText()
																						.isEmpty() || txtPhoneNumber
																						.getText()
																						.length() == 0)) {

																					if (!(txtPhoneNumber
																							.getText()
																							.length() > 21)) {

																						if (testForInteger(txtPhoneNumber
																								.getText())) {

																							txtPhoneNumber
																									.setBorder(borderGrey);

																							// At
																							// this
																							// point
																							// all
																							// the
																							// validations
																							// have
																							// been
																							// passed.

																							String firstName = txtFirstName
																									.getText();

																							char middleInitial = ' ';
																							if (!txtMiddleInitial
																									.getText()
																									.isEmpty()) {
																								middleInitial = txtMiddleInitial
																										.getText()
																										.charAt(0);
																							}

																							String lastName = txtLastName
																									.getText();
																							String addressLine1 = txtAddressLine1
																									.getText();
																							String addressLine2 = txtAddressLine2
																									.getText();
																							String city = txtCity
																									.getText();
																							String state = txtState
																									.getText();

																							BigInteger zipCode = new BigInteger(
																									txtZipCode
																											.getText());
																							BigInteger phoneNumber = new BigInteger(
																									txtPhoneNumber
																											.getText());

																							char gender = cmbGender
																									.getSelectedItem()
																									.toString()
																									.charAt(0);

																							Controller controller = new Controller();
																							ArrayList<User> tempUserList = controller
																									.readRecords();
																							Iterator<User> tempIterator = tempUserList
																									.iterator();

																							boolean duplicateUserFlag = false;

																							// check
																							// for
																							// duplicate
																							// user.
																							while (tempIterator
																									.hasNext()) {

																								User user = tempIterator
																										.next();

																								if (user.getFirstName()
																										.equalsIgnoreCase(
																												firstName)) {
																									if (Character
																											.toString(
																													user.getMiddleInitial())
																											.equalsIgnoreCase(
																													Character
																															.toString(middleInitial))) {
																										if (user.getLastName()
																												.equalsIgnoreCase(
																														lastName)) {
																											duplicateUserFlag = true;
																										}
																									}
																								}
																							}

																							if (duplicateUserFlag) {

																								txtInfo.setText(" ► A user with this name already exist.");

																							} else {

																								// Insert
																								// new
																								// user
																								// to
																								// contact
																								// list.

																								User newUser = new User();
																								newUser.setFirstName(firstName);
																								newUser.setLastName(lastName);
																								newUser.setMiddleInitial(middleInitial);
																								newUser.setAddressLine1(addressLine1);
																								newUser.setAddressLine2(addressLine2);
																								newUser.setCity(city);
																								newUser.setState(state);
																								newUser.setZipCode(zipCode);
																								newUser.setPhoneNumber(phoneNumber);
																								newUser.setGender(gender);

																								tempUserList
																										.add(newUser);
																								controller
																										.writeRecords(tempUserList);
																								txtInfo.setText(" ► New user has been added. Click on the contact from the list to view details.");

																								displayContacts();
																								desktopOuterPane
																										.setVisible(false);

																							}
																						} else {
																							txtInfo.setText(" ► Phone number must only contain digits (no characters allowed).");
																							txtPhoneNumber
																									.setBorder(borderRed);
																							txtPhoneNumber
																									.requestFocus();
																						}
																					} else {
																						txtInfo.setText(" ► Phone number cannot be more than 21 digits.");
																						txtPhoneNumber
																								.setBorder(borderRed);
																						txtPhoneNumber
																								.requestFocus();
																					}

																				} else {
																					txtInfo.setText(" ► Phone number cannot be empty.");
																					txtPhoneNumber
																							.setBorder(borderRed);
																					txtPhoneNumber
																							.requestFocus();
																				}
																			} else {
																				txtInfo.setText(" ► Zipcode must only contain digits (no characters allowed).");
																				txtZipCode
																						.setBorder(borderRed);
																				txtZipCode
																						.requestFocus();
																			}
																		} else {
																			txtInfo.setText(" ► Zipcode cannot be more than 9 digits.");
																			txtZipCode
																					.setBorder(borderRed);
																			txtZipCode
																					.requestFocus();
																		}
																	} else {
																		txtInfo.setText(" ► Zipcode cannot be empty.");
																		txtZipCode
																				.setBorder(borderRed);
																		txtZipCode
																				.requestFocus();
																	}
																} else {
																	txtInfo.setText(" ► State cannot be more than 2 characters.");
																	txtState.setBorder(borderRed);
																	txtState.requestFocus();
																}
															} else {
																txtInfo.setText(" ► State cannot be empty.");
																txtState.setBorder(borderRed);
																txtState.requestFocus();
															}
														} else {
															txtInfo.setText(" ► City cannot be more than 25 characters.");
															txtCity.setBorder(borderRed);
															txtCity.requestFocus();
														}
													} else {
														txtInfo.setText(" ► City cannot be empty.");
														txtCity.setBorder(borderRed);
														txtCity.requestFocus();
													}
												} else {
													txtInfo.setText(" ► Address Line 2 cannot be more than 35 characters.");
													txtAddressLine2
															.setBorder(borderRed);
													txtAddressLine2
															.requestFocus();
												}
											} else {
												txtInfo.setText(" ► Address Line 1 cannot be more than 35 characters.");
												txtAddressLine1
														.setBorder(borderRed);
												txtAddressLine1.requestFocus();
											}
										} else {
											txtInfo.setText(" ► Address Line 1 cannot be empty.");
											txtAddressLine1
													.setBorder(borderRed);
											txtAddressLine1.requestFocus();
										}
									} else {
										txtInfo.setText(" ► Last name cannot be more than 20 characters.");
										txtLastName.setBorder(borderRed);
										txtLastName.requestFocus();
									}
								} else {
									txtInfo.setText(" ► Last name cannot be empty.");
									txtLastName.setBorder(borderRed);
									txtLastName.requestFocus();
								}
							} else {
								txtInfo.setText(" ► Middle initial cannot be more than 1 character.");
								txtMiddleInitial.setBorder(borderRed);
								txtMiddleInitial.requestFocus();
							}
						} else {
							txtInfo.setText(" ► First name cannot be more than 20 characters.");
							txtFirstName.setBorder(borderRed);
							txtFirstName.requestFocus();
						}
					} else {
						txtInfo.setText(" ► First name cannot be empty.");
						txtFirstName.setBorder(borderRed);
						txtFirstName.requestFocus();
					}
				}
			});
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	/**
	 * testForInteger(String) - function to test where the passed parameter is
	 * of type integer.
	 *
	 * @param text
	 *            - String that is validated for integer type.
	 * @return - boolean value. True - If given text is of the type integer,
	 *         False - otherwise.
	 */
	private boolean testForInteger(String text) {
		// TODO Auto-generated method stub

		try {

			// Converting the text into Integer type helps to determine whether
			// text is of type integer. If this fails then the text is not
			// integer or contains other characters.

			for (int i = 0; i < text.length(); i++) {
				Integer.parseInt(Character.toString(text.charAt(i)));
			}
			return true;

		} catch (NumberFormatException exp) {

			return false;
		}
	}

	/**
	 * editContact(User) - function to edit a single user sent as parameter.
	 *
	 * @param user
	 *            - user who's details are edited.
	 */
	private void editContact(final User user) {
		// TODO Auto-generated method stub

		try {

			// Make the left pane visible which holds all the elements below in
			// order to display user details.
			desktopOuterPane.setVisible(true);

			// Remove both the previously existing buttons added by other
			// function.
			desktopOuterPane.remove(btnEditContact);
			desktopOuterPane.remove(btnDeleteContact);

			// Custom border. Default
			final Border borderGrey = BorderFactory
					.createLineBorder(Color.lightGray);

			// Enable all the text fields for editing and set the border to
			// default.
			txtFirstName.setEditable(true);
			txtFirstName.setBorder(borderGrey);
			txtMiddleInitial.setEditable(true);
			txtMiddleInitial.setBorder(borderGrey);
			txtLastName.setEditable(true);
			txtLastName.setBorder(borderGrey);
			txtAddressLine1.setEditable(true);
			txtAddressLine1.setBorder(borderGrey);
			txtAddressLine2.setEditable(true);
			txtAddressLine2.setBorder(borderGrey);
			txtCity.setEditable(true);
			txtCity.setBorder(borderGrey);
			txtState.setEditable(true);
			txtState.setBorder(borderGrey);
			txtZipCode.setEditable(true);
			txtZipCode.setBorder(borderGrey);
			txtPhoneNumber.setEditable(true);
			txtPhoneNumber.setBorder(borderGrey);
			cmbGender.setEnabled(true);
			cmbGender.setBorder(borderGrey);

			JButton btnCancel = new JButton("Cancel");
			btnCancel.setBounds(20, 291, 115, 25);
			btnCancel.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			btnCancel.setEnabled(true);
			desktopOuterPane.add(btnCancel);

			//
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {

					txtInfo.setText("Click on one of the contacts to view their complete details.");
					displayUser(user);
				}
			});

			// Listener for the cancel button.
			JButton btnSave = new JButton("Save");
			btnSave.setEnabled(false);
			btnSave.setBounds(145, 291, 115, 25);
			btnSave.setFont(new Font("Calibri Light", Font.ITALIC, 13));
			btnSave.setEnabled(true);
			desktopOuterPane.add(btnSave);

			// Listener for the save button. This performs several validations
			// on input fields. If all validations are clear then checks where
			// the user already exists. If not it simply adds the edited details
			// of the user to contacts.txt
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {

					Border borderRed = BorderFactory
							.createLineBorder(Color.red);

					if (!(txtFirstName.getText().isEmpty() || txtFirstName
							.getText().length() == 0)) {

						if (!(txtFirstName.getText().length() > 20)) {

							txtFirstName.setBorder(borderGrey);

							if (!(txtMiddleInitial.getText().length() > 1)) {

								txtMiddleInitial.setBorder(borderGrey);

								if (!(txtLastName.getText().isEmpty() || txtLastName
										.getText().length() == 0)) {

									if (!(txtLastName.getText().length() > 20)) {

										txtLastName.setBorder(borderGrey);

										if (!(txtAddressLine1.getText()
												.isEmpty() || txtAddressLine1
												.getText().length() == 0)) {

											if (!(txtAddressLine1.getText()
													.length() > 35)) {

												txtAddressLine1
														.setBorder(borderGrey);

												if (!(txtAddressLine2.getText()
														.length() > 35)) {

													txtAddressLine2
															.setBorder(borderGrey);

													if (!(txtCity.getText()
															.isEmpty() || txtCity
															.getText().length() == 0)) {

														if (!(txtCity.getText()
																.length() > 25)) {

															txtCity.setBorder(borderGrey);

															if (!(txtState
																	.getText()
																	.isEmpty() || txtState
																	.getText()
																	.length() == 0)) {

																if (!(txtState
																		.getText()
																		.length() > 2)) {

																	txtState.setBorder(borderGrey);

																	if (!(txtZipCode
																			.getText()
																			.isEmpty() || txtZipCode
																			.getText()
																			.length() == 0)) {

																		if (!(txtZipCode
																				.getText()
																				.length() > 9)) {

																			if (testForInteger(txtZipCode
																					.getText())) {

																				txtZipCode
																						.setBorder(borderGrey);

																				if (!(txtPhoneNumber
																						.getText()
																						.isEmpty() || txtPhoneNumber
																						.getText()
																						.length() == 0)) {

																					if (!(txtPhoneNumber
																							.getText()
																							.length() > 21)) {

																						if (testForInteger(txtPhoneNumber
																								.getText())) {

																							txtPhoneNumber
																									.setBorder(borderGrey);

																							// At
																							// this
																							// point
																							// all
																							// the
																							// validations
																							// have
																							// been
																							// passed.

																							Controller controller = new Controller();
																							ArrayList<User> tempUserList = controller
																									.readRecords();
																							Iterator<User> tempIterator = tempUserList
																									.iterator();

																							while (tempIterator
																									.hasNext()) {
																								User tempUser = tempIterator
																										.next();

																								if (tempUser
																										.getFirstName()
																										.equalsIgnoreCase(
																												user.getFirstName())) {
																									if (tempUser
																											.getMiddleInitial() == user
																											.getMiddleInitial()) {
																										if (tempUser
																												.getLastName()
																												.equalsIgnoreCase(
																														user.getLastName())) {
																											tempIterator
																													.remove();
																										}
																									}
																								}
																							}
																							controller
																									.writeRecords(tempUserList);

																							String firstName = txtFirstName
																									.getText();

																							char middleInitial = ' ';
																							if (!txtMiddleInitial
																									.getText()
																									.isEmpty()) {
																								middleInitial = txtMiddleInitial
																										.getText()
																										.charAt(0);
																							}

																							String lastName = txtLastName
																									.getText();
																							String addressLine1 = txtAddressLine1
																									.getText();
																							String addressLine2 = txtAddressLine2
																									.getText();
																							String city = txtCity
																									.getText();
																							String state = txtState
																									.getText();

																							BigInteger zipCode = new BigInteger(
																									txtZipCode
																											.getText());
																							BigInteger phoneNumber = new BigInteger(
																									txtPhoneNumber
																											.getText());

																							char gender = cmbGender
																									.getSelectedItem()
																									.toString()
																									.charAt(0);

																							tempUserList = controller
																									.readRecords();
																							tempIterator = tempUserList
																									.iterator();

																							boolean duplicateUserFlag = false;

																							// check
																							// for
																							// duplicate
																							// user.
																							while (tempIterator
																									.hasNext()) {

																								User user = tempIterator
																										.next();

																								if (user.getFirstName()
																										.equalsIgnoreCase(
																												firstName)) {
																									if (Character
																											.toString(
																													user.getMiddleInitial())
																											.equalsIgnoreCase(
																													Character
																															.toString(middleInitial))) {
																										if (user.getLastName()
																												.equalsIgnoreCase(
																														lastName)) {
																											duplicateUserFlag = true;
																										}
																									}
																								}
																							}

																							if (duplicateUserFlag) {

																								txtInfo.setText(" ► A user with this name already exist.");
																							} else {

																								// Update
																								// user
																								// to
																								// contact
																								// list.

																								user.setFirstName(firstName);
																								user.setLastName(lastName);
																								user.setMiddleInitial(middleInitial);
																								user.setAddressLine1(addressLine1);
																								user.setAddressLine2(addressLine2);
																								user.setCity(city);
																								user.setState(state);
																								user.setZipCode(zipCode);
																								user.setPhoneNumber(phoneNumber);
																								user.setGender(gender);

																								tempUserList
																										.add(user);
																								controller
																										.writeRecords(tempUserList);
																								txtInfo.setText(" ► User has been updated.");

																								displayContacts();
																								desktopOuterPane
																										.setVisible(false);
																							}
																						} else {
																							txtInfo.setText(" ► Phone number must only contain digits (no characters allowed).");
																							txtPhoneNumber
																									.setBorder(borderRed);
																							txtPhoneNumber
																									.requestFocus();
																						}
																					} else {
																						txtInfo.setText(" ► Phone number cannot be more than 21 digits.");
																						txtPhoneNumber
																								.setBorder(borderRed);
																						txtPhoneNumber
																								.requestFocus();
																					}

																				} else {
																					txtInfo.setText(" ► Phone number cannot be empty.");
																					txtPhoneNumber
																							.setBorder(borderRed);
																					txtPhoneNumber
																							.requestFocus();
																				}
																			} else {
																				txtInfo.setText(" ► Zipcode must only contain digits (no characters allowed).");
																				txtZipCode
																						.setBorder(borderRed);
																				txtZipCode
																						.requestFocus();
																			}
																		} else {
																			txtInfo.setText(" ► Zipcode cannot be more than 9 digits.");
																			txtZipCode
																					.setBorder(borderRed);
																			txtZipCode
																					.requestFocus();
																		}
																	} else {
																		txtInfo.setText(" ► Zipcode cannot be empty.");
																		txtZipCode
																				.setBorder(borderRed);
																		txtZipCode
																				.requestFocus();
																	}
																} else {
																	txtInfo.setText(" ► State cannot be more than 2 characters.");
																	txtState.setBorder(borderRed);
																	txtState.requestFocus();
																}
															} else {
																txtInfo.setText(" ► State cannot be empty.");
																txtState.setBorder(borderRed);
																txtState.requestFocus();
															}
														} else {
															txtInfo.setText(" ► City cannot be more than 25 characters.");
															txtCity.setBorder(borderRed);
															txtCity.requestFocus();
														}
													} else {
														txtInfo.setText(" ► City cannot be empty.");
														txtCity.setBorder(borderRed);
														txtCity.requestFocus();
													}
												} else {
													txtInfo.setText(" ► Address Line 2 cannot be more than 35 characters.");
													txtAddressLine2
															.setBorder(borderRed);
													txtAddressLine2
															.requestFocus();
												}
											} else {
												txtInfo.setText(" ► Address Line 1 cannot be more than 35 characters.");
												txtAddressLine1
														.setBorder(borderRed);
												txtAddressLine1.requestFocus();
											}
										} else {
											txtInfo.setText(" ► Address Line 1 cannot be empty.");
											txtAddressLine1
													.setBorder(borderRed);
											txtAddressLine1.requestFocus();
										}
									} else {
										txtInfo.setText(" ► Last name cannot be more than 20 characters.");
										txtLastName.setBorder(borderRed);
										txtLastName.requestFocus();
									}
								} else {
									txtInfo.setText(" ► Last name cannot be empty.");
									txtLastName.setBorder(borderRed);
									txtLastName.requestFocus();
								}
							} else {
								txtInfo.setText(" ► Middle initial cannot be more than 1 character.");
								txtMiddleInitial.setBorder(borderRed);
								txtMiddleInitial.requestFocus();
							}
						} else {
							txtInfo.setText(" ► First name cannot be more than 20 characters.");
							txtFirstName.setBorder(borderRed);
							txtFirstName.requestFocus();
						}
					} else {
						txtInfo.setText(" ► First name cannot be empty.");
						txtFirstName.setBorder(borderRed);
						txtFirstName.requestFocus();
					}
				}
			});

		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	/**
	 * deleteContact(User) - function to delete a single user sent as parameter.
	 *
	 * @param user
	 *            - user who's details are deleted from contacts.txt.
	 */
	private void deleteContact(User user) {
		// TODO deleteContact()

		try {

			Controller controller = new Controller();
			ArrayList<User> tempUserList = controller.readRecords();
			Iterator<User> tempIterator = tempUserList.iterator();

			while (tempIterator.hasNext()) {

				User tempUser = tempIterator.next();
				if (tempUser.getFirstName().equalsIgnoreCase(
						user.getFirstName())) {
					if (tempUser.getMiddleInitial() == user.getMiddleInitial()) {
						if (tempUser.getLastName().equalsIgnoreCase(
								user.getLastName())) {
							tempIterator.remove();
						}
					}
				}
			}

			controller.writeRecords(tempUserList);
			txtInfo.setText(" ► The User has been deleted. Click on the contact from the list to view details.");

			displayContacts();
			desktopOuterPane.setVisible(false);

		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
}
