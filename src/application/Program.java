package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.entities.Product;

public class Program {

	public static void main(String[] args) {
		
		List<Product> products = new ArrayList<>();
		String path = "C:\\Users\\lucas\\Desktop\\Ws-Eclipse\\csv_file\\products.csv";
		String outputPath = "C:\\Users\\lucas\\Desktop\\Ws-Eclipse\\csv_file\\out\\summary.csv";
		
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
		
		// create output directory
		boolean success = new File("C:\\Users\\lucas\\Desktop\\Ws-Eclipse\\csv_file\\out").mkdir();
		System.out.println("Directory created successfully: " + success);
		
		// write
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {
			for (Product p: products) {
				bw.write(p.getName() + "," + String.format("%.2f", p.total()));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
