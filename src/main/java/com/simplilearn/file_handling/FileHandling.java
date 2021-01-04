package com.simplilearn.file_handling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandling {
	private Path file;

	FileHandling(Path file) {
		this.file = file;
	}
	
	// read the contacts from the file and print them
	void printContacts() throws IOException{
		try (InputStream in = Files.newInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			String line;
			String name, number;
			while ((line = reader.readLine()) != null) {
				name = line.split(",")[0];
				number = line.split(",")[1];
				System.out.printf("%s: %s%n", name, number);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		String file = "src/main/java/contacts.txt";
		Path path = Paths.get(file);
		
		FileHandling fileHandling = new FileHandling(path);
		fileHandling.printContacts();
	}
}
