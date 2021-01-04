package com.simplilearn.file_handling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandling {
	private String file;

	FileHandling(String file) {
		this.file = file;
	}

	// read the contacts from the file and print them
	void printContacts() throws IOException {
		Path path = Paths.get(this.file);
		try (InputStream in = Files.newInputStream(path);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			String line;
			String name, number;
			int count = 1;
			while ((line = reader.readLine()) != null) {
				String[] nameNumber = line.split(",");
				name = nameNumber[0];
				number = nameNumber[1];
				System.out.printf("%d. %s: %s%n", count, name, number);
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// update contact
	void updateContact(String[] oldContact, String[] newContact) throws IOException {
		File fileToBeModified = new File(this.file);
		String newContent = "";
		BufferedReader reader = null;
		FileWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(fileToBeModified));
			String line;
			while ((line = reader.readLine()) != null) {
				// if the name matches, replace the old contact with new contact
				if (line.split(",")[0].equals(oldContact[0])) {
					newContent += (newContact[0] + "," + newContact[1]) + System.lineSeparator();
					continue;
				}
				newContent += line + System.lineSeparator();
			}
			writer = new FileWriter(fileToBeModified);
			writer.write(newContent);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			reader.close();
			writer.close();
		}
	}

	// append a contact to the end of the contact list
	void addContact(String[] contact) throws IOException {
		File fileToBeAppended = new File(this.file);
		try (BufferedReader reader = new BufferedReader(new FileReader(fileToBeAppended));
				FileWriter writer = new FileWriter(fileToBeAppended, true)) {
			String line = contact[0] + "," + contact[1];
			writer.write(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		String file = "src/main/java/contacts.txt";

		/**************** Read and Print contacts list **********/
		System.out.println("Printing the contacts:\n");
		FileHandling fileHandling = new FileHandling(file);
		fileHandling.printContacts();
		/**************** Update a contact with a new one ******/
		String[] oldContact = { "Danh", "444-444-4444" };
		String[] newContact = { "Tuyet", "123-456-7890" };
		fileHandling.updateContact(oldContact, newContact);
		System.out.println("\nAfter Update: Danh to Tuyet\n");
		fileHandling.printContacts();
		/**************** Append a contact *********************/
		String[] contactToBeAppended = { "Reviewer", "000-000-0000" };
		fileHandling.addContact(contactToBeAppended);
		System.out.println("\nAfter Append: Reviewer\n");
		fileHandling.printContacts();
	}
}
