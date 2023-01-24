import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends oScanner{
	
	public static void main(String[] args) {
		callHomePage(); // Calls the home page function
	}
	
	static String generateRandomPassword() { // Generates a random password
		Random random = new Random();
		int passwordLength = random.nextInt(14, 16);
		char letters[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' ,'k', 'l', 'm', 'n', 'o', 'p', 'q' , 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+'};
		String generatedPassword = "";
		
		for (int i = 0; i <= passwordLength; i++) {
			generatedPassword += letters[random.nextInt(letters.length)]; // Generates random char from array
		}
		
		return generatedPassword; // Returns the random generated password
	}
	
	static boolean savePassword() { // Save passwords
		String websiteName = null;
		String websitePassword = null;
		
		System.out.println("Enter website name: ");
		if (sc.hasNextLine()) {
			websiteName = sc.nextLine(); // Get website name
			System.out.println("Enter password for the website: ");
			
			if (sc.hasNextLine()) {
				websitePassword = sc.nextLine(); // Get website password
			} else {
				System.out.println("ioException: " + sc.ioException());
				exitProgram();
			}
		} else {
			System.out.println("Scanner error: " + sc.ioException());
			
			exitProgram();
		}
		
		try {
			File file = new File(websiteName + ".txt");
			if (file.createNewFile()) { // Check if the file is already existing
				try {
					FileWriter fileWriter = new FileWriter(websiteName + ".txt");
					fileWriter.write(websitePassword); // Write password into file
					fileWriter.close();
					System.out.println("Password saved successfully!");
				} catch (IOException e) {
					System.out.print("Can't save password... ioException: " );
					e.printStackTrace();
					
					exitProgram();
					return false;
				}
				
				return true;
			} else {
				System.out.println("Password already exists"); // File already existing
				
				return false;
			}
		} catch (IOException e) {
			System.out.print("Can't save password... ioException: " );
			e.printStackTrace();
			
			exitProgram();
			return false;
		}
	}
	
	static boolean findPassword() { // Find existing password
		String websiteName;
		String websitePassword = "";
		
		System.out.println("Enter website name: ");
		if (sc.hasNextLine()) {
			websiteName = sc.nextLine(); // Get website name
			File file = new File(websiteName + ".txt");
			
			if (file.canRead()) { // Check if file exists & can read
				try {
					Scanner fileSc = new Scanner(file);
					
					while (fileSc.hasNextLine()) {
						websitePassword = fileSc.nextLine(); // Read file
					}
					
					System.out.println(websiteName + " password: " + websitePassword);
					
					fileSc.close();
					return true;
				} catch (FileNotFoundException e) {
					System.out.println("FindNotFoundException: ");
					e.printStackTrace();
					
					exitProgram();
					return false;
				}
			} else {
				System.out.println("Password doesn't exist...");
				
				return false;
			}
		} else {
			System.out.println("Scanner error: " + sc.ioException());
			
			exitProgram();
			return false;
		}
	}
	
	static boolean updatePassword() { // Update existing password
		String websiteName;
		String currentWebsitePassword = "";
		String updatedWebsitePassword = "";
		
		System.out.println("Enter website name: ");
		if (sc.hasNextLine()) {
			websiteName = sc.nextLine(); // Get website name
			File file = new File(websiteName + ".txt");
			
			if (file.canRead()) { // Check if file exists & can read
				try {
					Scanner fileSc = new Scanner(file);
					
					while (fileSc.hasNextLine()) {
						currentWebsitePassword = fileSc.nextLine(); // Get current website password
					}
					
					System.out.println("Current " + websiteName + " password: " + currentWebsitePassword);
					
					fileSc.close();
					
					System.out.println("Enter password to update: ");
					if (sc.hasNextLine()) {
						updatedWebsitePassword = sc.nextLine(); // Get new website password
					}
					
					if (_updatePassword(websiteName, updatedWebsitePassword)) { // Update website password
						System.out.println("Password updated!");
					} else {
						System.out.println("Error while attenpting to update password");
						
						exitProgram();
						return false;
					}
				} catch (FileNotFoundException e) {
					System.out.println("FindNotFoundException: ");
					e.printStackTrace();
					
					exitProgram();
					return false;
				}
			} else {
				System.out.println("Password doesn't exist...");
				
				return false;
			}
		} else {
			System.out.println("Scanner error: " + sc.ioException());
			exitProgram();
			return false;
		}
		
		return false;
	}
	
	static boolean _updatePassword(String websiteName, String updatedWebsitePassword) {
		File file = new File(websiteName + ".txt");
		
		if (file.canWrite()) { // Check if file exists and can write
			try {
				FileWriter fileWriter = new FileWriter(file);
				
				fileWriter.write(updatedWebsitePassword); // Update password
				
				fileWriter.close();
				return true;
			} catch (IOException e) {
				System.out.println("Cannot update password... IOException: ");
				e.printStackTrace();
				
				exitProgram();
				return false;
			}
		} else {
			System.out.println("Password doesn't exist...");
			
			return false;
		}
	}
	
	static void callHomePage() {
		int option = getOption(); // Gets user option
		
		switch (option) {
		case 1:
			// Save new password
			savePassword();
			
			sleep(1000);
			
			callHomePage();
			break;
		
		case 2:
			// Find existing password
			findPassword();
			
			sleep(1000);
			
			callHomePage();
			
			break;
			
		case 3:
			// Update existing password
			updatePassword();

			sleep(1000);
			
			callHomePage();
			break;
			
		case 4:
			// Generate random password
			String randomPassword = generateRandomPassword();
			
			System.out.println("Generated password: " + randomPassword);

			sleep(1000);
			
			callHomePage();
			break;
			
		case 5:
			// Exit program
			exitProgram();
			
			break;
		}
	}
	
	static int getOption() {
		int option = 0;
		String homePageOptions[] = {
				"1. Save Password",
				"2. Find Password",
				"3. Update password",
				"4. Generate Random Password",
				"5. Exit"
		};
		
		for (String s : homePageOptions) {
			System.out.println(s); // Print options
		}
		
		System.out.println("Enter option: ");
		if (sc.hasNextInt()) {
			option = sc.nextInt(); // Gets user option
			sc.nextLine();
			if (isValidOption(option)) { // Checks if option is valid
				return option;
			} else {
				invalidOption(); // Option not valid
				
				getOption();
			}
		} else {
			System.out.println("Scanner error: " + sc.ioException());
			
			exitProgram();
		}
		return 0;
	}
	
	static boolean isValidOption(int option) {
		if (option == 1 || option == 2 || option == 3 || option == 4 || option == 5) {
			return true;
		} else {
			return false;
		}
	}
	
	static void invalidOption() {
		System.out.println("Invalid option!");
		System.out.println("Please enter valid option.");
		System.out.println("3..");
		sleep(1000);
		System.out.println("2..");
		sleep(1000);
		System.out.println("1..");
		sleep(1000);
	}
	
	static void sleep(int millisec) {
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static void exitProgram() {
		sc.close();
		System.exit(0);
	}

}
