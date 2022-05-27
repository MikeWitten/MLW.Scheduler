•  title and purpose of the application
	Title:		"Desktop Scheduling Application"
	Purpose:	Allows a user to add or manipulate appointments and customers to the company database.


•  author, contact information, student application version, and date
	Author: 	Micheal L Witten
	Contact:	mwitte5@wgu.edu  
	Version:	1.0
	Date:		5 May 2022
	

•  IDE including version number (e.g., IntelliJ Community 2020.01), full JDK of version used (e.g., Java SE 17.0.1), and JavaFX version compatible with JDK version (e.g. JavaFX-SDK-17.0.1)
	IDE:		IntelliJ Community 2022.1
	JDK:		Java SE 17.0.2
	JavaFX:		JavaFX-SDK 17.0.1

•  A description of the additional report of your choice you ran in part A3f
	Reports:	Additional reports for users, countries, customers, and first level divisions are available.  Each has an associated table with appointments, customers, or divisions.



•  the MySQL Connector driver version number, including the update number (e.g., mysql-connector-java-8.1.23)
	SQL connector:	mysql-connector-java-8.0.282


•  directions for how to run the program
	Directions:  		
		Log In: 	This page is available in English and French.  It contains the current system time and ZoneID.
				UserName : test  or  admin
				Password : test  or  admin

		Navigation:	The navigation bar at the top of each page allows users to navigate to the main pages of the program.
				At the bottom of each page their are Log Out and Exit buttons
				Log out will disconnect from the database, reset the active user, and navigate to the Log In screen.  Exit will exit the program.
	
		HomePage:	The Welcome back message will update with the username of whichever user is currently logged in.
				The Textbox shows the time and date of the next appointment, and reads "you have no future appointments" 
				if the current user has no scheduled appointments in the future.
				The button below the Textbox takes the user to the details of the next appointment.
				If an appointment for the current user is within 15 minutes of the current time an alert will show on the screen.

		Your Profile: 	This page shows the details for the active user and all of the associated appointments.
				The user information section is non-editable 
				The Associated appointments table will allow the user to add, edit, view, or delete associated appointments.

		Appointment Manager:
				This page starts in 'Month View'.  The entire page is dominated by a table of appointments
				From month view you can generate a monthly report of all appointments by selecting the "Generate Report" button.
				The page includes a label that changes to identify the month that is currently in view.
				Navigating the appointments can be done through the 'Previous' and 'Next' buttons just above the Appointments table.
				The user can switch to week view by selecting the 'Week View' radio button.  
				Reports are not generated weekly, so no report button is available.
				The page includes a label that will identify which week of the month is currently in view.
				NOTE: Each week begins on Sunday, it is possible for the first or last days of a month to fall in the first or last week of another month.
				The User has the ability to Add, Edit, View, or Delete an appointment from this page.
			Add Appointment:
				This button navigates to a blank Appointment detail page. 
			Edit Appointment:
				This button navigates to a pre-populated Appointment details page. 
			View Details:
				This button navigates to a pre-populated Appointment details page.
			Delete Appointment:
				This button allows a user to delete the selected appointment from the system and database.

		Appointment Details:
				This is the largest and most complex page in the system.  It features all of the information from an appointment,
				gives the user access to multiple reports, checks for valid entries, and converts time in real time via viewable labels.
				To generate a Customer, User, or Contact report the user can click on the corresponding report button to the right side 
				of the page.
				In order to make changes to the form, the user must click the 'Add/Edit' button.
				Auto generated fields include: Create Date, Created By, Last Update Date, Last Updated By, and Appointment ID.  These fields are disabled. 
				Start date must be chosen in order to determine what times are available in local time for an addition or change to an appointment.
				Start time will determine what times are available in the end time combo box (real time check for valid times).
				Choosing a new start date/time will nullify any previously entered end time and re-populate the end time options.
				Customers, Users, and Contacts available for an appointment are found in comboBoxes to the right of the page.
				Time conversion to the company HQ timezone is viewable via 2 labels in the middle of the page.
			Add/Edit:
				This button allows the user to make changes to an existing appointment, or add a new appointment.
			Clear All:
				This button clears all fields in the appointment details page.
			Delete:	This button deletes the current appointment if it exists.  
			Save:	This button Saves all changed information, if no changes were made an alert will trigger.
			Cancel:	This button will return the user to disregard changes and go back to the view only details page.
			Customer Report:
				This button will take the user to the details of the currently selected customer.  See CustomerDetails.
			User Report:
				This button will take the user to the details of the currently selected user. 
				The User Details Page will give all available information about the User and allow addition and editing of that user's associated appointments.
			Contact Report:
				This button will navigate the user to the selected contact details page.
				The Contact details page allows the user to add, edit, view and delete associated contact appointments.

		Customer Manager:
				This page is dominated by a single table of all customers.  the list can be sorted by ID, name, phone, or division.
				The table has associated buttons that allow a user to add, edit, view, or delete customers. 
			Add: 	The Add Customer button directs the user to a blank customer editor page.  See Customer Editor.
			Edit:	The Edit button directs the user to a pre- populated customer editor page.
			View Details:
				the View Details button sends the user to a pre-populated customer details screen.
			Delete: Allows the user to delete the selected customer
				NOTE: The Delete Customer function asks and executes an associated appointment purge before deleting the customer.

		Customer Details:
				The Customer details page gives all of the database information about the customer.  
				The table of associated appointments allows the user to manipulate the customer's associated appointments.
				It allows the user to select reports for the country and first level division associated with the customer.
			Country Details Report:
				The Country Details show all database information about the country and allows the user to view all first level divisions associated with the country.
			Division Details Report:
				The Division Details shows all database information about the division and gives the user the ability to add/edit/delete/view all associated customers.
				
		Customer Editor:
				This page starts is seperated into 2 blocks the right block will either show the pre-populated customer to edit or an empty preview box.
				The Preview/Current box will update as the user selects and changes information.
				The left box starts with just one combo box and says "Start Here".  
				The user will select a country, then a division.
				Once the selections are made the user will be asked to provide an address.  If editing an existing customer, the user may choose the 'Keep current address' button to move on without making changes.
				Once finished press the next button to continue.
				NOTE: The Address is checked for having correct format  123 Main Street, Locality, City
				The postal code section allows the user to keep current data if editing an existing customer.  
				Once finished press the Next button to continue.
				NOTE: The Postal code is checked for correct formatting based on the country selected.
				The phone number is not checked for validity.  Press the next button to continue.
				The final item to edit is the customer Name.  
				NOTE: The only optional field in this form is the Locality field, the program will notallow the user to continue if they leave the fields blank.
				After the name is entered the user has the ability to save changes or add the new customer.
			Start Over:
				All changes made will be nullified and the user can start from the beginning.
			Save:	This button will save a new customer or make changes to an existing one.
			Cancel:	This button will return the user to the Home page.


	END.
