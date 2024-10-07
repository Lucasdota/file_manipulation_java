package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.entities.Product;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);		
		List<Product> products = new ArrayList<>();
		
		System.out.println("Enter file path: ");
		String path = sc.nextLine();
		
		// get parent folder
		File sourceFile = new File(path);
		String sourceFolderStr = sourceFile.getParent();
				
		// create output directory
		boolean success = new File(sourceFolderStr + "\\out").mkdir();
		System.out.println("Directory created successfully: " + success);
		String targetFileStr = sourceFolderStr + "\\out\\summary.csv";
		
		// read
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			while (line != null) {
				String[] splitItens = line.split(",");
				String[] splitName = splitItens[0].split("\"");
				String[] splitQuantity = splitItens[2].split("\"");
				String name = splitName[1];
				Double price = Double.parseDouble(splitItens[1]);
				Integer quantity = Integer.parseInt(splitQuantity[0]);
				Product p = new Product(name, price, quantity);
				products.add(p);
				line = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}		
		
		// write
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))) {
			for (Product p: products) {
				bw.write(p.getName() + "," + String.format("%.2f", p.total()));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
